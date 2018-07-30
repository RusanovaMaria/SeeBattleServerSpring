package com.seebattleserver.repository;

import com.seebattleserver.application.user.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/controller")
public class UserListController {

    @Autowired
    private UserEntityRepository userEntityRepository;

    @PostMapping(path = "/add")
    public @ResponseBody String addNewUserEntity() {
        //UserEntity user = new UserEntity();
       // user.setName(name);
       // userEntityRepository.save(user);
        return "Saved";
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<UserEntity> getAllUserEntities() {
        return userEntityRepository.findAll();
    }
}
