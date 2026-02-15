package catering.businesslogic.event;
import catering.businesslogic.ShiftBoard.ShiftBoardInfo;
import catering.businesslogic.ShiftBoard.ShiftInfo;
import catering.businesslogic.task.SummarySheet;
import catering.businesslogic.task.TaskInfo;

public interface EventEventReceiver {
    public void updateSummarySheetCreated(EventInfo e, SummarySheet s);
    public void updateAddKitchenTask(SummarySheet s, TaskInfo t);
    public void updateAddShift(ShiftBoardInfo sb, ShiftInfo s);
    public void updateRemoveShift(ShiftInfo s);
    public void updateAssignTask(TaskInfo t, SummarySheet s);
    public void updateRemoveTask(TaskInfo t);
    public void updateUpdateTask(TaskInfo t);
    public void updateAddInfo(TaskInfo t);
}
