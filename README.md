# 基于Spring Boot的作业提交与批阅系统
这是我的毕业设计，是一个基于SpringBoot所编写的作业提交与批阅系统。
- 采用前后端分的架构，前端使用Vue+Element UI进行构建。（[前端代码]( https://github.com/A7103/Online-Assignment-System-Front-end )）
- <a href="/OperatingEnvironment.md">代码运行环境 </a>

### 目前已完成以下功能：
####学生端：
- [x] 选择课程加入
- [x] 查看课程信息
- [x] 查看作业信息
- [x] 多文件上传
- [x] 上传文件时，若有压缩包类型文件则自动解压
- [x] 上传文件重名时自动增加后缀 (列如提交了两个A.cpp，将会被分别命名为A.cpp、A(2).cpp)
####教师端：
- [x] 课程管理
    - [x] 课程创建、删除、修改 
    - [x] 通过Excel表格导入课程学生名单
    - [x] 删除课程学生（删除其所有关于此课程的信息，包括作业文件）
- [x] 作业管理
    - [x] 作业创建、删除、修改
    - [x] 在线浏览代码文件，并做到语法高亮与行号显示
    - [x] 在线浏览Word、PPT、Excel 文件（使用[Apache OpenOffice](https://www.openoffice.org/download/) 实现)
    - [x] 文件下载
####管理员端：
- [x] 账户管理（创建、删除、限制使用）
- [x] 课程管理
    - [x] 课程创建、删除、修改
    - [x] 通过Excel表格导入课程学生名单
    - [x] 删除课程学生（删除其所有关于此课程的信息，包括作业文件）
- [x] 作业管理（创建、删除、修改）
    - [x] 根据不同老师的不同课程创建、删除、修改作业
    - [x] 在线浏览代码文件，并做到语法高亮与行号显示
    - [x] 在线浏览Word、PPT、Excel 文件（使用[Apache OpenOffice](https://www.openoffice.org/download/) 实现)
    - [x] 文件下载

