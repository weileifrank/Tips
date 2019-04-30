# Android Gradle技巧

- 命令行示例解释

  ```
  mac:
  ./gradlew clean build --info > bugtags.log
  windows:
  gradlew.bat clean build --info > bugtags.log
  ```

  这个命令行的意思，是运行 clean 和 build 两个 gradle task，并且打开 info 参数使得输出更多的信息，最终把所有输出的信息，输出到项目根目录下的 bugtags.log 文件。用户把这个文件发给我，我根据这个输出文件，通常就能分析出问题所在。
  也可以执行如下,但会在控制台输入信息

  ```
  ./gradlew clean build --info
  ```

- help

  要知道都有哪些 gradle 命令运行的参数，可以使用help

  ```
  $ ./gradlew --help
  USAGE: gradlew [option...] [task...]
  ...
  ```

- 指定具体module执行命令

  AS 推荐的结构是 `multiple project` 结构，即一个 project 下，管理多个 module，如果每次都要 build 全部的 project 的话，有点浪费时间，则可以使用 `-p module` 参数，其中 module 是你要 build 的 module：

  ```
  $ ./gradlew -p app clean build
  
  或者执行:
  $ ./gradlew app:clean build
  ```

- 把命令集成到基本中,以app module为例,创建一个run.sh脚本,代码如下,然后在gitbash下执行 `./run.sh`

  ```
  #!/bin/sh
  ./gradlew -p app clean
  #./gradlew -p app uninstallDebug
  adb uninstall bupin.com.tmpdemo
  ./gradlew -p app installDebug
  adb shell am start -n bupin.com.tmpdemo/bupin.com.tmpdemo.MainActivity
  ```

- 常见的gradle命令

  ```
  //构建
  gradlew app:clean    //移除所有的编译输出文件，比如apk
  
  gradlew app:build   //构建 app module ，构建任务，相当于同时执行了check任务和assemble任务
  
  //检测
  gradlew app:check   //执行lint检测编译。
  
  //打包
  gradlew app:assemble //可以编译出release包和debug包，可以使用gradlew assembleRelease或者gradlew assembleDebug来单独编译一种包
  
  gradlew app:assembleRelease  //app module 打 release 包
  
  gradlew app:assembleDebug  //app module 打 debug 包
  
  //安装，卸载
  
  gradlew app:installDebug  //安装 app 的 debug 包到手机上
  
  gradlew app:uninstallDebug  //卸载手机上 app 的 debug 包
  
  gradlew app:uninstallRelease  //卸载手机上 app 的 release 包
  
  gradlew app:uninstallAll  //卸载手机上所有 app 的包
  ```

- 参考链接

  - https://blog.csdn.net/zhaoyanjun6/article/details/77678577
  - https://www.cnblogs.com/zhaoyanjun/p/7603640.html
  - https://blog.csdn.net/zhaoyanjun6/article/list/1     这位博主的博客可以一看
  - https://blog.csdn.net/iamzgx/article/list/1?t=1
  - https://www.jianshu.com/u/203b606b956c
  - https://www.jianshu.com/p/1274c1f1b6a4
  - https://www.jianshu.com/p/274c9d95cf76    Gradle之BuildConfig自定义常量
  - https://www.jianshu.com/p/bfa3bd3d8ed3?utm_campaign=maleskine&utm_content=note&utm_medium=seo_notes&utm_source=recommendation
  - https://www.cnblogs.com/startkey/p/10042194.html     系统app需要的配置
  - https://juejin.im/post/5be97882e51d4502df234ee4#heading-12

# ubuntu配置android开发环境

- 参考网址`https://qianngchn.github.io/wiki/8.html`

- 安装Launchpad PPA Repositories

  Launchpad PPA Repositories是很有用的非ubuntu官方的第三方个人资源库，可以很方便地安装第三方软件。1,2,3,10,14,18,22,23,28,34
  但是在运行add-apt-repository命令时，有时会提示命令不存在，这个时候直接apt-get add-apt-repository是不可以的！

  ```
  sudo apt-get install software-properties-common
  ```

- 配置jdk,然后`java -version`验证一下

  ```
  sudo add-apt-repository ppa:webupd8team/java
  sudo apt-get update
  sudo apt-get install oracle-java8-installer
  sudo apt-get install oracle-java8-set-default
  ```

