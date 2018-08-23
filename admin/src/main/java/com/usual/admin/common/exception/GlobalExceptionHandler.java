package com.usual.admin.common.exception;

import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局的的异常拦截器
 *
 * @author huyanchao
 * @since 2018-08-23
 */
@ControllerAdvice
@Order(-1)
@RestControllerAdvice
public class GlobalExceptionHandler {
    
}
