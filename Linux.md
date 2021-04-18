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