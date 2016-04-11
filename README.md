# Import User
An integration flow accessible through a HTTP REST call that will launch a batch.

That batch will parse a CSV file and write configured column into an other file.

## Run
With the wrapper or directly with de task :

    gradlew bootRun
    gradle bootRun

With Eclipse Buildship plugin :
- Open the `Gradle Tasks` View
- Double click on the `bootRun` task

## Trigger Job

    http://localhost:8080/import-user

## Result

    o.s.b.c.c.a.DefaultBatchConfigurer       : No datasource was provided...using a Map based JobRepository
    o.s.b.c.l.support.SimpleJobLauncher      : No TaskExecutor has been set, defaulting to synchronous executor.
    o.s.b.c.l.support.SimpleJobLauncher      : Job: [FlowJob: [name=importUserJob]] launched with the following parameters: [{}]
    o.s.batch.core.job.SimpleStepHandler     : Executing step: [importUserStep]
    o.s.b.c.l.support.SimpleJobLauncher      : Job: [FlowJob: [name=importUserJob]] completed with the following parameters: [{}] and the following status: [COMPLETED]
    o.s.integration.handler.LoggingHandler   : JobExecution: id=0, version=2, startTime=Mon Apr 11 13:29:38 CEST 2016, endTime=Mon Apr 11 13:29:38 CEST 2016, lastUpdated=Mon Apr 11 13:29:38 CEST 2016, status=COMPLETED, exitStatus=exitCode=COMPLETED;exitDescription=, job=[JobInstance: id=0, version=0, Job=[importUserJob]], jobParameters=[{}]
