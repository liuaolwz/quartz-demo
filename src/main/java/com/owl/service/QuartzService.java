package com.owl.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.owl.api.request.RegistRequest;
import com.owl.domain.JobExecutor;
import com.owl.domain.TriggerInfo;
import com.owl.utils.TriggerUtil;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

@Service
public class QuartzService {
    private Scheduler scheduler;
    //系统唯一job
    private final JobKey jobKey = new JobKey("owl-job","owl-job");

    @Autowired
    public QuartzService(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    /**
     * 注册任务
     * @param request 注册参数
     * @throws SchedulerException
     * @throws JsonProcessingException
     */
    public void addScheduler(RegistRequest request) throws SchedulerException, JsonProcessingException {
        initob();
        Trigger trigger = TriggerUtil.buildTrigger(request.getTriggerInfo());
        trigger.getTriggerBuilder().forJob(jobKey);
        scheduler.scheduleJob(trigger);
    }

    /**
     * 获取任务列表
     * @return 任务信息列表
     * @throws SchedulerException
     */
    public List<TriggerInfo> listTask() throws SchedulerException {
        List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
        List<TriggerInfo> infoList = triggers.stream().map(TriggerUtil::triggerInfo).collect(Collectors.toList());
        return infoList;
    }

    /**
     * 初始化job,注册新任务前调用
     * @throws SchedulerException
     */
    public void initob() throws SchedulerException {
        //若调度器无job则添加job
        if (!scheduler.checkExists(jobKey)){
            JobDetail job = JobBuilder.newJob().ofType(JobExecutor.class)
                .withDescription("调度服务统一job").storeDurably(true)
                .requestRecovery(true)
                .withIdentity(jobKey.getName(),jobKey.getGroup())
                .build();
            scheduler.addJob(job,true);
        }
    }

}
