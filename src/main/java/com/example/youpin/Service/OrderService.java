package com.example.youpin.Service;

import com.example.youpin.Mapper.*;
import com.example.youpin.POJO.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

@Service
@EnableAutoConfiguration
public class OrderService {
    @Autowired
    private AddrMapper addrMapper;
    @Autowired
    private OrderBriefMapper orderMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private DistrictMapper districtMapper;
    @Autowired
    private CityMapper cityMapper;
    @Autowired
    private ProvinceMapper provinceMapper;
    @Autowired
    private TypeMapper typeMapper;
    @Autowired
    private PicMapper picMapper;

    public Map<String, Object> checkout(String uid) {
        Map<String, Object> map = new Hashtable<>();
        Example example = new Example(Cart.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("uid", uid);
        criteria.andEqualTo("selected", 1);
        List<Cart> cartList = cartMapper.selectByExample(example);
        if (cartList.isEmpty()) {
            map.put("success", false);
        }
        OrderBrief order = new OrderBrief();
        order.setOtime1(new Timestamp(System.currentTimeMillis()));
        System.out.println(order.getOtime1().toString());
        order.setUid(uid);
        order.setOstate(0);
        orderMapper.insertSelective(order);
        for (Cart cart : cartList) {
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setGid(cart.getGid());
            orderInfo.setOid(order.getOid());
            orderInfo.setOiprice(goodsMapper.selectByPrimaryKey(cart.getGid()).getGprice());
            orderInfo.setOicount(cart.getCacount());
            orderInfoMapper.insertSelective(orderInfo);
        }
        map.put("success", true);
        map.put("oid", order.getOid());
        return map;
    }

    public Map<String, Object> quickCheckout(String uid, Integer caid) {
        Map<String, Object> map = new Hashtable<>();
        Cart cart = cartMapper.selectByPrimaryKey(caid);
        if (cart != null) {
            map.put("success", false);
            return map;
        }
        OrderBrief order = new OrderBrief();
        order.setOtime1(new Timestamp(System.currentTimeMillis()));
        System.out.println(order.getOtime1().toString());
        order.setUid(uid);
        orderMapper.insertSelective(order);
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setGid(cart.getGid());
        orderInfo.setOid(order.getOid());
        orderInfo.setOiprice(goodsMapper.selectByPrimaryKey(cart.getGid()).getGprice());
        orderInfo.setOicount(cart.getCacount());
        orderInfoMapper.insertSelective(orderInfo);
        map.put("success", true);
        map.put("oid", order.getOid());
        return map;
    }

    public Map<String, Object> editOrder(Integer oid, Integer aid){
        OrderBrief orderBrief = orderMapper.selectByPrimaryKey(oid);
        Map<String, Object> map = new Hashtable<>();
        Addr addr = addrMapper.selectByPrimaryKey(aid);
        if(addr == null || orderBrief == null || orderBrief.getOstate() != 0) {
            map.put("success", false);
            return map;
        }
        District district = districtMapper.selectByPrimaryKey(addr.getDid());
        String provinceName = provinceMapper.selectByPrimaryKey(district.getPid()).getPname();
        String cityName = cityMapper.selectByPrimaryKey(district.getCid()).getCname();
        String districtName = district.getDname();
        orderBrief.setAddr(provinceName + cityName + districtName + addr.getAdetail());
        orderBrief.setOname(addr.getAname());
        orderBrief.setAid(aid);
        orderMapper.updateByPrimaryKeySelective(orderBrief);
        map.put("success", true);
        return map;
    }

    public Map<String, Object> getOrderDetail(Integer oid) {
        Map<String, Object> map = new Hashtable<>();
        Map<String, Object> deliver = new Hashtable<>();
        Map<String, Object> basic = new Hashtable<>();
        List<Map<String, Object>> itemList = new ArrayList<>();
        OrderBrief orderBrief = orderMapper.selectByPrimaryKey(oid);
        if(orderBrief == null){
            map.put("success", false);
            return map;
        }
        if(orderBrief.getOname() != null) {
            deliver.put("name",orderBrief.getOname());
        }
        if(orderBrief.getAddr() != null) {
            deliver.put("addr",orderBrief.getAddr());
        }
        if(orderBrief.getOtel() != null) {
            deliver.put("otel",orderBrief.getOtel());
        }
        if(orderBrief.getAid() != null) {
            deliver.put("aid", orderBrief.getAid());
        }
        map.put("deliver", deliver);
        basic.put("oid",orderBrief.getOid());
        if(orderBrief.getOtime1() != null){
            basic.put("otime1", orderBrief.getOtime1().toString());
        }
        if(orderBrief.getOtime2() != null){
            basic.put("otime2", orderBrief.getOtime2().toString());
        }
        if(orderBrief.getOtime3() != null){
            basic.put("otime3", orderBrief.getOtime3().toString());
        }
        basic.put("state",orderBrief.getOstate());
        switch (orderBrief.getOstate()) {
            case 0:
                basic.put("stateName", "待支付");
                break;
            case 1:
                basic.put("stateName", "待收货");
                break;
            case 2:
                basic.put("stateName", "已取消");
                break;
            case 3:
                basic.put("stateName", "完成");
        }
        map.put("basic", basic);
        Example example = new Example(OrderInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("oid",orderBrief.getOid());
        example.setOrderByClause("'oiid' asc");
        List<OrderInfo> orderInfos = orderInfoMapper.selectByExample(example);
        Float totalPrice = 0f;
        List<Map<String, Object>> infos = new ArrayList<>();
        for (OrderInfo orderInfo : orderInfos) {
            Map<String, Object> item = new Hashtable<>();
            item.put("gid", orderInfo.getGid());
            item.put("count", orderInfo.getOicount());
            item.put("price", orderInfo.getOiprice());
            Goods goods = goodsMapper.selectByPrimaryKey(orderInfo.getGid());
            String type = "";
            if(goods.getTid1() != null){
                Type type1 = typeMapper.selectByPrimaryKey(goods.getTid1());
                type += type1.getTname();
            }
            if(goods.getTid2() != null){
                Type type1 = typeMapper.selectByPrimaryKey(goods.getTid2());
                if(!type.isEmpty()){
                    type += " ";
                }
                type += type1.getTname();
            }
            if(goods.getTid3() != null){
                Type type1 = typeMapper.selectByPrimaryKey(goods.getTid3());
                if(!type.isEmpty()){
                    type += " ";
                }
                type += type1.getTname();
            }
            item.put("name", goods.getGname());
            item.put("type", type);
            totalPrice += orderInfo.getOiprice() * orderInfo.getOicount();
            infos.add(item);
            Example example_pic = new Example(Pic.class);
            Example.Criteria criteria_pic = example_pic.createCriteria();
            criteria_pic.andEqualTo("gid", orderInfo.getGid());
            criteria_pic.andEqualTo("pos",0);
            criteria_pic.andEqualTo("pid", 0);
            List<Pic> picQ = picMapper.selectByExample(example_pic);
            if(!picQ.isEmpty()) {
                item.put("pic",picQ.get(0).getDir());
            }
        }
        map.put("infos",infos);
        map.put("totalPrice", String.format("%.2f", totalPrice));
        map.put("success",true);
        return map;
    }

    public Map<String, Object> cancelOrder(Integer oid) {
        Map<String, Object> map = new Hashtable<>();
        OrderBrief orderBrief = orderMapper.selectByPrimaryKey(oid);
        if(orderBrief == null || orderBrief.getOstate() != 0) {
            map.put("success", false);
            return map;
        }
        orderBrief.setOstate(2);
        orderBrief.setOtime2(new Timestamp(System.currentTimeMillis()));
        orderMapper.updateByPrimaryKeySelective(orderBrief);
        map.put("success", true);
        return map;
    }

    public Map<String, Object> payOrder(Integer oid) {
        Map<String, Object> map = new Hashtable<>();
        OrderBrief orderBrief = orderMapper.selectByPrimaryKey(oid);
        if(orderBrief == null || orderBrief.getOstate() != 0) {
            map.put("success", false);
            return map;
        }
        orderBrief.setOstate(1);
        orderBrief.setOtime2(new Timestamp(System.currentTimeMillis()));
        orderMapper.updateByPrimaryKeySelective(orderBrief);
        map.put("success", true);
        return map;
    }

    public Map<String, Object> finishOrder(Integer oid) {
        Map<String, Object> map = new Hashtable<>();
        OrderBrief orderBrief = orderMapper.selectByPrimaryKey(oid);
        if(orderBrief == null || orderBrief.getOstate() != 1) {
            map.put("success", false);
            return map;
        }
        orderBrief.setOstate(3);
        orderBrief.setOtime3(new Timestamp(System.currentTimeMillis()));
        orderMapper.updateByPrimaryKeySelective(orderBrief);
        map.put("success", true);
        return map;
    }

    public List<Map<String, Object>> getOrderList(String uid) {
        Example example_brief = new Example(OrderBrief.class);
        Example.Criteria criteria_brief = example_brief.createCriteria();
        criteria_brief.andEqualTo("uid",uid);
        example_brief.setOrderByClause("oid DESC");
        List<OrderBrief> orderBriefs = orderMapper.selectByExample(example_brief);
        List<Map<String,Object>> list = new ArrayList<>();
        for (OrderBrief orderBrief : orderBriefs) {
            Map<String, Object> briefMap = new Hashtable<>();
            Example example_info = new Example(OrderInfo.class);
            Example.Criteria criteria_info = example_info.createCriteria();
            criteria_info.andEqualTo("oid",orderBrief.getOid());
            example_info.setOrderByClause("oiid DESC");
            List<OrderInfo> orderInfos = orderInfoMapper.selectByExample(example_info);
            switch (orderBrief.getOstate()) {
                case 0:
                    briefMap.put("stateName", "待支付");
                    break;
                case 1:
                    briefMap.put("stateName", "待收货");
                    break;
                case 2:
                    briefMap.put("stateName", "已取消");
                    break;
                case 3:
                    briefMap.put("stateName", "完成");
            }
            Float total = 0f;
            List<String> pics = new ArrayList<>();
            if(orderInfos.size() == 1) {
                Goods goods = goodsMapper.selectByPrimaryKey(orderInfos.get(0).getGid());
                briefMap.put("name",goods.getGname()) ;
            }
            else {
                briefMap.put("name","");
            }
            for (OrderInfo orderInfo : orderInfos) {
                Example example_pic = new Example(Pic.class);
                Example.Criteria criteria_pic = example_pic.createCriteria();
                criteria_pic.andEqualTo("gid", orderInfo.getGid());
                criteria_pic.andEqualTo("pos",0);
                criteria_pic.andEqualTo("pid", 0);
                List<Pic> picQ = picMapper.selectByExample(example_pic);
                if(!picQ.isEmpty()) {
                    pics.add(picQ.get(0).getDir());
                }
                total += orderInfo.getOiprice() * orderInfo.getOicount();
            }
            briefMap.put("totalPrice", String.format("%.2f", total));
            briefMap.put("pics", pics);
            list.add(briefMap);
        }
        return list;
    }

}
