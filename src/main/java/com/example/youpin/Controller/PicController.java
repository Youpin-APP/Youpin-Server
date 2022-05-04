package com.example.youpin.Controller;

import com.example.youpin.Service.PicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
public class PicController {
    @Autowired
    private PicService picService;
    @RequestMapping(value = "/pic/{url}", method = RequestMethod.GET)
    @ResponseBody
    public void getPic(@PathVariable String url, HttpServletResponse response) {
        try{
            picService.getPic(url,response);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
