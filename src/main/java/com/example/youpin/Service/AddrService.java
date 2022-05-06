package com.example.youpin.Service;

import com.example.youpin.Mapper.AddrMapper;
import com.example.youpin.Mapper.CityMapper;
import com.example.youpin.Mapper.DistrictMapper;
import com.example.youpin.Mapper.ProvinceMapper;
import com.example.youpin.POJO.Addr;
import com.example.youpin.POJO.City;
import com.example.youpin.POJO.District;
import com.example.youpin.POJO.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

@Service
public class AddrService {
    @Autowired
    private ProvinceMapper provinceMapper;
    @Autowired
    private CityMapper cityMapper;
    @Autowired
    private DistrictMapper districtMapper;
    @Autowired
    private AddrMapper addrMapper;

    public List<Map<String, Object>> getProvince() {
        List<Province> queryList = provinceMapper.selectAll();
        List<Map<String, Object>> provinceList = new ArrayList<>();
        for (Province province : queryList) {
            Map<String, Object> provinceMap = new Hashtable<>();
            provinceMap.put("name", province.getPname());
            provinceMap.put("id", province.getPid());
            provinceList.add(provinceMap);
        }
        return provinceList;
    }

    public List<Map<String, Object>> getCityList(Integer pid) {
        Example example_city = new Example(City.class);
        Example.Criteria criteria = example_city.createCriteria();
        criteria.andEqualTo("pid", pid);
        List<City> queryList = cityMapper.selectByExample(example_city);
        List<Map<String, Object>> cityList = new ArrayList<>();
        for (City city : queryList) {
            Map<String, Object> cityMap = new Hashtable<>();
            cityMap.put("name", city.getCname());
            cityMap.put("id", city.getCid());
            cityList.add(cityMap);
        }
        return cityList;
    }

    public List<Map<String, Object>> getDistrictList(Integer cid) {
        Example example_district = new Example(District.class);
        Example.Criteria criteria = example_district.createCriteria();
        criteria.andEqualTo("cid", cid);
        List<District> queryList = districtMapper.selectByExample(example_district);
        List<Map<String, Object>> districtList = new ArrayList<>();
        for (District district : queryList) {
            Map<String, Object> cityMap = new Hashtable<>();
            cityMap.put("name", district.getDname());
            cityMap.put("id", district.getDid());
            districtList.add(cityMap);
        }
        return districtList;
    }

    public List<Map<String, Object>> getAddrList(String uid) {
        Example example = new Example(Addr.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("uid", uid);
        example.setOrderByClause("'aid' asc");
        List<Addr> addrs = addrMapper.selectByExample(example);
        List<Map<String, Object>> list = new ArrayList<>();
        for (Addr addr : addrs) {
            list.add(getAddr(addr));
        }
        return list;
    }

    public Map<String, Object> getAddr(Integer aid) {
        Addr addr = addrMapper.selectByPrimaryKey(aid);
        return getAddr(addr);
    }

    private Map<String, Object> getAddr(Addr addr) {
        Map<String, Object> map = new Hashtable<>();
        if (addr == null) {
            map.put("success", false);
            return map;
        }
        map.put("aid", addr.getAid());
        map.put("name", addr.getAname());
        map.put("tel", addr.getAtel());
        District district = districtMapper.selectByPrimaryKey(addr.getDid());
        Province province = provinceMapper.selectByPrimaryKey(district.getPid());
        City city = cityMapper.selectByPrimaryKey(district.getCid());
        map.put("pname", province.getPname());
        map.put("pid", province.getPid());
        map.put("cname", city.getCname());
        map.put("cid", city.getCid());
        map.put("dname", district.getDname());
        map.put("did", district.getDid());
        map.put("detail", addr.getAdetail());
        map.put("success", true);
        map.put("default", addr.getAdefault());
        return map;
    }

    public Map<String, Object> addAddr(String uid, Integer did, String addrDetail, String name, String tel,
                                       Integer isDefault) {
        Addr addr = new Addr();
        if(isDefault == 1) {
            Addr addr_clearDefault = new Addr();
            addr_clearDefault.setAdefault(0);
            Example example = new Example(Addr.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("uid", addr.getUid());
            addrMapper.updateByExampleSelective(addr_clearDefault,example);
        }
        addr.setAdefault(isDefault);
        addr.setAdetail(addrDetail);
        addr.setAname(name);
        addr.setAtel(tel);
        addr.setUid(uid);
        addr.setDid(did);
        Map<String, Object> map = new Hashtable<>();
        try {
            addrMapper.insertSelective(addr);
            map.put("success", true);
            return map;
        } catch (Exception e) {
            map.put("success", false);
            return map;
        }
    }

    public Map<String, Object> delAddr(Integer aid) {
        Map<String, Object> map = new Hashtable<>();
        if(addrMapper.existsWithPrimaryKey(aid)){
            addrMapper.deleteByPrimaryKey(aid);
            map.put("success", true);
        }
        else {
            map.put("success", false);
        }
        return map;
    }

    public Map<String, Object> updateAddr(Integer aid, Integer did, String addrDetail, String name, String tel,
                                          Integer isDefault) {
        Addr addr = addrMapper.selectByPrimaryKey(aid);
        Map<String, Object> map = new Hashtable<>();
        if(addr == null) {
            map.put("success", false);
            return map;
        }
        if(isDefault == 1) {
            Addr addr_clearDefault = new Addr();
            addr_clearDefault.setAdefault(0);
            Example example = new Example(Addr.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("uid", addr.getUid());
            addrMapper.updateByExampleSelective(addr_clearDefault,example);
        }
        addr.setAdetail(addrDetail);
        addr.setAname(name);
        addr.setAtel(tel);
        addr.setDid(did);
        addr.setAdefault(isDefault);
        try {
            addrMapper.updateByPrimaryKeySelective(addr);
            map.put("success", true);
            return map;
        } catch (Exception e) {
            map.put("success", false);
            return map;
        }
    }
}