- ### 安装 `Android SDK`

  下载地址：<http://developer.android.com/sdk/installing/studio.html>

  `android sdk` 工具包的一些命令行工具是基于`32`位系统的，在`64`为平台运行`32`程序必须安装 `i386` 的一些依赖库，方法如下：

  ```
  sudo dpkg --add-architecture i386
  sudo apt-get update
  sudo apt-get install libc6:i386 libncurses5:i386 libstdc++6:i386 lib32z1
  ```

  安装完成`32`位的依赖库后，我们使用`wget` 去官方下载最新的`linux`下`android SDK`包。

  ```
  cd ~
  wget http://dl.google.com/android/android-sdk_r24.4.1-linux.tgz
  tar xvzf android-sdk_r24.4.1-linux.tgz
  ```

  编辑 `.profile` 或者 `.bash_profile` 把下面的目录增加到 `path`的搜索路径中，确保`android SDK`的一些命令工具可以直接在终端使用，比如 `adb` 命令。

  ```
  ANDROID_HOME=$HOME/android-sdk-linux
  PATH="$PATH:$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools"
  ```

  使环境变量生效

  ```
  source ~/.profile
  ```

  环境变量生效后，你可以使用`android`命令 列出`sdk`相关的列表，以便我们选择和自己项目匹配的`SDK`版本。(刚才只是安装了最基础的`SDK`，要完全满足你的开发环境需要还得从下面的列表中选择你需要的`SDK`和工具更新下载)

  ```
  android list sdk --all
  ```

  输出如下所示：

  ```
  1- Android SDK Tools, revision 24.4.1
  2- Android SDK Platform-tools, revision 21
  3- Android SDK Build-tools, revision 21.1.2
  4- Android SDK Build-tools, revision 21.1.1
  5- Android SDK Build-tools, revision 21.1
  6- Android SDK Build-tools, revision 21.0.2
  7- Android SDK Build-tools, revision 21.0.1
  8- Android SDK Build-tools, revision 21
  9- Android SDK Build-tools, revision 20
  10- Android SDK Build-tools, revision 19.1
  11- Android SDK Build-tools, revision 19.0.3
  12- Android SDK Build-tools, revision 19.0.2
  13- Android SDK Build-tools, revision 19.0.1
  14- Android SDK Build-tools, revision 19
  15- Android SDK Build-tools, revision 18.1.1
  16- Android SDK Build-tools, revision 18.1
  17- Android SDK Build-tools, revision 18.0.1
  18- Android SDK Build-tools, revision 17
  19- Documentation for Android SDK, API 21, revision 1
  20- SDK Platform Android 5.0.1, API 21, revision 2
  21- SDK Platform Android 4.4W.2, API 20, revision 2
  22- SDK Platform Android 4.4.2, API 19, revision 4
  23- SDK Platform Android 4.3.1, API 18, revision 3
  24- SDK Platform Android 4.2.2, API 17, revision 3
  ....
  ```

  这里包括不同的`Android API` 版本和不同的构建工具，选择你想要安装项目的序号，这里我想安装 `build tools 19.1` ,`build tools 21` 及 `android 4.2.2`以上的`SDK`所以选择序号 `1,2,3,20,21,22,23`

  ```
  android update sdk -u -a -t  1,2,3,10,20,21,22,23
  ```

# ubuntu配置android环境2

- 参考链接

  ```
  https://www.jianshu.com/p/35b109ed9481
  https://blog.csdn.net/xiangwanpeng/article/details/54561374
  https://www.jianshu.com/p/7f8ea155a0c8
  ```

