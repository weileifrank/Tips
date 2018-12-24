# Kotlin基础语法汇总



## PART01

### Hello World

> 先贴上kotlin版本的HelloWorld,没啥好说的,直接记住

```
fun main(args: Array<String>) {
    println("hello kotlin")
}

fun定义函数的关键字
main函数的主入口
args:Array<String>字符串数组
println输出函数
同时kotlin跟其他新的语言一样,分号是可选的
```

> 可以对比java版本的HelloWorld

```java
public static void main(String[] args) {
        System.out.println("hello java");
}
```



### 基本数据类型

> 跟java很像

```ko
fun main(args: Array<String>) {
    var b:Boolean = false
    var byte:Byte = 10
    var int:Int =  20
    var long:Long = 40
    var char:Char = 'a'
    var double:Double = 1.1234567
    var float:Float = 1.1234567f
    var short:Short = 10
}
```



### 自动类型推断和类型转换

```kotl
fun main(args: Array<String>) {
    //kotlin智能类型推断 
    //类型安全的语言:类型一但确定,不再改变了
    //kotlin编译器推断出Int类型
    var b = 10
   // b = false  这个会报错,上面已经推导出Int类型了
    var e = 10L//long

    var c = "张三"
    var d = 'a'

    //String和int类型转换
    //to转换基本数据类型
    var m = 10
    var s = "10"
    println(s.toInt())
    s.toByte()
    //int类型和long类型转换
    //不同的数据类型不能相互赋值
    //kotlin数据类型可以通过to方法进行相互转换
    var u = 10
    var v = 10L
    v = u.toLong()//Int赋值给Long类型
    u = v.toInt()//Long赋值给Int类型

    //kotlin                    java                    js
    //最严格的类型检查        只能小的赋值给大的     最宽松
}
```



### 变量和常量

```ko
//可以通过反射修改里面的值
const val b:Int = 20 
//const var c:Int = 30  不能修饰var
fun main(args: Array<String>) {
    //项目开发中尽量使用val  实在不能使用val再使用var
    //可变变量
    var a = 10
    a = 20
    val d = 20
    //const val e = 20  报错,const不能修饰局部变量
    //不可变变量
   // d = 50
   // b = 30
}
```



### 字符串

- 普通字符串和原样字符串的定义

  ```ko
  fun main(args: Array<String>) {
      //定义普通字符串
      var addr1 = "上海市闵行区申长路"
      var addr2 = "上海市\n闵行区\n申长路"
      //定义原样输出字符串
      var addr3 = """
          上海市
          闵行区
          申长路
      """.trimIndent()
      println(addr1)
      println(addr2)
      println(addr3)
      var name = "   frank  "
      println(name)
      println(name.trim())
  
      //指定规则trim
      var str = """
          ,apple
          ,banana
          ,orange
      """.trimMargin(",")
      println("str===$str===")
  }
  ```

- 字符串比较

  ```
  fun main(args: Array<String>) {
      //字符串比较
      val s1 = "frank"
      val s2 = String(charArrayOf('f','r','a','n','k'))
      //equals  比较值  true
      println(s1.equals(s2))
      //== 比较的也是值
      println(s1 == s2)
      //=== 比较地址值 false
      println(s1 === s2)
  }
  ```

- 字符串切割

  ```ko
  fun main(args: Array<String>) {
      //字符串切割
      var a = "张三,李四,王五"
      println(a.split(","))
      //多条件切割
      val b = "frank.lucy-shannon"
      println(b.split(".","-"))
  }
  ```

- 字符串截取

  ```ko
  fun main(args: Array<String>) {
      val path = "C:/Users/w1138/Desktop/w/b.txt"
      //获取前6个字符
      println(path.substring(0, 6))
      println(path.substring(0..5))//0到5
      //把第一个r之前的字符截取
      println(path.substringBefore("w"))
      //把最后一个r之前的字符截取
      println(path.substringBeforeLast("w"))
      //把第一个r之后的字符截取
      println(path.substringAfter("w"))
      //把最后一个r之后的字符截取
      println(path.substringAfterLast("w"))
  }
  ```

- 字符串模板

  ```kotlin
  fun main(args: Array<String>) {
      var name = "frank"
      println("my name is $name")
  }
  ```


### 元组

```
fun main(args: Array<String>) {
    //定义二元元组 姓名  年纪
    val pair1 = Pair<String,Int>("frank",20)
    val pair2 = "frank" to 20
    println(pair1.first)
    println(pair2.second)
    println(pair2.first)
    println(pair2.second)

    //三元元组
    val triple = Triple<String,Int,String>("frank",20,"666")
    println(triple.first)
    println(triple.second)
    println(triple.third)
}
```



### 空值处理

```
fun main(args: Array<String>) {
    /**
     * val str:String 非空类型 不能赋值为null
     * val str: String? 可空类型 可以赋值为null
     
     *  空安全调用符:?.
     * 空值处理的运算符
     * 可空类型  Int?
     * 关闭空检查  a!!
     * 空安全调用符 a?.toInt
     * Elvis操作符  ?:
     */
    var str:String? = null
    //空安全调用符 返回的值其实就是可空类型
//    str?.toInt()
    //告诉编译器  不要检查了  我一定不为空  还是可能为空  不建议使用
//    str!!.toInt()
    var b = str?.toInt()?:-1
    println(b)
//    if (str == null) {
//        return -1
//    } else {
//        return str.toInt()
//    }
}
```

```
fun main(args: Array<String>) {
    //m+n
    var m:Int
    var n:Int
    //从控制台输入m和n
    m = readLine()?.toInt()?:-1
    n = readLine()?.toInt()?:-1

    println("m+n=" + (m + n))
}
```



### 定义函数

- 定义普通函数

  ```
  //无参无返回值
  fun sayHello(){//返回值
      println("hello")
  }
  //有参无返回值
  fun sayHello(name:String){
      println("hello $name")
  }
  //有参有返回值
  fun getLength(name:String):Int{
      return name.length
  }
  //无参有返回值
  fun get():String{
      return "hello"
  }
  ```

- 定义嵌套函数

  ```
  //函数式编程  函数式一等公民 函数可以独立于对象单独存在
  //顶层函数
  fun main(args: Array<String>) {
      //嵌套函数
      fun sayHello(){
          println("hello")
      }
      sayHello()
  }
  //顶层函数
  fun haha(){
  
  }
  ```


### 条件语句

- if else语句

  ```
  fun main(args: Array<String>) {
      val a = 10
      val b = 20
      var max = max(a, b)
      println(max)
  }
  //kotlin的if语句有返回值的  java if语句是没有返回值的
  fun max(a:Int, b: Int): Int {
      return if (a > b) a else b
  }
  //也可以简化成如下代码
  //fun max(a: Int, b: Int) = if (a > b) a else b
  ```

