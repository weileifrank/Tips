obj=main.o add.o minus.o multi.o divide.o
target=app
$(target):$(obj)
	gcc $(obj) -o $(target)
main.o:main.c
	gcc $< -Iinclude -c
%.o:src/%.c
	gcc $< -Iinclude -c
