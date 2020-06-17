package com.lphu.service.impl;

import com.lphu.constant.ErrorCode;
import com.lphu.dao.*;
import com.lphu.exception.BusinessException;
import com.lphu.model.domin.*;
import com.lphu.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author hupeilei
 * @create 2020/3/10 3:49 下午
 */
@Service
public class RecommendServiceImpl implements RecommendService {

    @Autowired
    RateMoreProductRepository rateMoreProductRepository;
    @Autowired
    RateMoreRecentlyProductRepository rateMoreRecentlyProductRepository;
    @Autowired
    UserRecsRepository userRecsRepository;
    @Autowired
    StreamRecsRepository streamRecsRepository;
    @Autowired
    ProductRepository productRepository;

    @Override
    public List<Product> getHotProducts(Integer num) {

        List<RateMoreRecentlyProduct> list = rateMoreRecentlyProductRepository.findAll();
        Collections.sort(list, new Comparator<RateMoreRecentlyProduct>() {
            @Override
            public int compare(RateMoreRecentlyProduct o1, RateMoreRecentlyProduct o2) {
                return o1.getYearmonth() - o2.getYearmonth();
            }
        });
        return removeDuplicate(list.subList(0, num).stream()
                .map(item -> productRepository.findByProductId(item.getProductId()))
                .collect(Collectors.toList()));
    }

    @Override
    public List<Product> getRateMoreRecommendations(Integer num) {
        List<RateMoreProduct> list = rateMoreProductRepository.findAll();
        Collections.sort(list, new Comparator<RateMoreProduct>() {
            @Override
            public int compare(RateMoreProduct o1, RateMoreProduct o2) {
                return o1.getCount() - o2.getCount();
            }
        });
        return removeDuplicate(list.subList(0, num).stream()
                .map(item -> productRepository.findByProductId(item.getProductId()))
                .collect(Collectors.toList()));
    }

    @Override
    public List<Product> getCollaborativeFilteringRecommendations(Integer id) {
        UserRecs userRecs = Optional.ofNullable(userRecsRepository.findByUserId(id))
                .orElseThrow(() -> new BusinessException(ErrorCode.PARAM_ERROR, "错误userId = {}", id));
        System.out.println(userRecs.toString());
        return removeDuplicate(userRecs.getRecs().stream()
                .map(recommendation -> productRepository.findByProductId(recommendation.getProductId()))
                .collect(Collectors.toList()));
    }

    @Override
    public List<Product> getStreamRecommendations(Integer id) {
        StreamRecs streamRecs = Optional.ofNullable(streamRecsRepository.findByUserId(id))
                .orElseThrow(() -> new BusinessException(ErrorCode.PARAM_ERROR, "错误userId = {}", id));
        return removeDuplicate(streamRecs.getRecs().stream()
                .map(recommendation -> productRepository.findByProductId(recommendation.getProductId()))
                .collect(Collectors.toList()));
    }

    private List<Product> removeDuplicate(List<Product> list) {
        return list.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(()
                    -> new TreeSet<>(Comparator.comparing(o
                    -> o.getProductId()))), ArrayList::new));
    }
}
