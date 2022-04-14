package com.example.sportalproject.controller;

import com.example.sportalproject.exceptions.BadRequestException;
import com.example.sportalproject.exceptions.UnauthorisedException;
import com.example.sportalproject.model.DTO.*;
import com.example.sportalproject.model.DTO.userDTOs.*;
import com.example.sportalproject.model.entity.User;
import com.example.sportalproject.service.UserService;
import com.mysql.cj.Session;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@RestController
public class UserController  {


    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;


    @PostMapping("/user/register")
    public ResponseEntity<UserRegisterResponseDTO> register(@RequestBody UserRegisterRequestDTO userDTO) {

        return new ResponseEntity<>(userService.registerUser(userDTO), HttpStatus.CREATED);

    }


    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable int id) {

        User u = userService.getById(id);
        UserResponseDTO dto = modelMapper.map(u, UserResponseDTO.class);
        dto.setMessage("User");
        return ResponseEntity.ok(dto);

    }

    @PutMapping("/edit/user")
    public ResponseEntity<UserEditResponseDTO> edit(@RequestBody UserEditRequestDTO user, HttpSession session, HttpServletRequest request) {

        SessionValidator.validateSession(session, request);
        User u = userService.getById(user.getId());
        u = userService.edit(user);
        UserEditResponseDTO dto = modelMapper.map(u, UserEditResponseDTO.class);
        return ResponseEntity.ok(dto);

    }

    @GetMapping("/auth")
    public ResponseEntity<UserResponseDTO> login(@RequestBody UserLoginDTO user, HttpSession session, HttpServletRequest request) {
        String username = user.getUsername();

        String password = user.getPassword();
        UserResponseDTO u = userService.login(username, password);
        session.setAttribute(SessionValidator.USER_ID, u.getId());
        session.setAttribute(SessionValidator.LOGGED, true);
        session.setAttribute("logged_from", request.getRemoteAddr());

        return ResponseEntity.ok(u);
    }

    @PutMapping("/changePassword")
    public ResponseEntity<UserResponsePassDTO> changePassword(@RequestBody UserChangePasswordDTO user, HttpSession session, HttpServletRequest request) {
        SessionValidator.validateSession(session, request);
        User u = userService.getById(user.getId());

        if (userService.checkOldPassword(user)) {
            UserResponsePassDTO dto = modelMapper.map(u, UserResponsePassDTO.class);
            dto.setMessage("You successfully changed your password!");
            return ResponseEntity.ok(dto);
        }
        throw new BadRequestException("wrong credentials!");
    }


    @PutMapping("/logout/user")
    public ResponseEntity<UserResponseDTO> logout( HttpSession session) {


        if(session.getAttribute(SessionValidator.USER_ID) == null){
            throw new BadRequestException("You are already logged out!");
        }
        User u=userService.getById((long) session.getAttribute(SessionValidator.USER_ID));

        if ((boolean) session.getAttribute(SessionValidator.LOGGED)) {
            session.setAttribute(SessionValidator.LOGGED_OUT, u);
            UserResponseDTO dto = modelMapper.map(u, UserResponseDTO.class);
            dto.setMessage("You logged out!");
            session.invalidate();
            return ResponseEntity.ok(dto);
        } else {
            throw new BadRequestException("User already logged out.");


        }

    }

    @DeleteMapping("/deleteUser{id}")
    public MessageDTO deleteUser(@PathVariable long id, HttpServletRequest request, HttpSession session) {

        SessionValidator.validateSession(session, request);
        User u = userService.getById((long) session.getAttribute(SessionValidator.USER_ID));
        if (!u.is_admin()) {
            throw new UnauthorisedException("You are not authorised for this operation!");
        }
        userService.deleteUser(id);
        MessageDTO message = new MessageDTO();
        message.setMessage("User is deleted!");

        return message;
    }

    @PostMapping("/uploadProfileImage")
    public String uploadProfileImage(@RequestParam(name = "file") MultipartFile file, HttpServletRequest request) {
        User u = userService.getById((long) request.getSession().getAttribute(SessionValidator.USER_ID));
        return userService.uploadProfilePicture(file, request, u);
    }
}
