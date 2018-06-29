package com.seebattleserver.repository;

import org.springframework.data.repository.CrudRepository;

public interface UserEntityRepository extends CrudRepository<UserEntity, Long> {
       // UserEntity getUserEntityByName(String name);
}
