
<div align="center">
  <img align="center" src="./img/logo.png" width="100" height="100" />
</div>

<h2 align="center">JarEditor <sup><em>liubsyy</em></sup></h2>

<h4 align="center"><a href="./README.md">English</a> | <strong>简体中文</strong></h4>

[![License](https://img.shields.io/github/license/Liubsyy/JarEditor?color=blue)](./LICENSE)
[![downloads](https://img.shields.io/jetbrains/plugin/d/24397)](https://plugins.jetbrains.com/plugin/24397-jareditor)
[![release](https://img.shields.io/jetbrains/plugin/v/24397?label=version)](https://plugins.jetbrains.com/plugin/24397-jareditor)
![sdk](https://img.shields.io/badge/plugin%20sdk-IDEA%202020.3-red.svg)
<a href="https://hellogithub.com/repository/03b39a2d1a55407797db59737724ce7c" target="_blank"><img src="https://abroad.hellogithub.com/v1/widgets/recommend.svg?rid=03b39a2d1a55407797db59737724ce7c&claim_uid=iEnYZr4sASMjWJb&theme=small" alt="Featured｜HelloGitHub" /></a>

可直接修改jar包内文件的IDEA插件，无需解压

**Plugin marketplace** : [https://plugins.jetbrains.com/plugin/24397-jareditor](https://plugins.jetbrains.com/plugin/24397-jareditor)

## 功能
- 直接编辑jar包内class/resource文件，无需解压
- 添加/删除/重命名jar包内文件/文件夹
- 搜索jar包的内容
- jar内复制/粘贴文件到外部剪切板
- 支持SpringBoot jar/嵌套jar
- 支持kotlin
- 可导出source jar
- 支持class字节码修改工具 : Javassist/VisualClassBytes
- 反编译器 : Fernflower/CFR/Procyon

## 快速开始

### 1. 从插件市场安装插件
首先从市场安装插件 JarEditor，IDEA版本 >= **2020.3**

<img src="./img/JarEditor_install.png" width="800" height="606" />


### 2. 编辑并构建 Jar
安装完成后，在.class反编译文件中可以看到切换到Jar Editor的tab页。

> **外部jar** ：File->Project Structure->Libraries->Add Library，然后就可以看到反编译的jar了。<br>
> **嵌套jar** : 嵌套jar上右键->JarEditor->Structure->Expand Nested Jar

<img src="./img/JarEditor_main.png" width="800" height="506" />

修改完成后，点击**Save（Compile）**，编译并保存当前修改的java内容。

最后点击**Build Jar**，将编译保存的类文件写入Jar包中。

修改jar包中的资源文件也是支持的。

下面是一个演示例子:

<img src="./img/JarEditor_demo.gif"  width="800" height="511" />

### 3. 修改字节码工具
针对混淆jar，反编译的效果不是很好，此时可以使用直接修改字节码工具
点击 **Class bytes tool** 图标选择工具

- **Javassist** : 可以对字段/方法/构造函数/静态代码块进行增删改 (包括内部类)

<img src="./img/JarEditor_javassist.png" width="800" height="600" />

- [**Visual ClassBytes**](https://github.com/Liubsyy/VisualClassBytes) : Class字节码编辑器,基于 ASM 和 BCEL

<img src="./img/vcb_main.png" width="800" height="600" />

### 4. 其他操作
在jar包的项目视图中，右键可以看到**JarEditor->New/Delete**等操作，可以在jar内添加/删除/重命名/复制/粘贴/导出/备份文件。

<img src="./img/JarEditor_new_delete.png" width="600" height="550" />

点击 **Search** 图标，可以搜索jar包的内容，如果是class jar将根据反编译的内容进行搜索

<img src="./img/JarEditor_search.png" width="600" height="470" />



## 一些机制
- 编译依赖的JDK是你的SDK列表中的JDK。您可以选择SDK和编译类的目标版本。
- 编译java时所依赖的classpath就是项目的Libraries依赖。如果找不到依赖包，可以添加Libraries(File->Project Structure->Libraries)。
- Save(Compile)会将修改后的文件保存到jar包所在目录的子目录**jar_edit_out**中，Build Jar会将修改的文件增量写入jar中，最后删除这个临时目录。

### SDK Default对应JDK版本

编译选择 **SDK Default** 时，使用的是Jetbrains集成的运行时JDK(JBR)，如果不选SDK Default则是具体用户安装的JDK

IDEA|JDK
---|---
IDEA 2020.3 - IDEA 2022.1 |JBR JDK11
IDEA 2022.2 - IDEA 2024.1 |JBR JDK17
IDEA 2024.2 及更高版本 |JBR JDK21
