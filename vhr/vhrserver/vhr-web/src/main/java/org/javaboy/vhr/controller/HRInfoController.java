package org.javaboy.vhr.controller;

import org.javaboy.vhr.model.Hr;
import org.javaboy.vhr.model.RespBean;
import org.javaboy.vhr.service.HrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author gcx_javaboy
 * @version 1.0
 * @PROJECT_NAME: vhr
 * @DATE: 2021/2/6 16:58
 */
@RestController
public class HRInfoController {
    @Autowired
    HrService hrService;
    @GetMapping("/hr/info")
    public Hr getCurrentHr(Authentication authentication){
        return (Hr)(authentication.getPrincipal());
    }

    @PutMapping("/hr/info")
    public RespBean updateHr(@RequestBody Hr hr,Authentication authentication){
        if(hrService.updateHr(hr)==1){
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(hr,authentication.getCredentials(),authentication.getAuthorities()));//对象，密码，角色//重新构建一个authentication实例
            return RespBean.ok("更新信息成功");
        }
        return RespBean.error("更新信息失败，请重试！");
    }

    @PutMapping("/hr/pass")
    public RespBean updateHrPasswd(@RequestBody Map<String,Object> info){
        String oldPass = (String) info.get("oldPass");
        String pass = (String) info.get("pass");
        Integer hrid = (Integer) info.get("hrid");
        if(hrService.updateHrPasswd(oldPass,pass,hrid)){
            return RespBean.ok("更新密码成功");
        }
        return RespBean.error("更新密码失败，请重试！");
    }
}
