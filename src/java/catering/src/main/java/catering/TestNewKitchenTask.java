package catering;

import catering.businesslogic.CatERing;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.EventException;
import catering.businesslogic.event.EventInfo;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.menu.MenuException;
import catering.businesslogic.recipe.Recipe;
import catering.businesslogic.task.SummarySheet;
import catering.businesslogic.task.SummarySheetException;
import catering.businesslogic.task.TaskInfo;
import catering.persistence.PersistenceManager;
import javafx.collections.ObservableList;

public class TestNewKitchenTask {
    public static void main(String[] args) {
        try {
            System.out.println("TEST DATABASE CONNECTION");
            PersistenceManager.testSQLConnection();
            CatERing.getInstance().getUserManager().fakeLogin("Lidia");
            System.out.println(CatERing.getInstance().getUserManager().getCurrentUser());
            System.out.println("\nTEST ADD NEW KITCHEN TASK SUMMARY SHEET\n");
            ObservableList<EventInfo> events = CatERing.getInstance().getEventManager().getEventInfo();
            ObservableList<ServiceInfo> services = events.get(0).getServices();
            ServiceInfo s = services.get(0);
            s.setMenu(s.loadMenu(s.getMenu_id(), s.getConfirmed()));
            SummarySheet sheet = CatERing.getInstance().getEventManager().createEventSheets(events.get(0), s);
            ObservableList<Recipe> recipes = CatERing.getInstance().getRecipeManager().getRecipes();
            System.out.println(sheet.toString() + ", before elements:\n");
            for (TaskInfo r: sheet.getTasks()) {
                System.out.println(r.toString());
            }
            System.out.println("\nVoglio aggiugere il 'vitello tonnato' al foglio riepilogativo mostrato sopra ...\n");
            CatERing.getInstance().getEventManager().addKitchenTask(s, recipes.get(0));
            System.out.println(services.get(0).getSummarySheet().toString() + ", after elements:\n");
            for (TaskInfo r: sheet.getTasks()) {
                if(r.getRecipe().getId() == recipes.get(0).getId()) {
                    System.out.println(r.toString() + " ---> elemento appena aggiunto");
                }else {
                    System.out.println(r.toString());
                }
            }
        } catch (SummarySheetException | UseCaseLogicException | MenuException | EventException e) {
            System.out.println("Errore di logica nello use case");
        }
    }
}
