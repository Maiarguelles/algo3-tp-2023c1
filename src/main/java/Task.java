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

    public void completeTask(){
        completed = true;
    }



}
