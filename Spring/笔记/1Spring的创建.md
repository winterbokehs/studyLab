# 1. hello Spring

- ###### 导入Jar包

  ```xml
  <dependency>
     <groupId>org.springframework</groupId>
     <artifactId>spring-webmvc</artifactId>
     <version>5.1.10.RELEASE</version>
  </dependency>
  ```

- 编写代码

  1、编写一个pojo实体类

  ```java
  public class Hello {
     private String name;
  
     public String getName() {
         return name;
    }
     public void setName(String name) {
         this.name = name;
    }
  
     public void show(){
         System.out.println("Hello,"+ name );
    }
  }
  ```

  2、编写XML文件，Spring的配置文件

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
         http://www.springframework.org/schema/beans/spring-beans.xsd">
  
     <!--bean就是java对象 , 由Spring创建和管理-->
     <bean id="hello" class="com.kuang.pojo.Hello">
         <property name="name" value="Spring"/>
     </bean>
  
  </beans>
  ```

  3、测试类

  ```java
  @Test
  public void test(){
     //解析beans.xml文件 , 生成管理相应的Bean对象
     ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
     //getBean : 参数即为spring配置文件中bean的id .
     Hello hello = (Hello) context.getBean("hello");
     hello.show();
  }
  ```

  

#  2.Spring配置

> 别名

alias 设置别名 , 为bean设置别名 , 可以设置多个别名

```xml
<!--设置别名：在获取Bean的时候可以使用别名获取-->
<alias name="userT" alias="userNew"/>
```

> Bean的配置

```xml
<!--bean就是java对象,由Spring创建和管理-->

<!--
   id 是bean的标识符,要唯一,如果没有配置id,name就是默认标识符
   如果配置id,又配置了name,那么name是别名
   name可以设置多个别名,可以用逗号,分号,空格隔开
   如果不配置id和name,可以根据applicationContext.getBean(.class)获取对象;

class是bean的全限定名=包名+类名
-->
<bean id="hello" name="hello2 h2,h3;h4" class="com.kuang.pojo.Hello">
   <property name="name" value="Spring"/>
</bean>
```

> import

团队的合作通过import来实现 .

```xml
<import resource="{path}/beans.xml"/>
```