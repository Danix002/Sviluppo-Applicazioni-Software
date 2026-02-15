package catering;

import catering.businesslogic.CatERing;
import catering.businesslogic.ShiftBoard.ShiftBoardException;
import catering.businesslogic.ShiftBoard.ShiftBoardInfo;
import catering.businesslogic.ShiftBoard.ShiftInfo;
import catering.businesslogic.event.EventInfo;
import catering.businesslogic.event.ServiceInfo;
import catering.persistence.PersistenceManager;
import javafx.collections.ObservableList;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class TestRemoveShift {
    public static void main(String[] args) {
        try {
            System.out.println("TEST DATABASE CONNECTION");
            PersistenceManager.testSQLConnection();
            CatERing.getInstance().getUserManager().fakeLogin("Lidia");
            System.out.println(CatERing.getInstance().getUserManager().getCurrentUser());
            System.out.println("\nTEST REMOVE SHIFT");
            ObservableList<EventInfo> events = CatERing.getInstance().getEventManager().getEventInfo();
            ObservableList<ServiceInfo> services = events.get(0).getServices();
            ServiceInfo s = services.get(0);
            ArrayList<ShiftInfo> shifts = new ArrayList<>();
            s.setBoard(new ShiftBoardInfo(shifts, s.getId()));
            Date currentDate = new Date();
            Time time = new Time(currentDate.getTime());
            CatERing.getInstance().getEventManager().addShift(s, currentDate, "Manfria", time, time, true, true);
            CatERing.getInstance().getEventManager().addShift(s, currentDate, "Licata", time, time, true, false);
            CatERing.getInstance().getEventManager().addShift(s, currentDate, "Licata", time, time, false, true);
            System.out.println("Before ... \n" + s.getShiftBoard().toString());
            CatERing.getInstance().getEventManager().removeShift(shifts.get(0),s);
            System.out.println("After ... \n" + s.getShiftBoard().toString());
        } catch (ShiftBoardException e) {
            System.out.println("Errore di logica nello use case");
        }
    }
}
