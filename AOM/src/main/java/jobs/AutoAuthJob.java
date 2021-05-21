package jobs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import utils.MailUtils;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

/**
 * @author fadingt
 * @version 1.0.0
 * @date 12/25/2020 5:29 PM
 */
public class AutoAuthJob implements Job {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // TODO: 12/25/2020
        LOGGER.trace("授权");
        LOGGER.trace("发邮件");

        MailUtils mailUtils = new MailUtils();
        mailUtils.init();
        mailUtils.sendEmail("测试", "", "liuxingyu@agree.com.cn");
    }

    public static void main(String[] args) {
        JobDetail jobDetail = JobBuilder.newJob(AutoAuthJob.class).withDescription("每日授权邮件").build();
        Trigger trigger = TriggerBuilder.newTrigger().withSchedule(simpleSchedule().withRepeatCount(1).withIntervalInMinutes(3)).startAt(DateBuilder.futureDate(5, DateBuilder.IntervalUnit.MINUTE)).build();
        SchedulerFactory factory = new StdSchedulerFactory();
        try {
            Scheduler scheduler = factory.getScheduler();
            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();
        } catch (SchedulerException e) {
        }
    }
}