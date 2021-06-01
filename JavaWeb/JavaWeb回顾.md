

# Servlet：

## 1. 概念

> 运行在服务器端的小程序

- Servlet就是一个接口，定义了Java类被浏览器访问到(  tomcat 识别 ) 的规则。
- 将来我们自定义一个类，实现Servlet接口， 复写方法。

## 2.QuickStart

**创建JavaEE项目**

**导入pom文件**

```xml
        <dependency>
            <groupId>org.apache.tomcat</groupId>
            <artifactId>servlet-api</artifactId>
            <version>6.0.29</version>
        </dependency>
```



**定义一个类，实现Servlet接口，实现接口中的抽象方法 **

```java
public class Servletdemo1 implements Servlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        System.out.println("init---------------");
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        System.out.println("hello service");
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}

```



**配置Servlet**

web.xml

```xml
<web-app>
  <display-name>Archetype Created Web Application</display-name>
  <servlet>
    <servlet-name>day01</servlet-name>
    <servlet-class>com.example.Servletdemo1</servlet-class>
  </servlet>
  <servlet-mapping>
    <servlet-name>day01</servlet-name>
    <url-pattern>/demo1</url-pattern>
  </servlet-mapping>
</web-app>
```

配置tomcat，地址栏输入http://localhost:8080/day01/demo1

## 3 执行原理:

1.当服务器接受到客户端浏览器的请求后，会解析请求URL路径，获取访问的Servlet的 资源路径
2.查找web. xml文件，是否有对应的<url-pattern>标签体内容。
3.如果有，则在找到对应的<servlet-class>全类名

4.tomcat会将字节 码文件加载进内存，并且创建其对象

5.调用其方法

## 4 Servlet中的生命周期:

1. 被创健：执行**init**方法，只执行一次

   ```markdown
   # Servlet什么时候被创建?
        * 默认情况下，第一次被访问时，Servlet被创建
        * 可以配道执行Servlet的创建时机。
        * 在<servlet>标签下配道
   1.第一次被访问时，创建
      * <load-on-startup>的值为负数
   2.在服务器启动时，创建
      * <load-on-startup>的值为0或正整数
      
   # Servlet的init方法， 只执行一次，说明一个Servlet在内存中只存在一 个对象，Servlet是单例的
      *多个用户同时访问时，可能存在线程安全问题。
      *解决:尽量不要在Servlet中定义成员变量。即使定义了成员变量，也不要修改值
   
   ```

   

2. 提供服务：执行**service**方法，执行多次

   ```markdown
   * 每次访问Servlet时，Service方法都会被调用一次。
   ```

   

3. 被销毁：执行**destroy**方法，只执行一次

   ```markdown
   * Servlet被销毁时执行。服务器关闭时，Servlet被销毁
   * 只有服务器正常关闭时,才会执行destroy方法。
   * destroy方法在Servlet被销毁之前执行，一般用于释放资源
   
   ```

## 5 Servlet3.0注解开发

```markdown
* 好处:
  * 支持注解配置。可以不要web.xml了。

* 步骤:
   1.创建JavaEE项目，选挥Servlet的版本3.0以 上，可以不创建web . xml
   2.定义一个类，实现Servlet接口
   3.复写方法
   4.在类上使用@WebServlet注解，进行配道

* @webServlet("资源路径")

```

```java
@WebServlet("/demo2")
public class HelloServlet implements Servlet {

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        System.out.println("init...");
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
```

## 6. Servlet的体系结构	

```markdown
    Servlet -- 接口
		|
	GenericServlet -- 抽象类
		|
	HttpServlet  -- 抽象类

	* GenericServlet：将Servlet接口中其他的方法做了默认空实现，只将service()方法作为抽象
		* 将来定义Servlet类时，可以继承GenericServlet，实现service()方法即可

   #  HttpServlet：对http协议的一种封装，简化操作
		1. 定义类继承HttpServlet
		2. 复写doGet/doPost方法，不要覆写service方法
```

## 7.Servlet相关配置

1. urlpartten:Servlet访问路径

2. 一个Servlet可以定义多个访问路径 ： **@WebServlet({"/d4","/dd4","/ddd4"})**

