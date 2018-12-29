# win10常见的命令

- 查找占用端口的进程,并杀死进程

  ```
  //查找进程
  $ netstat -ano | findstr 8000
    TCP    192.168.1.196:8000     0.0.0.0:0              LISTENING       4304
  
    
  // 干掉进程,进程号为4304
  // PID processid    指定要终止的进程的 PID
  // F 指定强制终止进程
  taskkill -PID 4304 -F
  ```
