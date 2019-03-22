#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int* fun2()
{
	int *p = (int*)malloc(sizeof(int));
	return p;
}

int fun(int a)
{
	printf("a=%d\n",a);
	return 0;
}

void testFPointer()
{
	//定义函数指针有三种方式
	//1先定义函数列席,根据类型定义指针变量
	typedef int Fun(int a);
	Fun *p1 = NULL;
	p1 = fun;
	p1(5);

	//2先定义函数指针类型,根据类型定义指针变量(常用)
	typedef int(*FUN)(int a);
	FUN p2 = fun;
	p2(7);
	//3直接电仪函数指针(常用)
	int(*p3)(int a) = fun;
	p3(9);
	int(*p4)(int a);
	p4 = fun;
	p4(11);
}

void plus()
{
	printf("plus111\n");
}

void minus()
{
	printf("minus222\n");
}

void multi()
{
	printf("multi333\n");
}

void divide()
{
	printf("divide444\n");
}

void myexit()
{
	exit(0);
}

int main()
{
	//testFPointer();

	void(*fun3)() = plus;
	fun3();
	void(*fun4[5])() = { plus,minus,multi,divide,myexit };
	char *buf[] = { "plus","minus","multi","divide","myexit" };
	char cmd[100];
	int i = 0;
	while (1)
	{
		printf("请输入指令:");
		scanf("%s", cmd);
			for (i = 0; i < 5; i++)
			{
				if (strcmp(cmd, buf[i]) == 0)
				{
					fun4[i]();
					break;
				}
			}
	}

	return 0;
}