package com.lphu.service;

import com.lphu.model.request.UserRateRequest;

/**
 * @author hupeilei
 * @create 2020/3/10 3:48 下午
 */
public interface RatingService {

     boolean rate(UserRateRequest request);
}
