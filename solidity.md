# ubuntu安装Node

- 安装

  ```
  sudo apt update
  sudo apt-get install nodejs-legacy nodejs
  sudo apt-get install npm
  ```

- 安装n来管理版本

  ```
  sudo npm install -g n
  
  sudo n lts 长期支持
  sudo n stable 稳定版
  sudo n latest 最新版
  sudo n 8.4.0 直接指定版本下载
  ```

- 切换版本

  ```
  sudo n
  直接键盘上下移动选择你要的版本，回车确认
  ```

  或者直接指定版本

  ```
  sudo n 8.10.0
  ```

- 查看版本

  ```
  node -v
  npm -v
  
  ```

- 常⻅错误：

  - json parse faild }....@sol

  - 解决办法:

    ```
    npm cache clean --force
    ```


# win10配置web3环境(PowerShell执行)

> 若安装web3报错, 注意,部分windows电脑可能要安装的工具

- 执行如下代码安装window所需要的编译的环境,如果还报错可以尝试安装visual studio

  ```
  npm install --global --production windows-build-tools 
  
  ```

- git 需要执行如下命令

  ```
  git config --global url.https://github.com/.insteadOf git://github.com/
  
  ```

- 上述安装window编译工具的过程中会安装python2,如果window没有配置,我们需要自己手动配置

  - 先找到python.exe所在的目录,一般如下

    ```
    C:\Users\w1138\.windows-build-tools\python27\python.exe
    
    ```

  - 执行如下命令

    ```
    npm config set python "C:\Users\w1138\.windows-build-tools\python27\python.exe"
    ```

# 

# webstorm 开发智能合约需要安装Intellij-Solidity代码提示的插件