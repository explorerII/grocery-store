package com.alvis.grocerystore.dao;

import com.alvis.grocerystore.dto.UserRegisterRequest;
import com.alvis.grocerystore.model.User;

public interface UserDao {
    Integer createUser(UserRegisterRequest userRegisterRequest);
    User getUserById(Integer userId);
    User getUserByEmail(UserRegisterRequest userRegisterRequest);
}
