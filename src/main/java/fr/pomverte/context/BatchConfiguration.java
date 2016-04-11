package fr.pomverte.context;

import java.io.File;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.batch.item.support.PassThroughItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import fr.pomverte.batch.UserFieldSetMapper;
import fr.pomverte.domain.User;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    /** All the column in the CSV file. */
    @Bean
    public LineTokenizer delimitedLineTokenizer() {
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        String[] names = { "gender", "title", "first", "last", "street", "city", "state", "zip", "email", "username",
                "password", "salt", "md5", "sha1", "sha256", "registered", "dob", "phone", "cell", "TFN", "large",
                "medium", "thumbnail", "version", "nationality" };
        lineTokenizer.setNames(names);
        return lineTokenizer;
    }

    @Bean
    public LineMapper<User> lineMapper(UserFieldSetMapper userFieldSetMapper,
            DelimitedLineTokenizer delimitedLineTokenizer) {
        DefaultLineMapper<User> defaultLineMapper = new DefaultLineMapper<>();
        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
        defaultLineMapper.setFieldSetMapper(userFieldSetMapper);
        // defaultLineMapper.setFieldSetMapper(new BeanWrapperFieldSetMapper<User>() {{setTargetType(User.class);}});
        return defaultLineMapper;
    }

    @Bean
    @StepScope
    public FlatFileItemReader<User> userCsvItemReader(LineMapper<User> lineMapper) {
        FlatFileItemReader<User> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new ClassPathResource("csv/users-100.csv"));
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper);
        return itemReader;
    }

    @Bean
    @StepScope
    public FlatFileItemWriter<User> userCsvFlatFileItemWriter() {
        FlatFileItemWriter<User> itemWriter = new FlatFileItemWriter<>();
        itemWriter.setResource(new FileSystemResource(new File("target/users-output.csv")));
        DelimitedLineAggregator<User> lineAggregator = new DelimitedLineAggregator<User>();
        lineAggregator.setFieldExtractor(new BeanWrapperFieldExtractor<User>() {
            {
                setNames(new String[] { "gender", "title", "first", "last", "email" });
            }
        });
        itemWriter.setLineAggregator(lineAggregator);
        return itemWriter;
    }

    @Bean
    public Step importUserStep(ItemReader<User> userCsvItemReader, ItemWriter<User> userCsvFlatFileItemWriter) {
        return this.stepBuilderFactory.get("importUserStep").<User, User> chunk(10).reader(userCsvItemReader)
                .processor(new PassThroughItemProcessor<User>()).writer(userCsvFlatFileItemWriter).build();
    }

    @Bean
    public Job importUserJob(Step importUserStep) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .flow(importUserStep).end()
                .build();
    }

}
