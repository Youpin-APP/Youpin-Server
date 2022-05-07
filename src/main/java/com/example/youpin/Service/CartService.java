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

    public List<Map<String, Object>> getCartList(String uid) {
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
            criteria_pic.andEqualTo("gid", cart.getGid());
            criteria_pic.andEqualTo("pos", 0);
            criteria_pic.andEqualTo("pid", 0);
            List<Pic> pics = picMapper.selectByExample(example_pic);
            Map<String, Object> map = new Hashtable<>();
            map.put("caid", cart.getCaid());
            map.put("count", cart.getCacount());
            map.put("name", goods.getGname());
            if (pics.isEmpty()) {
                map.put("pic", "");
            } else {
                map.put("pic", pics.get(0).getDir());
            }
            String type = "";
            if (goods.getTid1() != null) {
                type += typeMapper.selectByPrimaryKey(goods.getTid1()).getTname() + " ";
            }
            if (goods.getTid2() != null) {
                type += typeMapper.selectByPrimaryKey(goods.getTid2()).getTname() + " ";
            }
            if (goods.getTid3() != null) {
                type += typeMapper.selectByPrimaryKey(goods.getTid3()).getTname() + " ";
            }
            map.put("type", type);
            list.add(map);
        }
        return list;
    }

    public Map<String, Object> cartItemAddOne(Integer caid) {
        Cart cart = cartMapper.selectByPrimaryKey(caid);
        cart.setCacount(cart.getCacount() + 1);
        cartMapper.updateByPrimaryKeySelective(cart);
        Map<String, Object> map = new Hashtable<>();
        map.put("success", true);
        return map;
    }

    public Map<String, Object> cartItemMinusOne(Integer caid) {
        Cart cart = cartMapper.selectByPrimaryKey(caid);
        Map<String, Object> map = new Hashtable<>();
        if (cart.getCacount() > 1) {
            cart.setCacount(cart.getCacount() - 1);
            cartMapper.updateByPrimaryKeySelective(cart);
            map.put("success", true);
        } else {
            map.put("success", false);
        }
        return map;

    }

    public Map<String, Object> cartPutItem(Integer gid, String uid) {
        Map<String, Object> map = new Hashtable<>();
        Example example = new Example(Cart.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("gid", gid);
        criteria.andEqualTo("uid", uid);
        List<Cart> cartList = cartMapper.selectByExample(example);
        Cart cart = null;
        if (cartList.isEmpty()) {
            cart = new Cart();
            cart.setCacount(1);
            cart.setGid(gid);
            cart.setUid(uid);
            cart.setSelected(1);
            cartMapper.insertSelective(cart);
        } else {
            cart = cartList.get(0);
            cart.setCacount(cart.getCacount() + 1);
            cartMapper.updateByPrimaryKeySelective(cart);
        }
        map.put("success", true);
        return map;
    }

    public Map<String, Object> cartItemDelete(List<Integer> caids) {
        Map<String, Object> map = new Hashtable<>();
        for (Integer caid : caids) {

            if(!cartMapper.existsWithPrimaryKey(caid)){
                map.put("success", false);
                return map;
            }
        }
        for (Integer caid : caids) {
            cartMapper.deleteByPrimaryKey(caid);
        }
        map.put("success", true);
        return map;
    }
}
