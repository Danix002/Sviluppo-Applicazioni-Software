package catering.businesslogic.task;

import java.util.ArrayList;

import catering.businesslogic.recipe.Recipe;
import catering.businesslogic.user.User;

public class SummarySheet {
    private ArrayList<TaskInfo> tasks;
    private boolean order;
    private boolean modificable;
    private int id; //id sheet == id servizio per cui è stato creato

    public SummarySheet() {
        this.tasks = new ArrayList<>();
        this.order = false;
        this.modificable = false;
    }

    public boolean removeTask(TaskInfo task) {
        return this.tasks.remove(task);
    }

    public boolean modificable() {
        return this.modificable;
    }

    public TaskInfo createTask(Recipe recipe) {
        TaskInfo task = new TaskInfo(recipe);
        addTask(task);
        return task;
    }

    private void addTask(TaskInfo t){ this.tasks.add(t); }
    public boolean contains(TaskInfo task) {
        return this.tasks.contains(task);
    }

    public boolean checkCook(User cook) {
        if(!cook.isCook()) {
            return false;
        }
        for (TaskInfo task : tasks) {
            if(task.getCook() != null) {
                if (task.getCook().getId() == cook.getId()) {
                    return false;
                }
            }
        }
        return true;
    }

    public int lenght() {
        return tasks.size();
    }

    public boolean getModificable() {
        return this.modificable;
    }
    public void setModificable(boolean flag) {
        this.modificable = flag;
    }
    public boolean getOrder() {
        return this.order;
    }
    public ArrayList<TaskInfo> getTasks() {
        return this.tasks;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return this.id;
    }

    public String toString() {
        return "Task inside Sheet: " + tasks.size();
    }
}
