package com.habel.sb3multitenantjpa.entity.tenant;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Query(value = "select * from \"user\" where id = :id", nativeQuery = true)
    Optional<User> findUserByIdNative(Long id);
}
