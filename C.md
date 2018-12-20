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
