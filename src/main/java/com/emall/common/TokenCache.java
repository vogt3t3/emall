package com.emall.common;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


/**
 * Created by Administrator on 2017/11/30.
 */
public class TokenCache {
    private static final Logger logger=LoggerFactory.getLogger(TokenCache.class);
    public static final String TOKEN_PREFIX = "token_";//设定一个前缀
    //创建一个guava的本地缓存LocalCache对象
    private static LoadingCache<String,String> localCache = CacheBuilder.newBuilder()
            .initialCapacity(1000)
            .maximumSize(1000)//超过最大值，则使用LRU算法删除缓存数据
            .expireAfterAccess(12, TimeUnit.HOURS)//key-value保留12小时
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String s) throws Exception {
                    return null;//当key没有中时，guava会自动调用load方法，返回指定数据，默认为null值
                }
            });
    public static void setKey(String key,String value){
        localCache.put(key,value);
    }
    public static String getKey(String key){
        String value = null;
        try {
            value  = localCache.get(key);
        } catch (ExecutionException e) {
            logger.error("LocalCache",e);
        }
        return value;
    }
}
