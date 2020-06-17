package com.lphu.service;

import com.lphu.model.domin.Product;

import java.util.List;

/**
 * @author hupeilei
 * @create 2020/3/10 3:49 下午
 */
public interface ProductService {

    Product getProductInfo(Integer id);

    List<Product> getSearchProducts(String keyWord);
}
