# 踩过的坑

因为你使用的form表单，你点击表单里的按钮会默认刷新当前页面，AJAX请求可能发出来了，但是当前页面已经初始化了，建议把Button用Input替换

**<input type="button" name="btn" value="提交" id="btn"/>**



```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>登录</title>
    <script src="/js/jquery-3.3.1.min.js"></script>
</head>
<body>
 
<h1>登录</h1>
<form>
    <div id="fir"></div>
  账号<input type="text" name="username" id="username"><br>
    密码<input type="password" name="password" id="password">
    <button id="btn">提交</button>
</form>
</body>
<script>
    $("#btn").click(function () {
        var username = $("#username").val();
        var password = $("#password").val();
        $.post("/login",{username:username,password:password},function (data) {
            alert(data);
            if (data===200){
                window.location.href = "/index.html";
 
            }else if(data == 201){
                window.location.href = "/user.html";
            } else {
                $("#fir").html("<p style='color: red'>账号密码错误</p>");
                alert("账号密码错误!");
                window.location.href = "/login.html";
            }
        });
    })
 
 
</script>
</html>
```



# Ajax测试（myself）

1。controller

```java
package com.example.demo.controller;

import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class WriteController {

    @Autowired
    private UserMapper userMapper;

    @ResponseBody
    @RequestMapping("/map")
    public List<User> Test() throws JsonProcessingException {
        List<User> all = userMapper.findAll();
        return all;
    }
}
```

2.前端

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>all people</title>
    <script src="../js/jquery-3.3.1.min.js"></script>
</head>
<body>
<label id="lab"></label>
</body>
<script>
    $.ajax({
        type:"POST",
        url:"/map",
        data:{},
        datatype:'json',
        success:function(data){
            var str = "";
           for (i in data){
               str+="<p>" + data[i].id + "</p>" +
               "<p>" + data[i].name + "</p>" +
               "<p>" + data[i].pwd + "</p>"

           }
          $("#lab").html(str);
        }
    }
    );
</script>
</html>
```

## Ajax解释：

通过 AJAX POST 请求改变 div 元素的文本：

```
$("input").keyup(function(){
  txt=$("input").val();
  $.post("demo_ajax_gethint.asp",{suggest:txt},function(result){
    $("span").html(result);
  });
});
```

[亲自试一试](https://www.w3school.com.cn/tiy/t.asp?f=jquery_ajax_post)

### 定义和用法

post() 方法通过 HTTP POST 请求从服务器载入数据。

### 语法

```
jQuery.post(url,data,success(data, textStatus, jqXHR),dataType)
```

| 参数                               | 描述                                                         |
| :--------------------------------- | :----------------------------------------------------------- |
| *url*                              | 必需。规定把请求发送到哪个 URL。                             |
| *data*                             | 可选。映射或字符串值。规定连同请求发送到服务器的数据。       |
| *success(data, textStatus, jqXHR)* | 可选。请求成功时执行的回调函数。                             |
| *dataType*                         | 可选。规定预期的服务器响应的数据类型。默认执行智能判断（xml、json、script 或 html）。 |

### 详细说明

该函数是简写的 Ajax 函数，等价于：

```
$.ajax({
  type: 'POST',
  url: url,
  data: data,
  success: success,
  dataType: dataType
});
```

根据响应的不同的 MIME 类型，传递给 success 回调函数的返回数据也有所不同，这些数据可以是 XML 根元素、文本字符串、JavaScript 文件或者 JSON 对象。也可向 success 回调函数传递响应的文本状态。

对于 jQuery 1.5，也可以向 success 回调函数传递 [jqXHR 对象](https://www.w3school.com.cn/jquery/ajax_post.asp#jqxhr_object)（jQuery 1.4 中传递的是 XMLHttpRequest 对象）。

大部分实现会规定一个 success 函数：

```
$.post("ajax/test.html", function(data) {
  $(".result").html(data);
});
```

本例读取被请求的 HTML 片段，并插入页面中。





# **前端数据提交给后端**

AJAX提交

```html
<script type="text/javascript">
        function submitData() {
            //1.获取需要提交的数据
            var num = $("#num").val();
            var name = $("#name").val();
            var sex = $("#sex").val();
            var age = $("#age").val();
    
            //2.通过ajax提交数据到“stu/add”
            $.post("stu/add",{stuNum:num,stuName:name,stuSex:sex,stuAge:age},function(data){
                console.log(data);
            },"json");
        }
  </script>
```

**控制器接收**

方式1：属性接收

```java
@RequestMapping("stu/add")
    @ResponseBody
    public HashMap<String,Object> add(String stuNum,String stuName,String stuSex,int stuAge){
        System.out.println(stuNum);
        System.out.println(stuName);
        System.out.println(stuSex);
        System.out.println(stuAge);

        HashMap<String,Object> map = new HashMap<>();
        map.put("code",1);
        return map;
    }
