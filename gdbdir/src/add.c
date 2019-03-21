#include <stdio.h>
#include "calc.h"

int add(int a,int b)
{
	int i = 0;
	for(i=0;i<20;i++)
	{
		int num = i;
		printf("num=%d\n",num);
	} 
	return a+b;
}
