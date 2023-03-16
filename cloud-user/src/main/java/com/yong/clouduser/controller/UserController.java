package com.yong.clouduser.controller;

import com.yong.clouduser.loadbalancer.RandomLoadBalancer;
import com.yong.clouduser.loadbalancer.RoundLoadBalancer;
import com.yong.clouduser.loadbalancer.WeightLoadBalancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@RestController
@RequestMapping("/user")
@RefreshScope
public class UserController {

    @Autowired
    private Registration registration;
    @Autowired
    private RoundLoadBalancer roundLoadBalancer;
    @Autowired
    private RandomLoadBalancer randomLoadBalancer;
    @Autowired
    private WeightLoadBalancer weightLoadBalancer;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/round")
    public String round(){
        ServiceInstance serviceInstance = roundLoadBalancer.getInstances("cloud-order");
        URI rpcMemberUrl = serviceInstance.getUri();
       // String result = restTemplate.getForObject(rpcMemberUrl + "/getUser", String.class);
        return "订单调用会员返回结果:" + serviceInstance.getPort();
    }

    @GetMapping("/random")
    public String random(){
        ServiceInstance serviceInstance = randomLoadBalancer.getInstances("cloud-order");
        URI rpcMemberUrl = serviceInstance.getUri();
        // String result = restTemplate.getForObject(rpcMemberUrl + "/getUser", String.class);
        return "订单调用会员返回结果:" + serviceInstance.getPort();
    }

    @GetMapping("/weight")
    public String weight(){
        ServiceInstance serviceInstance = weightLoadBalancer.getInstances("cloud-order");
        URI rpcMemberUrl = serviceInstance.getUri();
        // String result = restTemplate.getForObject(rpcMemberUrl + "/getUser", String.class);
        return "订单调用会员返回结果:" + serviceInstance.getPort();
    }

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @GetMapping("/ribbon")
    public String ribbon(){
        ServiceInstance instance = loadBalancerClient.choose("cloud-order");
        return "订单调用会员返回结果:" + instance.getPort();
    }

    @Value("${yong.user}")
    private String user;

    @GetMapping("/user")
    public String user(){
        return "订单调用会员返回结果:" + user;
    }
}
