package org.apache.dubbo.admin.model.domain.nacos;

import java.util.List;

/**
 * Created by ZengPengHui at 2023/3/17.
 */
public class NacosServiceDetailResponse {

    private int count;

    private List<NacosServiceDetail> list;

    public NacosServiceDetailResponse(){

    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<NacosServiceDetail> getList() {
        return list;
    }

    public void setList(List<NacosServiceDetail> list) {
        this.list = list;
    }
}
