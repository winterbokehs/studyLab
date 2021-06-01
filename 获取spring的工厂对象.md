```java
//用来获取springboot创建好的工厂
@Configuration
public class ApplicationContextUtils implements ApplicationContextAware {

    //保留下来工厂
    private static ApplicationContext applicationContext;

    //将创建好工厂以参数形式传递给这个类
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    //提供在工厂中获取对象的方法
    // RedisTemplate  redisTemplate
    public static Object getBean(String beanName){
        return applicationContext.getBean(beanName);
    }

}
```

在使用spring时，如果在spring的配置加载前就要获取工厂的话，可以使用该utils

使用

```java
 //获取redis的模板
    private RedisTemplate<String, User> redisTemplate = (RedisTemplate) ApplicationContextUtils.getBean("redisTemplate");
```

