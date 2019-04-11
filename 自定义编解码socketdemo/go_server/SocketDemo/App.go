package main

import (
	"encoding/binary"
	"fmt"
	"image"
	"image/color"
	"image/draw"
	"image/png"
	"io/ioutil"
	"log"
	"math/rand"
	"net"
	"os"
	"strconv"
	"strings"
	"time"
)

// 创建用户结构体类型！
type Client struct {
	C    chan string
	Name string
	Addr string
}

// 创建全局map，存储在线用户
var onlineMap = make(map[string]Client)

// 创建全局 channel 传递用户消息。
var message = make(chan string)

func WriteMsgToClient(clnt Client, conn net.Conn) {
	rand.Seed(time.Now().UnixNano())
	for {
		time.Sleep(time.Millisecond * 50)
		fileName := strconv.FormatInt(time.Now().UnixNano(), 10) + ".png"
		r := rand.Intn(256)
		g := rand.Intn(256)
		b := rand.Intn(256)
		a := rand.Intn(256)
		//fmt.Println(r,g,b,a)
		rectImage := image.NewRGBA(image.Rect(0, 0, 1280, 800))
		green := color.RGBA{uint8(r),uint8(g),uint8(b), uint8(a)}
		draw.Draw(rectImage, rectImage.Bounds(), &image.Uniform{green}, image.ZP, draw.Src)
		file, err := os.Create(fileName)
		if err != nil {
			log.Fatalf("failed create file: %s", err)
		}
		png.Encode(file, rectImage)
		bytes := make([]byte, 0)
		image, _ := ioutil.ReadFile(fileName)
		//dataString := base64.StdEncoding.EncodeToString(image)
		lengthSlice := IntToBytes(len(image))
		fmt.Println("lengthSlice:",lengthSlice)
		bytes=append(bytes,lengthSlice...)
		bytes=append(bytes,0,1)
		bytes=append(bytes,image...)
		fmt.Println("event:",0,1)
		fmt.Println("image:",image)
		conn.Write(bytes)
		//conn.Write([]byte(dataString + "\n"))
		file.Close()
		err = os.Remove(fileName)
		if err != nil {
			fmt.Println(err)
		}
	}
	// 监听 用户自带Channel 上是否有消息。
	//for msg := range clnt.C {
	//	//conn.Write([]byte(msg + "\n"))
	//	fmt.Println(msg)
	//	time.Sleep(time.Millisecond * 1000)
	//	fmt.Println(time.Now().UnixNano())
	//	fileName := strconv.FormatInt(time.Now().UnixNano(), 10) + ".png"
	//	i := rand.Intn(256)
	//	rectImage := image.NewRGBA(image.Rect(0, 0, 200, 200))
	//	green := color.RGBA{0, 100, 0, uint8(i)}
	//	draw.Draw(rectImage, rectImage.Bounds(), &image.Uniform{green}, image.ZP, draw.Src)
	//	file, err := os.Create(fileName)
	//	if err != nil {
	//		log.Fatalf("failed create file: %s", err)
	//	}
	//	png.Encode(file, rectImage)
	//	image, _ := ioutil.ReadFile(fileName)
	//	dataString := base64.StdEncoding.EncodeToString(image)
	//	conn.Write([]byte(dataString+"\n"))
	//	fmt.Println(dataString)
	//	file.Close()
	//	err = os.Remove(fileName)
	//	if err != nil {
	//		fmt.Println(err)
	//	}
	//}
}

func IntToBytes(i int) []byte {
	var buf = make([]byte, 4)
	binary.BigEndian.PutUint32(buf, uint32(i))
	return buf
}


const (
	dx = 256
	dy = 256
)

func Pic(dx, dy int) [][]uint8 {
	pic := make([][]uint8, dx)
	for i := range pic {
		pic[i] = make([]uint8, dy)
		for j := range pic[i] {
			pic[i][j] = uint8(i * j % 255)
		}
	}
	return pic
}

