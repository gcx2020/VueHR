package org.javaboy.vhr.service;

import org.javaboy.vhr.mapper.MenuRoleMapper;
import org.javaboy.vhr.mapper.RoleMapper;
import org.javaboy.vhr.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gcx_javaboy
 * @version 1.0
 * @PROJECT_NAME: vhr
 * @DATE: 2021/1/16 21:05
 */
@Service
public class RoleService {
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    MenuRoleMapper menuRoleMapper;
    public List<Role> getAllRoles() {
        return roleMapper.getAllRoles();
    }

    public Integer addRole(Role role) {
        if(role.getName().startsWith("ROLE_")){
            role.setName("ROLE_"+role.getName());
        }
        return roleMapper.insert(role);
        //在springSecurity中 ROLE 不能少。
    }

    public Integer deleteRoleById(Integer rid) {
//        menuRoleMapper.deleteByRid(rid);//先把关系删了 再删除数据
        return roleMapper.deleteByPrimaryKey(rid);
    }
}
