package com.example.youpin.Service;

import cn.hutool.core.io.FileUtil;
import com.example.youpin.Mapper.PicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

@Service
@EnableAutoConfiguration
public class PicService {
    @Autowired
    private PicMapper picMapper;

    public void getPic(String url, HttpServletResponse response){
        OutputStream os = null;
        response.setContentType("image/jpeg");
        try {
            os = response.getOutputStream();
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }

        String filePath = "/root/YouPinImg/";
        File file = new File(filePath);
        int len = 0;
        if(file.exists()){
            InputStream inputStream = FileUtil.getInputStream(filePath + url);
            try {
                byte[] buffer = new byte[4096];
                while ((len = inputStream.read(buffer)) != -1)
                {
                    os.write(buffer, 0, len);
                }
                os.flush();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
