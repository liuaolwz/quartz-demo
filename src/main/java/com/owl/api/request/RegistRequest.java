package com.owl.api.request;

import com.owl.domain.HttpEntity;
import com.owl.domain.TaskInfo;
import com.owl.utils.JsonUtil;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.quartz.JobDataMap;

/**
 * 注册新调度器
 * Created by liuao on 2017/10/19.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistRequest {
  private TaskInfo taskInfo;
  private HttpEntity executeRequest;
  private HttpEntity callBack;

  public TaskInfo getTaskInfo() {
    JobDataMap jobDataMap = new JobDataMap();
    jobDataMap.put("request", JsonUtil.dumps(this.getExecuteRequest()));
    jobDataMap.put("callBack",JsonUtil.dumps(this.getCallBack()));
    taskInfo.setJobDataMap(jobDataMap);
    return taskInfo;
  }
}
