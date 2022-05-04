package com.example.youpin.Controller;

import com.example.youpin.Service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class StoreController {
    @Autowired
    private StoreService storeService;
    @GetMapping("/store/search")
    public List<Map<String,Object>> search(@RequestParam String name) {
        return storeService.search(name);
    }
}
