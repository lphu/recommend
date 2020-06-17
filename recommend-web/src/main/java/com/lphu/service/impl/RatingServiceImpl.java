package com.lphu.service.impl;

import com.lphu.constant.ErrorCode;
import com.lphu.dao.RatingRepository;
import com.lphu.exception.BusinessException;
import com.lphu.model.domin.Rating;
import com.lphu.model.request.UserRateRequest;
import com.lphu.service.RatingService;
import com.lphu.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hupeilei
 * @create 2020/3/10 3:48 下午
 */
@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    RatingRepository ratingRepository;
    @Autowired
    RedisUtil redisUtil;

    private final String PREFIX_USERID = "userId:";

    @Override
    public boolean rate(UserRateRequest request) {

        List<Object> list = redisUtil.lGet(PREFIX_USERID + request.getUserId().toString(), 0, -1);
        if (null != list || list.size() > 0) {
            // TODO: 判断userId, productId
            list.forEach(o -> {
                Integer productId = Integer.parseInt(o.toString().split("\\:")[0]);
                if (productId.equals(request.getProductId())) {
                    throw new BusinessException(ErrorCode.REPEAT_RATE, "重复评分, productId = {}", request.getProductId());
                }
            });
        }
        redisUtil.lSet(PREFIX_USERID + request.getUserId(), request.getProductId() + ":" + request.getScore());
        // TODO: 保证redis mongodb数据一致性
        ratingRepository.insert(new Rating(request.getUserId(), request.getProductId(), request.getScore(), System.currentTimeMillis()));
        return true;
    }
}
