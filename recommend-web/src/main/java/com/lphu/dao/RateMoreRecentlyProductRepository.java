package com.lphu.dao;

import com.lphu.model.domin.RateMoreRecentlyProduct;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author hupeilei
 * @create 2020/3/10 3:39 下午
 */
public interface RateMoreRecentlyProductRepository extends MongoRepository<RateMoreRecentlyProduct,String> {
}
