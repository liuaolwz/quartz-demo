package com.owl.api.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.owl.domain.HttpEntity;
import com.owl.domain.JobExecutor;
import com.owl.domain.JobInfo;
import com.owl.domain.TriggerInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Trigger;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.*;
import static org.quartz.CronScheduleBuilder.*;
import static org.quartz.CalendarIntervalScheduleBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.DateBuilder.*;

/**
 * Created by liuao on 2017/10/19.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistRequest {
  private JobInfo jobInfo;
  private TriggerInfo triggerInfo;
  private HttpEntity executeRequest;
  private HttpEntity callBack;

  public JobDetail getJobDetail() {
    JobDetail job = newJob(JobExecutor.class).withIdentity(jobInfo.getJobName(),jobInfo.getJobGroup())
            .withDescription(jobInfo.getDescription())
            .build();
    return job;
  }

  public Trigger getTrigger() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    JobDataMap jobDataMap = new JobDataMap();
    jobDataMap.put("callBack",mapper.writeValueAsString(this.getCallBack()));
    jobDataMap.put("request",mapper.writeValueAsString(this.getExecuteRequest()));
    Trigger trigger = newTrigger().withIdentity(triggerInfo.getTriggerName(),triggerInfo.getTriggerGroup())
            .forJob(getJobDetail())
            .startAt(triggerInfo.getStartTime().toDate()).endAt(triggerInfo.getEndTime().toDate())
            .withDescription(triggerInfo.getDescription()).withPriority(triggerInfo.getPriority())
            .withSchedule(simpleSchedule().withRepeatCount(10).withIntervalInSeconds(15))
            .usingJobData(jobDataMap)
            .build();
    return trigger;
  }
}
