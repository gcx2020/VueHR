package org.javaboy.vhr.service;

import org.javaboy.vhr.mapper.EmployeeMapper;
import org.javaboy.vhr.model.Employee;
import org.javaboy.vhr.model.MailConstants;
import org.javaboy.vhr.model.MailSendLog;
import org.javaboy.vhr.model.RespPageBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author gcx_javaboy
 * @version 1.0
 * @PROJECT_NAME: vhr
 * @DATE: 2021/1/18 22:32
 */
@Service
public class EmployeeService {
    @Autowired
    EmployeeMapper employeeMapper;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    MailSendLogService mailSendLogService;

    public final static Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
    DecimalFormat decimalFormat = new DecimalFormat("##.00");//保留两位有效数字


    public RespPageBean getEmployeeByPage(Integer page, Integer size, Employee employee, Date[] beginDataScope) {
        if (page != null && size != null) {
            page = (page - 1) * size;
        }
        List<Employee> data = employeeMapper.getEmployeeByPage(page, size, employee, beginDataScope);
        Long total = employeeMapper.getTotal(employee, beginDataScope);
        RespPageBean bean = new RespPageBean();
        bean.setData(data);
        bean.setTotal(total);
        return bean;
    }

    public Integer addEmp(Employee employee) {//添加时候没有id
        Date beginContract = employee.getBeginContract();
        Date endContract = employee.getEndContract();
        double gapMouth = (Double.parseDouble(yearFormat.format(endContract))
                - Double.parseDouble(yearFormat.format(beginContract))) * 12;
        double totalMouth = (Double.parseDouble(monthFormat.format(endContract)) - Double.parseDouble(monthFormat.format(beginContract))) + gapMouth;
        employee.setContractTerm(Double.parseDouble(decimalFormat.format(totalMouth / 12)));
        int result = employeeMapper.insertSelective(employee);
        if (result == 1) {
            Employee emp = employeeMapper.getEmployeeById(employee.getId());//添加完全之后才有id
//            logger.info(emp.toString());
            //生成消息的Id
            String msgId = UUID.randomUUID().toString();
            MailSendLog mailSendLog = new MailSendLog();
            mailSendLog.setMsgId(msgId);
            mailSendLog.setCreateTime(new Date());
            mailSendLog.setExchange(MailConstants.MAIL_EXCHANGE_NAME);
            mailSendLog.setRouteKey(MailConstants.MAIL_ROUTING_KEY_NAME);
            mailSendLog.setEmpId(emp.getId());
            mailSendLog.setCount(0);
            mailSendLog.setTryTime(new Date(System.currentTimeMillis() + 1000 * 60 * MailConstants.MSG_TIMEOUT));
            mailSendLogService.insert(mailSendLog);
            rabbitTemplate.convertAndSend(MailConstants.MAIL_EXCHANGE_NAME, MailConstants.MAIL_ROUTING_KEY_NAME, emp, new CorrelationData(msgId));
            rabbitTemplate.convertAndSend(MailConstants.MAIL_EXCHANGE_NAME, MailConstants.MAIL_ROUTING_KEY_NAME, emp, new CorrelationData(msgId));
        }
        return result;
    }

    public Integer maxWorkID() {
        return employeeMapper.maxWorkID();
    }

    public Integer deleteEmpByEid(Integer id) {
        return employeeMapper.deleteByPrimaryKey(id);
    }

    public Integer updateEmp(Employee employee) {
        Date beginContract = employee.getBeginContract();
        Date endContract = employee.getEndContract();
        double gapMouth = (Double.parseDouble(yearFormat.format(endContract))
                - Double.parseDouble(yearFormat.format(beginContract))) * 12;
        double totalMouth = (Double.parseDouble(monthFormat.format(endContract)) - Double.parseDouble(monthFormat.format(beginContract))) + gapMouth;
        employee.setContractTerm(Double.parseDouble(decimalFormat.format(totalMouth / 12)));
        return employeeMapper.updateByPrimaryKeySelective(employee);
    }

    public Integer addEmps(List<Employee> list) {
        return employeeMapper.addEmps(list);
    }

    public RespPageBean getEmployeeByPageWithSalary(Integer page, Integer size) {
        if (page != null && size != null) {
            page = (page - 1) * size;
        }
        List<Employee> list = employeeMapper.getEmployeeByPageWithSalary(page, size);
        RespPageBean respPageBean = new RespPageBean();
        respPageBean.setTotal(employeeMapper.getTotal(null, null));
        respPageBean.setData(list);
        return respPageBean;
    }

    public Integer updateEmployeeSalaryById(Integer eid, Integer sid) {
        return employeeMapper.updateEmployeeSalaryById(eid, sid);
    }

    public Employee getEmployeeById(Integer empId) {
        return employeeMapper.getEmployeeById(empId);
    }
}
