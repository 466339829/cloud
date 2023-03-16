package com.yong.clouduser.loadbalancer;

import org.springframework.cloud.client.ServiceInstance;

public interface LoadBalancer  {

    ServiceInstance getInstances(String serviceId);

}
