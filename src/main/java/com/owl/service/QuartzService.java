package com.owl.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.owl.api.request.RegistRequest;
import com.owl.domain.JobExecutor;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuartzService {
    private Scheduler scheduler;

    @Autowired
    public QuartzService(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void addScheduler(RegistRequest request) throws SchedulerException, JsonProcessingException {
        scheduler.scheduleJob(request.getTrigger());
    }
    public List<RegistRequest> listTask(){
        return null;
    }

}
