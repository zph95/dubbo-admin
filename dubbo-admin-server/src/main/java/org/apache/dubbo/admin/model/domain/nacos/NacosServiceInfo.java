package org.apache.dubbo.admin.model.domain.nacos;

/**
 * Created by ZengPengHui at 2023/3/17.
 */
public class NacosServiceInfo {
    private String name;
    private String groupName;
    private int clusterCount;
    private int ipCount;
    private int healthyInstanceCount;
    private String triggerFlag;

    public NacosServiceInfo() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setClusterCount(int clusterCount) {
        this.clusterCount = clusterCount;
    }

    public int getClusterCount() {
        return clusterCount;
    }

    public void setIpCount(int ipCount) {
        this.ipCount = ipCount;
    }

    public int getIpCount() {
        return ipCount;
    }

    public void setHealthyInstanceCount(int healthyInstanceCount) {
        this.healthyInstanceCount = healthyInstanceCount;
    }

    public int getHealthyInstanceCount() {
        return healthyInstanceCount;
    }

    public void setTriggerFlag(String triggerFlag) {
        this.triggerFlag = triggerFlag;
    }

    public String getTriggerFlag() {
        return triggerFlag;
    }
}