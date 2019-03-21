app:main.o add.o minus.o multi.o divide.o
	gcc *.o -o app
main.o:main.c
	gcc main.c -Iinclude -c
add.o:src/add.c
	gcc src/add.c -Iinclude -c
minus.o:src/minus.c
	gcc src/minus.c -Iinclude -c
multi.o:src/multi.c
	gcc src/multi.c -Iinclude -c
divide.o:src/divide.c
	gcc src/divide.c -Iinclude -c
