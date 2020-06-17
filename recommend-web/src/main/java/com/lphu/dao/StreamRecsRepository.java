package com.lphu.dao;

import com.lphu.model.domin.StreamRecs;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author hupeilei
 * @create 2020/3/10 3:40 下午
 */
public interface StreamRecsRepository extends MongoRepository<StreamRecs,String> {
    StreamRecs findByUserId(Integer userId);
}
