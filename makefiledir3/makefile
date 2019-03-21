obj=main.o add.o minus.o multi.o divide.o
target=app
$(target):$(obj)
	gcc $(obj) -o $(target)
main.o:main.c
	gcc -c $< -Iinclude -o $@
%.o:src/%.c
	gcc -c $< -Iinclude -o $@