- when表达式

  - 基本使用

    ```
    fun main(args: Array<String>) {
        //7岁 开始上小学
        //12岁 开始上中学
        //15岁 开始上高中
        //18岁  开始上大学
        val age = 7
        //小功能
        val result = todo(age)
        println(result)
    }
    
    fun todo(age: Int): String {
        when (age) {
            7 -> {
                return "开始上小学"
            }
            12->{
                return "开始上中学"
            }
            15->{
                return "开始上高中"
            }
            18->{
                return "开始上大学"
            }
            else -> {
                return "开始上社会大学"
            }
        }
    }
    
    //上面代码可以简化成如下代码
    fun todo2(age: Int): String {
        when (age) {
            7 -> return "开始上小学"
            12 -> return "开始上中学"
            15 -> return "开始上高中"
            18 -> return "开始上大学"
            else -> return "开始上社会大学"
        }
    }
    ```

  - when表达式高级

    >  kotlin里面的when表达式原理:
    >
    > 简单的when表达式通过switch语句实现
    >
    > 复杂的when表达式通过if else来实现

    ```
    fun main(args: Array<String>) {
        val age = 120
        val result = todo(age)
        println(result)
    }
    //区间和单点混搭
    fun todo(age: Int): String {
        when (age) {
            in 1..6 -> return "还没读书"  //age in 区间中
            7 -> return "开始上小学"
            in 8..11 -> return "在上小学"
            12 -> return "开始上中学"
            13, 14 -> return "正在上中学"
            15 -> return "开始上高中"
            16, 17 -> return "正在上高中"
            18 -> return "开始上大学"
            in 19..22 -> return "正在上大学"
            else -> return "开始上社会大学"
        }
    }
    //上面代码可以简化成如下代码
    fun todo2(age: Int): String {
        when {
            age in 1..6 -> return "还没读书"
            age == 7 -> return "开始上小学"
            age in 8..11 -> return "在上小学"
            age == 12 -> return "开始上中学"
            age in 13..14 -> return "正在上中学"
            age == 15 -> return "开始上高中"
            age in 16..17 -> return "正在上高中"
            age == 18 -> return "开始上大学"
            age in 19..22 -> return "正在上大学"
            else -> return "开始上社会大学"
        }
    }
    ```

  - when表达式返回值

    ```
    fun main(args: Array<String>) {
        val age = 7
        val result = todo(age)
        println(result)
    }
    fun todo(age: Int): String {
        return when (age) {
            7 -> {
                println("找到了分支")
                "开始上小学"
                "10"
            }
            12 ->"开始上中学"
            
            15 -> {
                 "开始上高中"
            }
            18 -> {
                 "开始上大学"
            }
            else -> {
                 "开始上社会大学"
            }
        }
    }
    ```


### 循环语句

- for循环

  ```
  fun main(args: Array<String>) {
       var str = "abcd"
      //for循环
      for (c in str) {
          println(c)
      }
      println("================")
      for (withIndex in str.withIndex()) {
          println(withIndex.index)
          println(withIndex.value)
          println(withIndex)
      }
      println("========对上面解构========")
      for ((index, c) in str.withIndex()) {
          println(index)
          println(c)
      }
  
  }
  ```

- foreach循环

  ```
  fun main(args: Array<String>) {
      //foreach循环
      var str = "abcd"
      str.forEach {
          println(it)
      }
      str.forEachIndexed { index, c -> println("$index----$c") }
  }
  ```

- while和do while循环

  ```
  fun main(args: Array<String>) {
      var i = 0
      var j = 0
      //while循环满足条件才执行
      while (i <= 10) {
          println(i)
          i++
      }
  	//do while 上来就执行一次
      do {
          println(j)
          j++
      } while (j <= 10)
  }
  ```


### 区间

- 区间定义

  ```
  fun main(args: Array<String>) {
      //定义1到10
      var range1 = 1..10
      var range2 = 1 until 10
      var range3 = IntRange(1,10)
      var range4 = 1.rangeTo(10)
  
      /*---------------------------- 长整形区间 ----------------------------*/
      val range5 = 1L..10L
      val range6 = LongRange(1L,100L)
      val range7 = 1L.rangeTo(10L)
      /*---------------------------- 字符区间 ----------------------------*/
      val range8 = 'a'..'z'
      val range9 = CharRange('a','z')
      val range10 = 'a'.rangeTo('z')
  }
  ```

- 区间的遍历

  ```
  fun main(args: Array<String>) {
     var range = 1..10
      for (i in range) {
          println(i)
      }
      for ((index, num) in range.withIndex()) {
          println("$index---$num")
      }
      range.forEach {
          println(it)
      }
      range.forEachIndexed { index, i -> println("$index---$i") }
  }
  ```

- 反向区间和区间反转

  ```
  fun main(args: Array<String>) {
    //反向区间
      val range = 10 downTo 1
      range.forEach {
          println(it)
      }
      //区间反转
      var reversed = range.reversed()
      reversed.forEach {
          println(it)
      }
      //设置步长
      for (i in reversed step 2) {
          println(i)
      }
  }
  ```


### 数组

- 数组的定义

  > 8中基本数据类型的数组可以通过Array创建或者通过自己的数据类型Array创建
  >
  >字符串数组只能通过Array创建
  >
  >

  ```
  fun main(args: Array<String>) {
      /*---------------------------- 定义数组并赋值 ----------------------------*/
      //张三  李四  王五
      val arr = arrayOf("张三","李四","王五")
      //10 20 30
      val arr1 = arrayOf(10,20,30)
      //a b c
      val arr2 = arrayOf('a','b','c')
      //"张三"  10  'a'
      val arr3 = arrayOf("张三",10,'a')
      /*---------------------------- 8中基本数组类型数组 ----------------------------*/
      //保存年纪信息
      val arr4 = IntArray(10)//new int[10]
      val arr5 = IntArray(10){//把数组里面每一个元素都初始化为30
          30
      }
  
  //    BooleanArray
  //    ByteArray
  //    ShortArray
  //    CharArray
  //    FloatArray
  //    DoubleArray
  //    LongArray
  //    StringArray//不能用
  }
  ```

- 数组的遍历

  ```
  fun main(args: Array<String>) {
      val arr = arrayOf("apple","banana","orange")
      for (s in arr) {
          println(s)
      }
      arr.forEach {
          println(it)
      }
  }
  ```

- 数组元素的修改

  ```
  fun main(args: Array<String>) {
     val arr = arrayOf("a",1,true,ArrayList<String>())
      arr.forEach { println(it) }
      arr[0]= 0
      arr.set(2,2)
      arr.set(3,3)
      arr.forEach { println(it) }
  }
  ```

- 数组查找

  ```
  fun main(args: Array<String>) {
      val array = arrayOf("张三","李四","张四","王五","张三","赵六")
      //查找第一个”张三”角标
      //返回第一对应的元素角标  如果没有找到返回-1
      val index1 = array.indexOf("张三")
      println(index1)
      //查找最后一个”张三”角标
      //找不到 返回-1
      val index2 = array.lastIndexOf("张三")
      println(index2)
  
      /*---------------------------- 高阶函数实现 ----------------------------*/
      //查找第一个姓”张”的人的角标
  //    val index3 = array.indexOfFirst {
  //        it.startsWith("张")
  //    }
  //    println(index3)
      //查找最后一个姓”张”的人的角标
      val index4 = array.indexOfLast { it.startsWith("张") }
      println(index4)
  
      val index5 = array.indexOfFirst { it=="张三" }
      println(index5)
  }
  ```


### 函数表达式

```
fun main(args: Array<String>) {
    var a = 10
    var b = 20
    //a+b
    println(add(a, b))
}
//函数体只有一行
//fun add(a:Int,b:Int):Int{
//    return a+b
//}
//函数体只有一行代码  可以省略{} 省略return 用=连接
//fun add(a:Int,b:Int):Int =  a+b
//返回值类型也不用写
fun add(a:Int,b:Int) =  a+b

fun sayHello(){
    println("hello")
    println("hello world")
}
```

