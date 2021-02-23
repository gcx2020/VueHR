package org.javaboy.vhr.service;

import org.javaboy.vhr.mapper.SalaryMapper;
import org.javaboy.vhr.model.Salary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author gcx_javaboy
 * @version 1.0
 * @PROJECT_NAME: vhr
 * @DATE: 2021/1/31 14:38
 */
@Service
public class SalaryService {
    @Autowired
    SalaryMapper salaryMapper;

    public List<Salary> getAllSalaries() {
        return salaryMapper.getAllSalaries();
    }

    public Integer addSalary(Salary salary) {
        salary.setCreateDate(new Date());
        return salaryMapper.insert(salary);
    }

    public Integer deleteSalaryById(Integer id) {
        return salaryMapper.deleteByPrimaryKey(id);
    }

    public Integer updateSalaryById(Salary salary) {
        return salaryMapper.updateByPrimaryKeySelective(salary);
    }
}
