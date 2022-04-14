package com.example.sportalproject.service;

import com.example.sportalproject.exceptions.BadRequestException;
import com.example.sportalproject.exceptions.NotFoundException;
import com.example.sportalproject.exceptions.UnauthorisedException;
import com.example.sportalproject.model.DTO.userDTOs.*;
import com.example.sportalproject.model.entity.Picture;
import com.example.sportalproject.model.entity.User;
import com.example.sportalproject.repository.PictureRepository;
import com.example.sportalproject.repository.UserRepository;
import com.example.sportalproject.util.Validator;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.example.sportalproject.controller.SessionValidator.validateSession;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PictureRepository pictureRepository;

    @Transactional
    public UserRegisterResponseDTO registerUser(UserRegisterRequestDTO userDTO) {
        userDTO.setFirst_name(Validator.validateEmptyField(userDTO.getFirst_name(), "First name"));
        userDTO.setLast_name(Validator.validateEmptyField(userDTO.getLast_name(), "Last name"));
        userDTO.setUsername(Validator.validateEmptyField(userDTO.getUsername(), "Username"));
        if (userRepository.findUserByUsername(userDTO.getUsername()).isPresent()) {
            throw new BadRequestException("Username already exists");
        }
        Validator.validatePassword(userDTO.getPassword());
        Validator.validatePassword(userDTO.getConfirmPassword());
        if (userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        } else {
            throw new BadRequestException("The passwords do not match!");
        }
        Validator.validatePhone(userDTO.getPhone());
        Validator.validateEmail(userDTO.getEmail());
        if (userRepository.findUserByEmail(userDTO.getEmail()) != null) {
            throw new BadRequestException("Email already exists");
        }

        User user = new User(userDTO);
        user.set_admin(false);
        user = userRepository.save(user);
        return modelMapper.map(user, UserRegisterResponseDTO.class);
    }


    public User getById(long id) {
        Optional<User> opt = userRepository.findById(id);

        if (opt.isPresent()) {
            return opt.get();
        } else {
            throw new NotFoundException("User not found.");
        }

    }


    @Transactional
    public User edit(UserEditRequestDTO user) {

        Optional<User> opt = userRepository.findById(user.getId());
        if (opt.isPresent()) {
            User u = modelMapper.map(opt, User.class);
            u.setFirstName(Validator.validateEmptyField(user.getFirstName(),"First name"));
            user.setLastName(Validator.validateEmptyField(user.getLastName(),"Last name"));
            u.setLastName(user.getLastName());
            Validator.validateEmail(user.getEmail());
            u.setEmail(user.getEmail());
            Validator.validatePhone(user.getPhone());
            u.setPhone(user.getPhone());
            u.setUpdated_at(LocalDateTime.now());
            userRepository.save(u);
            return u;
        } else {
            throw new NotFoundException("User not found.");
        }
    }


    public UserResponseDTO login(String username, String password) {
        User u = userRepository.findByUsername(username);

        if (username == null || username.isBlank()) {
            throw new BadRequestException("Username is mandatory.");
        }

        if (password == null || password.isBlank()) {
            throw new BadRequestException("Password is mandatory.");
        }

        if (!passwordEncoder.matches(password,u.getPassword())) {
            throw new UnauthorisedException("Wrong credentials");
        }

        UserResponseDTO dto = modelMapper.map(u, UserResponseDTO.class);
        dto.setMessage("Hello," + u.getUsername() + "! You logged in successfully!");

        return dto;
    }

    public Boolean checkOldPassword(UserChangePasswordDTO user) {
        Optional<User> opt = userRepository.findById(user.getId());
        if (!opt.isPresent()) {
            throw new NotFoundException("User not found.");
        }
        User u = userRepository.getById(user.getId());
        if (!(passwordEncoder.matches(user.getOldPassword(), u.getPassword()) && (user.getNewPassword().equals(user.getNewConfPassword())))) {
            throw new BadRequestException("Passwords are not matching !");
        }
        Validator.validatePassword(user.getNewConfPassword());
        opt.get().setPassword(passwordEncoder.encode(user.getNewConfPassword()));
        userRepository.save(opt.get());


        return true;

    }


    public void deleteUser(long user_id) {
        Optional<User> opt = userRepository.findById(user_id);
        if (!opt.isPresent()) {
            throw new NotFoundException("User not found");
        }
        userRepository.deleteById(user_id);
    }

    @SneakyThrows
    public String uploadProfilePicture(MultipartFile file, HttpServletRequest request, User u) {
        validateSession(request.getSession(), request);

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String name = System.nanoTime() + "." + extension;
        Files.copy(file.getInputStream(), new File("D://sportalpics" + File.separator + name).toPath());
        Picture picture = new Picture();
        picture.setUrl(name);

        u.setProfileImgUrl(name);
        pictureRepository.save(picture);
        userRepository.save(u);
        return name;
    }

}


