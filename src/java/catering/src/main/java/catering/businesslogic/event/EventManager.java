package catering.businesslogic.event;


import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import catering.businesslogic.CatERing;
import catering.businesslogic.ShiftBoard.ShiftBoardException;
import catering.businesslogic.ShiftBoard.ShiftBoardInfo;
import catering.businesslogic.ShiftBoard.ShiftException;
import catering.businesslogic.ShiftBoard.ShiftInfo;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.menu.Menu;
import catering.businesslogic.menu.MenuException;
import catering.businesslogic.recipe.Recipe;
import catering.businesslogic.task.SummarySheet;
import catering.businesslogic.task.SummarySheetException;
import catering.businesslogic.task.TaskInfo;
import catering.businesslogic.user.User;
import catering.businesslogic.user.UserException;
import javafx.collections.ObservableList;

public class EventManager {
    private EventInfo currentEvent;
    private ArrayList<EventEventReceiver> receivers;

    public EventManager() {
        receivers = new ArrayList<>();
    }

    public ObservableList<EventInfo> getEventInfo() {
        return EventInfo.loadAllEventInfo();
    }

    public void addReceiver(EventEventReceiver er) {this.receivers.add(er);}

    public void removeReceiver(EventEventReceiver er) {this.receivers.remove(er);}
    private void setCurrentEvent(EventInfo event) {
        this.currentEvent = event;
    }

    public void addKitchenTask(ServiceInfo service, Recipe kitchenTask) throws SummarySheetException {
        SummarySheet sheet = service.getSummarySheet();
        if (sheet == null) {
            throw new SummarySheetException();
        }
        TaskInfo t = sheet.createTask(kitchenTask);
        this.notifyAddKitchenTask(sheet, t);
    }

    public void addShift(ServiceInfo service, Date date, String place, Time start, Time end, boolean preparationShift, boolean serviceShift) throws ShiftBoardException {
        ShiftBoardInfo board = service.getShiftBoard();
        if (board == null) {
            throw new ShiftBoardException();
        }
        ShiftInfo newShift = board.createShift(date, place, start, end, preparationShift, serviceShift);
        this.notifyAddShift(board, newShift);
    }

    public void assignTask(ServiceInfo service, TaskInfo task, ShiftInfo shift, User cook) throws SummarySheetException, ShiftException, UserException {
        SummarySheet sheet = service.getSummarySheet();
        if (sheet == null) {
            throw new SummarySheetException();
        }
        if (!sheet.contains(task)) {
            throw new SummarySheetException();
        }
        if (!sheet.modificable()) {
            throw new SummarySheetException();
        }
        if (shift == null) {
            throw new ShiftException();
        }
        if (sheet.checkCook(cook)) {
            task.setShift(shift);
            task.assignCook(cook);
        }
        this.notifyAssignTask(task, sheet);
    }

    public void updateTask(ServiceInfo service, TaskInfo task, ShiftInfo shift, User cook) throws SummarySheetException, UserException {
        SummarySheet sheet = service.getSummarySheet();
        if (sheet == null) {
            throw new SummarySheetException();
        }
        if (!sheet.contains(task)) {
            throw new SummarySheetException();
        }
        if (!sheet.modificable()) {
            throw new SummarySheetException();
        }
        if(cook != null) {
            if (sheet.checkCook(cook)) {
                task.assignCook(cook);
            } else {
                throw new UserException();
            }
        }
        if(shift != null) {
            task.setShift(shift);
        }
        this.notifyUpdateTask(task);
    }

    public void removeTask(ServiceInfo service, TaskInfo task) throws SummarySheetException {
        SummarySheet sheet = service.getSummarySheet();
        if (sheet == null) {
            throw new SummarySheetException();
        }
        if (sheet.lenght() < 2) {
            throw new SummarySheetException();
        }
        if (!sheet.modificable()) {
            throw new SummarySheetException();
        }
        sheet.removeTask(task);
        this.notifyRemoveTask(task);
    }

    public void removeShift(ShiftInfo shift, ServiceInfo service) throws ShiftBoardException {
        ShiftBoardInfo board = service.getShiftBoard();
        if(board == null) {
            throw new ShiftBoardException();
        }
        board.removeShift(shift);
        this.notifyRemoveShift(shift);
    }

    public void addInfo(ServiceInfo service, TaskInfo task, int stimedTime, int portions) throws SummarySheetException {
        SummarySheet sheet = service.getSummarySheet();
        if (sheet == null) {
            throw new SummarySheetException();
        }
        if (!sheet.contains(task)) {
            throw new SummarySheetException();
        }
        if (!sheet.modificable()) {
            throw new SummarySheetException();
        }
        task.setPortions(portions);
        task.setStimedTime(stimedTime);
        this.notifyAddInfo(task);
    }

    public SummarySheet createEventSheets(EventInfo event, ServiceInfo service) throws UseCaseLogicException, MenuException, EventException {
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        Menu menu = service.getMenu();
        if (!user.isChef()) {
            throw new UseCaseLogicException("user non è uno chef");
        }
        if (!menu.isApproved()) {
            throw new MenuException("menu non approvato");
        }
        if (!event.isInProgress()) {
            throw new EventException("evento non in corso");
        }
        setCurrentEvent(event);
        
        service.createSummarySheet();

        ArrayList<Recipe> kitchenTasks = menu.getKitchenTask();
        for (Recipe recipe : kitchenTasks) {
            service.getSummarySheet().createTask(recipe);
        }
        this.notifySummarySheetCreated(service.getSummarySheet());
        return service.getSummarySheet();
    }

    private void notifySummarySheetCreated(SummarySheet s) {
        for (EventEventReceiver er : this.receivers) {
            er.updateSummarySheetCreated(this.currentEvent, s);
        }
    }

    private void notifyAddKitchenTask(SummarySheet s, TaskInfo t) {
        for (EventEventReceiver er : this.receivers) {
            er.updateAddKitchenTask(s, t);
        }
    }

    private void notifyAddShift(ShiftBoardInfo sb, ShiftInfo s) {
        for (EventEventReceiver er : this.receivers) {
            er.updateAddShift(sb, s);
        }
    }

    private void notifyRemoveShift(ShiftInfo s) {
        for (EventEventReceiver er : this.receivers) {
            er.updateRemoveShift(s);
        }
    }

    private void notifyAssignTask(TaskInfo t, SummarySheet s) {
        for (EventEventReceiver er : this.receivers) {
            er.updateAssignTask(t, s);
        }
    }

    private void notifyRemoveTask(TaskInfo t) {
        for (EventEventReceiver er : this.receivers) {
            er.updateRemoveTask(t);
        }
    }

    private void notifyUpdateTask(TaskInfo t) {
        for (EventEventReceiver er : this.receivers) {
            er.updateUpdateTask(t);
        }
    }

    private void notifyAddInfo(TaskInfo t) {
        for (EventEventReceiver er : this.receivers) {
            er.updateAddInfo(t);
        }
    }
}
