package com.me.dao;

import com.me.pojo.User;
import com.me.utils.MybatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import java.util.List;


public class UserMapperTest {
 @Test
    public void test(){
     SqlSession sqlSessionFactory = MybatisUtil.getSqlSession();
     UserMapper mapper = sqlSessionFactory.getMapper(UserMapper.class);
     List<User> userList = mapper.getUserList();
      for (User user : userList) {
          System.out.println(user);
     }

     sqlSessionFactory.close();
 }
 @Test
    public void getUserByIdTest(){
     SqlSession sqlSessionFactory = MybatisUtil.getSqlSession();
     UserMapper mapper = sqlSessionFactory.getMapper(UserMapper.class);
     User user = mapper.getUserById(1);
         System.out.println(user);

     sqlSessionFactory.close();
 }

    @Test
    public void addUserTest(){
        SqlSession sqlSessionFactory = MybatisUtil.getSqlSession();
        UserMapper mapper = sqlSessionFactory.getMapper(UserMapper.class);
        int i = mapper.addUser(new User(4, "王五", "123123"));
        //提交事务
        if(i>0){
            System.out.println("提交成功！");
        }
        sqlSessionFactory.commit();

        sqlSessionFactory.close();
    }

    @Test
    public void updateUserTest(){
        SqlSession sqlSessionFactory = MybatisUtil.getSqlSession();
        UserMapper mapper = sqlSessionFactory.getMapper(UserMapper.class);
        int i = mapper.updateUser(new User(4, "牛六", "123123"));
        //提交事务
        if(i>0){
            System.out.println("提交成功！");
        }
        sqlSessionFactory.commit();

        sqlSessionFactory.close();
    }
    @Test
    public void deleteUserTest(){
        SqlSession sqlSessionFactory = MybatisUtil.getSqlSession();
        UserMapper mapper = sqlSessionFactory.getMapper(UserMapper.class);
        int i = mapper.deleteUse(4);
        //提交事务
        if(i>0){
            System.out.println("提交成功！");
        }
        sqlSessionFactory.commit();

        sqlSessionFactory.close();
    }
}
