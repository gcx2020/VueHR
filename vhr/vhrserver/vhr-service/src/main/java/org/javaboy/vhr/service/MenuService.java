package org.javaboy.vhr.service;

import org.javaboy.vhr.mapper.MenuMapper;
import org.javaboy.vhr.mapper.MenuRoleMapper;
import org.javaboy.vhr.model.Hr;
import org.javaboy.vhr.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author gcx_javaboy
 * @version 1.0
 * @PROJECT_NAME: vhr
 * @DATE: 2021/1/12 21:17
 */
@Service
public class MenuService {
    @Autowired
    MenuMapper menuMapper;
    @Autowired
    MenuRoleMapper menuRoleMapper;

    public List<Menu> getMenusByHrId() {
        return menuMapper.getMenusByHrId(((Hr)(SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getId());
        //登录信息里面的hr的对应id
    }

//    @Cacheable//需要在这里配置radis
    public List<Menu> getAllMenusWithRole(){
        return menuMapper.getAllMenusWithRole();
    }

    public List<Menu> getAllMenus() {
        return menuMapper.getAllMenus();
    }

    public List<Integer> getMidsByRid(Integer rid) {
        return menuMapper.getMidsByRid(rid);
    }

    @Transactional//事务的注解
    public boolean updateMenuRole(Integer rid, Integer[] mids) {
        menuRoleMapper.deleteByRid(rid);
        if(mids == null||mids.length == 0){//如果为空的话 就不允许再次添加
            return true;
        }
        Integer result = menuRoleMapper.insertRecord(rid,mids);//添加有一个相应的操作
        return result==mids.length;
    }
}
