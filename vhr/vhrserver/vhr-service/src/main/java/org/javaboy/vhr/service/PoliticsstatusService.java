package org.javaboy.vhr.service;

import org.javaboy.vhr.mapper.PoliticsstatusMapper;
import org.javaboy.vhr.model.Politicsstatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gcx_javaboy
 * @version 1.0
 * @PROJECT_NAME: vhr
 * @DATE: 2021/1/25 21:02
 */
@Service
public class PoliticsstatusService {

    @Autowired
    PoliticsstatusMapper politicsstatusMapper;

    public List<Politicsstatus> getAllPoliticstatus() {
        return politicsstatusMapper.getAllPoliticstatus();
    }
}