3. 路径定义规则：

  1. /xxx：路径匹配

  2. /xxx/xxx:多层路径，目录结构

  3. *.do：扩展名匹配

     

## 8 Servlet三大作用域：

   1、**request作用域**：针对同一次请求，一般存放提示信息

         每一次请求都是一个新的request对象，如果Web组件之间需要共享同一个请求中的数据，只能使用request作用域
    
         对象类型 javax.servlet.http.HttpServletRequest

   2、**session作用域**：针对同一次会话，一般存放用户信息

         每一次会话都是一个新的session对象，如果需要一个会话中的多个同一个请求之间需要共享数据，只能使用session作用域
    
         对象类型 javax.servlet.http.HttpSession

   3、**application作用域**：针对同一个web项目/web应用（ServletContext）

        Tomcat从启动到关闭，web项目中有且仅有一个application对象，作用于整个Web应用，可以实现多个会话之间的数据共享
    
        对象类型 javax.servlet.ServletContext

一、请求作用域(request作用域)：1次请求中数据是有效的
    只要发送一个请求就会创建一个request对象，这个对象只在本次请求中有效。

```markdown
一般用于不同页面之间的同一请求，比如表单的传值，a链接的传值跳转等。

通过 HttpServletRequest  request 对象直接获取方法

常用的方法有：request.setAttribute(), request.getAttribute(), request.removeAttribute(), request.getParameter()等。
```

 

二、 会话作用域（session作用域）：1次会话中数据是有效的
        服务器会为每一个会话创建一个session对象，session中的数据可以被本次会话中的所有servlet访问，会话是从浏览器打开开始，到关闭浏览器结束。

```markdown
 # session常用于登录验证，
 登录成功后浏览器会为其分配一个session键值对，之后的访问权限判断键值对就可以了

 获取方式：HttpSession session = request.getSession();
```

Session的超时时间

  Tomcat中Session的默认超时时间为20分钟。

   java通过 session.setMaxInactiveInterval(int seconds) 修改超时时间。

  也可以修改web.xml 配置超时时间。例如修改为15分钟：

```xml
  <session-config>
  	<session-timeout>15</session-timeout> <!-- 单位： 分钟 -->
  </session-config>
</web-app>
```



三、全局作用域 (application作用域)：只要服务器不关，数据一直是有效的
      application是应用程序作用域，是从程序开始运行到运行停止。
      注意：服务器只会创建一个ServletContext 对象

获取方式：

```java
    ServletContext application1 = super.getServletContext();
    ServletContext application2 = req.getServletContext();
    ServletContext application3 = req.getSession().getServletContext();
```


# HTTP：

> **概念**：Hyper Text Transfer Protocol 超文本传输协议

* *传输协议*：定义了，客户端和服务器端通信时，发送数据的格式
* *特点*：
	1. 基于TCP/IP的高级协议
	2. 默认端口号:80
	3. 基于请求/响应模型的:一次请求对应一次响应
	4. 无状态的：每次请求之间相互独立，不能交互数据

* *历史版本*：
	* 1.0：每一次请求响应都会建立新的连接
	* 1.1：复用连接

# Request：

### *请求消息数据格式*

> 客户端发送给服务器端的数据

1. **请求行**
   请求方式    请求url         请求协议/版本
   GET             /login.html	HTTP/1.1

   * 请求方式：

     ```markdown
     HTTP协议有7中请求方式，常用的有2种
     
     * GET：
       1. 请求参数在请求行中，在url后。
       2. 请求的url长度有限制的
       3. 不太安全
       
       
     * POST：
       1. 请求参数在请求体中
       2. 请求的url长度没有限制的
       3. 相对安全
     ```

     

2. **请求头**：客户端浏览器告诉服务器一些信息
   请求头名称: 请求头值

   * 常见的请求头：

     ```markdown
     1. User-Agent：浏览器告诉服务器，我访问你使用的浏览器版本信息
        * 可以在服务器端获取该头的信息，解决浏览器的兼容性问题
     
     2. Referer：http://localhost/login.html
        * 告诉服务器，我(当前请求)从哪里来？
          * 作用：
            1. 防盗链：
            2. 统计工作：
     ```

     

3. **请求空行**
   空行，就是用于分割POST请求的请求头，和请求体的。