```
fun main(args: Array<String>) {
    var a = 10
    var b = 20
    /*---------------------------- 函数引用:: ----------------------------*/
    //对象变量
    //函数变量
    val padd= ::add//::获取add函数的引用
    //类似C语言函数指针
    //通过padd调用函数
    println(padd(a, b))
    println(padd?.invoke(a, b))//可以处理函数变量为空的情况下调用
    /*---------------------------- 函数变量 ----------------------------*/
    val padd1:(Int,Int)->Int = {a,b->a+b}//匿名函数
    val padd2:(Int,Int)->Int = {a,b->a+b}//匿名函数
    println(padd1(a,b))
}

fun add(a:Int,b:Int)=a+b
```



### 默认参数和具名参数

```
fun main(args: Array<String>) {
    sendRequest("http://www.baidu.com", "GET")
    sendRequest("http://www.baidu.com", "POST")
    sendRequest("http://www.baidu.com")
    sendRequest(method = "GET",path = "http://www.baidu.com")//具名参数 参数位置可以变化
}

fun sendRequest(path: String, method: String = "GET") {
    println("请求路径=${path},method=${method}")
}
```



### 可变参数

```
fun main(args: Array<String>) {
    var a = 10
    var b = 20
    var c = 30
    var result = sum(a, b, c, 10, 20, 30, 40, 50)
    println(result)
    result = sum(a, b, c)
    println(result)
    result = sum(1,2,3)
    println(result)
}

/**
 * 只要是Int数据类型  无论你传递多少个我都能求他们的和  可变参数
 */
fun sum(vararg a: Int): Int {//数组
    //a是什么类型?
    //智能类型推断
//    val newa = a
    var result = 0//和
    a.forEach {
        result += it
    }
    return result
}
```



###异常

```
fun main(args: Array<String>) {
    //java异常种类:编译时异常  运行时异常
    var a = 10
    var b = 0
    //异常处理
    try {
        a / b
    } catch (e: ArithmeticException) {
        println("捕获到异常")
    }finally {
        println("最终要执行的代码")
    }
    /*---------------------------- koltin编译时异常 ----------------------------*/
    //kotlin不检查编译时异常
    //kotlin认为大部分的编译时异常都是没有必要的
    val file = File("a.txt")
    val bfr = BufferedReader(FileReader(file))
}
```



### 递归

- 递归和迭代的对比:

  > - 常见的需求:通过迭代和递归都可以解决 复杂的问题用递归更容易实现
  > - 递归和迭代有什么优缺点呢?
  > - StackOverflowError 递归如果递归的层级比较深容易栈内存溢出
  > - 递归:优点:逻辑比较简单  容易实现  缺点:容易栈内存溢出(内存开销比较大)
  > - 迭代:优点:内存开销小 缺点:抽象出数学模型

  ```
  fun main(args: Array<String>) {
      //求1到n的数据和  1到10
      val result1 = sum1(10)
      println(result1)
      val result2 = sum2(10)
      println(result2)
  }
  
  //求1到n的和  1到10  1到9的和+10   1到n的和  1到n-1的和 + n
  fun sum2(n:Int):Int{
      if(n==1){
          return 1
      }else{
          return n+ sum2(n-1)
      }
  }
  /*
   * 求1到n的和  通过迭代的方式求和
   */
  fun sum1(n: Int): Int {//kotlin里面参数是固定  不能修改
      var result = 0
      var copyN = n
      while (copyN > 0) {
          result += copyN
          copyN--
      }
      return result
  }
  ```

- 尾递归优化

  > - 只有尾递归才能优化
  >
  >  * 第一步:需要将递归转换为尾递归
  >  * 第二步:加上tailrec
  >  * 尾递归优化的原理:将递归转换为迭代

  ```
  fun main(args: Array<String>) {
      val result = sum2(100000)
      println(result)
  }
  
  //尾递归
  tailrec fun sum2(n:Int,result:Int=0):Int{
      if(n==1){
          return result+1
      }else{
          return sum2(n-1,result+n)
      }
  }
  
  ```


### 运算法重载

>- 运算符重载
>
> * kotlin里面每一个运算符对应的都是一个方法 运算符相当于是方法的简写
> * 第一步:找到对应的函数
> * 第二步:函数前加上operator

```
fun main(args: Array<String>) {
    var a = 10
    var b = 20
    var sum = a.plus(b)
    println(sum)
    println(a+b)
    val g1 = Girl()
    val g2 = Girl()
    var i = g1 + g2
    var i1 = g1.plus(g2)
    println(i)
    println(i1)

}
class Girl {
    var name:String="aa"
    var age:Int = 20
    operator fun plus(girl: Girl): Int {
        return this.age+girl.age
    }
}
```



### 面向对象(上)

- 定义类

  > koltin字段是私有的  会自动生成get和set方法

  ```
  fun main(args: Array<String>) {
      val p = Person()
      println(p.age)
      println(p.name)
      p.age = 30
      p.name = "apple"
      println(p.age)
      println(p.name)
  }
  
  class Person {
      var name = "frank"
      var age = 20
  }
  ```

- 访问器

  ```
  fun main(args: Array<String>) {
      val p = Person()
      println(p.age)
      println(p.name)
      p.age = 30
      //私有了set访问器不能修改了
      //p.name = "apple"
      println(p.age)
      println(p.name)
  }
  
  class Person {
      var name = "frank"
      private set //私有了set方法
      var age = 20
  }
  ```

- 自定义访问器

  ```
  fun main(args: Array<String>) {
      val p = Person()
      println(p.age)
      println(p.name)
      p.age = 30
      //私有了set访问器不能修改了
      //p.name = "apple"
      println(p.age)
      println(p.name)
  }
  
  class Person {
      var name = "frank"
      private set
      var age = 20 //age超过150岁不能设置了
      set(value) {
          if (value < 150) {
           //   this.age = value
              field = value//age的set方法
          }
      }
  }
  ```

- 构造函数

  ```
  fun main(args: Array<String>) {
      val p = Person("frank",20)
      println(p.name)
      println(p.age)
  }
  
  class Person(name:String,age:Int) {//创建的时候就需要修改里面的name和age
      var name:String= "" //静态属性需要初始化
      var age:Int = 0
      //构造函数中写的代码可以放到init中执行
      init {
          this.name = name
          this.age = age
      }
  }
  ```

  ```
  fun main(args: Array<String>) {
      val p = Person("frank",20)
      println(p.name)
      println(p.age)
      p.name = "apple"
      println(p.name)
  }
  
  class Person(var name:String,var age:Int)
  ```

  ```
  fun main(args: Array<String>) {
      val p = Person("frank",20,"666")
  }
  
  class Person(var name: String, var age: Int){
      var phone = ""
      //次构函数必须调用主构函数
      constructor(name:String,age:Int,phone:String):this(name, age){
          this.phone = phone
      }
  }
  ```

  ```
  fun main(args: Array<String>) {
      val p = Person("frank",20,"666")
  }
  
  class Person(var name: String, var age: Int){
      var phone = ""
      //次构函数必须调用主构函数
      constructor(name:String,age:Int,phone:String):this(name, age){
          println("执行了次构函数")
          this.phone = phone
      }
      //先执行初始化,然后再执行次构造函数
      init {
          println("执行了初始化")
      }
  }
  ```


