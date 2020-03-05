

# 什么是shell

- shell的定义: 在计算机科学中，Shell就是一个**命令解释器。**

- shell是位于操作系统和应用程序之间，是他们二者最主要的接口，shell负责把应用程序的输入命令信息解释给操作系统，将操作系统指令处理后的结果解释给应用程序。

- shell的分类

  - **图形界面shell**:图形界面shell就是我们常说的桌面
  - **命令行式shell**
    - windows系统: cmd.exe 命令提示字符
    - linux系统: sh / csh / ksh / **bash** / **...**

- ubuntu下shell类型

  - 查看当前系统的shell类型

    ```
    echo $SHELL
    ```

  - 查看当前系统环境支持的shell

    ```
    root@iZuf6ggrfujyixxwpqrglbZ:~# cat /etc/shells
    # /etc/shells: valid login shells
    /bin/sh
    /bin/dash
    /bin/bash
    /bin/rbash
    ```

  

# shell基础知识梳理

1. ### **变量定义**

   - **普通变量**

     ```
     方式一：
       变量名=变量值 重点：变量值必须是一个整体，中间没有特殊字符
     方式二：
       变量名='变量值' 重点：不解析变量值的内容
     方式三：
       变量名="变量值" 重点：解析变量值的内容
     
     注意:变量默认都是字符串,习惯数字变量不加引号，其他默认加双引号
     ```

   - **命令变量**

     ```
     定义方式一： 变量名=`命令` 注意：`是反引号
     定义方式二： 变量名=$(命令)
     ```

   - 查看变量

     ```
     方式一： $变量名
     
     方式二： "$变量名"
     
     方式三： ${变量名}
     
     方式四：标准使用方式 "${变量名}"
     
     取消变量： unset 变量名
     ```

   - ### 变量分类

     **shell 中的变量:** 本地变量、全局变量

     1. #### 本地变量

        ```
        本地变量是：在当前系统的某个环境下才能生效的变量，作用范围小。
        ```

        

     2. #### 全局变量

        ```
        全局变量是：在当前系统的所有环境下都能生效的变量。
        ```

     3. #### **定义全局变量**

        ```
        方法一：
        
        变量名=值
        
        export 变量
        
        方法二：(最常用)
        
        export 变量名=值
        
        只对当前shell环境及子shell环境有效，shell退出后全局变量消失
        ```

        ```
        root@iZuf6ggrfujyixxwpqrglbZ:~/shdir# cat bupin.sh  #定义bupin.sh脚本文件
        #!/bin/bash
        
        echo $name
        
        root@iZuf6ggrfujyixxwpqrglbZ:~/shdir# name="frank" #定义局部变量
        root@iZuf6ggrfujyixxwpqrglbZ:~/shdir# echo $name   #本shell可以访问
        frank
        root@iZuf6ggrfujyixxwpqrglbZ:~/shdir# . bupin.sh  
        frank
        root@iZuf6ggrfujyixxwpqrglbZ:~/shdir# bash bupin.sh #重启的shell不能访问
        
        root@iZuf6ggrfujyixxwpqrglbZ:~/shdir# export name  #声明为全局变量
        root@iZuf6ggrfujyixxwpqrglbZ:~/shdir# bash bupin.sh  #可以访问全局变量
        frank
        
        ```

     4. **shell执行的方式**,以bupin.sh为例

        ```
        方法一:  . bupin.sh   		 #不启动子进程
        方法二:  source bupin.sh     #不启动子进程
        
        方法三:  ./bupin.sh   		 #启动子进程
        方法四:  bash bupin.sh       #启动子进程  推荐
        
        ```

     5. shell内置变量

        ```
        $0   获取当前执行的shell脚本文件名
        $$   获取执行shell脚本的进程号
        $n   获取当前执行的shell脚本的第n个参数值，n=1..9，当n为0时表示脚本的文件名，如	  果n大于9就要用大括号括起来${10}
        $#   获取当前shell命令行中参数的总个数
        $?   获取执行上一个指令的返回值（0为成功，非0为失败)
        ```

        ```
        #查看脚本test.sh的内容
        root@iZuf6ggrfujyixxwpqrglbZ:~/shdir# cat test.sh 
        #! /bin/bash
        echo "当前进程号:$$"
        echo "参数个数:$#"
        echo "当前脚本名字:$0"
        echo "第1个参数:$1"
        echo "第2个参数:$2"
        echo "第3个参数:$3"
        echo "第4个参数:$4"
        echo "第5个参数:$5"
        
        #通过bash执行脚本并传参
        root@iZuf6ggrfujyixxwpqrglbZ:~/shdir# bash test.sh 123 "abc" false "ad16"
        当前进程号:21489
        参数个数:4
        当前脚本名字:test.sh
        第1个参数:123
        第2个参数:abc
        第3个参数:false
        第4个参数:ad16
        第5个参数:
        
        #查看上次的执行结果
        root@iZuf6ggrfujyixxwpqrglbZ:~/shdir# echo $?
        0
        ```



2. 字符串和默认值

   - #### **字符串精确截取**  **格式：${变量名:起始位置:截取长度}**

     ```
     ${file:0:5}       从第1个字符开始，截取5个字符
     ${file:5:5}       从第6个字符开始，截取5个字符
     ${file:0-6:3}     从倒数第6个字符开始，截取之后的3个字符
     ```

     ```
     root@iZuf6ggrfujyixxwpqrglbZ:~/shdir# name="shanghaibupin"
     root@iZuf6ggrfujyixxwpqrglbZ:~/shdir# echo ${name:0:5}
     shang
     ```

   - #### **默认值**

     - 第一种:

       - 变量a如果有内容，那么就输出a的变量值

         变量a如果没有内容，那么就输出默认的内容

       - 格式：

          ${变量名:-默认值}

       - 套餐示例：

          如果我输入的参数为空，那么输出内容是 "您选择的套餐是： 套餐 1"

          如果我输入的参数为n，那么输出内容是 "您选择的套餐是： 套餐 n"

            ```
          root@iZuf6ggrfujyixxwpqrglbZ:~/shdir# cat defaultdemo1.sh 
          #!/bin/bash
          a="$1"
          echo "你选择的套餐是:套餐${a:-1}"
            ```
       
       root@iZuf6ggrfujyixxwpqrglbZ:~/shdir# bash defaultdemo1.sh 
       你选择的套餐是:套餐1
       
       root@iZuf6ggrfujyixxwpqrglbZ:~/shdir# bash defaultdemo1.sh "haha"
       你选择的套餐是:套餐haha
            ```

