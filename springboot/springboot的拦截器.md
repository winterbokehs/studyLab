# 配置拦截器

```java
@Configuration
public class JWTInterceptor implements HandlerInterceptor {

    /**
     * 对跨域提供支持
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setHeader("Access-control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (request.getMethod().equals(RequestMethod.OPTIONS.name())) {
            response.setStatus(HttpStatus.OK.value());
            return false;
        }
        ResponseEntity responseEntity = new ResponseEntity();
        //获取请求头 的令牌
        String token = request.getHeader("token");

        try {
            JWTUtil.checkJWT(token);//验证令牌

            return true;

        }catch (SignatureVerificationException e){
            e.printStackTrace();
            responseEntity.setMessage("签名不一致");
        }catch (TokenExpiredException e){
            e.printStackTrace();
            responseEntity.setMessage("令牌过期异常");

        }catch (AlgorithmMismatchException e){
            e.printStackTrace();
            responseEntity.setMessage("算法不匹配异常");
        }catch (InvalidClaimException e){
            e.printStackTrace();
            responseEntity.setMessage("失效的payload异常");

        }catch (Exception e){
            e.printStackTrace();
            responseEntity.setMessage("token无效");
        }
        responseEntity.setCode(500);
        String json = new ObjectMapper().writeValueAsString(responseEntity);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().println(json);
        return false;
    }
}

```

# 注册拦截器

```java
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    /**
     * 拦截访问请求，只有user页的资源和admin登录的资源会放行
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JWTInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/userBlog/**")
                .excludePathPatterns("/admin/login");//放行

    }
}
```

# 全局异常信息处理

```java
@ControllerAdvice
public class GlobalException {

    // 统一处理所有的异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity UnknownException(ResponseEntity responseEntity) {
        System.out.println(responseEntity);
        return responseEntity;
    }
}
```

eg::*抛出异常*

```java
@Configuration
public class JWTInterceptor implements HandlerInterceptor {

    /**
     * 对跨域提供支持
     */
    @SneakyThrows
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setHeader("Access-control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (request.getMethod().equals(RequestMethod.OPTIONS.name())) {
            response.setStatus(HttpStatus.OK.value());
            return false;
        }
        ResponseEntity responseEntity = new ResponseEntity();
        //获取请求头 的令牌
        String token = request.getHeader("token");

        try {
            JWTUtil.checkJWT(token);//验证令牌
            return true;

        }catch (SignatureVerificationException e){
            e.printStackTrace();
            responseEntity.setMessage("签名不一致");
        }catch (TokenExpiredException e){
            e.printStackTrace();
            responseEntity.setMessage("令牌过期异常");

        }catch (AlgorithmMismatchException e){
            e.printStackTrace();
            responseEntity.setMessage("算法不匹配异常");
        }catch (InvalidClaimException e){
            e.printStackTrace();
            responseEntity.setMessage("失效的payload异常");

        }catch (Exception e){
            e.printStackTrace();
            responseEntity.setMessage("token无效");
        }
        responseEntity.setCode(500);
//        String json = new ObjectMapper().writeValueAsString(responseEntity);
//        response.setContentType("application/json;charset=utf-8");
//        response.getWriter().println(json);
        //返回异常信息，前端页面在根据code跳转
        throw new GlobalException().UnknownException(responseEntity);

    }
}

```

前端通过code码 500  进行跳转