package catering.businesslogic.ShiftBoard;

import java.sql.Time;
import java.util.Date;

import catering.businesslogic.user.User;

public class ShiftInfo {;
    private Date date;
    private String place;
    private Time start;
    private Time end;
    private boolean preparationShift;
    private boolean serviceShift;
    public ShiftInfo(Date date, String place, Time start, Time end, boolean preparationShift, boolean serviceShift) {
        this.date = date;
        this.place = place;
        this.start = start;
        this.end = end;
        this.preparationShift = preparationShift;
        this.serviceShift = serviceShift;
    }

    public String toString() {
        String s = "Data: " + this.date + ", luogo: " + this.place + ", inizio: " + this.start + ", fine: " + this.end;
        if(this.preparationShift){
            s += ", prep";
        }
        if(this.serviceShift){
            s += ", serv";
        }
        return s + ".\n";
    }
}
