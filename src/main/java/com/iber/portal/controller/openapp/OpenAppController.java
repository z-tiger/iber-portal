package com.iber.portal.controller.openapp;

import com.iber.portal.advices.SystemServiceLog;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zengfeiyue
 */
@Controller
public class OpenAppController {


    @SystemServiceLog(description="打开app")
    @RequestMapping(value = "/open", method = { RequestMethod.GET })
    public String open(String sharePhone,String acceptUserPhone,HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=utf-8");

        return "openapp/openApp";
    }
}
