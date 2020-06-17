package com.lphu.model.domin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author hupeilei
 * @create 2020/3/10 3:36 下午
 */
@Data
@AllArgsConstructor
@Document(collection = "rate_more_recently_product")
public class RateMoreRecentlyProduct {

    @Id
    @JsonIgnore
    private String id;
    private Integer productId;
    private Integer count;
    private Integer yearmonth;
}
