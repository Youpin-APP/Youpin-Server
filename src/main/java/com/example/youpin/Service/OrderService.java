package com.example.youpin.Service;

import com.example.youpin.Mapper.AddrMapper;
import com.example.youpin.Mapper.GoodsMapper;
import com.example.youpin.Mapper.OrderMapper;
import com.example.youpin.Mapper.TypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;

@Service
@EnableAutoConfiguration
public class OrderService {
    @Autowired
    private AddrMapper addrMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private TypeMapper typeMapper;
}
