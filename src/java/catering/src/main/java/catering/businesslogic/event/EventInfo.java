package catering.businesslogic.event;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import catering.businesslogic.task.SummarySheet;
import catering.businesslogic.user.User;
import catering.persistence.BatchUpdateHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;

public class EventInfo implements EventItemInfo {
    private int id;
    private String name;
    private Date dateStart;
    private Date dateEnd;
    private int participants;
    private User organizer;

    //variables
    private int duration; //in hours
    private String place;
    private String type;
    private String[] requests;
    private boolean card;
    private String notes;
    private boolean inProgress;
    private boolean isFinished;

    //associations
    private ObservableList<ServiceInfo> services;
    private ArrayList<Penal> penals = new ArrayList<Penal>();
    private ArrayList<Documentation> documents = new ArrayList<>();

    //CONSTRUCTOR
    public EventInfo(String name) {
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    //GETS
    public ArrayList<Penal> getPenals() {
        return this.penals;
    }

    public ArrayList<Documentation> getDocumentation() {
        return this.documents;
    }

    public <T> void addDocument(Documentation<T> document) {
        this.documents.add(new Documentation(document));
    }

    public void addPenal(String motivation) {
        this.penals.add(new Penal(motivation));
    }
    public boolean isInProgress() {
        return this.inProgress;
    }

    public boolean isFinished() {
        return this.isFinished;
    }

    public String getNotes() {
        return this.notes;
    }

    public String getType() {
        return this.type;
    }

    public String getPlace() {
        return this.place;
    }

    public String[] getRequets() {
        return this.requests;
    }

    public boolean isCard() {
        return this.card;
    }

    public int getDuration() {
        return this.duration;
    }

    public int getNumberOfPartecipants() {
        return this.participants;
    }

    public String getName() {
        return this.name;
    }

    public int getID() {
        return this.id;
    }

    public Date getDateEnd() {
        return this.dateEnd;
    }

    public Date getDateStart() {
        return this.dateStart;
    }

    public User getOrganizer() {
        return this.organizer;
    }

    //SET
    public void setCard(boolean card) {
        this.card = card;
    }

    public void setInProgress(boolean inProgress) {
        this.inProgress = inProgress;
        setCard(!inProgress);
    }

    public void terminate() {
        this.inProgress = false;
        this.isFinished = true;
        this.card = false;
    }
    
    public ObservableList<ServiceInfo> getServices() {
        return FXCollections.unmodifiableObservableList(this.services);
    }

    public String toString() {
        return name + ": " + dateStart + "-" + dateEnd + ", " + participants + " pp. (" + organizer.getUserName() + ")";
    }

    //STATIC METHODS FOR PERSISTENCE
    public static ObservableList<EventInfo> loadAllEventInfo() {
        ObservableList<EventInfo> all = FXCollections.observableArrayList();
        String query = "SELECT * FROM Events WHERE true";
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                String n = rs.getString("name");
                EventInfo e = new EventInfo(n);
                e.id = rs.getInt("id");
                e.dateStart = rs.getDate("date_start");
                e.dateEnd = rs.getDate("date_end");
                e.participants = rs.getInt("expected_participants");
                int org = rs.getInt("organizer_id");
                e.organizer = User.loadUserById(org);
                e.inProgress = rs.getBoolean("inCorso");
                all.add(e);
            }
        });
        for (EventInfo e : all) {
            e.services = ServiceInfo.loadServiceInfoForEvent(e.id);
        }
        return all;
    }
}
