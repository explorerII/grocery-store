package com.alvis.grocerystore.service;

import com.alvis.grocerystore.dto.UserRegisterRequest;
import com.alvis.grocerystore.model.User;

public interface UserService {
    Integer register(UserRegisterRequest userRegisterRequest);
    User getUserById(Integer userId);
}