3. ### 测试语句

   ```
   root@iZuf6ggrfujyixxwpqrglbZ:~/shdir# cat demo.sh 
   #! /bin/bash -v
   count=3
   echo "please input password"
   read psd
   #[  ]
   while [ $psd != "frank" -a $count -gt 0 ];do
   #计算表达式
   count=$[count+1]
   echo "password incorrect please try again"
   read psd
   done
   
   ```

   

   

# 常见命令

- find

  ```
  #按名字查找
  find ./ -name "HomeAcitivity"
  
  #按类型查找 f/d/l/b/c/s/p
  find ./ -type d
  
  #按照大小查找
  #M大写  查找大于3M小于8M的文件
  find ~/ -size +3M -size -8M
  #k小写 查找大于3k小于8k
  find ~/ -size +3k -size -8k
  
  
  ```

- grep

  ```
  #按照文本内容查找文件
  
  grep -rn "/bin/bash" *
  ```

  - 查看某个进程是否active或者某个端口号是否被占用

    ```java
    //查看进程
    ps -aux | grep mysql
    ps -ef | grep mysql
        
    //查看端口
    netstat -anp | grep 3306
    
    ```

    

- find和grep命令结合

  ```shell
  find . -name "*.c" | xargs grep -n "main"
  ```

- scp

  ```
  #本地文件传输到远程home目录下,~带包home目录,然后提示输入密码即可
  scp a.txt root@47.100.3.249:~
  
  #把远程文件拷贝到本地,就是把上面的参数对调一下即可
  scp root@47.100.3.249:~/a.txt a.txt
  ```

- ssh-copy-id  多台机器可以实现免密登陆