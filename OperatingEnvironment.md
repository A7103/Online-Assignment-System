# 代码运行环境说明

## 本地开发环境：

### 系统环境：
##### Edition : Windows 10 CoreCountrySpecific
##### version : 2004

### 开发工具：
Navicat Premium 、DataGrip、IDEA 、VsCode
### 前端运行环境：

- Node.js 14.15.0版本
- vue-cli 4.5.8版本
- vue 2.6.10 版本
- Element UI 组件库 2.13 版本
- 前端的框架是来自[PanJiaChen](https://github.com/PanJiaChen) 大佬的[vue-admin-template](https://github.com/PanJiaChen/vue-admin-template)
### 后端运行环境：

- JDK 1.8.0_201 版本
- Spring Boot 2.3.4.RELEASE 版本
- MySQL 5.6.49版本
- TODO 忘了加Maven的版本了
- [Apache OpenOffice 4.1.10 released Windows 32-bit (x86) (EXE)](https://sourceforge.net/projects/openofficeorg.mirror/files/4.1.10/binaries/zh-CN/Apache_OpenOffice_4.1.10_Win_x86_install_zh-CN.exe/download) 版本

#### 其他运行环境：
MySQL数据库采用Docker进行初始化，docker-compose文件内容如下：
```
version: '3.3'
services:
  database:
    image: mysql:5.6.49
    container_name: mysql
    restart: always
    environment:
      MYSQL_DATABASE: 'graduation'   
      MYSQL_USER: 'graduation'
      MYSQL_PASSWORD: 'xSRB6wFH2ycfkJce'      
      MYSQL_ROOT_PASSWORD: '7169c3adfa92eba3'
    ports:
      - '3306:3306'
    expose:
      - '3306'
    volumes:
      - './.mysql-data/db:/var/lib/mysql'
```
## 服务器部署环境：

### 系统环境：

- 阿里云服务器ECS实例
- CentOS 8.1 64位
- Node.js 14.15.0版本
- JDK 1.8.0_201 版本
- MySQL 5.6.49版本 (与本地开发时一样，使用docker初始化数据库)
- [Apache OpenOffice 4.1.10 released Linux 64-bit (x86-64) (RPM)](https://sourceforge.net/projects/openofficeorg.mirror/files/4.1.10/binaries/zh-CN/Apache_OpenOffice_4.1.10_Linux_x86-64_install-rpm_zh-CN.tar.gz/download) 版本

### 部署相关：
进入前端代码根目录的.env.production文件，将其中的 base api改成自己的路径。

前端在本地代码目录终端下，使用下列命令进行build编译，并将build完的文件移动至服务器。
```
npm run build:prod
```
### 后端在本地代码目录终端下，使用
```
mvn clean package -Dmaven.test.skip=true
```
### 进行打包，将打包完的文件移动至服务器后使用以下命令启动项目并设置进程守护
```
nohup java -jar back-0.0.1-SNAPSHOT.jar --serverport=8080 >log.log 2>&1 &
```

