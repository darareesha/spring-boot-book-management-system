/*
package com.example.bookmanagement.batch;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.boot.CommandLineRunner;

import java.util.List;

@Configuration
public class BatchConfig {

    @Bean
    public ListItemReader<String> reader() {
        return new ListItemReader<>(List.of("Book1", "Book2", "Book3"));
    }

    @Bean
    public ItemWriter<String> writer() {
        return items -> {
            for (String item : items) {
                System.out.println("Processed: " + item);
            }
        };
    }

    @Bean
    public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step1", jobRepository)
                .<String, String>chunk(2, transactionManager)
                .reader(reader())
                .writer(writer())
                .build();
    }

    @Bean
    public Job job(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("bookJob", jobRepository)
                .start(step(jobRepository, transactionManager))
                .build();
    }

    @Bean
    public CommandLineRunner run(JobLauncher jobLauncher, Job job) {
        return args -> {
            jobLauncher.run(job, new JobParametersBuilder().toJobParameters());
        };
    }
}

 */