### 面向对象(下)

- 继承

  ```
  fun main(args: Array<String>) {
      val son = Son()
      println(son.name)
      println(son.age)
      son.hobby()
  }
  
  //kotlin的类都是final的  不能被继承
  open class Father {
      open var name = "张三"
      open var age = 20
      //动态行为
      open fun hobby() {
          println("父亲喜欢抽烟")
      }
  }
  
  //子承父业
  class Son : Father() {
      override var name: String = "张四"
      override var age: Int = 10
      override fun hobby() {
  //        super.horbe()
          println("孩子喜欢看书")
      }
  }
  ```

- 抽象类

  >- 抽象类以abstract表示
  >
  > * 抽象类可以没有抽象方法和抽象字段

  ```
  fun main(args: Array<String>) {
      val zhHuman = ZhHuman()
      val usHuman = UsHuman()
      val afHuman = AfHuman()
      println(zhHuman.color)
      println(zhHuman.language)
  }
  //人类 抽象类不用open关键字
  abstract class Human{
      //肤色
       abstract var color:String
      //语言
       abstract var language:String
      //吃饭
       abstract fun eat()
  }
  //中国人
  open class ZhHuman:Human(){
      override var color: String = "黄色"
      override var language: String = "中文"
  
      override fun eat() {
          println("用筷子吃饭")
      }
  }
  //美国人
  class UsHuman:Human(){
      override var color: String = "白色"
      override var language: String = "英文"
  
      override fun eat() {
          println("用刀叉吃饭")
      }
  }
  //非洲人
  class AfHuman:Human(){
      override var color: String = "黑色"
      override var language: String = "葡萄牙语"
  
      override fun eat() {
          println("用受抓恩希玛")
      }
  }
  ```

- 接口

  ```
  fun main(args: Array<String>) {
      val xiaoMing = XiaoMing()
      xiaoMing.ride()
      xiaoMing.drive()
  }
  //小明
  class XiaoMing: ZhHuman(),RideBike,DriveCar{
      override fun drive() {
          println("小明学会了开车")
      }
      override fun ride() {
          println("小明学会了骑自行车")
      }
  }
  //能力用接口表示
  interface RideBike{
      //骑自行车行为
      fun ride()
  }
  
  //开车能力
  interface DriveCar{
      //开车行为
      fun drive()
  }
  ```

  > kolin接口里面字段不能实现
  >
  > java接口的方法不能实现(jdk8以后可以有默认实现了)

  ```
  fun main(args: Array<String>) {
      val xiaoMing = XiaoMing()
      xiaoMing.ride()
      xiaoMing.drive()
      println(xiaoMing.license)
  }
  //小明
  class XiaoMing: ZhHuman(),RideBike,DriveCar{
      override var license: String="123"
      override fun drive() {
          println("小明学会了开车")
      }
      override fun ride() {
          println("小明学会了骑自行车")
      }
  }
  //能力用接口表示
  interface RideBike{
      //骑自行车行为
      fun ride()
  }
  
  //开车能力
  interface DriveCar{
      //驾照号码
  //    var license:String=""  //kolin接口里面字段不能实现
      var license:String
      //开车行为
      fun drive()
  }
  ```

- 多态

  > 多态特点:通过父类接收  执行的是子类的方法

  ```
  fun main(args: Array<String>) {
      //创建狗和猫的对象
      val dog: Animal = Dog()
      val cat = Cat()
      dog.call()
  //    cat.call()
  }
  
  //动物
  abstract class Animal {
      var color: String = ""
      //行为
      open fun call() {
          println("动物叫")
      }
  }
  
  //狗
  class Dog : Animal() {
      override fun call() {
          println("狗汪汪叫")
      }
  }
  
  //猫
  class Cat : Animal() {
      override fun call() {
          println("猫喵喵叫")
      }
  
  }
  ```

- 智能类型推断

  ```
  fun main(args: Array<String>) {
      val shepHerdDog:Dog = ShepHerdDog()
      val ruralDog = RuralDog()
  
      //想调用herdShep方法  1.判断是否是ShepHerdDog 2.转换成ShepHerdDog类型
      if(shepHerdDog is ShepHerdDog){
      //判断完之后已将将shepHerdDog类型由Dog类型转换为ShepHerdDog类型,无需再强转了
          //2.转换成ShepHerdDog类型
  //        val newDog = shepHerdDog as ShepHerdDog //as强转
  //        newDog.herdShep()
          shepHerdDog.herdShep()
      }
  }
  //Dog狗
  abstract class Dog
  //牧羊犬
  class ShepHerdDog:Dog(){
      //功能
      fun herdShep(){
          println("牧羊犬放羊")
      }
  }
  //中华田园犬
  class RuralDog:Dog(){
      //功能
      fun watchDoor(){
          println("中华田园犬看家")
      }
  }
  ```

- 嵌套类

  > 嵌套类是属于静态类 和外部类没有任何关系

  ```
  fun main(args: Array<String>) {
      //创建嵌套类对象
      val inClass = OutClass.InnnerClass()
  }
  class OutClass{
      var name = "张三"
      class InnnerClass{
          fun sayHello(){
          //引用外部类属性会报错
  //            println("你好$name")
          }
      }
  }
  ```

- 内部类

  > 内部类和java的内部类是一样的 需要依赖于外部类对象的环境  关键字inner

  ```
  fun main(args: Array<String>) {
      //创建内部类对象
      val inClass  = OutClass().InnnerClass()
  }
  class OutClass{
      var name = "张三"
      inner class InnnerClass{
          fun sayHello(){
              println("你好$name")
          }
      }
  }
  ```

- 内部类使用this

  > - 内部类和java的内部类是一样的 需要依赖于外部类对象的环境
  >
  >  * this@tag和java里面的OutClass.this.name一样的

  ```
  fun main(args: Array<String>) {
      //创建嵌套类对象
      val inClass = OutClass().InnnerClass()
      inClass.sayHello()
  }
  class OutClass {
      var name = "张三"
      inner class InnnerClass {
          var name= "李四"
          fun sayHello() {
              println("你好${this@OutClass.name}")
          }
      }
  }
  ```

### 面向对象补充

- 匿名内部类中的各种写法

  ```
  fun main(args: Array<String>) {
      val p = object : Person() {
          override fun greeting(): String {
              return "你好"
          }
          override fun eat(s: String) {
          }
      }
      p.eat("cake")
      val greeting = p.greeting()
      println(greeting)
  }
  
  abstract class Person {
      abstract fun greeting(): String
      abstract fun eat(s: String)
  }
  ```

