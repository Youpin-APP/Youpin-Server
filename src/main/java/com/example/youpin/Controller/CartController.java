package com.example.youpin.Controller;

import com.example.youpin.Mapper.CartMapper;
import com.example.youpin.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/cart/getList")
    public List<Map<String, Object>> getCartList(@RequestParam String uid) {
        return cartService.getCartList(uid);
    }

    @PostMapping("/cart/putItem")
    public Map<String, Object> cartPutItem(@RequestParam Integer gid, @RequestParam String uid) {
        return cartService.cartPutItem(gid, uid);
    }

    @PostMapping("/cart/cartItemAddOne")
    public Map<String, Object> cartItemAddOne(@RequestParam Integer caid) {
        return cartService.cartItemAddOne(caid);
    }

    @PostMapping("/cart/cartItemMinusOne")
    public Map<String, Object> cartItemMinusOne(@RequestParam Integer caid) {
        return cartService.cartItemMinusOne(caid);
    }

    @PostMapping("/cart/cartItemDelete")
    public Map<String, Object> cartItemDelete(@RequestParam List<Integer> caids){
        return cartService.cartItemDelete(caids);
    }

    @PostMapping("/cart/selectItem")
    public Map<String, Object> cartSelectItem(@RequestParam Integer caid, @RequestParam Integer selected) {
        return cartService.cartSelectItem(caid, selected);
    }

}
