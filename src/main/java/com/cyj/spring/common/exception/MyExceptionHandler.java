package com.cyj.spring.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理器
 * Created by cyj on 2018-07-25
 *
 * @author cyj
 */
@ControllerAdvice
public class MyExceptionHandler {
    private Logger logger = LoggerFactory.getLogger("MyExceptionHandler");

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Map<String, Object> errorHandler(Exception ex) {
        Map<String, Object> map = new HashMap<>();
        // 根据不同错误获取错误信息
        if (ex instanceof IException) {
            map.put("code", ((IException) ex).getCode());
            map.put("msg", ex.getMessage());
        }/* else if (ex instanceof UnauthorizedException) {
            map.put("code", 403);
            map.put("msg", "没有访问权限");
        }*/ else {
            String message = ex.getMessage();
            map.put("code", 500);
            map.put("msg", message == null || message.trim().isEmpty() ? "未知错误" : message);
            map.put("details", message);
            logger.error(ex.getMessage(), ex);
            ex.printStackTrace();
        }
        return map;
    }

}
