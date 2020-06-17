package com.lphu.model.domin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author hupeilei
 * @create 2020/3/10 3:23 下午
 */
@Data
@AllArgsConstructor
@Document(collection = "rate_avg_product")
public class RateAvgProduct {

    @Id
    @JsonIgnore
    private String id;
    private Integer productId;
    private Double avg;
}
