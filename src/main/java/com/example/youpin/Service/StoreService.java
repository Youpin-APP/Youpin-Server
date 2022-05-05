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
public class StoreService {
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private PicMapper picMapper;
    public List<Map<String,Object>> search(String name) {
        Example example = new Example(Goods.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("gname", "%"+name+"%");
        criteria.andEqualTo("enable", 1);
        List<Goods> queryList = goodsMapper.selectByExample(example);
        List<Map<String,Object>> goodsList = new ArrayList<>();
        for (Goods goods : queryList) {
            Example example_pic = new Example(Pic.class);
            Example.Criteria criteria_pic = example_pic.createCriteria();
            criteria_pic.andEqualTo("gid",goods.getGid());
            criteria_pic.andEqualTo("pid",0);
            criteria_pic.andEqualTo("pos",0);
            List<Pic> picQueryList = picMapper.selectByExample(example_pic);
            Map<String, Object> goodsQInfo = new Hashtable<>();
            goodsQInfo.put("name", goods.getGname());
            goodsQInfo.put("id", goods.getGid());
            if(!picQueryList.isEmpty()){
                goodsQInfo.put("picUrl",picQueryList.get(0).getDir());
            }
            else {
                goodsQInfo.put("picUrl","");
            }
            goodsList.add(goodsQInfo);
        }
        return goodsList;
    }
}
