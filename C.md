# 开发工具

```
vs2017秘钥:
https://blog.csdn.net/mrobama/article/details/80493146

qt安装如下:
https://blog.csdn.net/xiongtiancheng/article/details/80036605
```



# sizeof关键字

- sizeof不是函数，所以不需要包含任何头文件，它的功能是计算一个数据类型的大小，单位为字节

- sizeof的返回值为size_t

- size_t类型在32位操作系统下是unsigned int，是一个无符号的整数

  ```
  #include <stdio.h>
  int main()
  {
  	int a;
  	int b = sizeof(a);
  	printf("b=%d\n", b);
  	size_t c = sizeof(a);
  	printf("c=%u\n", c);
  	system("pause");
  	return 0;
  }
  ```



# ffmpeg学习

- 参考资料

  ```java
  
  https://juejin.im/post/59b7d2ff5188257e671b71be
  
  http://blog.xigulu.com/2016/08/25/FFMPEG-command/
  
  https://github.com/tonydeng/fmj/blob/master/ffmpeg.md
  
  https://einverne.github.io/post/2015/12/ffmpeg-first.html
  
  https://blog.csdn.net/liangjiubujiu/article/details/80568065
  
  https://www.jianshu.com/p/aac3e2a209c3
  
  https://blog.csdn.net/xuyankuanrong/article/details/77527381
  
  
  ```

  

