package com.ucsmy.ucas.quartz.util;

import org.quartz.*;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by ucs_masiming on 2017/9/13.
 */
public class QuartzUtils {

    /**
     * 获取 trigger
     * @param triggerName
     * @param group 为null则使用默认的
     * @param conf
     * @return
     */
    public static Trigger getTrigger(String triggerName, String group, String conf) {
        String schGroup = group;
        if(group == null){
            schGroup = Scheduler.DEFAULT_GROUP;
        }
        CronTrigger trigger = newTrigger()
                .withIdentity(triggerName, schGroup)
                .withSchedule(CronScheduleBuilder.cronSchedule(conf))
                .startNow()
                .build();
        return trigger;
    }

    /**
     * 获取 jobDetail
     * @param clazz
     * @param jobName
     * @param group 为null则使用默认的
     * @return
     */
    public static JobDetail getJobDetail(Class<? extends Job> clazz, String jobName, String group) {
        String schGroup = group;
        if(group == null){
            schGroup = Scheduler.DEFAULT_GROUP;
        }
        JobDetail jobDetail = newJob(clazz)
                .withIdentity(jobName, schGroup)
                .build();
        return jobDetail;
    }

}
