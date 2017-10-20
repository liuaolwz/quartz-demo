package com.owl.utils;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.owl.domain.TaskInfo;

import org.quartz.Trigger;
import org.springframework.beans.BeanUtils;

/**
 * Created by liuao on 2017/10/20.
 */
public class TriggerUtil {

  public static TaskInfo triggerInfo(Trigger trigger){
    TaskInfo taskInfo = new TaskInfo();
    BeanUtils.copyProperties(taskInfo,trigger);
    return taskInfo;
  }

  public static Trigger buildTrigger(TaskInfo taskInfo){
    Trigger trigger = newTrigger().withIdentity(taskInfo.getTriggerName(), taskInfo.getTriggerGroup())
        .startAt(taskInfo.getStartTime().toDate()).endAt(taskInfo.getEndTime().toDate())
        .withDescription(taskInfo.getDescription()).withPriority(taskInfo.getPriority())
        .withSchedule(simpleSchedule().withRepeatCount(10).withIntervalInSeconds(15))
        .usingJobData(taskInfo.getJobDataMap())
        .build();
    return trigger;
  }
}
