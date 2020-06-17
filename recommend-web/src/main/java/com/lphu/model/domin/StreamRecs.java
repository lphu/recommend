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
 * @create 2020/3/10 3:40 下午
 */
@Data
@AllArgsConstructor
@Document(collection = "stream_recs")
public class StreamRecs {

    @Id
    @JsonIgnore
    private String id;
    private Integer userId;
    private List<Recommendation> recs;
}
