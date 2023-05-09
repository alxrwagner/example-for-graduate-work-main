package ru.skypro.homework.service;

import ru.skypro.homework.dto.userDTO.RegisterReq;

public interface AuthService {
    boolean login(String userName, String password);

    boolean register(RegisterReq registerReq);
}
