package ro.ionutmarin.iehs.controller;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.ionutmarin.iehs.dao.AlertDao;
import ro.ionutmarin.iehs.dao.RoomDao;
import ro.ionutmarin.iehs.entity.AlertEntity;
import ro.ionutmarin.iehs.entity.RoomEntity;
import ro.ionutmarin.iehs.model.Emergency;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class EmergencyMapController {

    @Autowired
    private AlertDao alertDao;

    @Autowired
    private RoomDao roomDao;


    @RequestMapping("/emergencies")
    public List<Emergency> getEmergencies() {
       List<RoomEntity> rooms = roomDao.findAll();
       List<AlertEntity> alerts = alertDao.findAll();
       List<Emergency> emergencies = new ArrayList<>();

        rooms.stream()
                .forEach(room -> {
                    Emergency emergency = new Emergency();
                    emergency.setRoomEntity(room);

                    List<AlertEntity> alertsByRoomNo = alerts.stream()
                            .filter(a -> a.getRoomNumber() == room.getNumber())
                            .collect(Collectors.toList());

                    emergency.setAlertEntityList(alertsByRoomNo);

                    emergencies.add(emergency);
                });

       return emergencies;
    }

    private long getDate(String startDate) {
        if (startDate == "NaN") {
            System.out.println("start date is NaN");
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return startDate.equals("NaN") ?  cal.getTime().getTime() : Long.parseLong(startDate);
    }

    @RequestMapping("/emergency")
    public List<Emergency> getEmergenciesByTimestamp(@RequestParam("startDate") String startDate) {
        long startDateInMillis = getDate(startDate);

        List<RoomEntity> rooms = roomDao.findAll();
        List<AlertEntity> alerts = alertDao.findAll();
        List<Emergency> emergencies = new ArrayList<>();

        rooms.stream()
                .forEach(room -> {
                    Emergency emergency = new Emergency();
                    emergency.setRoomEntity(room);

                    List<AlertEntity> alertsByRoomNo = alerts.stream()
                            .filter(a -> a.getRoomNumber() == room.getNumber())
                            .filter(a -> a.getTimestamp().getTime() > startDateInMillis)
                            .collect(Collectors.toList());

                    emergency.setAlertEntityList(alertsByRoomNo);

                    emergencies.add(emergency);
                });

        return emergencies;
    }
}
