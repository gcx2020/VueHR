package org.javaboy.vhr.config;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author gcx_javaboy
 * @version 1.0
 * @PROJECT_NAME: vhr
 * @DATE: 2021/1/14 20:08
 */
@Component
public class CustomUrlDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object object,
                       Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {
        for (ConfigAttribute configAttribute : configAttributes) {
            String needRole = configAttribute.getAttribute();
            if(needRole.equals("ROLE_LOGIN")){
                if (authentication instanceof AnonymousAuthenticationToken){
                    //说明是匿名用户的实例就抛异常
                    throw new AccessDeniedException("尚未登录，请登录");
                }else {
                    return;
                }
            }
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();//拿到登录用户的角色
            for (GrantedAuthority authority : authorities) {
                if(authority.getAuthority().equals(needRole)){
                    return;
                }
            }
        }
        throw new AccessDeniedException("权限不足，请联系管理员!");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
