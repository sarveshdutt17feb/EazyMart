package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.entities.User;

import java.util.List;

public interface UserService {
    //create
     UserDto createUser(UserDto userDto);
    //update
    UserDto updateUser(UserDto userDto,String userId);
    //delete
    void deleteUser(String userId);

    // get all user
    List<UserDto> getAllUser();

    //get single user
    UserDto getSingleUser(String userId);
    //get single user by email
    UserDto getSingleUserByEmail(String email);
    //search user
    List<UserDto> searchUser(String keyword);

    //other user specific features provide here
}
