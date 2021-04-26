# Docker

## 1 什么是 Docker

![在这里插入图片描述](Docker学习.assets/20201021194159931.png)

> 官网的介绍是Docker is the world’s leading software container platform. 
>
> 官方给Docker的定位是一个应用容器平台。

## 2 为什么是Docker

```markdown
合作开发的时候，在本机可以跑，别人的电脑跑不起来
这里我们拿java Web应用程序举例，我们一个java Web应用程序涉及很多东西，比如jdk、tomcat、spring等等。当这些其中某一项版本不一致的时候，可能就会导致应用程序跑不起来这种情况。Docker则将程序直接打包成镜像，直接运行在容器中即可。

服务器自己的程序挂了，结果发现是别人程序出了问题把内存吃完了，自己程序因为内存不够就挂了

这种也是一种比较常见的情况，如果你的程序重要性不是特别高的话，公司基本上不可能让你的程序独享一台服务器的，这时候你的服务器就会跟公司其他人的程序共享一台服务器，所以不可避免地就会受到其他程序的干扰，导致自己的程序出现问题。Docker就很好解决了环境隔离的问题，别人程序不会影响到自己的程序。

公司要弄一个活动，可能会有大量的流量进来，公司需要再多部署几十台服务器
在没有Docker的情况下，要在几天内部署几十台服务器，这对运维来说是一件非常折磨人的事，而且每台服务器的环境还不一定一样，就会出现各种问题，最后部署地头皮发麻。用Docker的话，我只需要将程序打包到镜像，你要多少台服务，我就给力跑多少容器，极大地提高了部署效率。

```

## 3 Docker和虚拟机区别

> 关于Docker与虚拟机的区别，我在网上找到的一张图，非常直观形象地展示出来，话不多说，直接上图。

![在这里插入图片描述](Docker学习.assets/20201021194419123.png)

比较上面两张图，我们发现

- 虚拟机是携带操作系统，本身很小的应用程序却因为携带了操作系统而变得非常大，很笨重。
- Docker是不携带操作系统的，所以Docker的应用就非常的轻巧。
- 另外在调用宿主机的CPU、磁盘等等这些资源的时候，拿内存举例，`虚拟机`是利用Hypervisor去虚拟化内存，整个调用过程是虚拟内存->虚拟物理内存->真正物理内存，但是`Docker`是利用Docker Engine去调用宿主的的资源，这时候过程是虚拟内存->真正物理内存。

|             |              传统虚拟机              |            **Docker容器**             |
| ----------- | :----------------------------------: | :-----------------------------------: |
| 磁盘占用    |         几个GB到几十个GB左右         |          几十MB到几百MB左右           |
| CPU内存占用 |    虚拟操作系统非常占用CPU和内存     |          Docker引擎占用极低           |
| 启动速度    |      （从开机到运行项目）几分钟      |     （从开启容器到运行项目）几秒      |
| 安装管理    |          需要专门的运维技术          |            安装、管理方便             |
| 应用部署    |          每次部署都费时费力          |       从第二次部署开始轻松简捷        |
| 耦合性      | 多个应用服务安装到一起，容易互相影响 |    每个应用服务一个容器，达成隔离     |
| 系统依赖    |                  无                  | 需求相同或相似的内核，目前推荐是Linux |

## 4 Docker 的核心

![在这里插入图片描述](Docker学习.assets/2020102119445489.png)

- `镜像`: 一个镜像代表一个应用环境,他是一个只读的文件,如 mysql镜像,tomcat镜像,nginx镜像等
- `容器`:镜像每次运行之后就是产生一个容器,就是正在运行的镜像,特点就是可读可写
- `仓库`:用来存放镜像的位置,类似于maven仓库,也是镜像下载和上传的位置
- `dockerFile`:docker生成镜像配置文件,用来书写自定义镜像的一些配置
- `tar`:一个对镜像打包的文件,日后可以还原成镜像

## 5 Docker的安装(centos)

### 5.1 卸载原有 docker

```markdown
$ sudo yum remove docker \
                  docker-client \
                  docker-client-latest \
                  docker-common \
                  docker-latest \
                  docker-latest-logrotate \
                  docker-logrotate \
                  docker-engine

```

### 5.2 安装docker

> 官网：https://docs.docker.com/engine/install/centos/



```markdown
安装docker依赖
$ sudo yum install -y yum-utils

设置docker的yum源
$ sudo yum-config-manager \
    --add-repo \
    https://download.docker.com/linux/centos/docker-ce.repo

安装最新版的docker
$ sudo yum install docker-ce docker-ce-cli containerd.io

指定版本安装docker
$ yum list docker-ce --showduplicates | sort -r
$ sudo yum install docker-ce-<VERSION_STRING> docker-ce-cli-<VERSION_STRING> containerd.io
$ sudo yum install docker-ce-18.09.5-3.el7 docker-ce-cli-18.09.5-3.el7 containerd.io


启动docker
$ sudo systemctl start docker

关闭docker
$ sudo systemctl stop docker

测试docker安装
$ sudo docker run hello-world

```

sudo mkdir -p /etc/docker sudo tee /etc/docker/daemon.json <<-'EOF' {  "registry-mirrors": ["https://dq5u74r1.mirror.aliyuncs.com"] } EOF sudo systemctl daemon-reload sudo systemctl restart docker

## 6 Docker 配置阿里镜像加速服务

