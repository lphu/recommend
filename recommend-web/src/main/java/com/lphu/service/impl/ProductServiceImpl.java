package com.lphu.service.impl;

import com.lphu.constant.ErrorCode;
import com.lphu.dao.ProductRepository;
import com.lphu.exception.BusinessException;
import com.lphu.model.domin.Product;
import com.lphu.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author hupeilei
 * @create 2020/3/10 3:49 下午
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public Product getProductInfo(Integer id) {
        return Optional.ofNullable(productRepository.findByProductId(id))
                .orElseThrow(() -> new BusinessException(ErrorCode.PARAM_ERROR, "错误productId = {}", id));
    }

    @Override
    public List<Product> getSearchProducts(String keyWord) {
        return productRepository.findByNameLike(keyWord);
    }
}
