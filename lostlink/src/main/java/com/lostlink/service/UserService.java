package com.lostlink.service;

import com.lostlink.entity.User;
import com.lostlink.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    private final UserRepository userRepository;
    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;

    }
    public User saveUser(User user){
        return userRepository.save(user);
    }
    public List<User> getAllUser(){

        return userRepository.findAll();
    }
    public User getUserById(Long id){

        return userRepository.findById(id).orElse(null);
    }
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}
