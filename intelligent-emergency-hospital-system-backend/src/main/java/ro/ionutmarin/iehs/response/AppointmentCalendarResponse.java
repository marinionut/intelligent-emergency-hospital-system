package ro.ionutmarin.iehs.response;

import java.util.Date;
import java.util.List;

public class AppointmentCalendarResponse {
    public class Details {
        String name;
        String date;
        long value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public long getValue() {
            return value;
        }

        public void setValue(long value) {
            this.value = value;
        }
    }

    private Date date;
    private long total;
    private List<Details> details;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<Details> getDetails() {
        return details;
    }

    public void setDetails(List<Details> details) {
        this.details = details;
    }


    /*
            [
                {
                "date": "2019-01-01",
                "total": 17164,
                "details": [{
                    "name": "Project 1",
                    "date": "2019-01-01 12:30:45",
                    "value": 9192
                    }]
                 }
              ]
    */
}
