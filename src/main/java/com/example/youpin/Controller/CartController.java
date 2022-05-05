package com.example.youpin.Controller;

import com.example.youpin.Mapper.CartMapper;
import com.example.youpin.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/cart/getList")
    public List<Map<String, Object>> getCartList(Integer uid) {
        return cartService.getCartList(uid);
    }

    @PostMapping("/cart/putItem")
    public Map<String, Object> cartPutItem(Integer gid, Integer uid) {
        return cartService.cartPutItem(gid, uid);
    }

    @PostMapping("/cart/cartItemAddOne")
    public Map<String, Object> cartItemAddOne(Integer caid) {
        return cartService.cartItemAddOne(caid);
    }

    @PostMapping("/cart/cartItemMinusOne")
    public Map<String, Object> cartItemMinusOne(Integer caid) {
        return cartService.cartItemMinusOne(caid);
    }

    @PostMapping("/cart/cartItemDelete")
    public Map<String, Object> cartItemDelete(List<Integer> caids){
        return cartService.cartItemDelete(caids);
    }

}
