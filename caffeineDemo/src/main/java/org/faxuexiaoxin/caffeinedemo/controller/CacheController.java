package org.faxuexiaoxin.caffeinedemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ConcurrentMap;

@RestController
@RequestMapping("/test")
public class CacheController {
    @Autowired
    private CacheManager cacheManager;

    /**
     * 查询当前缓存
     * @return
     */
    @GetMapping("/allCache")
    public String outputAllCache(Integer id){
        Cache cache = cacheManager.getCache("simple");
        Cache.ValueWrapper valueWrapper = cache.get(id);
        if(Objects.isNull(valueWrapper)){
            return "该数据未被缓存";
        }
        return valueWrapper.toString();
    }

    /**
     * 增加缓存
     * @return
     */
    @Cacheable(cacheNames = "simple",key = "#queryId")
    @GetMapping("/add")
    public String addCache(Integer queryId){
        return String.format("当前查询的是%s的数据,时间：%s",queryId,new Date().toString());
    }

    /**
     * 删除缓存
     * @return
     */
    @CacheEvict(cacheNames = "simple",key = "#removeId")
    @GetMapping("/remove")
    public String removeCache(Integer removeId){
        return String.format("当前删除的是%s的数据",removeId);
    }
}
