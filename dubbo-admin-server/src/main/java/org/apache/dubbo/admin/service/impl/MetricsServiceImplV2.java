package org.apache.dubbo.admin.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.dubbo.admin.model.dto.RelationDTO;
import org.apache.dubbo.admin.schedule.ServiceManageCache;
import org.apache.dubbo.admin.service.MetricsService;
import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.apache.dubbo.common.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ZengPengHui at 2023/3/17.
 */
@Service(value = "relationV2")
public class MetricsServiceImplV2 implements MetricsService {
    private static final Logger logger = LoggerFactory.getLogger(MetricsServiceImplV2.class);
    @Autowired
    private ServiceManageCache serviceManageCache;

    @Override
    public RelationDTO getApplicationRelation() {


        // collect all service
        Set<String> serviceSet = serviceManageCache.getServiceSet();
        Map<String, RelationDTO.Node> consumerNodeMap = new HashMap<>();
        // collect consumer's service and applications map <service, set<application>>
        Map<String, Set<String>> consumerServiceApplicationMap = new HashMap<>();
        // collect provider's nodes
        Map<String, RelationDTO.Node> providerNodeMap = new HashMap<>();
        // collect provider's service and applications map <service, set<application>>
        Map<String, Set<String>> providerServiceApplicationMap = new HashMap<>();
        for (String name : serviceSet) {
            List<Map<String, String>> metaDataMapList = serviceManageCache.get(name);
            String serviceName =  getServiceName(name);

            for (Map<String, String> metaDataInfo : metaDataMapList) {
                String application = metaDataInfo.get("application");
                String side = metaDataInfo.get("side");
                String release = metaDataInfo.get("release");
                if ("provider".equals(side)) {
                    if (!providerNodeMap.keySet().contains(application)) {
                        RelationDTO.Node node = new RelationDTO.Node(application, RelationDTO.PROVIDER_CATEGORIES.getIndex(), release);
                        providerNodeMap.put(application, node);
                    }
                    providerServiceApplicationMap.computeIfAbsent(serviceName, s -> new HashSet<>());
                    providerServiceApplicationMap.get(serviceName).add(application);
                }
                if ("consumer".equals(side)) {
                    if (!consumerNodeMap.keySet().contains(application)) {
                        RelationDTO.Node node = new RelationDTO.Node(application, RelationDTO.CONSUMER_CATEGORIES.getIndex(), release);
                        consumerNodeMap.put(application, node);
                    }
                    consumerServiceApplicationMap.computeIfAbsent(serviceName, s -> new HashSet<>());
                    consumerServiceApplicationMap.get(serviceName).add(application);
                }
            }
        }

        // merge provider's nodes and consumer's nodes
        Map<String, RelationDTO.Node> nodeMap = new HashMap<>(consumerNodeMap);
        for (Map.Entry<String, RelationDTO.Node> entry : providerNodeMap.entrySet()) {
            if (nodeMap.get(entry.getKey()) != null) {
                nodeMap.get(entry.getKey()).setCategory(RelationDTO.CONSUMER_AND_PROVIDER_CATEGORIES.getIndex());
            } else {
                nodeMap.put(entry.getKey(), entry.getValue());
            }
        }
        int index = 0;
        for (RelationDTO.Node v : nodeMap.values()) {
            v.setIndex(index);
            v.setName(v.getName() + " " + v.getRelease());
            index++;
        }
        // build link by same service
        Set<RelationDTO.Link> linkSet = new HashSet<>();
        for (String name : serviceSet) {
            String service =  getServiceName(name);
            Set<String> consumerApplicationSet = consumerServiceApplicationMap.get(service);
            Set<String> providerApplicationSet = providerServiceApplicationMap.get(service);
            if (CollectionUtils.isNotEmpty(consumerApplicationSet)
                && CollectionUtils.isNotEmpty(providerApplicationSet)) {
                for (String providerApplication : providerApplicationSet) {
                    for (String consumerApplication : consumerApplicationSet) {
                        if (nodeMap.get(consumerApplication) != null && nodeMap.get(providerApplication) != null) {
                            Integer consumerIndex = nodeMap.get(consumerApplication).getIndex();
                            Integer providerIndex = nodeMap.get(providerApplication).getIndex();
                            linkSet.add(new RelationDTO.Link(consumerIndex, providerIndex));
                        }
                    }
                }
            }
        }
        // sort node by index
        List<RelationDTO.Node> nodeList = nodeMap.values().stream().sorted(Comparator.comparingInt(RelationDTO.Node::getIndex)).collect(Collectors.toList());
        return new RelationDTO(nodeList, new ArrayList<>(linkSet));
    }

    private String getServiceName(String name){
        String serviceName;
        if (name.startsWith("providers:")) {
            serviceName = name.replaceFirst("providers:", "");
        } else if (name.startsWith("consumers:")) {
            serviceName = name.replaceFirst("consumers:", "");
        } else {
            serviceName = name;
        }
        return serviceName;
    }
}
