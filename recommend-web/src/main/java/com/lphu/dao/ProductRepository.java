package com.lphu.dao;

import com.lphu.model.domin.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author hupeilei
 * @create 2020/3/10 3:14 下午
 */
public interface ProductRepository extends MongoRepository<Product,String> {
    Product findByProductId(Integer productId);

    List<Product> findByNameLike(String keyWord);
}
