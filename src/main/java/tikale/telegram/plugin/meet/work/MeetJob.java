package tikale.telegram.plugin.meet.work;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import tikale.telegram.plugin.meet.bot.BotManager;
import tikale.telegram.plugin.meet.checkday.CheckDayManager;
import tikale.telegram.plugin.meet.checkday.CheckDayType;
import tikale.telegram.plugin.meet.entity.MessageResponseDto;
import tikale.telegram.plugin.meet.entity.SendType;
import tikale.telegram.plugin.meet.util.FileUtil;
import tikale.telegram.plugin.meet.util.StringUtil;

@Component
public class MeetJob {

    private static final Logger LOG = LoggerFactory.getLogger(MeetJob.class);

    @Autowired
    private CheckDayManager checkDayManager;

    @Autowired
    private BotManager botManager;

    @Autowired
    private FileUtil fileUtil;

    @Value("${bot.chat}")
    private String chatName;

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
        //  if (checkDayType == CheckDayType.WORK) {
        if (chatName == null || chatName.isEmpty()) {
            LOG.error("chatName is null. Unable to work");
            return;
        }

        MessageResponseDto messageResponseDto = new MessageResponseDto();
        messageResponseDto.setChatName(chatName);
        messageResponseDto.setType(SendType.MARKDOWN);

        try {
            messageResponseDto.setMessage(fileUtil.load());
            botManager.send(messageResponseDto);
        } catch (Exception e) {
            LOG.error(StringUtil.ERROR_TEXT, e);
        }

        //        } else {
        //            LOG.warn("checkDayType for " + new Date() + " is " + checkDayType);
        //        }
    }
}
