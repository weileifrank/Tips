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