4. **请求体(正文)**：

   * 封装POST请求消息的请求参数的

   * 字符串格式：

     ```markdown
      * POST /login.html	HTTP/1.1
          Host: localhost
          User-Agent: Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:60.0) Gecko/20100101 Firefox/60.0
          Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
          Accept-Language: zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2
          Accept-Encoding: gzip, deflate
          Referer: http://localhost/login.html
          Connection: keep-alive
          Upgrade-Insecure-Requests: 1
     
          username=zhangsan	
     ```

     

###  *request对象和response对象的原理*

1. **request**和**response**对象是由服务器创建的。我们来使用它们
2. **request**对象是来获取请求消息，**response**对象是来设置响应消息

### *request对象继承体系结构*

```markdown
    ServletRequest		--	接口
		|	继承
	HttpServletRequest	-- 接口
		|	实现
	org.apache.catalina.connector.RequestFacade 类(tomcat)
```

### *request功能*：

```markdown
1. 获取请求消息数据
		1. 获取请求行数据
		    GET /day14/demo1?name=zhangsan HTTP/1.1
			方法：
				1. 获取请求方式 ：GET
					 String getMethod()  
				
				2. (*)获取虚拟目录：/day14
					 String getContextPath()
				
				3. 获取Servlet路径: /demo1
					 String getServletPath()
				
				4. 获取get方式请求参数：name=zhangsan
					 String getQueryString()
				
				5. (*)获取请求URI：/day14/demo1
					 String getRequestURI():		/day14/demo1
					 StringBuffer getRequestURL()  :http://localhost/day14/demo1

					 URL:统一资源定位符 ： http://localhost/day14/demo1	中华人民共和国
					 URI：统一资源标识符 : /day14/demo1					共和国
				
				6. 获取协议及版本：HTTP/1.1
					 String getProtocol()

				7. 获取客户机的IP地址：
					 String getRemoteAddr()


2. 获取请求头数据
			 方法：
				 (*)String getHeader(String name):通过请求头的名称获取请求头的值
				 Enumeration<String> getHeaderNames():获取所有的请求头名称
			


3. 获取请求体数据:
			 请求体：只有POST请求方式，才有请求体，在请求体中封装了POST请求的请求参数
			 步骤：
				1. 获取流对象
				 BufferedReader getReader()：获取字符输入流，只能操作字符数据
				 ServletInputStream getInputStream()：获取字节输入流，可以操作所有类型数据
						

				2. 再从流对象中拿数据
```

### *其他功能*：

```markdown
# 获取请求参数通用方式：不论get还是post请求方式都可以使用下列方法来获取请求参数
	*1. String getParameter(String name):根据参数名称获取参数值                                     username=zs&password=123
	
	2. String[] getParameterValues(String name):根据参数名称获取参数值的数组                        hobby=xx&hobby=game
	
	3. Enumeration<String> getParameterNames():获取所有请求的参数名称
	
	*4. Map<String,String[]> getParameterMap():获取所有参数的map集合
```

```markdown
# 中文乱码问题：
				* get方式：tomcat 8 已经将get方式乱码问题解决了
				* post方式：会乱码
					* 解决：在获取参数前，设置request的编码                          							request.setCharacterEncoding("utf-8");
```

 

### *请求转发*：

> 一种在服务器内部的资源跳转方式

**步骤**：

 1. 通过request对象获取请求转发器对象：

    ```java
    RequestDispatcher getRequestDispatcher(String path)
    ```

2.使用RequestDispatcher对象来进行转发：

         ```java
forward(ServletRequest request, ServletResponse response) 
         ```

3.使用：

```java
 request.getRequestDispatcher("/getServlet").forward(request,response);
```



**特点**：

 1. 浏览器地址栏路径不发生变化
 2. 只能转发到当前服务器内部资源中。
 3. 转发是一次请求

**共享数据**：
	*域对象：一个有作用范围的对象，可以在范围内共享数据
	*request域：代表一次请求的范围，一般用于请求转发的多个资源中共享数据
	*方法：

           ```java
    	1. void setAttribute(String name,Object obj):存储数据
    	2. Object getAttitude(String name):通过键获取值
    	3. void removeAttribute(String name):通过键移除键值对
           ```

**获取ServletContext**：

