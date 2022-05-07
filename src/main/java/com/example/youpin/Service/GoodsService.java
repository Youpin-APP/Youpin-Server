package com.example.youpin.Service;

import com.example.youpin.Mapper.GoodsMapper;
import com.example.youpin.Mapper.PicMapper;
import com.example.youpin.Mapper.TypeMapper;
import com.example.youpin.POJO.Goods;
import com.example.youpin.POJO.Pic;
import com.example.youpin.POJO.Type;
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
    @Autowired
    private TypeMapper typeMapper;

    public Map<String, Object> getInfo(String gid) {
        Hashtable<String, Object> map = new Hashtable<>();
        Goods goods = goodsMapper.selectByPrimaryKey(gid);
        if(goods == null) {
            map.put("success",false);
        }
        Hashtable<String, Object> content = new Hashtable<>();
        content.put("name",goods.getGname());
        content.put("price",goods.getGprice());
        map.put("content",content);
        Example example_banner = new Example(Pic.class);
        Example.Criteria criteria_banner = example_banner.createCriteria();
        criteria_banner.andEqualTo("pos",0);
        criteria_banner.andEqualTo("gid",gid);
        example_banner.setOrderByClause("pid asc");
        List<Pic> queryBannerList = picMapper.selectByExample(example_banner);
        List<Map<String, Object>> picBannerList = new ArrayList<>();
        for (Pic pic : queryBannerList) {
            Map<String, Object> picInfo = new Hashtable<>();
            picInfo.put("id",pic.getPid());
            picInfo.put("url",pic.getDir());
            picBannerList.add(picInfo);
        }
        map.put("pic_banner", picBannerList);
        Example example_detail = new Example(Pic.class);
        Example.Criteria criteria_detail = example_detail.createCriteria();
        criteria_detail.andEqualTo("pos",1);
        criteria_detail.andEqualTo("gid",gid);
        example_detail.setOrderByClause("pid asc");
        List<Pic> queryDetailList = picMapper.selectByExample(example_detail);
        List<Map<String, Object>> picDetailList = new ArrayList<>();
        for (Pic pic : queryDetailList) {
            Map<String, Object> picInfo = new Hashtable<>();
            picInfo.put("id",pic.getPid());
            picInfo.put("url",pic.getDir());
            picDetailList.add(picInfo);
        }
        map.put("pic_detail", picDetailList);
        map.put("success", true);
        if(goods.getTid1() != null){
            Map<String, Object> typeMap1 = new Hashtable<>();
            Type type = typeMapper.selectByPrimaryKey(goods.getTid1());
            typeMap1.put("name",type.getTname());
            typeMap1.put("id", type.getTid());
            map.put("type1", typeMap1);
        }
        if(goods.getTid2() != null){
            Map<String, Object> typeMap2 = new Hashtable<>();
            Type type = typeMapper.selectByPrimaryKey(goods.getTid2());
            typeMap2.put("name",type.getTname());
            typeMap2.put("id", type.getTid());
            map.put("type2", typeMap2);
        }
        if(goods.getTid3() != null){
            Map<String, Object> typeMap3 = new Hashtable<>();
            Type type = typeMapper.selectByPrimaryKey(goods.getTid3());
            typeMap3.put("name",type.getTname());
            typeMap3.put("id", type.getTid());
            map.put("type3", typeMap3);
        }
        return map;
    }

    public Map<String, Object> getTypeList(Integer gid){
        Goods goods = goodsMapper.selectByPrimaryKey(gid);
        Map<String, Object> map = new Hashtable<>();
        if(goods == null) {
            map.put("success",false);
            return map;
        }
        if(goods.getTid1() != null) {
            Type type = typeMapper.selectByPrimaryKey(goods.getTid1());
            Example example = new Example(Type.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("tfid", type.getTfid());
            List<Type> types = typeMapper.selectByExample(example);
            List<Map<String, Object>> typesInfos = new ArrayList<>();
            for (Type typeQ : types) {
                Map<String, Object> typeInfo = new Hashtable<>();
                typeInfo.put("tname", typeQ.getTname());
                typeInfo.put("tid", typeQ.getTid());
                typesInfos.add(typeInfo);
            }
            map.put("tid1", typesInfos);
        }
        if(goods.getTid2() != null) {
            Type type = typeMapper.selectByPrimaryKey(goods.getTid2());
            Example example = new Example(Type.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("tfid", type.getTfid());
            List<Type> types = typeMapper.selectByExample(example);
            List<Map<String, Object>> typesInfos = new ArrayList<>();
            for (Type typeQ : types) {
                Map<String, Object> typeInfo = new Hashtable<>();
                typeInfo.put("tname", typeQ.getTname());
                typeInfo.put("tid", typeQ.getTid());
                typesInfos.add(typeInfo);
            }
            map.put("tid2", typesInfos);
        }
        if(goods.getTid3() != null) {
            Type type = typeMapper.selectByPrimaryKey(goods.getTid3());
            Example example = new Example(Type.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("tfid", type.getTfid());
            List<Type> types = typeMapper.selectByExample(example);
            List<Map<String, Object>> typesInfos = new ArrayList<>();
            for (Type typeQ : types) {
                Map<String, Object> typeInfo = new Hashtable<>();
                typeInfo.put("tname", typeQ.getTname());
                typeInfo.put("tid", typeQ.getTid());
                typesInfos.add(typeInfo);
            }
            map.put("tid3", typesInfos);
        }
        map.put("success", true);
        return map;
    }

    public Map<String, Object> isAvailableType(Integer tid1, Integer tid2, Integer tid3){
        Example example = new Example(Goods.class);
        Example.Criteria criteria = example.createCriteria();
        if(tid1 != null){
            criteria.andEqualTo("tid1",tid1);
        }
        if(tid2 != null){
            criteria.andEqualTo("tid2", tid2);
        }
        if(tid3 != null){
            criteria.andEqualTo("tid3",tid3);
        }

        Hashtable<String, Object> map = new Hashtable<>();
        List<Goods> goodsList = goodsMapper.selectByExample(example);
        if(goodsList.isEmpty()){
            map.put("isAvailableType", false);
        }
        else {
            map.put("isAvailableType", true);
        }
        return map;
    }

    public Map<String, Object> getGidByType(Integer gid, Integer tid1, Integer tid2, Integer tid3) {
        Goods goods = goodsMapper.selectByPrimaryKey(gid);
        Example example = new Example(Goods.class);
        Example.Criteria criteria = example.createCriteria();
        Map<String, Object> map = new Hashtable<>();
        if(goods.getTid1() != null) {
            if(tid1 != null) {
                criteria.andEqualTo("tid1", tid1);
            }
            else {
                map.put("success", false);
                return map;
            }
        }
        if(goods.getTid2() != null) {
            if(tid2 != null) {
                criteria.andEqualTo("tid2", tid2);
            }
            else {
                map.put("success", false);
                return map;
            }
        }
        if(goods.getTid3() != null) {
            if(tid3 != null) {
                criteria.andEqualTo("tid3", tid3);
            }
            else {
                map.put("success", false);
                return map;
            }
        }
        List<Goods> list = goodsMapper.selectByExample(example);
        if(list.isEmpty()){
            map.put("gid","");
        }
        else {
            map.put("gid",list.get(0).getGid());
        }
        map.put("success",true);
        return map;
    }
}
