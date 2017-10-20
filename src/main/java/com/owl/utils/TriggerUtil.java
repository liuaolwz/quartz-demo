package com.owl.utils;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.owl.domain.TriggerInfo;

import org.quartz.Trigger;
import org.springframework.beans.BeanUtils;

/**
 * Created by liuao on 2017/10/20.
 */
public class TriggerUtil {

  public static TriggerInfo triggerInfo(Trigger trigger){
    TriggerInfo triggerInfo = new TriggerInfo();
    BeanUtils.copyProperties(triggerInfo,trigger);
    return triggerInfo;
  }

  public static Trigger buildTrigger(TriggerInfo triggerInfo) throws JsonProcessingException {
    Trigger trigger = newTrigger().withIdentity(triggerInfo.getTriggerName(),triggerInfo.getTriggerGroup())
        .startAt(triggerInfo.getStartTime().toDate()).endAt(triggerInfo.getEndTime().toDate())
        .withDescription(triggerInfo.getDescription()).withPriority(triggerInfo.getPriority())
        .withSchedule(simpleSchedule().withRepeatCount(10).withIntervalInSeconds(15))
        .usingJobData(triggerInfo.getJobDataMap())
        .build();
    return trigger;
  }
}
