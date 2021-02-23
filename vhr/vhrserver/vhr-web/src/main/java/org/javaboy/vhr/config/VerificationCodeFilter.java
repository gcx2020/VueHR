package org.javaboy.vhr.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.javaboy.vhr.model.RespBean;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author gcx_javaboy
 * @version 1.0
 * @PROJECT_NAME: vhr
 * @DATE: 2021/2/6 10:35
 */
@Component
public class VerificationCodeFilter extends GenericFilter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        if("POST".equals(req.getMethod())&&"/doLogin".equals(req.getServletPath())){
            //登录请求
            String code = req.getParameter("code");//从登录页面提取参数
            String verify_code = (String) req.getSession().getAttribute("verify_code");
            if(code==null||"".equals(code)||!verify_code.toLowerCase().equals(code.toLowerCase())){
                //验证码不正确
                resp.setContentType("application/json;charset=utf-8");
                PrintWriter out = resp.getWriter();
                out.write(new ObjectMapper().writeValueAsString(RespBean.error("验证码填写错误")));
                out.flush();
                out.close();
                return;
            }else{
                chain.doFilter(req,resp);//没有问题过滤器继续往下走；
            }

        }else{
            chain.doFilter(req,resp);//没有验证，就继续过滤
        }
    }
}
