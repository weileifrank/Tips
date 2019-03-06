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

  