package com.ucsmy.ucas.quartz.scan;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

public class ReconiliationJob extends BaseBatchJob {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    protected Tasklet tasklet() {
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution,
                                        ChunkContext context) {
                return RepeatStatus.FINISHED;
            }
        };
    }

    protected Step step1() throws Exception {
        return this.stepBuilderFactory.get("step1").tasklet(tasklet()).build();
    }

    @Override
    protected Job getJob() throws Exception {
        return this.jobBuilderFactory.get("job").start(step1()).build();
    }
}
