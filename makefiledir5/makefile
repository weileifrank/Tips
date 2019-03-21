obj=main.o add.o minus.o multi.o divide.o
target=app
$(target):$(obj)
	gcc $(obj) -o $(target)
%.o:%.c
	gcc -c $< -o $@
