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

  