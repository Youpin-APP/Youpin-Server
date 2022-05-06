package com.example.youpin.Service;

import com.example.youpin.Mapper.PicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;

@Service
@EnableAutoConfiguration
public class PicService {
    @Autowired
    private PicMapper picMapper;

    public byte[] getPic(String url){
        String filePath = "/root/YouPinImg/";
        File file = new File(filePath);
        if(file.exists()){
            try {
                FileInputStream inputStream = new FileInputStream(filePath + url);
                byte[] bytes = new byte[inputStream.available()];
                inputStream.read(bytes, 0, inputStream.available());
                return bytes;
            }
            catch (Exception e) {
                return null;
            }
        }
        return null;
    }
}
