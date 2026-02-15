package catering;

import catering.businesslogic.CatERing;
import catering.businesslogic.ShiftBoard.ShiftBoardException;
import catering.businesslogic.ShiftBoard.ShiftBoardInfo;
import catering.businesslogic.ShiftBoard.ShiftException;
import catering.businesslogic.ShiftBoard.ShiftInfo;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.EventException;
import catering.businesslogic.event.EventInfo;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.menu.MenuException;
import catering.businesslogic.task.SummarySheet;
import catering.businesslogic.task.SummarySheetException;
import catering.businesslogic.task.TaskInfo;
import catering.businesslogic.user.User;
import catering.businesslogic.user.UserException;
import catering.persistence.PersistenceManager;
import javafx.collections.ObservableList;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class TestAddInfo {
    public static void main(String[] args) {
        try {
            System.out.println("TEST DATABASE CONNECTION");
            PersistenceManager.testSQLConnection();
            CatERing.getInstance().getUserManager().fakeLogin("Lidia");
            System.out.println(CatERing.getInstance().getUserManager().getCurrentUser());
            System.out.println("\nTEST ADD INFO\n");
            ObservableList<EventInfo> events = CatERing.getInstance().getEventManager().getEventInfo();
            ObservableList<ServiceInfo> services = events.get(0).getServices();
            ServiceInfo s = services.get(0);
            s.setMenu(s.loadMenu(s.getMenu_id(), s.getConfirmed()));
            SummarySheet sheet = CatERing.getInstance().getEventManager().createEventSheets(events.get(0), s);
            System.out.println("Before ..." + sheet.toString() + ", elements:");
            for (TaskInfo r: sheet.getTasks()) {
                System.out.println(r.toString());
            }
            sheet.setModificable(true);
            CatERing.getInstance().getEventManager().addInfo(s, sheet.getTasks().get(0), 20, 100);
            CatERing.getInstance().getEventManager().addInfo(s, sheet.getTasks().get(1), 20, 100);
            CatERing.getInstance().getEventManager().addInfo(s, sheet.getTasks().get(2), 30, 40);
            CatERing.getInstance().getEventManager().addInfo(s, sheet.getTasks().get(3), 30, 100);
            CatERing.getInstance().getEventManager().addInfo(s, sheet.getTasks().get(4), 15, 30);
            CatERing.getInstance().getEventManager().addInfo(s, sheet.getTasks().get(5), 15, 30);
            CatERing.getInstance().getEventManager().addInfo(s, sheet.getTasks().get(6), 15, 30);
            System.out.println("\nAfter ..." + sheet.toString() + ", elements:");
            for (TaskInfo r: sheet.getTasks()) {
                System.out.println(r.toString());
            }
        } catch (UseCaseLogicException | MenuException | EventException | SummarySheetException e) {
            System.out.println("Errore di logica nello use case");
        }
    }
}
