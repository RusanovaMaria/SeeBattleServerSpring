package com.seebattleserver.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping(path = "/users")
public class UserController {

    private UserEntityRepository userEntityRepository;

    @Autowired
    public UserController(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    @PostMapping(path = "/new")
    public @ResponseBody String addNewUserEntity(@RequestParam String name) {
        UserEntity user = new UserEntity();
        user.setName(name);
        userEntityRepository.save(user);
        return "200";
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<UserEntity> getAllUserEntities() {
        return userEntityRepository.findAll();
    }

    @PutMapping(path = "/newName")
    public @ResponseBody
    String changeUserEntityName(@RequestParam String oldName, @RequestParam String newName) {
        Iterable<UserEntity> allUserEntity = userEntityRepository.findAll();
        if (allUserEntity != null) {
            Iterator<UserEntity> iterator = allUserEntity.iterator();
            while (iterator.hasNext()) {
                UserEntity userEntity = iterator.next();
                if (userEntity.getName().equals(oldName)) {
                    userEntity.setName(newName);
                    userEntityRepository.save(userEntity);
                    return "200";
                }
            }
        }
        return "404";
    }

    @DeleteMapping(path = "/remove")
    public @ResponseBody
    String deleteUserEntity(@RequestParam String name) {
        Iterable<UserEntity> allUserEntity = userEntityRepository.findAll();
        if (allUserEntity != null) {
            Iterator<UserEntity> iterator = allUserEntity.iterator();
            while (iterator.hasNext()) {
                UserEntity userEntity = iterator.next();
                if (userEntity.getName().equals(name)) {
                    userEntityRepository.delete(userEntity);
                    return "204";
                }
            }
        }
        return "404";
    }

    @PostMapping(path = "/choiceByName")
    public @ResponseBody
    Iterable<UserEntity> getUserEntitiesByName(@RequestParam String... names) {
        List<UserEntity> userEntities = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            Iterable<UserEntity> allUserEntity = userEntityRepository.findAll();
            if(allUserEntity != null) {
                Iterator<UserEntity> iterator = allUserEntity.iterator();
                while (iterator.hasNext()) {
                    UserEntity userEntity = iterator.next();
                    if (userEntity.getName().equals(name)) {
                        userEntities.add(userEntity);
                    }
                }
            }
        }
        return (Iterable)userEntities;
    }
}
