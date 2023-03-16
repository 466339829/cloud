package com.yong.clouduser.loadbalancer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class RandomLoadBalancer implements LoadBalancer {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Override
    public ServiceInstance getInstances(String serviceId) {
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
        if (instances==null||instances.size()==0){
            return null;
        }
        Random random = new Random();
        int index = random.nextInt(instances.size());
        return instances.get(index);
    }
}
