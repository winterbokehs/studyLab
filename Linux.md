# xftp传文件

用命令更改文件夹权限：chmod 777 文件夹名（需要赋予权限的文件夹）

# 防火墙

>  **1:查看防火状态**

**systemctl status firewalld**

**service  iptables status**

>  **2:暂时关闭防火墙**

**systemctl stop firewalld**

***\*service  iptables stop\****

>  **3:永久关闭防火墙**

**systemctl disable firewalld**

***\*chkconfig iptables off\****

>  **4:重启防火墙**

**systemctl enable firewalld**

**service iptables restart** 

> **5:永久关闭后重启**

//暂时还没有试过

**chkconfig iptables on**





# 压缩

1.压缩命令：

　　命令格式：tar -zcvf  压缩文件名.tar.gz  被压缩文件名

   可先切换到当前目录下。压缩文件名和被压缩文件名都可加入路径。

 

2.解压缩命令：

　　命令格式：tar -zxvf  压缩文件名.tar.gz

　　解压缩后的文件只能放在当前的目录。

# 移动

[root@localhost ~]# mv 【选项】 源文件 目标文件

选项：

- -f：强制覆盖，如果目标文件已经存在，则不询问，直接强制覆盖；
- -i：交互移动，如果目标文件已经存在，则询问用户是否覆盖（默认选项）；
- -n：如果目标文件已经存在，则不会覆盖移动，而且不询问用户；
- -v：显示文件或目录的移动过程；
- -u：若目标文件已经存在，但两者相比，源文件更新，则会对目标文件进行升级；



# 进程

1、ps 命令用于查看当前正在运行的进程。
grep 是搜索
例如： ps -ef | grep java
表示查看所有进程里 CMD 是 java 的进程信息

2、ps -aux | grep java
-aux 显示所有状态
ps



\3. kill 命令用于终止进程
例如： kill -9 [PID]
-9 表示强迫进程立即停止
通常用 ps 查看进程 PID ，用 kill 命令终止进程



# 记事本

：q   退出

：wq   保存并退出

: qa!不保存退出

# nginx

```bash
nginx -c /usr/local/nginx/conf/nginx.conf

//安装路径nginx的路径
```

```markdown
 3.安装 nginx

     nginx-1.1.1.tar.gz  上传到/home 目录下面

     1) 解压 nginx

       tar zxvf    nginx-1.1.1.tar.gz  解压 nginx 后 /home 下面会有nginx-1.1.1 文件夹

      2 配置nginx

       cd   nginx-1.1.1

       ./configure --prefix=/usr/local/nginx --with-http_stub_status_module

       3)make

       在linux 中输入 make 命令后屏幕会生成一堆文件，不用去管它

     4)安装

       在linux 中输入 make install

      5) 检查是否安装成功  

         cd  /usr/local/nginx/sbin

         ./nginx -t 

    报错：Nginx: error while loading shared libraries: libpcre.so.1 ，就像项目缺少依赖包一样    

    [ew69@SCLABHADOOP01 lib]$ cd /lib  

    [ew69@SCLABHADOOP01 lib]$ ls *pcre*  

    libpcre.so.0  libpcre.so.0.0.1   

    添加软链接:

    Shell代码  

    [ew69@SCLABHADOOP01 lib]$ ln -s /lib/libpcre.so.0.0.1 /lib/libpcre.so.1

     再次运行./nginx -t

  结果显示：

        nginx: the configuration file /usr/local/nginx/conf/nginx.conf syntax is ok

        nginx: configuration file /usr/local/nginx/conf/nginx.conf test is successful

说明nginx安装成功

       6)启动nginx 

          cd  /usr/local/nginx/sbin 目录下面 输入 ./nginx  启动 nginx

       7 )检查是否启动成功

      netstat  -ntlp|grep nginx    可以看到nginx已经启动成功了，占用80端口

      ie 浏览器中输入 http://192.168.15.132（此处换成自己的ip）

      发现网页无法访问，将防火墙关闭

     /etc/init.d/iptables stop

     关闭了防火墙以后，一切访问都正常, 可以看到

Welcome to nginx!
```

