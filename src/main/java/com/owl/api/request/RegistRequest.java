package com.owl.api.request;

import com.owl.domain.HttpEntity;
import com.owl.domain.TriggerInfo;
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
  private TriggerInfo triggerInfo;
  private HttpEntity executeRequest;
  private HttpEntity callBack;

  public TriggerInfo getTriggerInfo() {
    JobDataMap jobDataMap = new JobDataMap();
    jobDataMap.put("request", JsonUtil.dumps(this.getExecuteRequest()));
    jobDataMap.put("callBack",JsonUtil.dumps(this.getCallBack()));
    triggerInfo.setJobDataMap(jobDataMap);
    return triggerInfo;
  }
}
