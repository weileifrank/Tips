### JVM

- JVM常用命令

  - `java`命令

    ```
    //查看JVM初始化参数
    [root@iZbp1btxfzq77txs3ttq5lZ ~]# java -XX:+PrintFlagsInitial
    
    
    
    //查看jvm设定的参数
    [root@iZbp1btxfzq77txs3ttq5lZ ~]# java -XX:+PrintFlagsFinal
    
    
    //查看jvm使用的垃圾回收器
    [root@iZbp1btxfzq77txs3ttq5lZ ~]# java -XX:+PrintCommandLineFlags
    -XX:InitialHeapSize=125798016 -XX:MaxHeapSize=2012768256 -XX:+PrintCommandLineFlags -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseParallelGC
    ```

    

  - `jps`查看java项目进程

    ```
    jps -l
    ```

  - `jinfo`查看对应的信息

    ```java
    //查看是否启用了UseSerialGC这个参数
    [root@iZbp1btxfzq77txs3ttq5lZ ~]# jinfo -flag UseSerialGC 18948
    -XX:-UseSerialGC
    
    //查看全部使用的参数
    [root@iZbp1btxfzq77txs3ttq5lZ ~]# jinfo -flags 18948
    Attaching to process ID 18948, please wait...
    Debugger attached successfully.
    Server compiler detected.
    JVM version is 25.181-b13
    Non-default VM flags: -XX:CICompilerCount=2 -XX:InitialHeapSize=125829120 -XX:MaxHeapSize=2013265920 -XX:MaxNewSize=671088640 -XX:MinHeapDeltaBytes=524288 -XX:NewSize=41943040 -XX:OldSize=83886080 -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseParallelGC 
    Command line:  
        
        
    ```

    