package com.lphu.service.impl;

import com.lphu.constant.ErrorCode;
import com.lphu.dao.ProductRepository;
import com.lphu.dao.RatingRepository;
import com.lphu.dao.UserRepository;
import com.lphu.exception.BusinessException;
import com.lphu.model.domin.Product;
import com.lphu.model.domin.User;
import com.lphu.model.request.UserRequest;
import com.lphu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author hupeilei
 * @create 2020/3/10 3:47 下午
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RatingRepository ratingRepository;
    @Autowired
    ProductRepository productRepository;

    @Override
    public boolean registerUser(UserRequest userRequest) {
        if (checkExist(userRequest.getUsername())) {
            return false;
        }
        User user = new User(userRequest.getUsername(), userRequest.getPassword());
        userRepository.insert(user);
        return true;
    }

    @Override
    public User login(UserRequest userRequest) {
        User user = Optional.ofNullable(userRepository.findByUserName(userRequest.getUsername()))
                .orElseThrow(() -> new BusinessException(ErrorCode.USERNAME_NOT_EXIST, "username = {}", userRequest.getUsername()));

        if (!user.getPassword().equals(userRequest.getPassword())) {
            throw new BusinessException(ErrorCode.PASSWORD_WRONG, "password = {}", userRequest.getPassword());
        }
        return user;
    }

    @Override
    public List<Product> getMyRating(Integer id) {
        return Optional.ofNullable(ratingRepository.findByUserId(id)).get().stream()
                .map(rating -> productRepository.findByProductId(rating.getProductId()))
                .collect(Collectors.toList());
    }

    private boolean checkExist(String username) {
        return null != userRepository.findByUserName(username);
    }
}