- android中相关的示例代码

  ```
  class MainActivity : AppCompatActivity() {
  
      override fun onCreate(savedInstanceState: Bundle?) {
          super.onCreate(savedInstanceState)
          setContentView(R.layout.activity_main)
          var button = findViewById<Button>(R.id.bt)
          var lv = findViewById<ListView>(R.id.lv)
          button.setOnClickListener {
              Toast.makeText(this, "点击的类型${it.javaClass.name}", Toast.LENGTH_SHORT).show()
          }
          //OnLongClickListener是接口不需要,不能在后面加(),加上变成构造方法了
          button.setOnLongClickListener(object :View.OnLongClickListener {
              override fun onLongClick(v: View?): Boolean {
                  return false
              }
          })
          //省略object的匿名内部类写法
          button.setOnLongClickListener(View.OnLongClickListener {
              false
          })
          //setOnLongClickListener可以接受java8的lambda表达式,然后通过kotlin省略括号得到如下
          button.setOnLongClickListener{
              false
          }
  
          var baseAdapter = object:BaseAdapter(){
              override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
                  return Button(parent?.context)
              }
  
              override fun getItem(position: Int): Any {
                  return ""
              }
  
              override fun getItemId(position: Int): Long {
                  return 0
              }
  
              override fun getCount(): Int {
                 return 0
              }
          }
  
      }
  }
  
  
  ```


### 泛型

- 定义泛型

  > - 定义对象的时候使用泛型
  >
  >  * 定义子类时候执行泛型
  >  * 定义子类的时候不知道具体类型,继续使用泛型

  ```
  fun main(args: Array<String>) {
      //用箱子
      val box =Box<String>("apple")
      println(box.thing)
  }
  //箱子
  open class Box<T>(var thing:T)//物品类型不确定  定义泛型和使用泛型
  //水果箱子
  class FruitBox(thing:Fruit):Box<Fruit>(thing)
  //不知道具体放什么东西
  class SonBox<T>(thing:T):Box<T>(thing)//第一个是申明  后面两个都是使用
  //水果
  abstract class Fruit
  //苹果
  class Apple:Fruit()
  //橘子
  class Orange:Fruit()
  ```

- 泛型函数

  ```
  fun main(args: Array<String>) {
      parseType(10)
      parseType("张三")
      parseType(Apple())
  }
  
  //方法 thing  判断类型
  fun <T> parseType(thing: T) {//前面定义泛型  后面使用泛型
      when (thing) {
          is Int -> println("是Int类型")
          is String -> println("是String类型")
          else -> println("不知道具体类型")
      }
  }
  ```

- 泛型上线

  >- 定义对象的时候使用泛型
  >
  > * 定义子类时候执行泛型
  > * 定义子类的时候不知道具体类型,继续使用泛型
  > * T:Fruit  泛型上限  泛型智能是Fruit类型或者Fruit类型的子类
  > * 泛型作用:放任何类型 限制存放的类型

  ```
  fun main(args: Array<String>) {
      val apple = Apple()
      val orange = Orange()
      val thing = Thing()
      var fruit = Fruit()
      val fruitBox = FruitBox(apple)
      val fruitBox2 = FruitBox(fruit)
      //下面会报错 泛型限定为Fruit子类
      //val fruitBox = FruitBox(thing)
  }
  //箱子
  open class Box<T>(var thing:T)//物品类型不确定  定义泛型和使用泛型
  //水果箱子
  class FruitBox<T:Fruit>(thing:T):Box<T>(thing)//只能水果
  open class Thing
  //水果
  open class Fruit: Thing()
  //苹果
  class Apple:Fruit()
  //橘子
  class Orange:Fruit()
  ```

- 泛型檫除

  >  * 解决泛型擦除方案:
  >  * 第一步:泛型前加reified关键字
  >  * 第二步:方法前加上inline关键字

  ```
  fun main(args: Array<String>) {
      val box1 = Box<String>("")
      val box2 = Box<Int>(10)
  
      //class类型
      val clz1 = box1.javaClass.name
      val clz2 = box2.javaClass.name
  
      println(clz1)
      println(clz2)
      println(String::class.java)
  
      parseType(10)
      parseType("frank")
  }
  
  inline fun <reified T>parseType(thing:T){
      //获取传递的thing的class类型
      val name = T::class.java.name
      println(name)
  }
  ```

- 泛型类型投射

  > - out:接收当前类型或它的子类 相当于java的 ? extents
  >
  >  * in:接收当前类型或者它的父类  相当于java的 ? super

  ```
  fun main(args: Array<String>) {
      val list = ArrayList<Thing>()
      setFruitList(list)
      val list2 = ArrayList<Apple>()
      //下面报错
     // setFruitList(list2)
  }
  
  fun setFruitList(list:ArrayList<in Fruit>){
      println(list.size)
  }
  ```

- 星号投射

  > *可以传递任何类型  相当于java的 ?

  ```
  fun main(args: Array<String>) {
      val list = ArrayList<Int>()
      setFruitList(list)
  }
  //ArrayList里面可以接收任何类型 怎么办?
  fun setFruitList(list:ArrayList<*>){//接收只能是Fruit Apple是Fruit子类
      println(list.size)
  }
  ```


## Part02



### 中缀表达式

```
中缀表达式 让代码更加简洁易懂 DSL 不能用于顶层函数,可以用于成员函数或者拓展函数
```

```
fun main(args: Array<String>) {
    var p1 = Person()
    p1.sayHelloTo("frank")
    p1 sayHelloTo "lucy"
}

class Person {
    infix fun sayHelloTo(name: String) {
        println("你好,$name")
    }
}
```



### 委托

- 基本使用

  ```
  fun main(args: Array<String>) {
      var smallHeadFather = SmallHeadFather()
      smallHeadFather.wash()
  }
  interface WashPower {
      fun wash()
  }
  class BigHeadSon : WashPower {
      override fun wash() {
          println("大头儿子开始洗碗了")
      }
  }
  class SmallHeadFather : WashPower by BigHeadSon()
  ```

- 通过接口参数实现委托

  ```
  fun main(args: Array<String>) {
      val bigHeadSon = BigHeadSon()
      val smallHeadFather = SmallHeadFather(SmallHeadSon())
      val smallHeadFather2 = SmallHeadFather(bigHeadSon)
      smallHeadFather.wash()
      smallHeadFather2.wash()
  }
  interface WashPower {
      fun wash()
  }
  class BigHeadSon : WashPower {
      override fun wash() {
          println("大头儿子开始洗碗了")
      }
  }
  class SmallHeadSon : WashPower {
      override fun wash() {
          println("小头儿子开始洗碗了")
      }
  }
  //class SmallHeadFather : WashPower by BigHeadSon()
  class SmallHeadFather(var washPower: WashPower):WashPower by washPower
  ```

- 委托加强

  ```
  fun main(args: Array<String>) {
      val smallHeadFather = SmallHeadFather(SmallHeadSon())
      smallHeadFather.wash()
  }
  
  interface WashPower {
      fun wash()
  }
  class BigHeadSon : WashPower {
      override fun wash() {
          println("大头儿子开始洗碗了")
      }
  }
  class SmallHeadSon : WashPower {
      override fun wash() {
          println("小头儿子开始洗碗了")
      }
  }
  class SmallHeadFather(var washPower: WashPower):WashPower by washPower{
      override fun wash() {
          //付钱
          println("付给小头儿子1块钱")
          //小头儿子功能
          washPower.wash()
          //洗完之后
          println("干的很好  下次继续")
      }
  }
  ```

