package com.lcwd.electronic.store.services.impl;

import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.entities.User;
import com.lcwd.electronic.store.exceptions.ResourceNotFoundException;
import com.lcwd.electronic.store.repositories.UserRepository;
import com.lcwd.electronic.store.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;
    @Override
    public UserDto createUser(UserDto userDto) {
        //generate unique id in string format
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);
        //dto-->entity
        User user = dtoToEntity(userDto);
        User savedUser = userRepository.save(user);
        //entity-->dto
        UserDto newDto=entityToDto(savedUser);

        return newDto;
    }


    @Override
    public UserDto updateUser(UserDto userDto, String userId) {

        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("user not found with given id"));
        user.setName(userDto.getName());
        //email update
        user.setAbout(userDto.getAbout());
        user.setPassword(userDto.getPassword());
        user.setImageName(userDto.getImageName());
        user.setGender(userDto.getGender());
        //save data
        User updatedUser = userRepository.save(user);
        UserDto updatedUserDto = entityToDto(updatedUser);
        return updatedUserDto;
    }

    @Override
    public void deleteUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("user not found with given id"));
        //delete
        userRepository.delete(user);

    }

    @Override
    public List<UserDto> getAllUser(int pageNumber,int pageSize,String sortBy ,String sortDir) {
        //pageNumber default starts from 0
        Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<User> page = userRepository.findAll(pageable);
        List<User> users = page.getContent();
        List<UserDto> dtoList = users.stream().map((user) -> entityToDto(user)).collect(Collectors.toList());
        return dtoList;
    }

    @Override
    public UserDto getUserById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user not found with given id"));
        return entityToDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()->new ResourceAccessException("user not found with given email"));
        return entityToDto(user);
    }




    @Override
    public List<UserDto> searchUser(String keyword) {
        List<User> users =userRepository.findByNameContaining(keyword);
        List<UserDto> dtoList=users.stream().map(user->entityToDto(user)).collect(Collectors.toList());
        return dtoList;
    }


    private UserDto entityToDto(User savedUser) {
//       UserDto userDto= UserDto.builder().
//                userId(savedUser.getUserId())
//                .name(savedUser.getName())
//                .email(savedUser.getEmail())
//                .password(savedUser.getPassword())
//                .gender(savedUser.getGender())
//                .about(savedUser.getAbout())
//                .imageName(savedUser.getImageName())
//                .build();

        return mapper.map(savedUser, UserDto.class);
    }

    private User dtoToEntity(UserDto userDto) {
//        User user = User.builder()
//                .userId(userDto.getUserId())
//                .name(userDto.getName())
//                .email(userDto.getEmail())
//                .password(userDto.getPassword())
//                .about(userDto.getAbout())
//                .gender(userDto.getGender())
//                .imageName(userDto.getImageName())
//                .build();

        return  mapper.map(userDto,User.class);
    }

}
