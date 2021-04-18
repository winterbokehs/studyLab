## 解决SpringBoot中无法访问js、css、img等静态资源的问题

## **一. 异常问题**

我一开始在SpringBoot中的resources/static/目录下创建了easeui、layui等静态资源目录，然后在html文件中我一开始如下图所示进行引用。

![img](https://pic2.zhimg.com/80/v2-31433df37ccdfba0abd965640a768421_720w.jpg)

结果访问页面的时候，就导致了如下效果：

![img](https://pic2.zhimg.com/80/v2-5d4630df4eee3188334007c651dbfbad_720w.jpg)

## **二. 原因分析**

这是因为Springboot默认的静态资源路径为static，我们不需要再添加/static/前缀，所以需要使用正确的方式来引用，否则就会导致404的问题。

## **三. 解决办法**

```js
 <link rel="stylesheet" href="/layui/css/layui.css">
 <link rel="stylesheet" href="/easyui/default/easyui.css">
 <script src="/layui/jquery-1.10.2.min.js" type="text/javascript"></script>
 <script src="/easyui/jquery.easyui.min.js" type="text/javascript"></script>
```

![img](https://pic4.zhimg.com/80/v2-4feb1ffd76f10da689161dcb578929f3_720w.jpg)

此时运行效果如下：

![img](https://pic2.zhimg.com/80/v2-0f0fb900b1a14648be2902e0e2d3938d_720w.jpg)