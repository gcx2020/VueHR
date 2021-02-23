package org.javaboy.vhr.controller;

import org.javaboy.vhr.model.ChatMsg;
import org.javaboy.vhr.model.Hr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.util.Date;

/**
 * @author gcx_javaboy
 * @version 1.0
 * @PROJECT_NAME: vhr
 * @DATE: 2021/2/2 21:00
 */
@Controller
public class WsController {
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/ws/chat")
    public void handleMsg(Authentication authentication, ChatMsg chatMsg) {
        //Principal获取当前的用户
        //authentication所有信息
        Hr hr = (Hr) authentication.getPrincipal();
        chatMsg.setFrom(hr.getUsername());//消息从哪来，已经解决来的问题
        chatMsg.setDate(new Date());
        chatMsg.setFromNickName(hr.getName());
        simpMessagingTemplate.convertAndSendToUser(chatMsg.getTo(), "/queue/chat", chatMsg);
    }
}
