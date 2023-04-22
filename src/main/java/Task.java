import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Task {

    private String title;
    private String description;
    private boolean completeDay;
    private boolean completed;
    private LocalDateTime startDate;
    private LocalDateTime expirationDate;

    private ArrayList<Alarm> alarms;

    /*
    public Task(String title, String description, LocalDateTime startDate){
        this.completed = false;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.completeDay = true;
        this.expirationDate = null;
    }

    public Task(String title, String description, LocalDateTime startDate,  LocalDateTime expirationDate){
        this.completed = false;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.completeDay = false;
        this.expirationDate = expirationDate;
    }
    */


    public Task(String title, String description, LocalDateTime startDate,  LocalDateTime expirationDate, boolean isCompleteDay){
        this.completed = false;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.completeDay = isCompleteDay;
        this.expirationDate = (isCompleteDay) ? startDate.plusDays(1) : expirationDate;
    }

    public void completeTask(){
        completed = true;
    }
    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCompleteDay(boolean completeDay) {
        this.completeDay = completeDay;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public LocalDateTime getExpirationDate(){
        return this.getExpirationDate();
    }
    public void addAlarm(Alarm alarm){
        alarms.add(alarm);
    }

    public void removeAlarm(Alarm alarm){
        alarms.remove(alarm);
    }

    public ArrayList<Alarm> getAlarms(){
        return alarms;
    }


}
