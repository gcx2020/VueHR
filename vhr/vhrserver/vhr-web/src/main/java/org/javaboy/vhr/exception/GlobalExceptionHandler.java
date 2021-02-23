package org.javaboy.vhr.exception;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.javaboy.vhr.model.RespBean;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

/**
 * @author gcx_javaboy
 * @version 1.0
 * @PROJECT_NAME: vhr
 * @DATE: 2021/1/15 22:12
 *
 * 全局异常，统一处理。
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(SQLException.class)
    public RespBean SQLException(SQLException e){
        if(e instanceof MySQLIntegrityConstraintViolationException){
            return RespBean.error("该数据有关联数据，操作失败！");
        }
        return RespBean.error("数据库异常，操作失败");
    }
}
