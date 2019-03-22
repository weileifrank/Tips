#!/bin/bash

str=$1

if [ $str == "frank" ];then
	echo "获取的名称为$str"
elif [ $str == "tom" ];then
	echo "it is tom"
else
	echo "其他"
fi
