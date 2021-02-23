package org.javaboy.vhr.controller;

/**
 * @author gcx_javaboy
 * @version 1.0
 * @PROJECT_NAME: vhr
 * @DATE: 2021/2/2 22:23
 */

import org.javaboy.vhr.model.Hr;
import org.javaboy.vhr.service.HrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author gcx_javaboy
 * @version 1.0
 * @PROJECT_NAME: vhr
 * @DATE: 2021/2/2 20:10
 */
@RestController
@RequestMapping("/chat")
public class  ChatController {
    @Autowired
    HrService hrService;
    @GetMapping("/hrs")
    public List<Hr> getAllHrsExceptCurrentHr(){
        return hrService.getAllHrsExceptCurrentHr();
    }
}