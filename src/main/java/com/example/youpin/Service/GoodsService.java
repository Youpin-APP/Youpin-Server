package com.example.youpin.Service;

import com.example.youpin.Mapper.GoodsMapper;
import com.example.youpin.Mapper.PicMapper;
import com.example.youpin.POJO.Goods;
import com.example.youpin.POJO.Pic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

@Service
@EnableAutoConfiguration
public class GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private PicMapper picMapper;
    public Map<String, Object> getInfo(Integer gid) {
        Hashtable<String, Object> map = new Hashtable<>();
        Goods goods = goodsMapper.selectByPrimaryKey(gid);
        if(goods == null) {
            map.put("success",false);
        }
        Hashtable<String, Object> content = new Hashtable<>();
        content.put("name",goods.getGname());
        content.put("price",goods.getGprice());
        map.put("content",content);
        Example example = new Example(Pic.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("pos",0);
        criteria.andEqualTo("gid",gid);
        List<Pic> queryList = picMapper.selectByExample(example);
        List<Map<String, Object>> picList = new ArrayList<>();
        for (Pic pic : queryList) {
            Map<String, Object> picInfo = new Hashtable<>();
            picInfo.put("id",pic.getPid());
            picInfo.put("url",pic.getDir());
            picList.add(picInfo);
        }
        map.put("pics", picList);
        map.put("success", true);
        return map;
    }
}
