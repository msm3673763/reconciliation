package com.ucsmy.ucas.config;

import com.ucsmy.ucas.quartz.util.ScheduleJobFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.Properties;

/**
 * quartz配置
 * Created by chenqilin on 2017/4/19.
 */
@Configuration
public class QuartzConfig {

    @Bean
    public ScheduleJobFactory getScheduleJobFactory() {
        return new ScheduleJobFactory();
    }

    @Bean(name = "scheduler")
    public SchedulerFactoryBean getSchedulerFactoryBean() {
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        // 注入自定义工厂类
        factoryBean.setJobFactory(getScheduleJobFactory());
        factoryBean.setQuartzProperties(quartzProperties());
        return factoryBean;
    }

    public Properties quartzProperties() {
        Properties properties = new Properties();
        // 默认的jobFactory
        properties.setProperty("org.quartz.scheduler.jobFactory.class", "org.quartz.simpl.SimpleJobFactory");
        // 默认的线程池
        properties.setProperty("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        // 目前线程数量
        properties.setProperty("org.quartz.threadPool.threadCount", "5");
        return properties;
    }

}
