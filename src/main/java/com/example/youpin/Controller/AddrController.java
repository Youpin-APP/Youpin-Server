package com.example.youpin.Controller;

import com.example.youpin.Service.AddrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class AddrController {
    @Autowired
    private AddrService addrService;

    @GetMapping("/addr/getProvinceList")
    public List<Map<String,Object>> getProvince() {
        return addrService.getProvince();
    }

    @GetMapping("/addr/getCityList")
    public List<Map<String,Object>> getCity(@RequestParam Integer pid) {
        return addrService.getCityList(pid);
    }

    @GetMapping("/addr/getDistrictList")
    public List<Map<String,Object>> getProvince(@RequestParam Integer cid) {
        return addrService.getDistrictList(cid);
    }

}
