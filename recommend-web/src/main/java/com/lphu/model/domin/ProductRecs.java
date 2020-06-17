package com.lphu.model.domin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lphu.model.bo.Recommendation;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author hupeilei
 * @create 2020/3/10 3:19 下午
 */
@Data
@AllArgsConstructor
@Document(collection = "product_recs")
public class ProductRecs {

    @Id
    @JsonIgnore
    private String id;

    private String productId;

    private List<Recommendation> list;
}
