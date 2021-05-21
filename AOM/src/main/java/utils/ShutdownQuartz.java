package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author fadingt
 * @version 1.0.0
 * @date 12/24/2020 5:25 PM
 */
public class ShutdownQuartz implements ServletContextListener {
    private static Logger logger = LogManager.getLogger(ShutdownQuartz.class);
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            // TODO: 4/1/2021 找到所有创建的Scheduler
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.shutdown(true);
        } catch (SchedulerException e) {
            logger.error("SchedulerException:关闭Quartz失败");
        }
    }
}
