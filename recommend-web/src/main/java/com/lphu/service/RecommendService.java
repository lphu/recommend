package com.lphu.service;

import com.lphu.model.domin.*;

import java.util.List;

/**
 * @author hupeilei
 * @create 2020/3/10 3:49 下午
 */
public interface RecommendService {

    /**
     * 热门
     * @param num
     * @return
     */
    List<Product> getHotProducts(Integer num);

    /**
     * 评分最多
     * @param num
     * @return
     */
    List<Product> getRateMoreRecommendations(Integer num);

    /**
     * 离线
     * @param id
     * @return
     */
    List<Product> getCollaborativeFilteringRecommendations(Integer id);

    /**
     * 实时
     * @param id
     * @return
     */
    List<Product> getStreamRecommendations(Integer id);
}