- 配置jdk

  - 在root目录下新建一个android文件夹,解压下载后的jdk压缩包,然后把java文件包移到/usr/下并命名为java

    ```
    tar -zxvf jdk-8u191-linux-x64.tar.gz
    mv jdk1.8.0_191/ /usr/java/
    ```

  ​	

  - 现在配置系统环境变量，现在我们在全局配置文件/etc/profile下配置，即为所有用户配置Java环境，使用vi命令编辑/etc/profile文件

    ```
    sudo vi /etc/profile
    ```

  - 在文件底部加上四条配置信息：

    ```
    export JAVA_HOME=/usr/java/
    export JRE_HOME=${JAVA_HOME}/jre
    export CLASSPATH=.:%{JAVA_HOME}/lib:%{JRE_HOME}/lib
    export PATH=${JAVA_HOME}/bin:$PATH
    ```

  - 其中JAVA_HOME指定为你的JDK安装目录，另外，linux中是以冒号分隔，不同于windows下使用分号进行分隔。 编辑好后保存退出，执行命令：

    ```
    source /etc/profile
    ```

  - 补充一下：source命令，也称为“点命令”，也就是一个点符号（.）。它通常用于重新执行刚修改的初始化文件，使之立即生效，而不必注销并重新登录。同时，可以把一些命令做成一个文件，让它自动顺序执行，它可以把这个文件的内容当成shell来执行。 

  - 现在可以执行javac命令和java -version命令进行测试： 

    ```
    root@iZuf6ggrfujyixxwpqrglbZ:/etc# java -version
    java version "1.8.0_191"
    Java(TM) SE Runtime Environment (build 1.8.0_191-b12)
    Java HotSpot(TM) 64-Bit Server VM (build 25.191-b12, mixed mode)
    ```


- 配置android sdk

  - 参考链接

    ```
    https://qianngchn.github.io/wiki/8.html
    https://www.jianshu.com/p/94c34759de68
    http://www.androiddocs.com/sdk/#top
    ```

  - 下载解压

    ```
    wget http://dl.google.com/android/android-sdk_r24.4.1-linux.tgz
    tar -zxvf android-sdk_r24.4.1-linux.tgz
    mv android-sdk-linux/ sdk/
    ```

  - 配置环境变量 打开了/etc/profile

    ```
    ANDROID_HOME=/root/android/sdk
    PATH="$PATH:$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools"
    ```

  - 使环境变量生效

    ```
    source /etc/profile
    ```

  - 更新sdk

    ```
    android update sdk --no-ui
    # 这里只是更新现有的以及下载最新的sdk，如果再加一个参数-a, 就可以把所有的版本都下载。
    ```

- 配置ndk

  - 参考链接

    ```
    https://blog.csdn.net/coder_pig/article/details/79134625
    ```

  - 下载ndk并解压

    ```
    unzip android-ndk-r14b-linux-x86_64.zip
    mv android-ndk-r14b/ ndk/
    ```

  - 配置ndk环境变量

    ```
    
    export ANDROID_NDK=/root/android/ndk
    export PATH=$ANDROID_NDK:$PATH
    ```

- 踩坑记录

  - you need the ndkr10e or later

    ```
    build on Linux x86_64
    ANDROID_NDK=/home/czl/Android/android-ndk-r16b
    IJK_NDK_REL=16.1.4479499
    You need the NDKr10e or later
    ```

    解决办法:

    - 下载最低版本ndk https://link.jianshu.com/?t=https%3A%2F%2Fdeveloper.android.google.cn%2Fndk%2Fdownloads%2Frevision_history.html%3Fhl%3Dzh-cn
    - 下载版本号14的[android-ndk-r14b-linux-x86_64.zip](https://link.jianshu.com/?t=https%3A%2F%2Fdl.google.com%2Fandroid%2Frepository%2Fandroid-ndk-r14b-linux-x86_64.zip)
    - 重新设置NDK路径

# adb技巧

- 常用的adb指令

  ```
  //向sdcard推拉当前目录下的mainapp.go文件
  adb push mainapp.go /sdcard/
  
  //从sdcard目录下拉取文件mainapp.go到当前目录下
  adb pull /sdcard/mainapp.go
  
  //启动应用
  adb shell am start -n 包名/全类名  
  
  //卸载应用
  adb uninstall 包名  
  
  //获取屏幕分辨率
  adb shell wm size  
  
  //对某个应用做monkey测试
  adb shell monkey -p com.bupin.webversion 500
  
  ```

# Android 学习的网址

```
https://blog.csdn.net/carson_ho?t=1

java nio基本概念介绍
http://wiki.jikexueyuan.com/project/java-nio-zh/java-nio-buffer.html
```

