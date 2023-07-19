package org.apache.dubbo.admin.schedule;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import org.apache.dubbo.admin.service.RegistryCache;
import org.springframework.stereotype.Component;

/**
 * Created by ZengPengHui at 2023/3/16.
 */
@Component
public class ServiceManageCache  implements RegistryCache<String, List<Map<String,String>>> {

    private final ConcurrentMap<String, List<Map<String, String>>> serviceCache = new ConcurrentHashMap<>();

    @Override
    public void put(String key, List<Map<String, String>> value) {
        this.serviceCache.put(key, value);
    }

    @Override
    public List<Map<String, String>> get(String key) {
        return  this.serviceCache.get(key);
    }
    @Override
    public List<Map<String,String>> computeIfAbsent(String key, Function<? super String, ? extends  List<Map<String,String>>> mappingFunction) {
        return serviceCache.computeIfAbsent(key, mappingFunction);
    }

    public Set<String> getServiceSet(){
        return serviceCache.keySet();
    }

}
