package com.dk.apiversionone.user;

import com.dk.apiversionone.user.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Integer> {
}
