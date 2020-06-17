package com.lphu.dao;

import com.lphu.model.domin.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author hupeilei
 * @create 2020/3/10 3:14 下午
 */
public interface RatingRepository extends MongoRepository<Rating,String> {
    List<Rating> findByUserId(Integer id);
}
