package com.alvis.grocerystore.service.impl;

import com.alvis.grocerystore.dao.UserDao;
import com.alvis.grocerystore.dto.UserRegisterRequest;
import com.alvis.grocerystore.model.User;
import com.alvis.grocerystore.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserServiceImpl implements UserService {

    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {

        // check if email is registered
        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());

        if (user != null) {
            log.warn("the email {} is already registered.", userRegisterRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // use Bcrypt encode password
        userRegisterRequest.setPassword(
                passwordEncoder.encode(userRegisterRequest.getPassword()));


        // create account
        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }
}