```

## 

# html通过Ajax获取后端数据]

我们知道在Java Web中，前端的JSP可以使用EL表达式来获取Servlet传过来的数据Spring Boot中也有Thymeleaf模板可以使用th: text="${XXX}"来获取Controller传过来的值

但是我就是不想要使用thymeleaf，我想使用普通的html，这里呢，使用Ajax向后端获取数据，先来展示一下最终结果图吧

![img](https://img2018.cnblogs.com/blog/793293/201902/793293-20190215165032678-1821329136.png)

先来展示一下后端代码吧，后端的Controller向数据库获取数据，然后以JSON格式传给前台，但是我懒，哈哈哈，所以我造两个假数据，以后再写一个Mybatis+Mysql的例子。先新建一个Java Bean

```java
package com.wechat.main.bean;

/**
 * 消息表对应的Java Bean
 */
public class Message {
    private String id;
    private String command;
    private String description;
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
```

然后新建我们的Controller

```java
package com.wechat.main.controller;

import com.wechat.main.bean.Message;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 列表页面初始化
 */
@Controller
public class ListController {

    @ResponseBody
    @RequestMapping("/list")
    public List<Message> list(){
        List<Message> list=new ArrayList<>();
        Message message=new Message();
        message.setId("1");
        message.setCommand("许嵩");
        message.setDescription("歌手");
        message.setContent("最佳歌手");
        Message message1=new Message();
        message1.setId("2");
        message1.setCommand("蜀云泉");
        message1.setDescription("程序员");
        message1.setContent("不断成长的程序员");
        list.add(message);
        list.add(message1);
        return list;
    }

}
```

@ResponseBody这个注解就是把数据以JSON格式传走

看看我们的前端吧，我这里用了人家的，还有css，还有图片，所以比较好看。你们就自己写一个table凑合用吧

```html
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta http-equiv="X-UA-Compatible"content="IE=9; IE=8; IE=7; IE=EDGE" />
		<title>内容列表页面</title>
		<link href="css/all.css" rel="stylesheet" type="text/css" />
		<script src="js/jquery-1.8.0.min.js"></script>
	</head>
	<body style="background: #e1e9eb;">
		<form action="" id="mainForm" method="post">
			<div class="right">
				<div class="current">当前位置：<a href="javascript:void(0)" style="color:#6E6E6E;">内容管理</a> &gt; 内容列表</div>
				<div class="rightCont">
					<p class="g_title fix">内容列表 <a class="btn03" href="#">新 增</a>&nbsp;&nbsp;&nbsp;&nbsp;<a class="btn03" href="#">删 除</a></p>
					<table class="tab1">
						<tbody>
							<tr>
								<td width="90" align="right">演示字段1：</td>
								<td>
									<input type="text" class="allInput" value=""/>
								</td>
								<td width="90" align="right">演示字段2：</td>
								<td>
									<input type="text" class="allInput" value=""/>
								</td>
	                            <td width="85" align="right"><input type="submit" class="tabSub" value="查 询" /></td>
	                            <td width="85" align="right"><input type="button" class="tabSub"  onclick="refurbishIndex()" value="刷新" /></td>
	       					</tr>
						</tbody>
					</table>
					<div class="zixun fix">
						<table class="tab2" width="100%">
							<tr>
								<th><input type="checkbox" id="all" onclick="#"/></th>
								<th>id</th>
								<th>指令</th>
								<th>描述</th>
								<th>操作</th>
							</tr>

							<tbody id="tbodydata">

							</tbody>
						</table>
						<div class='page fix'>
							共 <b>4</b> 条
							<a href='###' class='first'>首页</a>
							<a href='###' class='pre'>上一页</a>
							当前第<span>1/1</span>页
							<a href='###' class='next'>下一页</a>
							<a href='###' class='last'>末页</a>
							跳至&nbsp;<input type='text' value='1' class='allInput w28' />&nbsp;页&nbsp;
							<a href='###' class='go'>GO</a>
						</div>
					</div>
				</div>
			</div>
	    </form>
	</body>
</html>


<script type="text/javascript">

	$(function () {
		refurbishIndex();
    })

    function refurbishIndex(){
        $.ajax({
            type:"post",
            url:"/list",
            data:{},
            async: false,
            success:function (data) {
                var str="";
                for (i in data) {
                    str += "<tr>" +
                        "<td>"+"<input type=\"checkbox\" />"+"</td>"+
						"<td align='center'>" + data[i].id + "</td>" +
                        "<td align='center'>" + data[i].command + "</td>" +
                        "<td align='center'>" + data[i].description + "</td>" +
							"<td>\n" +
                        "<a href=\"#\">修改</a>\n" +
                        "<a href=\"#\">删除</a>\n" +
                        "</td>"
                        "</tr>";
                }

                document.getElementById("tbodydata").innerHTML=str;

            }
        });
    }
</script>
```

大功告成