package org.apache.dubbo.admin.schedule;


import com.alibaba.fastjson.JSON;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.dubbo.admin.model.domain.nacos.NacosServiceDetail;
import org.apache.dubbo.admin.model.domain.nacos.NacosServiceDetailResponse;
import org.apache.dubbo.admin.model.domain.nacos.NacosServiceInfo;
import org.apache.dubbo.admin.model.domain.nacos.NacosServiceResponse;
import org.apache.dubbo.admin.service.impl.MetricsServiceImplV2;
import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by ZengPengHui at 2023/3/16.
 */

@Component
public class NacosRefreshCacheJob {
    private static final Logger logger = LoggerFactory.getLogger(NacosRefreshCacheJob.class);
    @Value("${admin.nacos.url:http://18.142.28.140:5848/nacos}")
    private String nacosUrl;

    @Value("${admin.nacos.namespace:public}")
    private String namespace;

    @Value("${admin.nacos.pageSize:100}")
    private Integer pageSize;

    @Value("${admin.nacos.clusterName:DEFAULT}")
    private String clusterName;

    @Value("${admin.nacos.groupName:DEFAULT_GROUP}")
    private String groupName;

    @Autowired
    private ServiceManageCache serviceManageCache;

    @Scheduled(fixedDelay = 30000L)
    public void refreshCache() {
        List<NacosServiceInfo> result = getNacosServiceInfoList();
        for (NacosServiceInfo info : result) {
            List<NacosServiceDetail> detailList = getNacosServiceDetailList(info.getName());
            List<Map<String, String>> serviceMapList =new ArrayList<>();
            for (NacosServiceDetail detail : detailList) {
                Map<String, String> metaDataMap = detail.getMetadata();
                serviceMapList.add(metaDataMap);
            }
            serviceManageCache.put(info.getName(), serviceMapList);
            logger.info("get nacos service info: " + info.getName());
        }
    }

    private List<NacosServiceInfo> getNacosServiceInfoList() {
        List<NacosServiceInfo> infoList = new ArrayList<>();
        Integer pageNo = 1;
        int count = 0;
        do {
            Map<String, String> paramsMap = new HashMap<>();
            paramsMap.put("pageNo", pageNo.toString());
            paramsMap.put("pageSize", pageSize.toString());
            paramsMap.put("namespaceId", namespace);
            String result = get(nacosUrl + "/nacos/v1/ns/catalog/services", paramsMap);
            NacosServiceResponse response = JSON.parseObject(result, NacosServiceResponse.class);
            count = response.getCount();
            infoList.addAll(response.getServiceList());
            pageNo++;
        } while (pageNo * pageSize < count);
        return infoList;
    }

    private List<NacosServiceDetail> getNacosServiceDetailList(String serviceName) {
        List<NacosServiceDetail> detailList = new ArrayList<>();
        Integer pageNo = 1;
        int count = 0;
        do {
            Map<String, String> paramsMap = new HashMap<>();
            paramsMap.put("serviceName", serviceName);
            paramsMap.put("clusterName", clusterName);
            paramsMap.put("pageNo", pageNo.toString());
            paramsMap.put("pageSize", pageSize.toString());
            paramsMap.put("namespaceId", namespace);
            String result = get(nacosUrl + "/nacos/v1/ns/catalog/instances", paramsMap);
            NacosServiceDetailResponse response = JSON.parseObject(result, NacosServiceDetailResponse.class);
            count = response.getCount();
            detailList.addAll(response.getList());
            pageNo++;
        } while (pageNo * pageSize < count);
        return detailList;
    }


    private String get(String urlString, Map<String, String> paramsMap) {
        try {
            if (!paramsMap.isEmpty()) {
                urlString += "?" + paramsMap.entrySet().stream().map(entry -> entry.getKey() + "="
                                                                              + entry.getValue()).collect(Collectors.joining("&"));
            }

            // 创建 URL 对象
            URL url = new URL(urlString);

            // 打开连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // 设置请求方法为 GET
            connection.setRequestMethod("GET");

            // 读取返回的数据
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
