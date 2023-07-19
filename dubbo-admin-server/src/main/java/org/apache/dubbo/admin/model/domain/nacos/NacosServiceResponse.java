package org.apache.dubbo.admin.model.domain.nacos;

import java.util.List;

/**
 * Created by ZengPengHui at 2023/3/17.
 */
public class NacosServiceResponse {

    private int count;

    private List<NacosServiceInfo> serviceList;

    public NacosServiceResponse(){

    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<NacosServiceInfo> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<NacosServiceInfo> serviceList) {
        this.serviceList = serviceList;
    }
}
