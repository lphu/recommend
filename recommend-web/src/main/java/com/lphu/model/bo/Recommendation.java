package com.lphu.model.bo;

import lombok.Data;

/**
 * @author hupeilei
 * @create 2020/3/10 3:17 下午
 */
@Data
public class Recommendation {

    private Integer productId;
    private Double score;
}
