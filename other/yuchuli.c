#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define PI 3.14
#define TEST(a,b) (a)*(b)

#define MAX2(a,b) (a)>(b)?(a):(b)  //宏定义找出两个数的最大值
#define MAX3(a,b,c) (a)>(MAX2(b,c))?(a):(MAX2(b,c))  //宏定义找出三个数的最大值

void fun()
{
#define A 10   //定义了宏定义,下面的代码都可以用,类似于全局变量
	int i = 20;
}

void test()
{
	int a = A;
	//a = i;  err作用域错误  宏定义是在预处理的时候执行的
}

#undef A  //取消宏定义

void met()
{
	//int b = A; //err 上面已经取消了宏定义
}

#define D1
#define TRUE 1

int main()
{
	int r = 10;
	printf("%lf\n", PI*r*r);

	int a = TEST(1, 2);
	printf("a=%d\n", a);

	int b = TEST(1+1, 2);
	printf("a=%d\n", b);

	int max2 = MAX2(10, 6);
	printf("max2=%d\n", max2);

	int max3 = MAX3(10, 6,20);
	printf("max3=%d\n", max3);

#ifdef D1
	printf("d111111111111\n");
#else
	printf("other222222222\n");
#endif

#if TRUE
	printf("true..........\n");
#else
	printf("false.............\n");
#endif

	printf("----------------------------\n");
	//__FILE__和__LINE__是系统内置的两个宏,前者是表示所在的文件路径,后者是表示该文件哪一行
	printf("filename=%s\nline=%d\n", __FILE__, __LINE__);

	return 0;
}