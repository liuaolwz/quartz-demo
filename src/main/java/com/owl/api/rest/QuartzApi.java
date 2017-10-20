package com.owl.api.rest;

import com.owl.api.request.RegistRequest;
import com.owl.domain.TaskInfo;
import com.owl.service.QuartzService;

import io.swagger.annotations.ApiOperation;

import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

/**
 * Created by liuao on 2017/10/19.
 */
@Slf4j
@RequestMapping(value = "/schedule/api/v1")
@RestController
public class QuartzApi {
    private QuartzService quartzService;

    @Autowired
    public QuartzApi(QuartzService quartzService) {
        this.quartzService = quartzService;
    }
    @ApiOperation(value = "注册新任务")
    @PostMapping(value = "/job")
    public ResponseEntity regist(@RequestBody final RegistRequest request) throws SchedulerException {
        quartzService.addTask(request);
        log.info("添加任务成功");
        return new ResponseEntity(HttpStatus.CREATED);
    }
    @ApiOperation(value = "定时任务列表")
    @GetMapping(value = "/job")
    public ResponseEntity<List<TaskInfo>> list() throws SchedulerException {
        return new ResponseEntity<>(quartzService.listTasks(), HttpStatus.OK);
    }
    @ApiOperation(value = "手动执行定时任务")
    @GetMapping(value = "/job/run")
    public ResponseEntity manual(@ModelAttribute TriggerKey triggerKey) throws SchedulerException {
        quartzService.manualExecuteTask(triggerKey);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "修改任务信息")
    @PutMapping(value = "/job")
    public ResponseEntity update(@RequestBody RegistRequest request,
        @RequestBody TriggerKey triggerKey) throws SchedulerException {
        quartzService.updateTask(request, triggerKey);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
