package catering.businesslogic.task;

import catering.businesslogic.ShiftBoard.ShiftInfo;
import catering.businesslogic.recipe.Recipe;
import catering.businesslogic.user.User;
import catering.businesslogic.user.UserException;

public class TaskInfo {
    private Recipe recipe;
    private int stimedTime;
    private int portions;
    private boolean preparare;
    private boolean completato;
    private User cook;
    private ShiftInfo shift;
    public TaskInfo(Recipe recipe) {
        this.recipe = recipe;
        this.stimedTime = 0;
        this.portions = 0;
    }
    public void setStimedTime(int time) {
        this.stimedTime = time;
    }
    public void setPortions(int number) {
        this.portions = number;
    }
    public void setPreparare(boolean prep) {
        this.preparare = prep;
    }
    public void setCompletato(boolean compl) {
        this.completato = compl;
    }

    public int getStimedTime() {
        return this.stimedTime;
    }
    public int getPortions() {
        return this.portions;
    }
    public Recipe getRecipe() {
        return this.recipe;
    }
    public boolean getPreparare() {
        return this.preparare;
    }
    public boolean getCompletato() {
        return this.completato;
    }
    public User getCook() {
        return this.cook;
    }
    public void assignCook(User cook) throws UserException {
        if (!cook.isCook()) {
            throw new UserException();
        }
        this.cook = cook;
    }
    public void setShift(ShiftInfo shift) {
        this.shift = shift;
    }

    public String toString() {
        if(this.cook != null)
            return "\n" + recipe.getName() + ", cuoco assegnato: " + this.cook.toString() + "\n" + "Turno di svolgimento: " + this.shift.toString();
        else if(this.portions > 0 && this.stimedTime > 0)
            return recipe.getName() + ", Tempo stimato: " + this.stimedTime + " min, Porzioni: " + this.portions;
        else
            return recipe.getName();
    }
}
