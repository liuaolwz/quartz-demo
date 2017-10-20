package com.owl.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.quartz.CronExpression;
import org.quartz.JobDataMap;

/**
 * Created by liuao on 2017/10/19.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TriggerInfo {
    private String scheduleName;
    private String triggerName;
    private String triggerGroup;
    private String jobName;
    private String jobGroup;
    private String description;
    private String nextFireTime;
    private String prevFireTime;
    private Integer priority;
    private String triggerState;
    private String triggerType;
    private DateTime startTime;
    private DateTime endTime;
    private String calenderName;
    private String misFireInstr;
    private String cronExpression;
    private JobDataMap jobDataMap;
}