### 6.1 docker 运行流程

![在这里插入图片描述](Docker学习.assets/20201021194543922.png)

### 6.2 docker配置阿里云镜像加速

> 访问阿里云登录自己账号查看docker镜像加速服务

```markdown
sudo mkdir -p /etc/docker
sudo tee /etc/docker/daemon.json <<-'EOF'
{
  "registry-mirrors": ["https://dq5u74r1.mirror.aliyuncs.com"]
}
EOF
sudo systemctl daemon-reload
sudo systemctl restart docker
```

验证docker的镜像加速是否生效

```markdown
[root@localhost ~]# docker info
..........
127.0.0.0/8
Registry Mirrors:
'https://lz2nib3q.mirror.aliyuncs.com/'
Live Restore Enabled: false
Product License: Community Engine

```

## 7 Docker的入门应用

### 7.1 docker 的第一个程序

```markdown
docker  run hello-world
```

```markdown
[root@localhost ~]# docker run hello-world

Hello from Docker!
This message shows that your installation appears to be working correctly.

To generate this message, Docker took the following steps:
 1. The Docker client contacted the Docker daemon.
 2. The Docker daemon pulled the "hello-world" image from the Docker Hub.
    (amd64)
 3. The Docker daemon created a new container from that image which runs the
    executable that produces the output you are currently reading.
 4. The Docker daemon streamed that output to the Docker client, which sent it
    to your terminal.

To try something more ambitious, you can run an Ubuntu container with:
 $ docker run -it ubuntu bash

Share images, automate workflows, and more with a free Docker ID:
 https://hub.docker.com/

For more examples and ideas, visit:
 https://docs.docker.com/get-started/

```

## 8 常用命令

### 8.1 辅助命令

```markdown
# 1.安装完成辅助命令

docker version  --	查看docker的信息
docker info		--	查看更详细的信息
docker --help    --	帮助命令

```

### 8.2 Images 镜像命令

>  1.查看本机中所有镜像

docker images	   --------->	列出本地所有镜像
-a	 列出所有镜像（包含中间映像层）
-q	 只显示镜像id

> 2.搜索镜像

docker search [options] 镜像名 -------->	去dockerhub上查询当前镜像
-s 指定值		列出收藏数不少于指定值的镜像
--no-trunc	  显示完整的镜像信息

> 3.从仓库下载镜像

docker pull  镜像名  [:TAG|@DIGEST]	-------> 下载镜像

> 4.删除镜像

docker rmi 镜像名	------->  删除镜像
-f		强制删除

### 8.3 Contrainer 容器命令



```markdown
# 1.通过镜像运行一个容器
docker run   镜像名:tag |  镜像id

   a.以tomcat镜像为例运行tomcat容器(运行tomcat实例)
   docker run tomcat:8 0-jre8

   b.宿主机端口与容器中端口进行映射-P
   docker run -P 8080( 系统上外部端口):8080(容器内服务监听的端口) toncat:8. 0-jre8(或者直接写IMAGE的ID) 
   
   c.启动容器映射外部端口后台启动
  docker run -P 8080( 系统上外部端口):8080(容器内服务监听的端口) -d(后台运行)tomcat:8.0-jre8


   d.启动容器指定名称后台运行端口映射
docker run -d(后台运行) -P 8081:8080(书写多个) --name tomcat0l(容器名称)
tomcat:8. 0-jre8(镜像名称: tag)




# 2.查看当前运行的容器
docker ps       查看正在运行容器

docker Ps -a    查看所有容器(运行&非运行)

docker ps -q    返回正在运行容器id

docker ps -qa   返回所有容器的id
```

![image-20210426185817277](Docker学习.assets/image-20210426185817277.png)

```markdown
3.停止重启容器的命令

docker start   容器名字或者容器id          开启容器
docker restart 容器名或者容器id            重启容器
docker stop    容器名或者容器id            正常停止容器运行
docker kill    容器名或者容器id           立即停止容器运行

```









```markdown
 1 运行容器
	docker run 镜像名	------------	镜像名新建并启动容器
    --name 					为容器起一个别名
    -d							启动守护式容器（在后台启动容器）
    -p 							映射端口号：原始端口号		 指定端口号启动

	例：docker run -p 8080:8080 tomcat:8.5.61-jdk8-adoptopenjdk-openj9 
	
2 查看运行的容器
	docker ps		--------	列出所有正在运行的容器
	-a			正在运行的和历史运行过的容器
	-q			静默模式，只显示容器编号

3 停止|关闭|重启容器
	docker start   容器名字或者容器id  ----- 开启容器
	docker restart 容器名或者容器id   ----- 重启容器
	docker stop  容器名或者容器id 	   ------ 正常停止容器运行
	docker  kill  容器名或者容器id    ----- 立即停止容器运行

4 删除容器
	docker rm -f 容器id和容器名     
	docker rm -f $(docker ps -aq)	---------	删除所有容器

5.查看容器内进程
	docker top 容器id或者容器名 ------ 查看容器内的进程

6 查看查看容器内部细节
	docker inspect 容器id 		------ 查看容器内部细节

7 查看容器的运行日志
	docker logs [OPTIONS] 容器id或容器名	 ------ 查看容器日志
    -t			 加入时间戳
    -f			 跟随最新的日志打印
    --tail 	 数字	显示最后多少条


```



