package org.github.xx.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "multicache.redis")
public class RedisProperties {
    //********************单机配置项**************************/
    /**
     * 单机配置项
     */
    String host = "localhost";
    Integer port = 6379;

    //********************集群配置**************************/
    /**
     * 不为空表示集群版，示例
     * localhost:7379,localhost2:7379
     */
    String cluster = "";

    //********************哨兵配置**************************/
    /**
     * 哨兵master名称,示例：mymaster
     */
    String sentinelMaster = "mymaster";

    /**
     * 哨兵节点，示例：localhost:26397,localhost2:26397
     */
    String sentinelNodes = "";

    //********************通用配置**************************/
    /**
     * 使用数据库
     */
    Integer database = 0;

    /**
     * 密码
     */
    String password = null;

    /**
     * 超时时间 单位秒 默认一个小时
     */
    Integer timeout = 3600;

    /**
     * 序列化方式:
     KryoRedisSerializer,ProtostuffRedisSerializer,JacksonRedisSerializer,JdkRedisSerializer
     */
    String serializer = "ProtostuffRedisSerializer";

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public String getSentinelMaster() {
        return sentinelMaster;
    }

    public void setSentinelMaster(String sentinelMaster) {
        this.sentinelMaster = sentinelMaster;
    }

    public String getSentinelNodes() {
        return sentinelNodes;
    }

    public void setSentinelNodes(String sentinelNodes) {
        this.sentinelNodes = sentinelNodes;
    }

    public Integer getDatabase() {
        return database;
    }

    public void setDatabase(Integer database) {
        this.database = database;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public String getSerializer() {
        return serializer;
    }

    public void setSerializer(String serializer) {
        this.serializer = serializer;
    }
}