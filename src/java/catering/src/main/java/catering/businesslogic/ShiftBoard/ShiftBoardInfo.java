package catering.businesslogic.ShiftBoard;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class ShiftBoardInfo {
    private ArrayList<ShiftInfo> shifts;
    private int serv_id;

    public ShiftBoardInfo(ArrayList<ShiftInfo> shifts, int id){ this.shifts = shifts; this.serv_id = id;}
    public ShiftInfo createShift(Date date, String place, Time start, Time end, boolean preparationShift, boolean serviceShift) {
        ShiftInfo shift = new ShiftInfo(date, place, start, end, preparationShift, serviceShift);
        addShift(shift);
        return shift;
    }

    public void setId(int id) {
        this.serv_id = id;
    }
    public int getId() {
        return this.serv_id;
    }
    public void removeShift(ShiftInfo shift) {
        this.shifts.remove(shift);
    }
    private void addShift(ShiftInfo shift) {
        this.shifts.add(shift);
    }

    public String toString() {
        String stringa = "Tabellone turni per il servizio: " + this.serv_id + "\n";
        for (ShiftInfo s: this.shifts) {
            stringa += s.toString();
        }
        return stringa + this.shifts.size() + " turni presenti";
    }
}
