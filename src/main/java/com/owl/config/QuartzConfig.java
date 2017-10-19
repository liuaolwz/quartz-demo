package com.owl.config;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

/**
 * Created by liuao on 2017/10/19.
 */

@Slf4j
@Configuration
public class QuartzConfig {

  @PostConstruct
  public void init() {
    log.debug("QuartzConfig initialized.");
  }

  @Bean
  public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource, PlatformTransactionManager transactionManager,
      AutowiringSpringBeanJobFactory autowiringSpringBeanJobFactory) {
    SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();

    schedulerFactoryBean.setDataSource(dataSource);
    schedulerFactoryBean.setTransactionManager(transactionManager);
    schedulerFactoryBean.setOverwriteExistingJobs(true);
    schedulerFactoryBean.setWaitForJobsToCompleteOnShutdown(false);
    schedulerFactoryBean.setSchedulerName("etongdai-quartz");
    schedulerFactoryBean.setAutoStartup(true);
    schedulerFactoryBean.setJobFactory(autowiringSpringBeanJobFactory);

    schedulerFactoryBean.setQuartzProperties(quartzProperties());

    return schedulerFactoryBean;
  }

  private Properties quartzProperties() {
    PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
    propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
    try {
      propertiesFactoryBean.afterPropertiesSet();
      return propertiesFactoryBean.getObject();
    } catch (IOException e) {
      log.error("read quartz.properties file error: {}", e.getMessage());
    }
    return null;
  }
  @Bean
  RestTemplate restTemplate(){
    return new RestTemplate();
  }
}
