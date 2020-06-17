package com.lphu.controller;

import com.lphu.dto.Result;
import com.lphu.model.request.UserRateRequest;
import com.lphu.service.ProductService;
import com.lphu.service.RatingService;
import com.lphu.service.RecommendService;
import com.lphu.service.UserService;
import com.lphu.utils.Results;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author hupeilei
 * @create 2020/3/10 5:35 下午
 */

@Api("商品接口")
@RestController
@RequestMapping("/api/product")
@Slf4j
public class ProductController {

    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;
    @Autowired
    RatingService ratingService;
    @Autowired
    RecommendService recommendService;

    private static final String RATING_PREFIX = "PRODUCT_RATING_PREFIX:";


    /**
     * 热门商品
     * @param num
     * @return
     */
    @ApiOperation("获取热门商品")
    @GetMapping("/hot")
    public Result getHotProducts(@ApiParam(value = "获取数量", required = true) @RequestParam("num") Integer num) {
        return Results.success(recommendService.getHotProducts(num));
    }

    /**
     * 评分最多商品
     * @param num
     * @return
     */
    @ApiOperation("获取评分最多商品")
    @GetMapping("/most")
    public Result getRateMoreProducts(@ApiParam(value = "获取数量", required = true) @RequestParam("num") Integer num) {
        return Results.success(recommendService.getRateMoreRecommendations(num));
    }

    /**
     * 单个商品详情
     * @param id
     * @return
     */
    @ApiOperation("单个商品详情")
    @GetMapping("/info/{id}")
    public Result getProductInfo(@ApiParam(value = "商品id", required = true) @PathVariable("id") Integer id) {
        return Results.success(productService.getProductInfo(id));
    }

    /**
     * 搜索商品
     * @param keyWord
     * @return
     */
    @ApiOperation("展示所有商品/搜索")
    @GetMapping("/search")
    public Result getSearchProducts(@ApiParam(value = "关键词", required = false) @RequestParam(value = "keyWord", required = false) String keyWord) {
        return Results.success(productService.getSearchProducts(keyWord));
    }

    /**
     * 打分
     * @return
     */
    @ApiOperation("用户评分接口")
    @PostMapping("/rate")
    public Result rateProduct(@RequestBody UserRateRequest userRateRequest) {
        // log -> flume -> kafka -> spark streaming
        // userId|productId|score|timestamp
        if (ratingService.rate(userRateRequest)) {
            log.info(RATING_PREFIX + userRateRequest.getUserId() + "|" + userRateRequest.getProductId() + "|" + userRateRequest.getScore() + "|" + System.currentTimeMillis()/1000);
        }
        return Results.success(true);
    }

    /**
     * 实时推荐
     * @param id
     * @return
     */
    @ApiOperation("获取当前用户的实时推荐列表")
    @GetMapping("/online")
    public Result getOnlineProducts(@RequestParam("id") Integer id) {
        return Results.success(recommendService.getStreamRecommendations(id));
    }

    /**
     * 离线
     * @param id
     * @return
     */
    @ApiOperation("获取当前用户的离线推荐列表")
    @GetMapping("/offline")
    public Result getOfflineProducts(@RequestParam("id") Integer id) {
        return Results.success(recommendService.getCollaborativeFilteringRecommendations(id));
    }
}
