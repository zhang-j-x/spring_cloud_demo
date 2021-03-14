package com.jx.order.controller;


import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.jx.common.vo.common.Rs;
import com.jx.order.exception.OrderCreateException;
import com.jx.order.form.PlaceOrderForm;
import com.jx.order.service.IOrderService;
import com.jx.product.client.ProductClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Description:
 * @Author: zhangjx
 * @Date: 2021-01-26 15:31
 **/
@RestController
@RequestMapping("/order/")
@Slf4j
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    ProductClient productClient;

    /**
     * 下单接口
     * @param placeOrder 订单信息
     * @return
     */
    @PostMapping("placeOrder")
    @SentinelResource(value = "placeOrder")
    public Rs placeOrder(@RequestBody PlaceOrderForm placeOrder){
//        try {
            orderService.createOrder(placeOrder);
//        }catch (OrderCreateException e){
//            return Rs.fail(e.getMessage());
//        }
        return Rs.success();
    }

    @PostMapping("ribbonTest")
    @SentinelResource(value = "ribbonTest")
    public Rs ribbonTest(){
//        ResponseEntity response = restTemplate.getForEntity("http://product/product/qryProductInfo?productId=1", String.class);
//        ServiceInstance productInstance = loadBalancerClient.choose("product");
//        String url = String.format("http://%s:%s",productInstance.getHost(),productInstance.getPort()) + "product/serverPort";
//        RestTemplate restTemplate = new RestTemplate();
//        return Rs.success(restTemplate.getForEntity(url,String.class));
        log.info("ribbonTest in order ....");
        return Rs.success(productClient.serverPort());
    }


    public static void main(String[] args) throws InterruptedException {
        // 配置规则.
        initFlowRules();

        while (true) {
            // 1.5.0 版本开始可以直接利用 try-with-resources 特性，自动 exit entry
            try (Entry entry = SphU.entry("HelloWorld")) {
                // 被保护的逻辑
                System.out.println("hello world");
            } catch (BlockException ex) {
                // 处理被流控的逻辑
                System.out.println("HelloWorld 被流控了!");
            }
            Thread.sleep(100);
        }
    }

    private static void initFlowRules() {
        //流控规则
//        List<FlowRule> rules = new ArrayList<>();
//        FlowRule rule = new FlowRule();
//        rule.setResource("HelloWorld");
//        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
//        rule.setCount(5);
//        rules.add(rule);
//        FlowRuleManager.loadRules(rules);
        //降级规则
//        List<DegradeRule> rules = new ArrayList<>();
//        DegradeRule rule = new DegradeRule();
//        rule.setResource("HelloWorld");
//        rule.setCount(10);
//        rule.setGrade(RuleConstant.DEGRADE_GRADE_RT);
//        rule.setTimeWindow(10);
//        rules.add(rule);
//        DegradeRuleManager.loadRules(rules);
        //热点规则
//        List<FlowRule> rules = new ArrayList<>();
//        ParamFlowRule rule = new ParamFlowRule();
//        rule.setResource("HelloWorld");
//        rule.setParamIdx(0);
//        rule.setCount(5);
//        ParamFlowItem item = new ParamFlowItem();
//        item.setObject();
//        item.setClassType(int.class.getName());
//        item.setCount(10);
//        rule.setParamFlowItemList(Collections.singletonList(item));
//        ParamFlowRuleManager.loadRules(Collections.singletonList(rule));
    }

}
