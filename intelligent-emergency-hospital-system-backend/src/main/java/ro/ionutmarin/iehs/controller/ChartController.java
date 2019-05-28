package ro.ionutmarin.iehs.controller;

import io.jsonwebtoken.lang.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.ionutmarin.iehs.dao.AlertDao;
import ro.ionutmarin.iehs.dao.AppointmentDao;
import ro.ionutmarin.iehs.dao.DoctorDao;
import ro.ionutmarin.iehs.entity.AppointmentEntity;
import ro.ionutmarin.iehs.entity.DoctorEntity;
import ro.ionutmarin.iehs.response.AppointmentCalendarResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;


@RestController
@RequestMapping(value = "/chart")
public class ChartController {

    @Autowired
    private DoctorDao doctorDao;

    @Autowired
    private AppointmentDao appointmentDao;

    @Autowired
    private AlertDao alertDao;

    @RequestMapping(value = "/doctorBySpecialization")
    public Map<String, Long> getNoOfDoctorsGroupBySpecialization() {
        Map<String, Long> doctorBySpecialization = doctorDao.findAll()
                .stream()
                .collect(groupingBy(DoctorEntity::getSpecialization, counting()));
        System.out.println(doctorBySpecialization);

        return doctorBySpecialization;
    }

    @RequestMapping(value = "/appointmentCalendar")
    public List<AppointmentCalendarResponse> getAppointmentCalendar() {
        Map<Date, Long> groupByDate= appointmentDao.findAll()
                .stream()
                .collect(groupingBy(a -> new Date(a.getTimestamp().getTime()), counting()));

        List<AppointmentCalendarResponse> appCalendar = appointmentDao.findAll()
                .stream()
                .map( appEntity -> {
                    AppointmentCalendarResponse app = new AppointmentCalendarResponse();
                    app.setDate(new Date(appEntity.getTimestamp().getTime()));
                    app.setTotal(groupByDate.get(app.getDate()));
                    app.setDetails(new ArrayList<>());
                    return app;
                })
                .collect(Collectors.toList());

        System.out.println(appCalendar);

        return appCalendar;
    }

    @RequestMapping(value = "/appointmentCalendar2")
    public Map<String, Long> getAppointmentCalendar2() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Long> doctorBySpecialization = appointmentDao.findAll()
                .stream()
                .collect(groupingBy(app -> sdf.format(new Date(app.getTimestamp().getTime())), counting()));

        System.out.println(doctorBySpecialization);
        return doctorBySpecialization;
    }

    @RequestMapping(value = "/alertsByRoom")
    public Map<String, Long> getAlertsByRoom() {
        Map<String, Long> alertsByRoom = alertDao.findAll()
                .stream()
                .collect(groupingBy(alert -> "Camera " + alert.getRoomNumber(), counting()));

        System.out.println(alertsByRoom);
        return alertsByRoom;
    }
}