- 属性代理

  ```
  fun main(args: Array<String>) {
      val bigHeadSon = BigHeadSon()
      //爷爷奶奶给了100块钱
      bigHeadSon.ysMoney = 150
      //取压岁钱
  //    println(bigHeadSon.ysMoney)
  }
  class BigHeadSon {
      var ysMoney: Int by Mother()
  }
  class Mother {
      var sonMoney = 0
      var selfMoney = 0
      operator fun getValue(bigHeadSon: BigHeadSon, property: KProperty<*>): Int {
          return sonMoney
      }
      operator fun setValue(bigHeadSon: BigHeadSon, property: KProperty<*>, i: Int) {
          sonMoney += 50
          selfMoney += i - 50
          println("selfMoney=$selfMoney")
      }
  }
  ```


### 惰性加载

> * 字段:val  不可变
>  * by lazy放到成员变量中 可以单独存在
>  * by lazy返回值就是最后一行
>  * by lazy线程安全 (同步)

```
fun main(args: Array<String>) {
    val p = Person()
    println(p.name1)
    println(name1)
    println(name1)
}
val name1: String  by lazy {
    println("初始化了")
    "张三"
}
val age = 20

class Person{
    //用的时候再赋值
    val name1:String  by lazy { "张三"
        "aa"}
    val name2:String = "张三"
    val name3:String = "张三"
    val name4:String = "张三"
}
```



### 延迟加载

> * lateinit:延迟加载  用的时候再赋值  不赋值不能用  程序错误处理
> * 1.by lazy 和lateinit  都可以单独使用或者放到成员变量中使用
> * 2.by lazy 知道具体值   用的时候再加载
> * 3.lateinit  不知道具体值  后面再赋值
> * 4.by lazy变量必须通过val修饰  lateinit需要通过var修饰

```
lateinit var name1: String
fun main(args: Array<String>) {
    name1 = "haha"
    println(name1)
}
```



### 拓展函数

> ```
> 扩展函数可以访问当前对象里面的字段和方法
> ```

```
fun main(args: Array<String>) {
    val str:String? = null
    //str是否为空
    val myIsEmpty = str.myIsEmpty()
    println(myIsEmpty)
    val son = Son()
    son.sayHello()
}
fun String?.myIsEmpty():Boolean{
    return this==null||this.length==0
}
//扩展方法
fun Father.sayHello(){
    println("爸爸打招呼了")
}
open class Father{
    fun haha(){
    }
}
class Son:Father(){
}
```



### 伴生对象

- 单例

  > * object单例  所有的字段都是static静态  方法不是
  >  * object试用条件:字段不多的时候
  >  * java可以控制字段是静态还是非静态 static,kotlin没有static关键字
  >  * 看不懂的可以在idea中show kotlin bytecode,然后decompile查看java代码对比

  ```
  fun main(args: Array<String>) {
      //单例调用方法和属性的方式
      println(Utils.name)
      Utils.sayHello()
  }
  //设置成一个单例
  object Utils{
      var name = "张三"
      fun sayHello(){
          println("hello")
      }
  }
  ```

- 伴生对象

  > 伴生对象作用就是控制属性的静态

  ```
  fun main(args: Array<String>) {
      //访问age
      val person = Person()
      println(person.age)
      //name访问
      println(Person.name)
  }
  class Person {
      var age = 20
      companion object {
          //静态的name
          var name = "张三"
      }
  }
  ```

- 和java一样的单例

  > 搞不懂可以show kotlin bytecode,然后decompile

  ```
  fun main(args: Array<String>) {
      println(Utils.instan.age)
  //    val utils = Utils()
  }
  
  class Utils private constructor() {
      //第一步:私有构造函数
  //非静态的
      var age = 20
  
      companion object {
          //静态
          var name = "张三"
          //instance代表Utils的对象实例
          val instan by lazy { Utils() }//惰性加载  只会加载一次  线程安全
      }
  }
  ```


### 枚举

- 基本定义

  ```
  fun main(args: Array<String>) {
      //星期一
      println(WEEK.星期一)
      println(WEEK.星期二)
      //所有的元素全部找到
      val result = WEEK.values()
      result.forEach { println(it) }
      //when switch
      println(todo(WEEK.星期四))
      println(todo(WEEK.星期日))
  }
  
  fun todo(week: WEEK) {
      when (week) {
          in WEEK.星期一..WEEK.星期五 -> println("上班")
          WEEK.星期六, WEEK.星期日 -> println("休息")
      }
  }
  
  //一周枚举
  enum class WEEK { //数据
      星期一, 星期二, 星期三, 星期四, 星期五, 星期六, 星期日
  }
  ```

- 枚举加强

  > 枚举里面可以定义构造函数

  ```
  fun main(args: Array<String>) {
      COLOR.RED.r
      COLOR.RED.g
      COLOR.RED.b
  }
  //枚举三元素
  // 红  r 255 g 0 b 0
  // 蓝  r 0 g 0 b 255
  // 绿  r 0 g 255 b 0
  enum class COLOR(var r:Int,var g:Int,var b:Int){
      RED(255,0,0),BLUE(0,0,255),GREEN(0,255,0)
  }
  ```


### 数据类

```
fun main(args: Array<String>) {
    val news = News("标题","简介","路径","内容")
    println(news.title)
    println(news.desc)

    println(news.component1())//第一个元素
    println(news.component2())//第二个元素

    //解构
    val (title,desc,imgPath,content) = News("标题","简介","路径","内容")
    println(title)
    println(desc)
}

/**
 * 构造函数
 * get set
 * toString
 * hashcode
 * equeals
 * 参数
 * copy
 */
//数据类型
data class News(var title:String,var desc:String,var imgPath:String,var content:String)
```

### 解构

```
fun main(args: Array<String>) {
    //解构
    val (title,desc,imgPath,content) = News("标题","简介","路径","内容")
    println(title)
    println(desc)
    val (title1,desc1,imgPath1,content1) = News("标题","简介","路径","内容")
    println(title1)
    println(desc1)
    println(imgPath1)
    println(content1)
}

/**
 * 构造函数
 * get set
 * toString
 * hashcode
 * equeals
 * 参数
 * copy
 */
//数据类型
data class News(var title:String,var desc:String,var imgPath:String,var content:String)
```

### 密封类

```
fun main(args: Array<String>) {
    val startk1 = JonSnow3()
    val h1 = hasRight(startk1)
    println(h1)
}

/**
 * 判断有没有继承权
 * 先把所有有继承权的(数量固定) 放在一起  其他都不用管了
 */
fun hasRight(startk:NedStark):Boolean{
    return when(startk){
        is NedStark.AryaStark->true
        is NedStark.SansaStark->true
        is NedStark.RobStark->true
        is NedStark.BrandonStark->true
        else->false
    }
}

//斯塔克
sealed class NedStark{//密封类封装的是类型 类型是确定的
    class RobStark : NedStark()
    class SansaStark : NedStark()
    class AryaStark : NedStark()
    class BrandonStark : NedStark()
}

class JonSnow : NedStark()
class JonSnow2 : NedStark()
class JonSnow3 : NedStark()
class JonSnow4 : NedStark()
```

## 集合框架和lambda表达式

### List

