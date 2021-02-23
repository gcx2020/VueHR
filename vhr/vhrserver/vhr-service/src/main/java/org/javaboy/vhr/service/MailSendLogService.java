package org.javaboy.vhr.service;


import org.javaboy.vhr.mapper.MailSendLogMapper;
import org.javaboy.vhr.model.MailSendLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

/**
 * @author gcx_javaboy
 * @version 1.0
 * @PROJECT_NAME: vhr
 * @DATE: 2021/2/5 21:17
 */

@Service
public class MailSendLogService {
    @Autowired
    MailSendLogMapper mailSendLogMapper;
    public Integer updateMailSendLogStatus(String msgId, Integer status) {
        return mailSendLogMapper.updateMailSendLogStatus(msgId, status);
    }

    public Integer insert(MailSendLog mailSendLog) {
        return mailSendLogMapper.insert(mailSendLog);
    }

    public List<MailSendLog> getMailSendLogsByStatus() {
        return mailSendLogMapper.getMailSendLogsByStatus();
    }

    public Integer updateCount(String msgId, Date date) {
        return mailSendLogMapper.updateCount(msgId,date);
    }
}
