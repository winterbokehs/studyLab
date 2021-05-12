# div背景平铺（ES6）

> 创建全局css样式

```
/*全局样式*/
HTML,body,#app{
    height: 100%;
    margin: 0;
    padding:0;
}
```





> 并在main.js添加
>
> ```
> //加入全局样式
> import './assets/css/global.css'
> ```

```css
.login_container {
  background-image: url("../assets/loginBg.jpg");
  background-size: 100% 100%;
  background-position: center center;
    //overflow: auto;//滚动条
  height: 100%;
}
```

```html
<div class="login_container">
    
</div>
```



# 固定背景

```css
<body background="bj.jpg" style="background-attachment:fixed";> 
```





# [element-ui表格显示html格式]()

```html
<el-table-column type="String" label="内容" prop="tpl" width="580" align="center">
          <template slot-scope="scope">
                <p v-html='scope.row.tpl'></p>
            </template>
        </el-table-column>
```



# 带参数跳转

第一个页面

```js
//编辑博客，传入博客的id
editBlog(id) {
    this.$message.info("正在跳转");
    location.href = 'editor.html?id='+id;
},
```

跳转页面

```js
//获取BlogID
var url = decodeURI(window.location.href);
var argsIndex = url.split("?id=");
var Blogid = argsIndex[1];
console.log(Blogid)
```