package com.leyou.common.advice;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.Lyexception;
import com.leyou.common.vo.ExceptionResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(Lyexception.class)
    public ResponseEntity<ExceptionResult> handleException(Lyexception e){
        ExceptionEnum em = e.getExceptionEnum();
        return ResponseEntity.status(em.getCode())
                .body(new ExceptionResult(e.getExceptionEnum()));
    }


}