- 基本使用

  ```
  fun main(args: Array<String>) {
      /*---------------------------- listof ----------------------------*/
      //林青霞  高圆圆 范冰冰
      val list1 = listOf("林青霞","高圆圆","林志玲")
      //不能添加元素
      //不能修改元素
      //只读List集合
  //    list1[0] = "柳岩"
  //    list1.add()
      list1.forEach {
          println(it)
      }
      /*---------------------------- mutableListOf ----------------------------*/
      //返回的也是ArrayList集合
      val list2 = mutableListOf("林青霞","高圆圆","林志玲")
      //修改第一个元素
      list2.set(0,"柳岩")
      //添加元素
      list2.add(1,"刘诗诗")
      println(list2)
  
      /*---------------------------- java的集合 ----------------------------*/
      val list3 = arrayListOf("林青霞","","")
      list3[2]= "liuyan"
      list3.add("abc")
      println("list3=$list3")
      val list4 = ArrayList<String>()
      list4.add("abc")
      println("list4=$list4")
  }
  
  ```

- 集合遍历

  ```
  fun main(args: Array<String>) {
      val list1 = mutableListOf("林青霞","高圆圆","林志玲")
      list1.forEach {
          println(it)
      }
  }
  
  ```


### Set

- 基本使用

  ```
  fun main(args: Array<String>) {
      //set集合 不能存放重复的元素
      //林青霞  高圆圆  柳岩  高圆圆
      /*---------------------------- setof ----------------------------*/
      val set1 = setOf("林青霞","高圆圆","柳岩","高圆圆")
      //修改
  //    set1.
      //添加
  //    println(set1)
      /*---------------------------- 可写可修改   ----------------------------*/
      //Treeset元素需要实现Comparable比较接口
      val set2 = mutableSetOf("林青霞","高圆圆","柳岩","高圆圆")
      //添加
      set2.add("刘诗诗")
  
      //修改
  //    set2.
      println(set2)
      /*---------------------------- java的set集合 ----------------------------*/
  //    hashSetOf<>()
  //    val treeSet = TreeSet<String>()
  //    treeSet.add("z")
  //    treeSet.add("f")
  //    treeSet.add("e")
  //    treeSet.add("a")
  //
  //    println(treeSet)
  
  //    val treeSet2 = TreeSet<Person>()
  //    treeSet2.add(Person("林青霞",20))
  //    treeSet2.add(Person("张曼玉",30))
  //    treeSet2.add(Person("张三",60))
  //    println(treeSet2)
  }
  //class Person(var name:String,var age:Int)
  ```

- set的遍历

  > 跟上面list类似

### Map

- 基本使用

  ```
  fun main(args: Array<String>) {
      /*---------------------------- 不可变 ----------------------------*/
      val map = mapOf("中国" to "China","英国" to "England","美国" to "USA")
      /*---------------------------- 可变的map ----------------------------*/
  //    mutableMapOf()
      /*---------------------------- java的集合 ----------------------------*/
  //    hashMapOf("" to "")
      val hashTable  =  Hashtable<String,String>()
  
  }
  ```

- map的遍历

  ```
  fun main(args: Array<String>) {
      val map = mapOf("中国" to "China","英国" to "England","美国" to "USA")
      //遍历map集合
      /*---------------------------- 遍历所有的key ----------------------------*/
      val keySet = map.keys
  //    keySet.forEach { println(it) }
      /*---------------------------- 遍历所有的value ----------------------------*/
      val values = map.values
  //    values.forEach { println(it) }
      /*---------------------------- key和value ----------------------------*/
  //    val entrys = map.entries
      map.forEach { t, u ->
          println("key=$t value=$u")
      }
      for ((key,value) in map) {
          println("key=$key value=$value")
      }
  }
  ```


### 闭包

```
fun main(args: Array<String>) {
    val result = test()
    result()
    result()
    result()
}
//闭包 lambda表达式 函数式编程 函数可以作为方法的返回值 方法可以作为函数的参数
//函数不保存状态  闭包可以让函数携带状态
fun test():()->Unit{
    var a  =10
    return {
        println(a)
        a++
    }
}
```

### 高阶函数

```
fun main(args: Array<String>) {
    val a = 10
    val b = 20
    //调用cacl函数传递需要的工具 返回对应的值
    //第三个参数应该是函数的引用
    val sum = calc(a,b,::add)
    val subResult = calc(a,b,::sub)
    println("sum=$sum")
    println("subResult=$subResult")
}

/**
 * a:传递的第一个数据
 * b:传递的第二个数据
 * block:传递的工具  add  sub
 * 返回值:使用工具求出的值
 */
//第三个参数是函数类型 说明kotlin里面的函数可以传递函数参数  如果函数里面传递函数参数的话 就称为高阶函数
fun calc(a:Int,b:Int,block:(Int,Int)->Int)=block(a,b)
//求两个数的和
fun add(a:Int,b:Int) = a+b
//求两个数的差
fun sub(a:Int,b:Int) = a-b
```



### lambda

- 基本使用

  >- lambda表达式有返回值的最后一行是返回值
  >- 没有返回值的,lambda表达式可以自由书写

  ![](imgs/1.png)

  ```
  fun main(args: Array<String>) {
      val a = 30
      val b = 20
      //只能我用这个工具  不能让其他人用
      //函数的参数定义出来之后 可以自动推断出类型  返回值不需要写 推断出当前的返回值类型
      //匿名函数 lambda表达式
      val sum = calc(a, b, { m, n ->
          m + n
      })
      val sum2 = calc(a, b, { m: Int, n: Int ->
          m + n + 10
      })
      var sum3 = calc(a, b) { x, y -> x + y + 30 }
      println("sum=$sum")
      println("sum2=$sum2")
      println("sum3=$sum3")
  }
  
  /**
   * a:传递的第一个数据
   * b:传递的第二个数据
   * block:传递的工具  add  sub
   * 返回值:使用工具求出的值
   */
  //第三个参数是函数类型 说明kotlin里面的函数可以传递函数参数  如果函数里面传递函数参数的话 就称为高阶函数
  fun calc(a: Int, b: Int, block: (Int, Int) -> Int) = block(a, b)
  ```

- lambda表达式单纯存在

  ```
  fun main(args: Array<String>) {
      //嵌套匿名函数
      //和sayHello一样功能的函数
  //    {
  //        println("hello")
  //    }()
      {
          println("hello")
      }?.invoke()
      //不能通过名称来调用
  
      //有名函数
      fun sayHello(){
          println("hello")
      }
      sayHello()
  }
  ```

- 有参数的lambda表达式

  ```
  fun main(args: Array<String>) {
      //嵌套有参的lambda表达式  实现a+b的和
      var result = {a:Int,b:Int->a+b}?.invoke(10,20)
      println(result)
  }
  ```

- 保存lambda表达式

  ```
  fun main(args: Array<String>) {
      //foreach it
      //保存lambda表达式
      var block:(()->Unit)? = null //可空的函数变量类型
  //            {
  //                println("hello")
  //            }
      //调用lambda表达式
  //    block()
      val result = block?.invoke()?:-1
      println(result)
      block = test
      block.invoke()
  //    test()
  }
  val test = {
      println("hello")
  }
  ```

- lambda中的it

  ```
  fun main(args: Array<String>) {
  	//高阶函数最后一个参数是函数的,可以把小括号前移
  	//如果该函数只有一个参数,可以省略,默认it代替
      var result = myPrint(10) {
          it + 20
          100
      }
      println(result)
  }
  
  //高阶函数
  fun myPrint(m: Int, block: (Int) -> Int): Int {
      val result = block(m)
      return result
  }
  ```

