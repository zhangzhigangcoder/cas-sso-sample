package com.zhangzhigang.sso.cache;

public interface CacheService {
	
    void set(String key, String value);

    void set(String key, String value, int seconds);

    String get(String key);

    /**
     * 功能描述: 设置缓存对象
     *
     * @param key 缓存Key
     * @param model 缓存Value，JSON字符串
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    <T> void setModel(String key, T model);

    /**
     * 功能描述: 设置缓存对象，指定超时时间
     *
     * @param key 缓存Key
     * @param model 缓存Value，JSON字符串
     * @param seconds 超时时间
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    <T> void setModel(String key, T model, int seconds);

    /**
     * 功能描述: 获取缓存对象
     *
     * @param key 缓存Key
     * @param clazz 缓存JSON对应的类型
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    <T> T getModel(String key, Class<T> clazz);

    <T> void delModel(String key);

}
