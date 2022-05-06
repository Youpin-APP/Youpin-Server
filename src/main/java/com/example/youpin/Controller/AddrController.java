package com.example.youpin.Controller;

import com.example.youpin.Service.AddrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    public List<Map<String,Object>> getDistrict(@RequestParam Integer cid) {
        return addrService.getDistrictList(cid);
    }

    @PostMapping("/user/getAddr")
    public Map<String, Object> getAddr(@RequestParam Integer aid) {
        return addrService.getAddr(aid);
    }

    @PostMapping("/user/getAddrList")
    public List<Map<String, Object>> getAddrList(@RequestParam String uid) {
        return addrService.getAddrList(uid);
    }

    @PostMapping("/user/addAddr")
    public Map<String, Object> addAddr(@RequestParam String uid, @RequestParam Integer did,
                                       @RequestParam String addrDetail,@RequestParam  String name,
                                       @RequestParam String tel, @RequestParam Integer isDefault) {
        return addrService.addAddr(uid,did,addrDetail,name,tel,isDefault);
    }

    @PostMapping("/user/delAddr")
    public Map<String, Object> delAddr(@RequestParam Integer aid) {
        return addrService.delAddr(aid);
    }

    @PostMapping("/user/updateAddr")
    public Map<String, Object> updateAddr(@RequestParam Integer aid, @RequestParam Integer did,
                                          @RequestParam String addrDetail,@RequestParam  String name,
                                          @RequestParam String tel, @RequestParam Integer isDefault) {
        return addrService.updateAddr(aid,did,addrDetail,name,tel,isDefault);
    }
}
