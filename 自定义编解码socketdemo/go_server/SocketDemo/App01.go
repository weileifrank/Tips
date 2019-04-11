package main

import (
	"fmt"
	"net"
)

func main01() {
	listener, err := net.Listen("tcp", ":8000")
	if err != nil {
		fmt.Println("net.Listen err:", err)
		return
	}
	defer listener.Close()

	// 监听客户端连接请求
	for {
		fmt.Println("服务器等待客户端连接...")
		conn, err := listener.Accept()
		if err != nil {
			fmt.Println("listener.Accept err:", err)
			return
		}
		// 具体完成服务器和客户端的数据通信
		go HandlerConnect01(conn)
	}
}

func HandlerConnect01(conn net.Conn) {
	defer conn.Close()
	// 获取连接的客户端 Addr
	addr := conn.RemoteAddr()
	fmt.Println(addr, "客户端成功连接！")
	// 循环读取客户端发送数据
	buf := make([]byte, 1024*1024*2)
	for {
		n, err := conn.Read(buf)
		if "exit\n" == string(buf[:n]) || "exit\r\n" == string(buf[:n]) {
			fmt.Println("服务器接收的客户端退出请求，服务器关闭")
			return
		}
		if n == 0 {
			fmt.Println("服务器检测到客户端已关闭，断开连接！！！")
			return
		}
		if err != nil {
			fmt.Println("conn.Read err:", err)
			return
		}
		fmt.Println("服务器读到数据：", string(buf[:n])) // 使用数据

		s:=``
		conn.Write([]byte(s + "\n"))
	}
}
