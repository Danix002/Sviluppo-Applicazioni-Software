package catering.businesslogic.event;

import catering.businesslogic.menu.MenuItem;
import catering.businesslogic.menu.Section;
import catering.businesslogic.recipe.Recipe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import catering.businesslogic.ShiftBoard.ShiftBoardInfo;
import catering.businesslogic.menu.Menu;
import catering.businesslogic.task.SummarySheet;
import catering.businesslogic.user.User;
import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;

public class ServiceInfo implements EventItemInfo {
    private int id;
    private String name;
    private Date date;
    private Time timeStart;
    private Time timeEnd;
    private String type;
    private boolean confirmed;
    private int participants;
    private ArrayList<User> personal; //service personal
    private Menu menu;
    private int menu_id;
    private ShiftBoardInfo board;
    private SummarySheet sheet;

    //CONSTRUCTOR
    public ServiceInfo(String name) {
        this.name = name;
    }

    public ServiceInfo(ShiftBoardInfo board, Menu menu, ArrayList<User> personal, String name, Date date, Time timeStart, Time timeEnd, String type, boolean confirmed, int participants) {
        this.confirmed = confirmed;
        this.board = board;
        this.date = date;
        this.menu = menu;
        this.personal = personal;
        this.name = name;
        this.participants = participants;
        this.timeEnd = timeEnd;
        this.timeStart = timeStart;
        this.type = type;
    }

    public ShiftBoardInfo getShiftBoard() {
        return this.board;
    }

    public String testSheetCreated() {
        if(this.sheet != null) {
            String result = "\n" + "Sheet id: " + this.sheet.getId() + ", created for service id: " + this.id +
                    ", information service:\n"+ this.toString() + "\n";
            return result;
        }else{
            return null;
        }
    }

    public String toString() {
        return name + ": " + date + " (" + timeStart + "-" + timeEnd + "), " + participants + " pp.";
    }

    //GETS
    public int getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public Date getDate() {
        return this.date;
    }
    public Time getTimeStart() {
        return this.timeStart;
    }
    public Time getTimeEnd() {
        return this.timeEnd;
    }
    public String getType() {
        return this.type;
    }
    public boolean getConfirmed() {
        return this.confirmed;
    }
    public int getMenu_id(){return this.menu_id;}
    public int getParticipants() {
        return this.participants;
    }
    public ArrayList<User> getPersonal() {
        return this.personal;
    }

    public Menu getMenu() {
        return this.menu;
    }

    public void setMenu(Menu menu){
        this.menu = menu;
    }
    public void setBoard(ShiftBoardInfo shifts){
        this.board = shifts;
    }

    //FUNCTIONS
    public void createSummarySheet() {
        this.sheet = new SummarySheet();
        this.sheet.setId(this.id);
    }
    public SummarySheet getSummarySheet() {
        return this.sheet;
    }

    public void setConfirmed(boolean confirm) {
        this.confirmed = confirm;
    }

    //STATIC METHODS FOR PERSISTENCE
    public static ObservableList<ServiceInfo> loadServiceInfoForEvent(int event_id) {
        ObservableList<ServiceInfo> result = FXCollections.observableArrayList();
        String query = "SELECT * FROM Services WHERE event_id = " + event_id;
        
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                String s = rs.getString("name");
                ServiceInfo serv = new ServiceInfo(s);
                serv.id = rs.getInt("id");
                serv.date = rs.getDate("service_date");
                serv.timeStart = rs.getTime("time_start");
                serv.timeEnd = rs.getTime("time_end");
                serv.participants = rs.getInt("expected_participants");

                int approvedMenu = rs.getInt("approved_menu_id");
                int proposedMenu = rs.getInt("proposed_menu_id");

                if(approvedMenu > 0) {
                    serv.menu_id = approvedMenu;
                    serv.confirmed = true;
                }else if(proposedMenu > 0){
                    serv.menu_id = proposedMenu;
                    serv.confirmed = false;
                }
                result.add(serv);
            }
        });
        return result;
    }

    public Menu loadMenu(int menu_id,  boolean isApproved) {
        ObservableList<Menu> menus = Menu.loadAllMenus();
        for (Menu menu : menus) {
            if (menu.getId() == menu_id) {
                menu.setApproved(isApproved);
                return menu;
            }
        }
        return null;
    }

}
