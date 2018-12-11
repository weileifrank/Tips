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

  - https://www.cnblogs.com/zhaoyanjun/p/7603640.html
  - https://www.cnblogs.com/zhaoyanjun/    这位博主的博客可以一看

