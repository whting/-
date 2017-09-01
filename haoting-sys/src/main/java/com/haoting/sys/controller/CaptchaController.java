package com.haoting.sys.controller;

import com.haoting.mvc.captcha.CaptchaHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: haoting.wang
 * @Date: Created in 下午3:19 2017/8/22
 */
@Controller
@RequestMapping("/captcha")
public class CaptchaController {


    @RequestMapping(method = RequestMethod.GET)
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setDateHeader("Expires", 0L);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        CaptchaHelper.setInCache(request, response);
    }
}
