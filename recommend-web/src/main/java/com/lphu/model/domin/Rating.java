package com.lphu.model.domin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.aspectj.runtime.internal.cflowstack.ThreadStackImpl11;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author hupeilei
 * @create 2020/3/10 3:03 下午
 */
@Data
@AllArgsConstructor
@Document(collection = "rating")
public class Rating {

    @Id
    @JsonIgnore
    private String id;
    private Integer userId;
    private Integer productId;
    private Double score;
    private Long timestamp;

    public Rating(Integer userId, Integer productId, Double score, Long timestamp) {
        this.userId = userId;
        this.productId = productId;
        this.score = score;
        this.timestamp = timestamp;
    }
}
