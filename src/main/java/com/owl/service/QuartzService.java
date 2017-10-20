package com.owl.service;

import com.owl.api.request.RegistRequest;
import com.owl.domain.JobExecutor;
import com.owl.domain.TaskInfo;
import com.owl.utils.TriggerUtil;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.jdbcjobstore.TriggerStatus;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuartzService implements IQuartzService{
    private Scheduler scheduler;
    //系统唯一job
    private final JobKey jobKey = new JobKey("owl-job","owl-job");

    @Autowired
    public QuartzService(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public void addTask(RegistRequest request) throws SchedulerException{
        initJob();
        Trigger trigger = TriggerUtil.buildTrigger(request.getTaskInfo());
        trigger.getTriggerBuilder().forJob(jobKey);
        scheduler.scheduleJob(trigger);
    }
    @Override
    public List<TaskInfo> listTasks() throws SchedulerException {
        List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
        List<TaskInfo> infoList = triggers.stream().map(TriggerUtil::triggerInfo).collect(Collectors.toList());
        return infoList;
    }

    @Override
    public void removeTask(TriggerKey triggerKey) throws SchedulerException {
        scheduler.unscheduleJob(triggerKey);
    }

    @Override
    public void pauseTask(TriggerKey triggerKey) throws SchedulerException {
        scheduler.pauseTrigger(triggerKey);
    }

    @Override
    public void pauseTasks(List<TriggerKey> triggerKeys) throws SchedulerException {

    }

    @Override
    public void pauseAll() throws SchedulerException {
        scheduler.pauseTriggers(GroupMatcher.anyTriggerGroup());
    }

    @Override
    public void resumeTask(TriggerKey triggerKey) throws SchedulerException {
        scheduler.resumeTrigger(triggerKey);
    }

    @Override
    public void resumeTasks(List<TriggerKey> triggerKeys) throws SchedulerException {

    }

    @Override
    public void resumeAll() throws SchedulerException {
        scheduler.resumeTriggers(GroupMatcher.anyTriggerGroup());
    }

    @Override
    public TriggerStatus getTriggerState(TriggerKey triggerKey) throws SchedulerException {
        Trigger trigger = scheduler.getTrigger(triggerKey);
        TriggerStatus status = new TriggerStatus(scheduler.getTriggerState(triggerKey).toString(),trigger.getNextFireTime());
        return status;
    }

    @Override
    public void manualExecuteTask(TriggerKey triggerKey) throws SchedulerException {
        scheduler.triggerJob(jobKey,scheduler.getTrigger(triggerKey).getJobDataMap());
    }

    @Override
    public void updateTask(RegistRequest request,TriggerKey triggerKey) throws SchedulerException {
        scheduler.unscheduleJob(triggerKey);
        addTask(request);
    }


    /**
     * 初始化job,注册新任务前调用
     * @throws SchedulerException
     */
    public void initJob() throws SchedulerException {
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