- 常见lambda表达式

  ```
  fun main(args: Array<String>) {
      /**
       * foreach是一个扩展函数
       * foreach参数是一个函数
       *
       */
      //foreach
      val str = "frank"
      str.forEach(::println)
   
      str.forEach({char->
          println(char)
      })
      //lambda表达式在最后一位  可以括号前移 迁移之后()没有参数 可以省略
      //lambda只有一个参数 只有一个参数可以使用it
      str.forEach{
          println(it)
      }
  
      /*---------------------------- indexof ----------------------------*/
      /**
       * indexOfFirst 是Array类的扩展函数
       * indexOfFirst参数是函数类型  函数参数类型时数组每一个元素的类型  函数的返回值是boolean类型
       */
      val array = arrayOf("林青霞","张曼玉")
      val index = array.indexOfFirst{
          it.startsWith("林")
      }
  
  }
  fun myPrint(char:Char){
      println(char)
  }
  ```


### 聚合函数

- 过滤

  ```
  val list = listOf(
      Girl("依儿", 18, 168, "山东"),
      Girl("笑笑", 19, 175, "河南"),
      Girl("小百合", 17, 155, "福建"),
      Girl("喵喵", 27, 164, "河南"),
      Girl("安琦", 19, 159, "河北"),
      Girl("叶子", 20, 160, "广东")
  )
  
  fun main(args: Array<String>) {
      /*---------------------------- 俺是河南里,俺只找河南的妹子 ----------------------------*/
      /**
       * filter参数是函数类型
       * filter的predicate函数的参数 是Girl类型
       * predicate 返回值 boolean
       * filter函数返回值 List集合
       */
      val filter = list.filter { it.place == "河南" }
      println(filter)
  }
  
  data class Girl(var name: String, var age: Int, var height: Int, var place: String)
  ```

  ```
  fun main(args: Array<String>) {
      val list = listOf("张三", "李四", "王五","张思", "赵六", "张四", "李五", "李六")
      val list2 = listOf("周芷若", "张无忌", "张五", "李善长", "林青霞", "李寻欢")
  
  //    找到第一个姓张的
      var find = list.find { it.startsWith("张") }
      println(find)
  //    把第一个集合中所有姓张的找出来
      var filter = list.filter { it.startsWith("张") }
      println(filter)
  //    把两个集合中所有姓张的找到并存放在同一个集合中
      var mutableList = mutableListOf<String>()
      list.filterTo(mutableList){it.startsWith("张")}
      list2.filterTo(mutableList){it.startsWith("张")}
      println(mutableList)
  
  //    把第一个集合中角标为偶数的元素找出来
      var filterIndexed = list.filterIndexed { index, s -> index % 2 == 0 }
      println(filterIndexed)
  }
  
  ```

- 排序

  ```
  fun main(args: Array<String>) {
      val list = listOf("z","b","d")
  //    正序排序  b d z
      println(list.sorted())
  //    倒序排序
      println(list.sortedDescending())
      val list1 = listOf(Person("林青霞",50),Person("张曼玉",30),Person("柳岩",70))
      println(list1.sortedBy { it.age })
      println(list1.sortedByDescending { it.age })
  }
  data class Person(var name:String,var age:Int)
  ```

- 分组

  ```
  fun main(args: Array<String>) {
      val list = listOf("张三", "李四", "王五", "赵六", "张四", "李五", "李六")
      //姓张的一组 姓李的一组 其他一组
      var groupBy = list.groupBy {
          var substring = it.substring(0,1)
          when (substring) {
              "张" -> "张"
              "李" -> "李"
              else -> "其他"
          }
      }
      println(groupBy)
  }
  ```

- 最值

  ```
  fun main(args: Array<String>) {
      val  list = listOf("z","g","r")
  //    最大值
      println(list.max())
  //    最小值
      println(list.min())
  
      val list1 = listOf(Person("林青霞",50),Person("张曼玉",30),Person("柳岩",70))
  //    对象最大值
  //    list1.maxBy { it.age }
      println(list1.maxBy { it.age })
  //    对象最小值
      println(list1.minBy { it.age })
  }
  data class Person(var name:String,var age:Int)
  ```

- 去重复

  ```
  fun main(args: Array<String>) {
      val list = listOf("张三","李四","王五","赵六","张四","李五","张三","李六")
  //    把重复的张三去掉
      var toSet = list.toSet()
      println(toSet)
      //list集合
      println(list.distinct())
  //    把重复的同姓的去掉
      println(list.distinctBy {
          //张  李  往  找
          it.substring(0, 1)
      })
  }
  ```

- 集合拆分

  ```
  fun main(args: Array<String>) {
      val list = listOf("张三","李四","王五","赵六","张四","李五","张三","李六")
      //姓张的一部分,另外的一部分
      var partition = list.partition { it.startsWith("张") }
      println(partition.first)
      println(partition.second)
  }
  ```

- 集合重新组合

  ```
  fun main(args: Array<String>) {
      val list1 = listOf(Person("林青霞",50),Person("张曼玉",30),Person("柳岩",70))
      //将Person里面每一个name获取
      val list2 = list1.map {
          it.name.substring(0,1)//姓氏
      }
      println(list2)
  }
  ```

- 四大函数

  ```
  fun main(args: Array<String>) {
      val list: ArrayList<String> = arrayListOf("林青霞", "范冰冰", "柳岩")
      /*---------------------------- apply ----------------------------*/
      /**
       * 任意类型都有apply函数扩展
       * apply参数是一个函数  T.() -> Unit 带接收者的函数字面值
       * lambda表达式里this代表调用的对象
       * 在lambda表达式里可以访问对象的方法
       * apply函数返回值就是调用者本身
       */
      list?.apply {
          add("张三")
          add("张三")
          add("李四")
      }
  
      set {
          name
      }
  
  
      /*---------------------------- let ----------------------------*/
      /**
       * 任意对象都有let扩展函数
       * let函数参数也是一个函数
       * 函数参数它的参数是调用者本身
       * let函数返回值是函数参数的返回值 就是lambda表达式的返回值
       */
      list?.let {
          it.add("张三")
          it.add("张三")
          it.add("张三")
          "哈哈"
          10
      }
      /*---------------------------- with ----------------------------*/
      /**
       * with是独立的函数  可以在任意地方调用
       * with函数需要接收两个参数
       * 第一个参数可以接收任意类型
       * 第二个参数是函数参数,并且这个函数参数是带接收者的函数字面值 接收者就是第一个参数
       * with函数返回值是第二个函数参数的返回值
       * 相当于apply和let的结合
       */
      with(list) {
          this.add("")
          this.add("")
          add("")
          "哈哈"
          10
      }
  
      /*---------------------------- run ----------------------------*/
      /**
       * 任意类型都有run扩展函数
       * run函数参数是待接收者的函数 接收者是调用者本身
       * run函数返回值就是函数参数的返回值
       */
      list.run {
          this.add("")
          "哈哈"
      }.length
  }
  
  /**
   * T.()->Unit
   * lambda相当于定义在T里面的函数  访问对象里面的字段或者方法
   * 调用的时候两种:1.Data().block()  2.block(Data())
   */
  fun set(block: Data.() -> Unit) {
      block(Data())
      Data().block()
  }
  
  class Data {
      var name = "张三"
      fun haha() {
  
      }
  
      fun sayHello() {
          this.haha()
          haha()
          name
      }
  }
  
  ```


