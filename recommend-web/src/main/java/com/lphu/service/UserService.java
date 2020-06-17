package com.lphu.service;

import com.lphu.model.domin.Product;
import com.lphu.model.domin.User;
import com.lphu.model.request.UserRequest;

import java.util.List;

/**
 * @author hupeilei
 * @create 2020/3/10 3:47 下午
 */
public interface UserService {

    boolean registerUser(UserRequest userRequest);

    User login(UserRequest userRequest);

    List<Product> getMyRating(Integer id);
}