```java
ServletContext getServletContext()
```

# Response

>  *响应消息*：服务器端发送给客户端的数据

### 数据格式

```markdown
   # 响应行
		1. 组成：协议/版本 响应状态码 状态码描述
		2. 响应状态码：服务器告诉客户端浏览器本次请求和响应的一个状态。
			1. 状态码都是3位数字 
			2. 分类：
				1. 1xx：服务器就收客户端消息，但没有接受完成，一段时间后，发送1xx多状态码
				2. 2xx：成功。代表：200
				3. 3xx：重定向。代表：302(重定向)，304(访问缓存)
				4. 4xx：客户端错误。
					* 代表：
						* 404（请求路径没有对应的资源） 
						* 405：请求方式没有对应的doXxx方法
				5. 5xx：服务器端错误。代表：500(服务器内部出现异常)
   # 响应头：
		    1. 格式：头名称： 值
		    2. 常见的响应头：
			   1. Content-Type：服务器告诉客户端本次响应体数据格式以及编码格式
			   2. Content-disposition：服务器告诉客户端以什么格式打开响应体数据
			  	 * 值：
				 	* in-line:默认值,在当前页面内打开
					* attachment;filename=xxx：以附件形式打开响应体。文件下载
   # 响应空行
   # 响应体:传输的数据
```

```markdown
#  响应字符串格式
	HTTP/1.1 200 OK
	Content-Type: text/html;charset=UTF-8
	Content-Length: 101
	Date: Wed, 06 Jun 2018 07:08:42 GMT

	<html>
	  <head>
	    <title>$Title$</title>
	  </head>
	  <body>
	  hello , response
	  </body>
	</html>
```
### Response对象

**功能**：设置响应消息

1. *设置响应行*
	1. 格式：HTTP/1.1 200 ok
	2. 设置状态码：setStatus(int sc) 
2. *设置响应头*：setHeader(String name, String value) 
	
3. *设置响应体*：
	* 使用步骤：
		1. 获取输出流
			* 字符输出流：PrintWriter getWriter()

			* 字节输出流：ServletOutputStream getOutputStream()

		2. 使用输出流，将数据输出到客户端浏览器

### 完成重定向

* 重定向：资源跳转的方式
	
	 * 代码实现：
	
	   ```java
	   
	   			//1. 设置状态码为302
	   	        response.setStatus(302);
	   	        //2.设置响应头location
	   	        response.setHeader("location","/day15/responseDemo2");
	               //简单的重定向方法
	   	        response.sendRedirect("/day15/responseDemo2");
	   ```
	
	   

### 	**forward** 和  **redirect** 区别

		* 转发的特点：forward
			1. 转发地址栏路径不变
			2. 转发只能访问当前服务器下的资源
			3. 转发是一次请求，可以使用request对象来共享数据


​		
		* 重定向的特点 : redirect
			1. 地址栏发生变化
			2. 重定向可以访问其他站点(服务器)的资源
			3. 重定向是两次请求。不能使用request对象来共享数据
### 路径写法

```markdown
# 路径分类
	1. 相对路径：通过相对路径不可以确定唯一资源
		* 如：./index.html
		* 不以/开头，以.开头路径

		* 规则：找到当前资源和目标资源之间的相对位置关系
			* ./：当前目录
			* ../:后退一级目录
	2. 绝对路径：通过绝对路径可以确定唯一资源
			* 如：http://localhost/day15/responseDemo2		/day15/responseDemo2
			* 以/开头的路径

			* 规则：判断定义的路径是给谁用的？判断请求将来从哪儿发出
			* 给客户端浏览器使用：需要加虚拟目录(项目的访问路径)
				* 建议虚拟目录动态获取：request.getContextPath()
				* <a> , <form> 重定向...
			* 给服务器使用：不需要加虚拟目录
				* 转发路径
```

###  服务器输出字符数据到浏览器

```markdown
   # 步骤：
		1. 获取字符输出流
		2. 输出数据
   # 注意：
		* 乱码问题：
			1. PrintWriter pw = response.getWriter();获取的流的默认编码是ISO-8859-1
			2. 设置该流的默认编码
			3. 告诉浏览器响应体使用的编码

            //简单的形式，设置编码，是在获取流之前设置
   			response.setContentType("text/html;charset=utf-8");
  
```

