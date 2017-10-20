package com.owl.service;

import com.owl.api.request.RegistRequest;
import com.owl.domain.TaskInfo;

import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.jdbcjobstore.TriggerStatus;

import java.util.List;

/**
 * quartz service interface
 * Created by liuao on 2017/10/20.
 */
public interface IQuartzService {
  /**
   * add a new task
   * @param request task info
   * @throws SchedulerException
   */
  void addTask(RegistRequest request) throws SchedulerException;

  /**
   * remove a given task
   * @param triggerKey given task key
   * @throws SchedulerException
   */
  void removeTask(TriggerKey triggerKey) throws SchedulerException;

  /**
   * pause a given task
   * @param triggerKey given task key
   * @throws SchedulerException
   */
  void pauseTask(TriggerKey triggerKey) throws SchedulerException;

  /**
   * pause all given tasks
   * @param triggerKeys
   * @throws SchedulerException
   */
  void pauseTasks(List<TriggerKey> triggerKeys) throws SchedulerException;

  /**
   * pause all tasks
   * @throws SchedulerException
   */
  void pauseAll() throws SchedulerException;

  /**
   * resume a given task
   * @param triggerKey
   * @throws SchedulerException
   */
  void resumeTask(TriggerKey triggerKey) throws SchedulerException;

  /**
   * resume given tasks
   * @param triggerKeys
   * @throws SchedulerException
   */
  void resumeTasks(List<TriggerKey> triggerKeys) throws SchedulerException;

  /**
   * resume all tasks
   * @throws SchedulerException
   */
  void resumeAll() throws SchedulerException;

  /**
   * get task status
   * @param triggerKey
   * @return task execute status
   * @throws SchedulerException
   */
  TriggerStatus getTriggerState(TriggerKey triggerKey) throws SchedulerException;

  /**
   * git all task info
   * @throws SchedulerException
   */
  List<TaskInfo> listTasks() throws SchedulerException;

  /**
   * manual execute a given task
   * @param triggerKey
   * @throws SchedulerException
   */
  void manualExecuteTask(TriggerKey triggerKey) throws SchedulerException;
  void updateTask(RegistRequest request,TriggerKey triggerKey) throws SchedulerException;
}
