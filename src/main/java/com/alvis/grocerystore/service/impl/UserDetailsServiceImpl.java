package com.alvis.grocerystore.service.impl;

import com.alvis.grocerystore.dao.UserDao;
import com.alvis.grocerystore.model.MyUserDetails;
import com.alvis.grocerystore.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userDao.getUserByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("Not found user: " + email);
        }
        return new MyUserDetails(user);
    }
}