```java
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.println("<h1>你好</h1>");
```

```markdown
 # 服务器输出字节数据到浏览器
		* 步骤：
			1. 获取字节输出流
			2. 输出数据
```

```java

        ServletOutputStream sos = response.getOutputStream();
        sos.write("getOutputStream你好".getBytes("UTF-8"));
```

# ServletContext对象

> **概念**：代表整个web应用，可以和程序的容器(服务器)来通信

**获取**：
	1.通过*request*对象获取

```java
	request.getServletContext();
```


​	2.通过*HttpServlet*获取

```java
    this.getServletContext();
```

 **功能**：

1.获取*MIME*类型：

	* *MIME*类型:在互联网通信过程中定义的一种文件数据类型
		* 格式： 大类型/小类型   text/html		image/jpeg

```
    * 获取：String getMimeType(String file)  
```
2.域对象：共享数据

1. setAttribute(String name,Object value)
2. getAttribute(String name)
3. removeAttribute(String name)

> ***ServletContext***对象范围：所有用户所有请求的数据

3.获取文件的真实(服务器)路径

方法：

```java
 ServletContext context = this.getServletContext();
String b = context.getRealPath("/b.txt");//web目录下资源访问
  System.out.println(b);

 String c = context.getRealPath("/WEB-INF/c.txt");//WEB-INF目录下的资源访问
 System.out.println(c);

 String a = context.getRealPath("/WEB-INF/classes/a.txt");//src目录下的资源访问
 System.out.println(a);
```



# 文件下载案例

```markdown
 # 分析：
	1. 超链接指向的资源如果能够被浏览器解析，则在浏览器中展示，如果不能解析，则弹出下载提示框。不满足需求
	2. 任何资源都必须弹出下载提示框
	3. 使用响应头设置资源的打开方式：
		* content-disposition:attachment;filename=xxx
```

```markdown
# 步骤：
	1. 定义页面，编辑超链接href属性，指向Servlet，传递资源名称filename
	2. 定义Servlet
		1. 获取文件名称
		2. 使用字节输入流加载文件进内存
		3. 指定response的响应头： content-disposition:attachment;filename=xxx
		4. 将数据写出到response输出流
```

```markdown
#  问题：
	* 中文文件问题
		* 解决思路：
			1. 获取客户端使用的浏览器版本信息
			2. 根据不同的版本信息，设置filename的编码方式不同
```

```java
/**
*文件下载乱码工具包
*/
public class DownLoadUtils {

    public static String getFileName(String agent, String filename) throws UnsupportedEncodingException {
        if (agent.contains("MSIE")) {
            // IE浏览器
            filename = URLEncoder.encode(filename, "utf-8");
            filename = filename.replace("+", " ");
        } else if (agent.contains("Firefox")) {
            // 火狐浏览器
            BASE64Encoder base64Encoder = new BASE64Encoder();
            filename = "=?utf-8?B?" + base64Encoder.encode(filename.getBytes("utf-8")) + "?=";
        } else {
            // 其它浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        }
        return filename;
    }

}
```

```java
@WebServlet("/downloadServlet")
public class DownloadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取请求参数，文件名称
        String filename = request.getParameter("filename");
        //2.使用字节输入流加载文件进内存
        //2.1找到文件服务器路径
        ServletContext servletContext = this.getServletContext();
        String realPath = servletContext.getRealPath("/img/" + filename);
        //2.2用字节流关联
        FileInputStream fis = new FileInputStream(realPath);

        //3.设置response的响应头
        //3.1设置响应头类型：content-type
        String mimeType = servletContext.getMimeType(filename);//获取文件的mime类型
        response.setHeader("content-type",mimeType);
        //3.2设置响应头打开方式:content-disposition
    
        //解决中文文件名问题
        //1.获取user-agent请求头、
        String agent = request.getHeader("user-agent");
        //2.使用工具类方法编码文件名即可
        filename = DownLoadUtils.getFileName(agent, filename);
    
        response.setHeader("content-disposition","attachment;filename="+filename);
        //4.将输入流的数据写出到输出流中
        ServletOutputStream sos = response.getOutputStream();
        byte[] buff = new byte[1024 * 8];
        int len = 0;
        while((len = fis.read(buff)) != -1){
            sos.write(buff,0,len);
        }
    
        fis.close();


    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }

}
```



