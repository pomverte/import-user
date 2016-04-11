package fr.pomverte.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.integration.launch.JobLaunchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Transformer;
import org.springframework.stereotype.Component;

@Component
public class HttpToJobRequest {

    @Autowired
    private Job importUserJob;

    @Transformer
    public JobLaunchRequest toRequest() {
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        return new JobLaunchRequest(this.importUserJob, jobParametersBuilder.toJobParameters());
    }

}
