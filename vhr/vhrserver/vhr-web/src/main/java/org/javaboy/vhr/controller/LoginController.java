package org.javaboy.vhr.controller;

import org.javaboy.vhr.config.VerificationCode;
import org.javaboy.vhr.model.RespBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author gcx_javaboy
 * @version 1.0
 * @PROJECT_NAME: vhr
 * @DATE: 2021/1/10 20:16
 */
@RestController
public class LoginController {
    @GetMapping("/login")
    public RespBean login(){
        RespBean error = RespBean.error("尚未登录，请登录后再试。");
        return error;
    }
    @GetMapping("/verifyCode")
    public void verifyCode(HttpSession session, HttpServletResponse resp) throws IOException {
        VerificationCode code = new VerificationCode();
        BufferedImage image = code.getImage();
        String text = code.getText();
        session.setAttribute("verify_code",text);
        VerificationCode.output(image,resp.getOutputStream());
    }
}
