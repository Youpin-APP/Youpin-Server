package com.example.youpin.Controller;

import com.example.youpin.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/order/checkout")
    public Map<String, Object> checkout(@RequestParam String uid) {
        return orderService.checkout(uid);
    }

    @PostMapping("/order/quickCheckout")
    public Map<String, Object> quickCheckout(@RequestParam String uid) {
        return orderService.quickCheckout(uid);
    }

    @PostMapping("/order/editOrder")
    public Map<String, Object> editOrder(@RequestParam Integer oid, @RequestParam Integer aid) {
        return orderService.editOrder(oid, aid);
    }

    @PostMapping("/order/getOrderDetail")
    public Map<String, Object> getOrderDetail(@RequestParam Integer oid) {
        return orderService.getOrderDetail(oid);
    }

    @PostMapping("/order/cancelOrder")
    public Map<String, Object> cancelOrder(@RequestParam Integer oid) {
        return orderService.cancelOrder(oid);
    }

    @PostMapping("/order/payOrder")
    public Map<String, Object> payOrder(@RequestParam Integer oid) {
        return orderService.payOrder(oid);
    }

    @PostMapping("/order/finishOrder")
    public Map<String, Object> finishOrder(@RequestParam Integer oid) {
        return orderService.finishOrder(oid);
    }
}
