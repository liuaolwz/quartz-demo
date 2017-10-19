package com.owl.api.rest;

import com.owl.api.request.RegistRequest;
import com.owl.service.QuartzService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by liuao on 2017/10/19.
 */
@Slf4j
@RestController(value = "/schedule/api/v1")
public class QuartzApi {
    private QuartzService quartzService;

    @Autowired
    public QuartzApi(QuartzService quartzService) {
        this.quartzService = quartzService;
    }

    @PostMapping(value = "/job")
    public ResponseEntity regist(@RequestBody final RegistRequest registRequest){
        try {
            quartzService.addScheduler(registRequest);
            log.info("添加任务成功");
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            log.info("添加任务失败");
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