func HandlerConnect(conn net.Conn) {
	defer conn.Close()
	// 创建channel 判断，用户是否活跃。
	hasData := make(chan bool)

	// 获取用户 网络地址 IP+port
	netAddr := conn.RemoteAddr().String()
	// 创建新连接用户的 结构体. 默认用户是 IP+port
	clnt := Client{make(chan string), netAddr, netAddr}

	// 将新连接用户，添加到在线用户map中. key: IP+port value：client
	onlineMap[netAddr] = clnt

	// 创建专门用来给当前 用户发送消息的 go 程
	go WriteMsgToClient(clnt, conn)

	// 发送 用户上线消息到 全局channel 中
	//message <- "[" + netAddr + "]" + clnt.Name + "login"
	message <- "login"

	// 创建一个 channel ， 用来判断用退出状态
	isQuit := make(chan bool)

	// 创建一个匿名 go 程， 专门处理用户发送的消息。
	go func() {
		buf := make([]byte, 4096)
		for {
			n, err := conn.Read(buf)
			if n == 0 {
				isQuit <- true
				fmt.Printf("检测到客户端:%s退出\n", clnt.Name)
				return
			}
			if err != nil {
				fmt.Println("conn.Read err:", err)
				return
			}
			// 将读到的用户消息，保存到msg中，string 类型
			//fmt.Println(buf[:n-1])

			msg := string(buf[:n-1])
			split := strings.Split(msg, "\n")
			//fmt.Println("len(split):",len(split))
			for _, item := range split {
				msg = item
				// 提取在线用户列表
				if msg == "who" && len(msg) == 3 {
					conn.Write([]byte("online user list:\n"))
					// 遍历当前 map ，获取在线用户
					for _, user := range onlineMap {
						userInfo := user.Addr + ":" + user.Name + "\n"
						conn.Write([]byte(userInfo))
					}
					// 判断用户发送了 改名 命令
				} else if len(msg) >= 8 && msg[:6] == "rename" { // rename|
					newName := strings.Split(msg, "|")[1] // msg[8:]
					clnt.Name = newName                   // 修改结构体成员name
					onlineMap[netAddr] = clnt             // 更新 onlineMap
					conn.Write([]byte("rename successful\n"))
				} else {
					// 将读到的用户消息，写入到message中。
					fmt.Println("收到消息:", msg)
					message <- msg
				}
				hasData <- true
			}
		}
	}()

	// 保证 不退出
	for {
		// 监听 channel 上的数据流动
		select {
		case <-isQuit:
			delete(onlineMap, clnt.Addr) // 将用户从 online移除
			message <- "logout"          // 写入用户退出消息到全局channel
			return
		case <-hasData:
			// 什么都不做。 目的是重置 下面 case 的计时器。
		case <-time.After(time.Second * 30):
			delete(onlineMap, clnt.Addr) // 将用户从 online移除
			message <- "time out leaved" // 写入用户退出消息到全局channel
			return
		}
	}
}

func Manager() {
	// 初始化 onlineMap
	onlineMap = make(map[string]Client)
}

func main() {
	ip := getLocalIp()
	fmt.Println("ip:", ip)
	// 创建监听套接字
	listener, err := net.Listen("tcp", ip+":8000")
	if err != nil {
		fmt.Println("Listen err", err)
		return
	}
	defer listener.Close()
	// 监听全局channel 中是否有数据, 有数据存储至 msg， 无数据阻塞。
	go func() {
		for {
			msg := <-message
			// 循环发送消息给 所有在线用户。要想执行，必须 msg := <-message 执行完， 解除阻塞。
			for _, clnt := range onlineMap {
				clnt.C <- msg
			}
		}
	}()
	// 循环监听客户端连接请求
	for {
		conn, err := listener.Accept()
		if err != nil {
			fmt.Println("Accept err", err)
			continue
		}
		// 启动go程处理客户端数据请求
		go HandlerConnect(conn)
	}
}

func getLocalIp() (string) {
	ip := ""
	netInterfaces, err := net.Interfaces()
	if err != nil {
		fmt.Println("net.Interfaces failed, err:", err.Error())
		return ip
	}
	for i := 0; i < len(netInterfaces); i++ {
		if (netInterfaces[i].Flags & net.FlagUp) != 0 {
			addrs, _ := netInterfaces[i].Addrs()
			for _, address := range addrs {
				if ipnet, ok := address.(*net.IPNet); ok && !ipnet.IP.IsLoopback() {
					if ipnet.IP.To4() != nil {
						ip = ipnet.IP.String()
						return ip
					}
				}
			}
		}
	}
	return ip;
}
