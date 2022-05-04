package com.example.youpin.Service;

import com.example.youpin.Mapper.CityMapper;
import com.example.youpin.Mapper.DistrictMapper;
import com.example.youpin.Mapper.ProvinceMapper;
import com.example.youpin.POJO.City;
import com.example.youpin.POJO.District;
import com.example.youpin.POJO.Province;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Map<String,Object>> getProvince() {
        List<Province> queryList = provinceMapper.selectAll();
        List<Map<String,Object>> provinceList = new ArrayList<>();
        for (Province province : queryList) {
            Map<String, Object> provinceMap = new Hashtable<>();
            provinceMap.put("name",province.getPname());
            provinceMap.put("id",province.getPid());
            provinceList.add(provinceMap);
        }
        return provinceList;
    }
    public List<Map<String, Object>> getCityList(Integer pid) {
        Example example_city = new Example(City.class);
        Example.Criteria criteria = example_city.createCriteria();
        criteria.andEqualTo("pid",pid);
        List<City> queryList = cityMapper.selectByExample(example_city);
        List<Map<String,Object>> cityList = new ArrayList<>();
        for (City city : queryList) {
            Map<String, Object> cityMap = new Hashtable<>();
            cityMap.put("name",city.getCname());
            cityMap.put("id",city.getCid());
            cityList.add(cityMap);
        }
        return cityList;
    }
    public List<Map<String, Object>> getDistrictList(Integer cid) {
        Example example_district = new Example(District.class);
        Example.Criteria criteria = example_district.createCriteria();
        criteria.andEqualTo("cid", cid);
        List<District> queryList = districtMapper.selectByExample(example_district);
        List<Map<String,Object>> districtList = new ArrayList<>();
        for (District district : queryList) {
            Map<String, Object> cityMap = new Hashtable<>();
            cityMap.put("name",district.getDname());
            cityMap.put("id",district.getDid());
            districtList.add(cityMap);
        }
        return districtList;
    }
}
