package com.yong.clouduser.config;

import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class WeightLoadBalancerConfig extends AbstractLoadBalancerRule {
    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    private AtomicInteger atomicCount = new AtomicInteger(0);

    @Override
    public Server choose(Object key) {
        return choose(getLoadBalancer(), key);
    }

    public Server choose(ILoadBalancer lb, Object key) {
        if (lb == null) {
            return null;
        }
        List<Server> upList = lb.getReachableServers();
        ArrayList<NacosServer> newNacosServers = new ArrayList<>();
        upList.forEach(server -> {
            NacosServer nacosServer = (NacosServer) server;
            double weight = nacosServer.getInstance().getWeight();
            for (int i=0;i<weight;i++){
                newNacosServers.add(nacosServer);
            }
        });
        int index = atomicCount.incrementAndGet() % newNacosServers.size();
        return newNacosServers.get(index);
    }

}