# 会话技术

**会话**：一次会话中包含多次请求和响应。

* 一次会话：浏览器第一次给服务器资源发送请求，会话建立，直到有一方断开为止

**功能**：在一次会话的范围内的多次请求间，共享数据

**方式**：

1. 客户端会话技术：*Cookie*
2. 服务器端会话技术：*Session*

## Cookie

> **概念**：*客户端*会话技术，将数据保存到客户端

### 快速入门：

```java
使用步骤：

1. 创建Cookie对象，绑定数据
   * new Cookie(String name, String value) 
       Cookie cookie = new Cookie("msg", "你好啊");
2. 发送Cookie对象
   * response.addCookie(Cookie cookie) 
       response.addCookie(cookie);
3. 获取Cookie，拿到数据
   * Cookie[]  request.getCookies()  
       Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            System.out.println(cookie.getName()+"::"+cookie.getValue());
        }
```

### 实现原理

> * 基于响应头set-cookie和请求头cookie实现

### cookie的细节

```markdown
# 一次可不可以发送多个cookie?
	* 可以
	* 可以创建多个Cookie对象，使用response调用多次addCookie方法发送cookie即可。
# cookie在浏览器中保存多长时间？
	1. 默认情况下，当浏览器关闭后，Cookie数据被销毁
	2. 持久化存储：
		* setMaxAge(int seconds)
			1. 正数：将Cookie数据写到硬盘的文件中。持久化存储。并指定cookie存活时间，时间到后，cookie文件自动失效
			2. 负数：默认值
			3. 零：删除cookie信息
# cookie能不能存中文？
	* 在tomcat 8 之前 cookie中不能直接存储中文数据。
	* 需要将中文数据转码---一般采用URL编码(%E3)
	* 在tomcat 8 之后，cookie支持中文数据。特殊字符还是不支持，建议使用URL编码存储，URL解码解析
# cookie共享问题？
	1. 假设在一个tomcat服务器中，部署了多个web项目，那么在这些web项目中cookie能不能共享？
		* 默认情况下cookie不能共享
		* setPath(String path):设置cookie的获取范围。默认情况下，设置当前的虚拟目录
		* 如果要共享，则可以将path设置为"/"
	2. 不同的tomcat服务器间cookie共享问题？
		* setDomain(String path):如果设置一级域名相同，那么多个服务器之间cookie可以共享
		* setDomain(".baidu.com"),那么tieba.baidu.com和news.baidu.com中cookie可以共享
```

### Cookie的特点和作用

```markdown
# cookie存储数据在客户端浏览器
# 浏览器对于单个cookie 的大小有限制(4kb) 以及 对同一个域名下的总cookie数量也有限制(20个)

	* 作用：
		1. cookie一般用于存出少量的不太敏感的数据
		2. 在不登录的情况下，完成服务器对客户端的身份识别
```

## Session

**概念：**

>  服务器端会话技术，在一次会话的多次请求间共享数据，将数据保存在服务器端的对象中。

### 快速入门

```markdown
# 获取HttpSession对象：
		HttpSession session = request.getSession();
# 使用HttpSession对象：
		Object getAttribute(String name)  
		void setAttribute(String name, Object value)
		void removeAttribute(String name)  
# 使用
        存
		HttpSession session = request.getSession();
        session.setAttribute("msg","hello");
        
        取
        HttpSession session = request.getSession();
        System.out.println(session.getAttribute("msg"));
```

### 原理

​	* *Session*的实现是依赖于*Cookie*的。

### 细节

```markdown
# 当客户端关闭后，服务器不关闭，两次获取session是否为同一个？
		* 默认情况下。不是。
		* 如果需要相同，则可以创建Cookie,键为JSESSIONID，设置最大存活时间，让cookie持久化保存。
	         HttpSession session = request.getSession();
			 Cookie c = new Cookie("JSESSIONID",session.getId());
	         c.setMaxAge(60*60);
	         response.addCookie(c);
# 客户端不关闭，服务器关闭后，两次获取的session是同一个吗？
		* 不是同一个，但是要确保数据不丢失。tomcat自动完成以下工作
			* session的钝化：
				* 在服务器正常关闭之前，将session对象系列化到硬盘上
			* session的活化：
				* 在服务器启动后，将session文件转化为内存中的session对象即可。
			
# session什么时候被销毁？
		1. `服务器关闭`
		2. session对象调用invalidate() 。
		3. session默认失效时间 30分钟
			选择性配置修改	
			<session-config>
		        <session-timeout>30</session-timeout>
		    </session-config>
```

