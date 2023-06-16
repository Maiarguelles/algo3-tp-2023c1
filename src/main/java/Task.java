import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Task extends Reminder{

    private boolean completed;

    public Task(String title, String description, LocalDateTime startDate, boolean completeDay){
        super(title, description, completeDay, startDate);
        this.completed = false;
    }

    public boolean isCompleted(){
        return completed;
    }

    public void setCompleteTask(boolean completeTask){
        completed = completeTask;
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

    public Reminder repeatReminder(LocalDateTime startDate){
        var taskRepetition = new Task(title, description, startDate, isCompleteDay());
        for (Alarm alarm : alarms) {
            taskRepetition.addAlarm(alarm.cloneAlarm(startDate));
        }
        return taskRepetition;
    }

    @Override
    public boolean equals(Object o){
        if(o.getClass() != Task.class)
            return false;

        if(this == o)
            return true;

        if(o == null){
            return false;
        }

        boolean isEqual = true;

        if(!(this.completed ==((Task) o).completed))
            isEqual = false;
        if(!(this.title.equals(((Task) o).title)))
            isEqual = false;
        if(!(this.description.equals(((Task) o).description)))
            isEqual = false;
        if(!(this.completeDay == ((Task) o).completeDay))
            isEqual = false;
        if(!(this.startDate.equals(((Task) o).startDate)))
            isEqual = false;
        if(!(this.alarms.equals(((Task) o).alarms)))
            isEqual = false;

        return isEqual;
    }


    @Override
    public int hashCode(){
        int x = 24;
        if (completeDay){
            x+=16;
        }
        int y = 3;
        for (int i = 0; i<title.length(); i++){
            y+= title.charAt(i);
        }

        int z = 9;
        for (int i = 0; i<description.length(); i++){
            z+= description.charAt(i);
        }

        int date1 = startDate.getMonthValue()*startDate.getDayOfMonth()*startDate.getYear();

        return date1*x+y*z;
    }



}
