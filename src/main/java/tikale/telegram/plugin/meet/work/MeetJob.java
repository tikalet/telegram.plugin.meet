package tikale.telegram.plugin.meet.work;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import tikale.telegram.plugin.meet.checkday.CheckDayManager;
import tikale.telegram.plugin.meet.checkday.CheckDayType;

@Component
public class MeetJob {

    private static final Logger LOG = LoggerFactory.getLogger(MeetJob.class);

    @Autowired
    private CheckDayManager checkDayManager;

    @Scheduled(cron = "${cron.expression}")
    public void execute() {
        try {
            CheckDayType checkDayType = checkDayManager.checkDay(new Date());
            processCheckDay(checkDayType);
        } catch (Exception e) {
            LOG.error("Timer unable to work", e);
        }
    }

    private void processCheckDay(CheckDayType checkDayType) {
        if (checkDayType == CheckDayType.WORK) {

        } else {
            LOG.warn("checkDayType for " + new Date() + " is " + checkDayType);
        }
    }
}
