package catering.persistence;

import catering.businesslogic.ShiftBoard.ShiftBoardInfo;
import catering.businesslogic.ShiftBoard.ShiftInfo;
import catering.businesslogic.event.EventEventReceiver;
import catering.businesslogic.event.EventInfo;
import catering.businesslogic.task.SummarySheet;
import catering.businesslogic.task.TaskInfo;

public class EventPersistence implements EventEventReceiver {
    @Override
    public void updateSummarySheetCreated(EventInfo e, SummarySheet s) { }

    @Override
    public void updateAddKitchenTask(SummarySheet s, TaskInfo t) { }

    @Override
    public void updateAddShift(ShiftBoardInfo sb, ShiftInfo s) { }

    @Override
    public void updateRemoveShift(ShiftInfo s) { }

    @Override
    public void updateAssignTask(TaskInfo t, SummarySheet s) { }

    @Override
    public void updateRemoveTask(TaskInfo t) { }

    @Override
    public void updateUpdateTask(TaskInfo t) { }

    @Override
    public void updateAddInfo(TaskInfo t) { }
}
