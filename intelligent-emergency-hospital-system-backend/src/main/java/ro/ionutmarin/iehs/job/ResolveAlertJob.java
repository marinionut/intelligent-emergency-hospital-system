package ro.ionutmarin.iehs.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ro.ionutmarin.iehs.dao.AlertDao;
import ro.ionutmarin.iehs.entity.AlertEntity;
import ro.ionutmarin.iehs.model.Alert;
import ro.ionutmarin.iehs.sms.SmsService;
import ro.ionutmarin.iehs.util.Constants;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResolveAlertJob {

    @Autowired
    AlertDao alertDao;

    @Autowired
    SmsService smsService;


    @Scheduled(fixedDelay = 1200000, initialDelay = 1000)
    public void scheduleFixedRateWithInitialDelayTask() {

        System.out.println("starting resolve unprocessed alerts job");
        String onCallNumber = "0762815077";

        List<AlertEntity> alertEntities = alertDao
                .findAll()
                .stream()
                .filter(a -> a.getStatus() == Constants.ALERT_INITIALIZED)
                .collect(Collectors.toList());

        alertEntities.forEach(a -> {
            smsService.sendSms(onCallNumber, a.getMessage());
            a.setStatus(Constants.ALERT_CONFIRMED);
            alertDao.save(a);
        });

        System.out.println("completed resolve unprocessed alerts job");

    }
}
