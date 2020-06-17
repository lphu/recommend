package com.lphu.dao;

import com.lphu.model.domin.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {
    User findByUserName(String userName);
}