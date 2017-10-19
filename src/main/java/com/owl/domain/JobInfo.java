package com.owl.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.quartz.JobDataMap;

/**
 * Created by liuao on 2017/10/19.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobInfo {
  private String scheduleName;
  private String jobName;
  private String jobGroup;
  private String description;
  private String jobClassName;
  private Boolean isDurable;
  private Boolean isNonConcurrent;
  private Boolean isUpdateData;
  private JobDataMap jobDataMap;
}
