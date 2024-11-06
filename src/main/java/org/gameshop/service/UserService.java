package org.gameshop.service;


import org.gameshop.data.entities.User;
import org.gameshop.service.dtos.LoginUserDTO;
import org.gameshop.service.dtos.UserRegisterDTO;

public interface UserService {
    String registerUser(UserRegisterDTO registerUserDto);

    String loginUser(LoginUserDTO loginUserDTO);

    String logout();

    User getLoggedIn();
}
