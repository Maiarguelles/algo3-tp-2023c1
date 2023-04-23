import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Task extends Reminder{

    private boolean completed;

    public Task(String title, String description, LocalDateTime startDate, boolean completeDay){
        super(title, description, completeDay, startDate);
        this.completed = false;
        isRepeating = false;
    }

    public void completeTask(){
        completed = true;
    }

    public ArrayList<LocalDateTime> showDatesOfReminder(LocalDateTime date1, LocalDateTime date2){
        ArrayList<LocalDateTime> dates = new ArrayList<>();
        if (startDate.isAfter(date1) && startDate.isBefore(date2))
            dates.add(startDate);

        return dates;
    }

    public void changeCompleteDay(LocalDateTime startDate){
        completeDay = false;
        this.startDate = startDate;
    }

    public void makeReminderCompleteDay(){
        completeDay = true;
    }

    public Reminder addRepetitionByDate(Reminder reminder, LocalDateTime expirationDate, FrequencyStrategy frequencyStrategy){
        return reminder;
    }

    public Reminder addOcurrencesRepetition(Reminder reminder, int ocurrences, FrequencyStrategy frequencyStrategy){
        return reminder;
    }

    public Reminder addInfiniteRepetition(Reminder reminder, FrequencyStrategy frequencyStrategy){
        return reminder;
    }


    public Reminder repeatReminder(LocalDateTime startDate){
        var taskRepetition = new Task(title, description, startDate, isCompleteDay());
        for (Alarm alarm : alarms) {
            taskRepetition.addAlarm(alarm.cloneAlarm(startDate));
        }
        return taskRepetition;
    }

}
