package org.apache.dubbo.admin.model.domain.nacos;

import java.util.Map;

/**
 * Created by ZengPengHui at 2023/3/17.
 */
public class NacosServiceDetail {

    private String ip;
    private int port;
    private int weight;
    private boolean healthy;
    private boolean enabled;
    private boolean ephemeral;
    private String clusterName;
    private String serviceName;
    private Map<String,String> metadata;
    private int instanceHeartBeatInterval;
    private int instanceHeartBeatTimeOut;
    private int ipDeleteTimeout;

    public void setIp(String ip) {
        this.ip = ip;
    }
    public String getIp() {
        return ip;
    }

    public void setPort(int port) {
        this.port = port;
    }
    public int getPort() {
        return port;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
    public int getWeight() {
        return weight;
    }

    public void setHealthy(boolean healthy) {
        this.healthy = healthy;
    }
    public boolean getHealthy() {
        return healthy;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    public boolean getEnabled() {
        return enabled;
    }

    public void setEphemeral(boolean ephemeral) {
        this.ephemeral = ephemeral;
    }
    public boolean getEphemeral() {
        return ephemeral;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }
    public String getClusterName() {
        return clusterName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    public String getServiceName() {
        return serviceName;
    }

    public void setMetadata(Map<String,String> metadata) {
        this.metadata = metadata;
    }
    public Map<String,String> getMetadata() {
        return metadata;
    }

    public void setInstanceHeartBeatInterval(int instanceHeartBeatInterval) {
        this.instanceHeartBeatInterval = instanceHeartBeatInterval;
    }
    public int getInstanceHeartBeatInterval() {
        return instanceHeartBeatInterval;
    }

    public void setInstanceHeartBeatTimeOut(int instanceHeartBeatTimeOut) {
        this.instanceHeartBeatTimeOut = instanceHeartBeatTimeOut;
    }
    public int getInstanceHeartBeatTimeOut() {
        return instanceHeartBeatTimeOut;
    }

    public void setIpDeleteTimeout(int ipDeleteTimeout) {
        this.ipDeleteTimeout = ipDeleteTimeout;
    }
    public int getIpDeleteTimeout() {
        return ipDeleteTimeout;
    }

}