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

public class TestUpdateTask {
    public static void main(String[] args) {
        try {
            System.out.println("TEST DATABASE CONNECTION");
            PersistenceManager.testSQLConnection();
            CatERing.getInstance().getUserManager().fakeLogin("Lidia");
            System.out.println(CatERing.getInstance().getUserManager().getCurrentUser());
            System.out.println("\nTEST UPDATE TASK\n");
            ObservableList<EventInfo> events = CatERing.getInstance().getEventManager().getEventInfo();
            ObservableList<ServiceInfo> services = events.get(0).getServices();
            ServiceInfo s = services.get(0);
            s.setMenu(s.loadMenu(s.getMenu_id(), s.getConfirmed()));
            SummarySheet sheet = CatERing.getInstance().getEventManager().createEventSheets(events.get(0), s);
            ArrayList<ShiftInfo> shifts = new ArrayList<>();
            s.setBoard(new ShiftBoardInfo(shifts, s.getId()));
            Date currentDate = new Date();
            Time time = new Time(currentDate.getTime());
            CatERing.getInstance().getEventManager().addShift(s, currentDate, "Manfria", time, time, true, true);
            CatERing.getInstance().getEventManager().addShift(s, currentDate, "Licata", time, time, true, false);
            CatERing.getInstance().getEventManager().addShift(s, currentDate, "Licata", time, time, false, true);
            sheet.setModificable(true);
            User guido = User.loadUserById(5);
            CatERing.getInstance().getEventManager().assignTask(s, sheet.getTasks().get(3), shifts.get(1), guido);
            System.out.println(sheet.toString() + ", elements:");
            for (TaskInfo r: sheet.getTasks()) {
                System.out.println(r.toString());
            }
            System.out.println("\nPrima modifica --> modifico solo il turno di svolgimento della task ...");
            CatERing.getInstance().getEventManager().updateTask(s, sheet.getTasks().get(3), shifts.get(0), null);
            System.out.println("\n" + sheet.toString() + ", elements:");
            for (TaskInfo r: sheet.getTasks()) {
                System.out.println(r.toString());
            }
            System.out.println("\nSeconda modifica --> modifico solo il cuoco assegnato alla task ...");
            User paola = User.loadUserById(7);
            CatERing.getInstance().getEventManager().updateTask(s, sheet.getTasks().get(3), null, paola);
            System.out.println("\n" + sheet.toString() + ", elements:");
            for (TaskInfo r: sheet.getTasks()) {
                System.out.println(r.toString());
            }
            System.out.println("\nTerza modifica --> modifico il cuoco e il turno di svolgimento della task, ritorno com'ero in partenza ...");
            CatERing.getInstance().getEventManager().updateTask(s, sheet.getTasks().get(3), shifts.get(1), guido);
            System.out.println("\n" + sheet.toString() + ", elements:");
            for (TaskInfo r: sheet.getTasks()) {
                System.out.println(r.toString());
            }
        } catch (UseCaseLogicException | MenuException | EventException | ShiftBoardException | SummarySheetException |
                 ShiftException | UserException e) {
            System.out.println("Errore di logica nello use case");
        }
    }
}
