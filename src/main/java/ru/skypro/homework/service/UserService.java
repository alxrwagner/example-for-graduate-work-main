package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import ru.skypro.homework.repository.UserRepos;

@Service
public class UserService {
    private UserRepos userRepos;

    public UserService(UserRepos userRepos) {
        this.userRepos = userRepos;
    }

    
}
