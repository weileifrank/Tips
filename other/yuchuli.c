#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define PI 3.14
#define TEST(a,b) (a)*(b)

#define MAX2(a,b) (a)>(b)?(a):(b)  //�궨���ҳ������������ֵ
#define MAX3(a,b,c) (a)>(MAX2(b,c))?(a):(MAX2(b,c))  //�궨���ҳ������������ֵ

void fun()
{
#define A 10   //�����˺궨��,����Ĵ��붼������,������ȫ�ֱ���
	int i = 20;
}

void test()
{
	int a = A;
	//a = i;  err���������  �궨������Ԥ�����ʱ��ִ�е�
}

#undef A  //ȡ���궨��

void met()
{
	//int b = A; //err �����Ѿ�ȡ���˺궨��
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
	//__FILE__��__LINE__��ϵͳ���õ�������,ǰ���Ǳ�ʾ���ڵ��ļ�·��,�����Ǳ�ʾ���ļ���һ��
	printf("filename=%s\nline=%d\n", __FILE__, __LINE__);

	return 0;
}