### session的特点

  1. session用于存储一次会话的多次请求的数据，存在服务器端
  2. session可以存储任意类型，任意大小的数据

### session与Cookie的区别：

	1. session存储数据在服务器端，Cookie在客户端
	2. session没有数据大小限制，Cookie有
	3. session数据安全，Cookie相对于不安全

# Filter

## 概念：

> 	* 生活中的过滤器：净水器,空气净化器，土匪、
> 	* web中的过滤器：当访问服务器的资源时，过滤器可以将请求拦截下来，完成一些特殊的功能。
> 	* 过滤器的作用：
> 	  - 一般用于完成通用的操作。如：登录验证、统一编码处理、敏感字符过滤...

## 快速入门：

**步骤**：

```markdown
1. 定义一个类，实现接口Filter
2. 复写方法
3. 配置拦截路径
	1. web.xml
	2. 注解
```

**代码**:

 ```java
@WebFilter("/*")//访问所有资源之前，都会执行该过滤器
		public class FilterDemo1 implements Filter {
		    @Override
		    public void init(FilterConfig filterConfig) throws ServletException {
		
		    }
		
		    @Override
		    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		        System.out.println("filterDemo1被执行了....");
                //放行
		        filterChain.doFilter(servletRequest,servletResponse);
		
		    }
		
		    @Override
		    public void destroy() {
		
		    }
		}
 ```

**web.xml配置**	     

```xml
        <filter>
	        <filter-name>demo1</filter-name>
	        <filter-class>cn.itcast.web.filter.FilterDemo1</filter-class>
	    </filter>
	    <filter-mapping>
	        <filter-name>demo1</filter-name>
			<!-- 拦截路径 -->
	        <url-pattern>/*</url-pattern>
	    </filter-mapping>
```

## 过滤器执行流程

		1. 执行过滤器
		2. 执行放行后的资源
		3. 回来执行过滤器放行代码下边的代码
## 过滤器生命周期方法

	1. init:在服务器启动后，会创建Filter对象，然后调用init方法。只执行一次。用于加载资源
	
	2. doFilter:每一次请求被拦截资源时，会执行。执行多次
	
	3. destroy:在服务器关闭后，Filter对象被销毁。如果服务器是正常关闭，则会执行destroy方法。只执行一    次。用于释放资源
## 过滤器配置详解

```markdown
# 拦截路径配置：
		1. 具体资源路径： /index.jsp   只有访问index.jsp资源时，过滤器才会被执行
		2. 拦截目录： /user/*	访问/user下的所有资源时，过滤器都会被执行
		3. 后缀名拦截： *.jsp		访问所有后缀名为jsp资源时，过滤器都会被执行
		4. 拦截所有资源：/*		访问所有资源时，过滤器都会被执行
# 拦截方式配置：资源被访问的方式
		## 注解配置：
			* 设置dispatcherTypes属性
				1. REQUEST：默认值。浏览器直接请求资源
				2. FORWARD：转发访问资源
				3. INCLUDE：包含访问资源
				4. ERROR：错误跳转资源
				5. ASYNC：异步访问资源
		## web.xml配置
			* 设置<dispatcher></dispatcher>标签即可
```
## 过滤器链(配置多个过滤器)

```markdown
# 执行顺序：如果有两个过滤器：过滤器1和过滤器2
			1. 过滤器1
			2. 过滤器2
			3. 资源执行
			4. 过滤器2
			5. 过滤器1 

#  过滤器先后顺序问题：
			1. 注解配置：按照类名的字符串比较规则比较，值小的先执行
				* 如： AFilter 和 BFilter，AFilter就先执行了。
			2. web.xml配置： <filter-mapping>谁定义在上边，谁先执行
```

