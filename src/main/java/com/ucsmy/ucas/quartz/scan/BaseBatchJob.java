package com.ucsmy.ucas.quartz.scan;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/** 
 * @ClassName: BaseBatchJob 
 * @Description: 
 * @author masiming
 * @date 2017-9-13
 * @version V1.0 
 */
public abstract class BaseBatchJob implements org.quartz.Job {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseBatchJob.class);
	
	@Autowired
	private JobLauncher jobLauncher;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			this.jobLauncher.run(this.getJob(), this.getJobParameters());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new JobExecutionException(e.getMessage());
		}
	}

	/**
	 *
	 * @Title: getJobName
	 * @Description: 获取任务名称
	 * @return String    返回类型
	 * @author masiming
	 * @throws
	 * @date 2017-9-13
	 * @version V1.0
	 */
	public String getJobName() {
		return this.getClass().getName();
	}

	/**
	* @Title: getJob 
	* @Description: 
	* @return Job    返回类型
	* @author masiming
	* @throws
	* @date 2017-9-13
	* @version V1.0   
	*/
	protected abstract Job getJob() throws Exception;

	/** 
	* @Title: getJobParameters 
	* @Description: 
	* @return JobParameters    返回类型
	* @author masiming
	* @throws
	* @date 2017-9-13
	* @version V1.0   
	*/
	protected JobParameters getJobParameters() {
		Map<String, JobParameter> map = new HashMap<>();
		map.put("jobname", new JobParameter(this.getJobName()));
		map.put("timestamp", new JobParameter(System.currentTimeMillis()));
		map.put("thread", new JobParameter(Thread.currentThread().getName()));
		return new JobParameters(map);
	}

}
