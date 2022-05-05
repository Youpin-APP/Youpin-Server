package com.example.youpin.Service;

import com.example.youpin.Mapper.CartMapper;
import com.example.youpin.Mapper.GoodsMapper;
import com.example.youpin.Mapper.PicMapper;
import com.example.youpin.Mapper.TypeMapper;
import com.example.youpin.POJO.Cart;
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
public class CartService {
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private PicMapper picMapper;
    @Autowired
    private TypeMapper typeMapper;

    public List<Map<String, Object>> getCartList(Integer uid) {
        List<Map<String, Object>> list = new ArrayList<>();
        Example example_cart = new Example(Cart.class);
        Example.Criteria criteria_cart = example_cart.createCriteria();
        criteria_cart.andEqualTo("uid", uid);
        example_cart.setOrderByClause("caid DESC");
        List<Cart> cartQueryList = cartMapper.selectByExample(example_cart);
        for (Cart cart : cartQueryList) {
            Goods goods = goodsMapper.selectByPrimaryKey(cart.getGid());
            Example example_pic = new Example(Pic.class);
            Example.Criteria criteria_pic = example_pic.createCriteria();
            criteria_pic.andEqualTo("gid",cart.getGid());
            criteria_pic.andEqualTo("pos",0);
            criteria_pic.andEqualTo("pid", 0);
            List<Pic> pics = picMapper.selectByExample(example_pic);
            Map<String, Object> map = new Hashtable<>();
            map.put("caid", cart.getCaid());
            map.put("count", cart.getCacount());
            map.put("name", goods.getGname());
            if(pics.isEmpty()){
                map.put("pic","");
            }
            else {
                map.put("pic", pics.get(0).getDir());
            }
            String type = "";
            if(goods.getTid1() != null) {
                type += typeMapper.selectByPrimaryKey(goods.getTid1()).getTname() + " ";
            }
            if(goods.getTid2() != null) {
                type += typeMapper.selectByPrimaryKey(goods.getTid2()).getTname() + " ";
            }
            if(goods.getTid3() != null) {
                type += typeMapper.selectByPrimaryKey(goods.getTid3()).getTname() + " ";
            }
            map.put("type", type);
        }
        return list;
    }
}
