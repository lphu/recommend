package com.lphu.dao;

import com.lphu.model.domin.UserRecs;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author hupeilei
 * @create 2020/3/10 3:39 下午
 */
public interface UserRecsRepository extends MongoRepository<UserRecs,String> {
    UserRecs findByUserId(Integer userId);
}
