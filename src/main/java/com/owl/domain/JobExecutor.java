package com.owl.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Slf4j
@Data
public class JobExecutor implements Job{
    @Autowired
    private RestTemplate restTemplate;
    private String request;
    private String callBack;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        log.info("定时任务{}开始执行",jobDetail.getKey());
        try {
            ObjectMapper mapper = new ObjectMapper();
            HttpEntity requestEntity = mapper.readValue(this.getRequest(), HttpEntity.class);
            HttpEntity callbackEntity = mapper.readValue(this.getCallBack(), HttpEntity.class);
            ResponseEntity responseEntity = restTemplate.execute(requestEntity.getUrl(), requestEntity.getMethod(), (requestCallback) -> {
                        requestCallback.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                        if (requestCallback.getMethod() != HttpMethod.GET && requestEntity.getParam().size() != 0) {
                            mapper.writeValue(requestCallback.getBody(), requestEntity.getParam()); //设置参数
                        }
                    },
                    (response) -> new ResponseEntity(response.getStatusCode())   //返回值设置;
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
