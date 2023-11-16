package com.alvis.grocerystore.controller;

import com.alvis.grocerystore.dto.CurrentUserResponse;
import com.alvis.grocerystore.dto.UserRegisterRequest;
import com.alvis.grocerystore.model.MyUserDetails;
import com.alvis.grocerystore.model.User;
import com.alvis.grocerystore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest){

        Integer userId = userService.register(userRegisterRequest);

        User user = userService.getUserById(userId);

        return ResponseEntity.status(201).body(user);
    }

    @GetMapping("/users/currentUser")
    public ResponseEntity<CurrentUserResponse> getCurrentUser (@AuthenticationPrincipal MyUserDetails myUserDetails) {
       if (myUserDetails != null) {
           return ResponseEntity.status(200)
                   .body(new CurrentUserResponse(myUserDetails.getUserId(), myUserDetails.getUsername()));
       } else {
           return ResponseEntity.status(200).body(null);
       }
    }
}