package com.ucsmy.ucas.quartz.listener;

import com.ucsmy.ucas.quartz.scan.ReconiliationJob;
import com.ucsmy.ucas.quartz.util.QuartzUtils;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * 定时任务扫描Listener
 */
@WebListener
//@ConfigurationProperties(prefix = "quartz")
public class StartupListener implements ServletContextListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(StartupListener.class);

    private static final String SCHEDULER_NAME = "scheduler";

    private static final String RECONILIATION_NAME = "reconiliation";

    private static final String RECONILIATIONI_JOB_CONF = "0 0/1 * * * ?";

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            this.initial(servletContextEvent);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    /**
     * 用来启动定时器的
     */
    private void initial(ServletContextEvent servletContextEvent) throws SchedulerException {
        ServletContext context = servletContextEvent.getServletContext();
        ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(context);
        Scheduler scheduler = (Scheduler) applicationContext.getBean(SCHEDULER_NAME);

        JobDetail jobDetail = QuartzUtils.getJobDetail(ReconiliationJob.class, RECONILIATION_NAME, null);
        Trigger trigger = QuartzUtils.getTrigger(RECONILIATION_NAME, null, RECONILIATIONI_JOB_CONF);
        scheduler.scheduleJob(jobDetail, trigger);
    }

}
