package com.yong.clouduser.loadbalancer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class WeightLoadBalancer implements LoadBalancer {

    @Autowired
    private DiscoveryClient discoveryClient;

    private AtomicInteger atomicCount = new AtomicInteger(0);

    @Override
    public ServiceInstance getInstances(String serviceId) {
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
        if (instances==null||instances.size()==0){
            return null;
        }
        ArrayList<ServiceInstance> serviceInstances = new ArrayList<>();
        instances.forEach(service -> {
            Double weight = Double.parseDouble(service.getMetadata().get("nacos.weight"));
            for (int i = 0;i<weight;i++){
                serviceInstances.add(service);
            }
        });
        int index = atomicCount.incrementAndGet() % serviceInstances.size();
        return serviceInstances.get(index);
    }
}
