#obj=main.o add.o minus.o multi.o divide.o
target=app
src=$(wildcard ./*.c)
obj=$(patsubst ./%.c,./%.o,$(src))
CC = gcc
CPPFLAGS = -I
$(target):$(obj)
	$(CC) $(obj) -o $(target)
%.o:%.c
	$(CC) -c $< -o $@
.PHONY:clean
clean:
	rm $(obj) $(target) -f
hello:
	echo "hello makefile"
