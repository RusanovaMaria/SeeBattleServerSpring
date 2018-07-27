package com.seebattleserver.repository;

import com.seebattleserver.application.user.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/controller")
public class UserListController {

    @Autowired
    private UserEntityRepository userEntityRepository;

    @GetMapping(path = "/addGameObjects")
    public @ResponseBody String addNewUserEntity(@RequestParam String name, @RequestParam UserStatus status) {

        UserEntity user = new UserEntity();
        user.setName(name);
        user.setStatus(status);
        userEntityRepository.save(user);
        return "Saved";
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<UserEntity> getAllUserEntities() {
        return userEntityRepository.findAll();
    }
}
