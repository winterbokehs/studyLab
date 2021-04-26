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
  overflow: auto;
  height: 100%;
}
```

```html
<div class="login_container">
    
</div>
```





# [element-ui表格显示html格式]()

```html
<el-table-column type="String" label="内容" prop="tpl" width="580" align="center">
          <template slot-scope="scope">
                <p v-html='scope.row.tpl'></p>
            </template>
        </el-table-column>
```