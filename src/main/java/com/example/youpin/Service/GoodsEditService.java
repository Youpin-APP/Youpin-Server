package com.example.youpin.Service;

import com.example.youpin.Mapper.GoodsMapper;
import com.example.youpin.Mapper.PicMapper;
import com.example.youpin.Mapper.TypeMapper;
import com.example.youpin.POJO.Goods;
import com.example.youpin.POJO.Pic;
import com.example.youpin.POJO.Type;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.io.File;
import java.util.*;

@Service
@EnableAutoConfiguration
public class GoodsEditService {
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private PicMapper picMapper;
    @Autowired
    private TypeMapper typeMapper;

    public Map<String, Object> addGoods(String name, Integer sid, Integer tid1, Integer tid2, Integer tid3){
        Goods goods = new Goods();
        goods.setGname(name);
        goods.setGprice(0.0f);
        goods.setGcount(0);
        goods.setSid(sid);
        goods.setTid1(tid1);
        goods.setTid2(tid2);
        goods.setTid3(tid3);
        goods.setEnable(1);
        goodsMapper.insertSelective(goods);
        Hashtable<String,Object> map = new Hashtable<>();
        map.put("gid",goods.getGid());
        return map;
    }
    public Map<String, Object> editGoods(@RequestParam Integer gid, String name, Integer sid, Float price,
                                         Integer tid1, Integer tid2, Integer tid3) {
        Goods goods = new Goods();
        goods.setGid(gid);
        goods.setGname(name);
        goods.setSid(sid);
        goods.setGprice(price);
        goods.setTid1(tid1);
        goods.setTid2(tid2);
        goods.setTid3(tid3);
        Hashtable<String,Object> map = new Hashtable<>();
        try{
            goodsMapper.updateByPrimaryKeySelective(goods);
        }
        catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            return map;
        }
        map.put("success",true);
        return map;
    }

    public Map<String, Object> editGoodsCount(Integer gid, Integer count) {
        Hashtable<String, Object> map = new Hashtable<>();
        if(count < 0) {
            map.put("success",false);
            return map;
        }
        Goods goods = new Goods();
        goods.setGid(gid);
        goods.setGcount(count);
        goodsMapper.updateByPrimaryKeySelective(goods);
        map.put("success",true);
        return map;
    }

    public Map<String, Object> addBannerPic(Integer gid, MultipartFile file){
        return addBannerPic(gid,file,0);
    }

    public Map<String, Object> addDetailPic(Integer gid, MultipartFile file){
        return addBannerPic(gid,file,1);
    }

    public Map<String, Object> addBannerPic(Integer gid, MultipartFile file, Integer pos) {
        Hashtable<String, Object> map = new Hashtable<>();
        if(!goodsMapper.existsWithPrimaryKey(gid)) {
            map.put("success",false);
            return map;
        }
        String fileName = file.getOriginalFilename();
        String suffixName = Objects.requireNonNull(fileName).substring(fileName.lastIndexOf('.'));
        fileName = UUID.randomUUID() + suffixName;
        String filePath = "";
        try{
            String path = ResourceUtils.getURL("classpath:").getPath()+"static/YouPinImg";
            filePath = path.replace('/', '\\').substring(1,path.length());
        }
        catch (Exception e) {
            e.printStackTrace();
            map.put("success",false);
            return map;
        }
        try {
            file.transferTo(new File(filePath+fileName));
        }
        catch (Exception e) {
            e.printStackTrace();
            map.put("success",false);
            return map;
        }
        Example example = new Example(Pic.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("pos",pos);
        criteria.andEqualTo("gid",gid);
        List<Pic> picList = picMapper.selectByExample(example);
        Pic pic = new Pic();
        pic.setPid(picList.size());
        pic.setDir(fileName);
        pic.setPos(pos);
        pic.setGid(gid);
        picMapper.insert(pic);
        map.put("success",true);
        return map;
    }

    public Map<String, Object> changeGoodsSellingState(Integer gid, Integer state) {
        Goods goods = new Goods();
        goods.setEnable(state);
        goods.setGid(gid);
        Map<String, Object> map = new Hashtable<>();
        if(state !=0 && state != 1) {
            map.put("success",false);
            return map;
        }
        try {
            goodsMapper.updateByPrimaryKeySelective(goods);
        }
        catch (Exception e) {
            map.put("success",false);
            return map;
        }
        map.put("success",true);
        return map;
    }

    public Map<String, Object> addGoodsType(Integer gid, String tname1, String tname2, String tname3) {
        Map<String, Object> map = new Hashtable<>();
        Goods goods = goodsMapper.selectByPrimaryKey(gid);
        if(goods == null) {
            map.put("success", false);
        }
        else{
            Boolean change = false;
            if(tname1 != null && !tname1.isEmpty()) {
                Type type1 = new Type();
                type1.setTname(tname1);
                typeMapper.insertSelective(type1);
                if(goods.getTid1() == null) {
                    type1.setTfid(type1.getTid());
                    typeMapper.updateByPrimaryKeySelective(type1);
                    List<Goods> goodsList = getAllRelativeGoods(goods);
                    for (Goods goods1 : goodsList) {
                        goods1.setTid1(type1.getTid());
                        goodsMapper.updateByPrimaryKeySelective(goods1);
                    }
                }
                else {
                    type1.setTfid(typeMapper.selectByPrimaryKey(goods.getTid1()).getTfid());
                    typeMapper.updateByPrimaryKeySelective(type1);
                }
                change = true;
            }

            if(tname2 != null && !tname2.isEmpty()) {
                Type type2 = new Type();
                type2.setTname(tname2);
                typeMapper.insertSelective(type2);
                if(goods.getTid2() == null) {
                    type2.setTfid(type2.getTid());
                    typeMapper.updateByPrimaryKeySelective(type2);
                    List<Goods> goodsList = getAllRelativeGoods(goods);
                    for (Goods goods1 : goodsList) {
                        goods1.setTid2(type2.getTid());
                        goodsMapper.updateByPrimaryKeySelective(goods1);
                    }
                }
                else {
                    type2.setTfid(typeMapper.selectByPrimaryKey(goods.getTid2()).getTfid());
                    typeMapper.updateByPrimaryKeySelective(type2);
                }
                change = true;
            }

            if(tname3 != null && !tname3.isEmpty()) {
                Type type3 = new Type();
                type3.setTname(tname3);
                typeMapper.insertSelective(type3);
                if(goods.getTid3() == null) {
                    type3.setTfid(type3.getTid());
                    typeMapper.updateByPrimaryKeySelective(type3);
                    List<Goods> goodsList = getAllRelativeGoods(goods);
                    for (Goods goods1 : goodsList) {
                        goods1.setTid3(type3.getTid());
                        goodsMapper.updateByPrimaryKeySelective(goods1);
                    }
                }
                else {
                    type3.setTfid(typeMapper.selectByPrimaryKey(goods.getTid3()).getTfid());
                    typeMapper.updateByPrimaryKeySelective(type3);
                }
                change = true;
            }
            map.put("success",change);
        }
        return map;
    }

    private List<Goods> getAllRelativeGoods(Goods goods){
        Example example = new Example(Goods.class);
        Example.Criteria criteria = example.createCriteria();
        if(goods.getTid1() == null && goods.getTid2() == null &&goods.getTid3() == null) {
            List<Goods> list = new ArrayList<>();
            list.add(goods);
            return list;
        }
        if(goods.getTid1() != null){
            Example example_type = new Example(Type.class);
            Example.Criteria criteria_type = example_type.createCriteria();
            criteria_type.andEqualTo("tfid", typeMapper.selectByPrimaryKey(goods.getTid1()).getTfid());
            List<Type> typeList = typeMapper.selectByExample(example_type);
            for (Type type : typeList) {
                criteria.orEqualTo("tid1", type.getTid());
            }
        }
        if(goods.getTid2() != null){
            Example example_type = new Example(Type.class);
            Example.Criteria criteria_type = example_type.createCriteria();
            criteria_type.andEqualTo("tfid", typeMapper.selectByPrimaryKey(goods.getTid2()).getTfid());
            List<Type> typeList = typeMapper.selectByExample(example_type);
            for (Type type : typeList) {
                criteria.orEqualTo("tid2", type.getTid());
            }
        }
        if(goods.getTid3() != null){
            Example example_type = new Example(Type.class);
            Example.Criteria criteria_type = example_type.createCriteria();
            criteria_type.andEqualTo("tfid", typeMapper.selectByPrimaryKey(goods.getTid3()).getTfid());
            List<Type> typeList = typeMapper.selectByExample(example_type);
            for (Type type : typeList) {
                criteria.orEqualTo("tid3", type.getTid());
            }
        }
        return goodsMapper.selectByExample(example);
    }


}
