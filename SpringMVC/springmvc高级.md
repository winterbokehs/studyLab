# springMVC的拦截器

### 作用

> **作用**:类似于javaweb中的Filter，用来对请求进行拦截，可以将多个Controller中执行的共同代码放入拦截器中执行，减少Controller类中代码的冗余.

### 特点

*拦截器*只能拦截Controller的请求，不能拦截jsp

拦截器可中断用户的请求轨迹
请求先经过拦截器，之后还会经过拦截器

- *preHandle*方法会在进入controller方法体(这里指@RequestMapping等注释的方法体)之前执行。

- *postHandle*会在controller方法体执行完但是还没处理return 语句之前执行。

- *afterCompletion*会在controller方法体内处理return 语句之后执行。

### 开发拦截器

```java
/**
 * 自定义拦截器
 */
public class MyInterceptor  implements HandlerInterceptor {

    //1.请求经过拦截器会优先进入拦截器中preHandler方法执行preHandler方法中内容
    //2.如果preHandler 返回为true 代表放行请求  如果返回值为false 中断请求
    //3.如果preHandler返回值为true,会执行当前请求对应的控制器中方法
    //4.当控制器方法执行结束之后,会返回拦截器中执行拦截器中postHandler方法
    //5.posthanlder执行完成之后响应请求,在响应请求完成后会执行afterCompletion方法
    @Override
    //参数1: 当前请求对象 参数2:当前请求对应响应对象  参数3:当前请求的控制器对应的方法对象
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(((HandlerMethod)handler).getMethod().getName());
        System.out.println("===========1=============");
        //强制用户登录
        /*Object user = request.getSession().getAttribute("user");
        if(user==null){
            //重定向到登录页面
            response.sendRedirect(request.getContextPath()+"/login.jsp");
            return false;
        }*/
        return true;
    }

    @Override
    //参数1: 当前请求对象 参数2:当前请求对应响应对象  参数3:当前请求的控制器对应的方法对象 参数4: 当前请求控制器方法返回值 = 当前请求控制器方法返回的modelandview对象 modelandview 模型和试图
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println(modelAndView);
        System.out.println("===========3=============");
    }


    @Override
    //注意: 无论正确还是失败都会执行
    //参数1: 当前请求对象 参数2:当前请求对应响应对象  参数3:当前请求的控制器对应的方法对象  参数4: 请求过程中出现异常时异常对象
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if(ex!=null){
            System.out.println(ex.getMessage());
        }
        System.out.println("===========4=============");
    }
}

```

### 配置拦截器

springmvc.xml

```xml
# 单个拦截器
 <!--注册拦截器-->
    <bean id="myInterceptor" class="com.yxj.interceptors.MyInterceptor"/>


    <!--配置拦截器-->
    <mvc:interceptors>

        <!--配置一个拦截器-->
        <mvc:interceptor>
            <!--mvc:mapping 代表拦截那个请求路径-->
            <mvc:mapping path="/json/*"/>
            <!--mvc:exclude-mapping 排除拦截那个请求-->
            <mvc:exclude-mapping path="/json/showAll"/>
            <!--使用那个拦截器-->
            <ref bean="myInterceptor"/>
        </mvc:interceptor>

    </mvc:interceptors>

# 多个拦截器
 <!--配置拦截器-->
    <bean id="myInterceptor1" class="com.yxj.interceptors.MyInterceptor1"></bean>
    <bean id="myInterceptor2" class="com.yxj.interceptors.MyInterceptor2"></bean>

    <!--配置拦截器-->
    <mvc:interceptors>
        <mvc:interceptor>
            <!--拦截的请求是谁-->
            <mvc:mapping path="/json/**"/>
            <!--放行某些请求-->
            <!--<mvc:exclude-mapping path="/json/test2"/>-->
            <ref bean="myInterceptor1"/>
            <!--<bean id="handlerInterceptorDemo2"
class="com.yxj.interceptors.MyInterceptor1"></bean> -->
        </mvc:interceptor>

        <mvc:interceptor>
            <mvc:mapping path="/json/test2"/>
            <ref bean="myInterceptor2"></ref>
        </mvc:interceptor>


    </mvc:interceptors>

```

> /*  :   代表拦截所有请求路径

# springMVC全局异常处理

### 作用

> 当控制器中某个方法在运行过程中突然发生运行时异常时，为了增加用户体验对于用户不能出现500错误代码，应该给用户良好展示错误界面，全局异常处理就能更好解决这个问题

### 全局异常处理开发

```java
public class GlobalExceptionResolver  implements HandlerExceptionResolver {


    /**
     * 用来处理发生异常时方法
     * @param request   当前请求对象
     * @param response  当前请求对应的响应对象
     * @param handler   当前请求的方法对象
     * @param ex        当前出现异常时的异常对象
     * @return          出现异常时展示视图和数据
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        System.out.println("进入全局异常处理器获取的异常信息为: "+ex.getMessage());
        ModelAndView modelAndView = new ModelAndView();
        //基于不同业务异常跳转到不同页面
        if(ex instanceof UserNameNotFoundException){
            modelAndView.setViewName("redirect:/login.jsp");
        }else{
            modelAndView.setViewName("redirect:/error.jsp");//return "error" ===>  /error.jsp
        }
        //modelandview中model 默认放入request作用域  如果使用redirect跳转:model中数据会自动拼接到跳转url
        modelAndView.addObject("msg",ex.getMessage());
        return modelAndView;
    }
}


package com.yxj.exceptions;

/**
 * Created by HIAPAD on 2019/10/28.
 */
//自定义用户名不存在异常类
public class UserNameNotFoundException extends RuntimeException {


    public UserNameNotFoundException(String message) {
        super(message);
    }
}


```

### 配置全局异常处理

```xml
<!--配置全局异常处理 -->
<bean class="com.yxj.handlerxception.GlobalExceptionResolver"/>

```

