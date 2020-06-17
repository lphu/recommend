package com.lphu.dao;

import com.lphu.model.domin.RateMoreProduct;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author hupeilei
 * @create 2020/3/10 3:39 下午
 */
public interface RateMoreProductRepository extends MongoRepository<RateMoreProduct,String> {
}
