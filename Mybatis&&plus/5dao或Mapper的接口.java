package com.me.dao;

import com.me.pojo.User;

import java.util.List;

public interface UserMapper {
    /**
     * 查询所有用户
     * @return
     */
    List<User> getUserList();

    /**
     * 根据id查询用户
     * @return
     */
    User getUserById(int id);

    /**
     * 添加用户
     * @param user
     * @return
     */
    int addUser(User user);

    /**
     * 更新用户
     * @param user
     * @return
     */
    int updateUser(User user);

    /**
     * 删除用户
     * @param id
     * @return
     */
    int deleteUse(int id);
}
