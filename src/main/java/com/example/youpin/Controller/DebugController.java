package com.example.youpin.Controller;

import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DebugController {
    @GetMapping("/debug")
    public void debug() {

        try{
            //文件上传的地址
            String path = ResourceUtils.getURL("classpath:").getPath()+"static/static";
            String realPath = path.replace('/', '\\').substring(1,path.length());
            //用于查看路径是否正确
            System.out.println(realPath);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
