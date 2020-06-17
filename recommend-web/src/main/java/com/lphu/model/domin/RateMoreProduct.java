package com.lphu.model.domin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author hupeilei
 * @create 2020/3/10 3:24 下午
 */
@Data
@AllArgsConstructor
@Document(collection = "rate_more_product")
public class RateMoreProduct {

    @Id
    @JsonIgnore
    private String id;
    private Integer productId;
    private Integer count;
}
