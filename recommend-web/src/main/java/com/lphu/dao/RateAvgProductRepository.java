package com.lphu.dao;

import com.lphu.model.domin.RateAvgProduct;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author hupeilei
 * @create 2020/3/10 3:38 下午
 */
public interface RateAvgProductRepository extends MongoRepository<RateAvgProduct,String>
{
}
