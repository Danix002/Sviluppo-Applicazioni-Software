package catering.businesslogic;

import catering.businesslogic.ShiftBoard.ShiftBoardManager;
import catering.businesslogic.event.EventManager;
import catering.businesslogic.menu.MenuManager;
import catering.businesslogic.recipe.RecipeManager;
import catering.businesslogic.task.TaskManager;
import catering.businesslogic.user.UserManager;
import catering.persistence.EventPersistence;
import catering.persistence.MenuPersistence;

public class CatERing {
    private static CatERing singleInstance;

    public static CatERing getInstance() {
        if (singleInstance == null) {
            singleInstance = new CatERing();
        }
        return singleInstance;
    }

    private MenuManager menuMgr;
    private RecipeManager recipeMgr;
    private UserManager userMgr;
    private EventManager eventMgr;
    private TaskManager taskMgr;
    private ShiftBoardManager shiftMgr;
    private MenuPersistence menuPersistence;
    private EventPersistence eventPersistence;

    private CatERing() {
        menuMgr = new MenuManager();
        recipeMgr = new RecipeManager();
        userMgr = new UserManager();
        eventMgr = new EventManager();
        taskMgr = new TaskManager();
        shiftMgr = new ShiftBoardManager();
        menuPersistence = new MenuPersistence();
        eventPersistence = new EventPersistence();
        menuMgr.addEventReceiver(menuPersistence);
        eventMgr.addReceiver(eventPersistence);
    }

    public MenuManager getMenuManager() {
        return menuMgr;
    }

    public RecipeManager getRecipeManager() {
        return recipeMgr;
    }

    public UserManager getUserManager() {
        return userMgr;
    }

    public ShiftBoardManager getShiftBoardManager() {
        return shiftMgr;
    }

    public TaskManager getTaskManager(){ return taskMgr; }

    public EventManager getEventManager() { return eventMgr; }

}
