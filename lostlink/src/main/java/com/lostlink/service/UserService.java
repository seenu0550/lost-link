package com.lostlink.service;

import com.lostlink.dto.UserDTO;
import com.lostlink.entity.User;
import com.lostlink.mapper.UserMapper;
import com.lostlink.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public UserDTO saveUser(UserDTO userDTO) {

        User user = UserMapper.toEntity(userDTO);

        user = userRepository.save(user);

        return UserMapper.toDTO(user);
    }

    public List<UserDTO> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {

        User user = userRepository.findById(id).orElse(null);

        return UserMapper.toDTO(user);
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {

        User existingUser = userRepository.findById(id).orElse(null);

        if (existingUser != null) {

            existingUser.setName(userDTO.getName());
            existingUser.setEmail(userDTO.getEmail());
            existingUser.setPhone(userDTO.getPhone());
            existingUser.setRole(userDTO.getRole());

            existingUser = userRepository.save(existingUser);

            return UserMapper.toDTO(existingUser);
        }

        return null;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}