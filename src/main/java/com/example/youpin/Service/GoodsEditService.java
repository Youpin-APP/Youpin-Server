package com.example.youpin.Service;

import com.example.youpin.Mapper.GoodsMapper;
import com.example.youpin.POJO.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

@Service
@EnableAutoConfiguration
public class GoodsEditService {
    @Autowired
    private GoodsMapper goodsMapper;

    public Map<String, Object> addGoods(String name, Integer sid, Integer tid1, Integer tid2, Integer tid3){
        Goods goods = new Goods();
        goods.setGname(name);
        goods.setGprice(0.0f);
        goods.setGcount(0);
        goods.setSid(sid);
        goods.setTid1(tid1);
        goods.setTid2(tid2);
        goods.setTid3(tid3);
        goodsMapper.insertSelective(goods);
        Hashtable<String,Object> map = new Hashtable<>();
        map.put("gid",goods.getGid());
        return map;
    }
}
