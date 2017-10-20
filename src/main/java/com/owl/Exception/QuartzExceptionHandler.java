package com.owl.Exception;

import lombok.extern.slf4j.Slf4j;

import org.quartz.SchedulerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created by liuao on 2017/10/20.
 */
@ControllerAdvice
@Slf4j
public class QuartzExceptionHandler extends ResponseEntityExceptionHandler {
  @ExceptionHandler(value = SchedulerException.class)
  protected ResponseEntity<Object> handleScheduleException(SchedulerException e){
    return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_GATEWAY);
  }
}
