package org.javaboy.vhr.service;


import org.javaboy.vhr.mapper.HrMapper;
import org.javaboy.vhr.mapper.HrRoleMapper;
import org.javaboy.vhr.model.Hr;
import org.javaboy.vhr.utils.HrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author gcx_javaboy
 * @version 1.0
 * @PROJECT_NAME: vhr
 * @DATE: 2021/1/10 15:23
 */

@Service
public class HrService implements UserDetailsService {
    @Autowired
    HrMapper hrMapper;
    @Autowired
    HrRoleMapper hrRoleMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Hr hr = hrMapper.loadUserByUsername(username);
        if(hr==null){
            throw new UsernameNotFoundException("用户名不存在！");
        }
        hr.setRoles(hrMapper.getHrRolesById(hr.getId()));//设置角色
        return hr;
    }

    public List<Hr> getAllHrs(String keywords) {

        return hrMapper.getAllHrs(HrUtils.getCurrentHr().getId(),keywords);//除了自己的信息
    }

    public Integer updateHr(Hr hr) {
        return hrMapper.updateByPrimaryKeySelective(hr);
    }

    @Transactional//确保两个都同时成功或同时失败
    public boolean updateHrRole(Integer hrid, Integer[] rids) {
        hrRoleMapper.deleteByHrId(hrid);
        return hrRoleMapper.addRole(hrid,rids)==rids.length;
    }

    public Integer deleteHrById(Integer id) {
        return hrMapper.deleteByPrimaryKey(id);
    }

    public List<Hr> getAllHrsExceptCurrentHr() {
        return hrMapper.getAllHrsExceptCurrentHr(HrUtils.getCurrentHr().getId());
    }

    public Boolean updateHrPasswd(String oldPass, String pass, Integer hrid) {
        Hr hr = hrMapper.selectByPrimaryKey(hrid);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(encoder.matches(oldPass,hr.getPassword())){
            //第一个参数是明文密码，第二个参数是密文密码
            String encode = encoder.encode(pass);//新密码进行加密
            Integer result = hrMapper.updatePasswd(hrid,encode);
            if(result==1){
                return true;
            }
        }
        return false;
    }
}