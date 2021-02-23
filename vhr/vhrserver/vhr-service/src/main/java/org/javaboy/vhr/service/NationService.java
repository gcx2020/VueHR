package org.javaboy.vhr.service;

import org.javaboy.vhr.mapper.NationMapper;
import org.javaboy.vhr.model.Nation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gcx_javaboy
 * @version 1.0
 * @PROJECT_NAME: vhr
 * @DATE: 2021/1/25 21:00
 */
@Service
public class NationService {
    @Autowired
    NationMapper nationMapper;
    public List<Nation> getAllNations() {
        return nationMapper.getAllNations();
    }
}
