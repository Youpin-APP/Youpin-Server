package com.example.youpin.Service;

import com.example.youpin.Mapper.GoodsMapper;
import com.example.youpin.Mapper.PicMapper;
import com.example.youpin.POJO.Goods;
import com.example.youpin.POJO.Pic;
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
    public Map<String, Object> editGoods(@RequestParam Integer gid, @RequestParam String name,
                                         @RequestParam Integer sid, @RequestParam Float price,
                                         @RequestParam Integer tid1, @RequestParam Integer tid2,
                                         @RequestParam Integer tid3) {
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

    public Map<String, Object> addBannerPic(Integer gid, MultipartFile file) {
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
        criteria.andEqualTo("pos",0);
        criteria.andEqualTo("gid",gid);
        List<Pic> picList = picMapper.selectByExample(example);
        Pic pic = new Pic();
        pic.setPid(picList.size());
        pic.setDir(fileName);
        pic.setPos(0);
        pic.setGid(gid);
        picMapper.insert(pic);
        map.put("success",true);
        return map;
    }
}
