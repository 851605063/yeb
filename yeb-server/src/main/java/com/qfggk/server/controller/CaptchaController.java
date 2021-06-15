package com.qfggk.server.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author: WJQ
 * @date 2021-06-15 14:58
 */
@RestController
@Slf4j
public class CaptchaController {

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @ApiOperation(value = "验证码")
    @GetMapping(value = "/captcha",produces = "image/jpeg")//produces = "image/jpeg"：使swagger2接口文档可以看到验证码图片
    public void captcha(HttpServletRequest request, HttpServletResponse response) {
        //定义response输出类型为image/jpeg
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        //------------------------生成验证码BEGIN----------------------
        //获取验证码
        String text=defaultKaptcha.createText();
        log.info("验证码内容：{}",text);
        //将文本内容放入session
        request.getSession().setAttribute("captcha",text);
        //根据验证码文本生成图片
        BufferedImage bufferedImage=defaultKaptcha.createImage(text);
        ServletOutputStream outputStream=null;
        try {
            //获取返回输出流
            outputStream=response.getOutputStream();
            //输出流输出为图片，格式为JPG
            ImageIO.write(bufferedImage,"jpg",outputStream);
            //输出
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(outputStream!=null) {
                try {
                    //输出流关闭
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //------------------------生成验证码end------------------------
    }
}
