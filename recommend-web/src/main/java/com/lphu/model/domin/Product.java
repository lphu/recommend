package com.lphu.model.domin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author hupeilei
 * @create 2020/3/10 3:03 下午
 */
@Data
@AllArgsConstructor
@Document(collection = "product")
public class Product {

    @Id
    @JsonIgnore
    private String id;
    private Integer productId;
    private String name;
    private String imgUrl;
    private String categories;
    private String tags;
}
