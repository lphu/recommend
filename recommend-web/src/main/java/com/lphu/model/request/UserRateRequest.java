package com.lphu.model.request;

import lombok.Data;

/**
 * @author hupeilei
 * @create 2020/3/10 8:49 下午
 */
@Data
public class UserRateRequest {

    private Integer productId;
    private Integer userId;
    private Double score;
}
