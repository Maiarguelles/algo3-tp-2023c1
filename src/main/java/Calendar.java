import java.net.InetSocketAddress;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Timer;

public class Calendar {
    private final ArrayList<Event> events;
    private final ArrayList<Task> tasks;
    private Alarm nextAlarm;

    public Calendar(){
        events = new ArrayList<Event>();
        tasks = new ArrayList<Task>();
        nextAlarm = null;
    }

    public boolean addEvent(Event event){
        if(event == null)
            return false;
        events.add(event);
        var alarms = event.getAlarms();
        if(alarms != null)
            updateNextAlarm();
        return true;
    }


    public void deleteEvent(Event event){
        if(event.getAlarms().contains(nextAlarm)){
            nextAlarm = null;
            events.remove(event);
            updateNextAlarm();
        }
        else
            events.remove(event);
    }

    public boolean addTask(Task task){
        if(task == null)
            return false;
        tasks.add(task);
        var alarms = task.getAlarms();
        if(alarms != null)
            updateNextAlarm();
        return true;
    }

    public void deleteTask(Task task){
        if(task.getAlarms().contains(nextAlarm)){
            nextAlarm = null;
            tasks.remove(task);
            updateNextAlarm();
        }
        else
            tasks.remove(task);
    }

    private void updateNextAlarm(){
        Event event = null;
        Alarm alarm = null;
        for(int i = 0; i < events.size(); i++){
            event = events.get(i);
            for(int j = 0; j < event.getAlarms().size(); j++){
                alarm = event.getAlarms().get(j);
                if(nextAlarm == null)
                    nextAlarm = alarm;
                else if(alarm.getGoOffTime().isBefore(nextAlarm.getGoOffTime()))
                    nextAlarm = alarm;
            }
        }
        Task task = null;
        for(int i = 0; i < tasks.size(); i++){
            task = tasks.get(i);
            for(int j = 0; j < task.getAlarms().size(); j++){
                alarm = task.getAlarms().get(j);
                if(nextAlarm == null)
                    nextAlarm = alarm;
                else if(alarm.getGoOffTime().isBefore(nextAlarm.getGoOffTime()))
                    nextAlarm = alarm;
            }
        }
    }

    public LocalDateTime nextAlarm(){
        if(nextAlarm != null)
            return nextAlarm.getGoOffTime();
        else
            return null;
    }


    private Event searchEvent(Event event){
        Event searchedEvent = null;

        for(int i = 0; i < events.size();i++){
            if(events.get(i).equals(event)) {
                searchedEvent = event;
                return searchedEvent;
            }
        }
        return searchedEvent;
    }

    private Task searchTask(Task task){
        Task searchedTask = null;
        for(int i = 0; i < tasks.size();i++){
            searchedTask = tasks.get(i);
            if(searchedTask.equals(task)){
                searchedTask = task;
                return searchedTask;
            }
        }
        return searchedTask;
    }


    public boolean addAlarmToExistentEvent(Event event, Alarm alarm){
        if(event == null)
            return false;

        Event existentEvent = searchEvent(event);

        if(existentEvent != null) {
            existentEvent.addAlarm(alarm);
            updateNextAlarm();
            return true;
        }

        return false;
    }
    public boolean addAlarmToExistentTask(Task task, Alarm alarm){
        if(task == null)
            return false;

        Task existentTask = searchTask(task);

        if(existentTask != null) {
            existentTask.addAlarm(alarm);
            updateNextAlarm();
            return true;
        }

        return false;
    }


    public boolean existEvent(LocalDate date){
        Event event = null;
        for (Event value : events) {
            event = value;
            if (date.isEqual(event.getStartDate().toLocalDate()))
                return true;
        }
        return false;
    }

    public boolean existTask(LocalDateTime date){
        Task task = null;
        for (Task value : tasks) {
            task = value;
            if (date.isEqual(task.getStartDate()))
                return true;
        }
        return false;
    }


    public ArrayList<Event> showEvents(LocalDateTime date1, LocalDateTime date2){
        var eventsToShow = new ArrayList<Event>();
        var dates = new ArrayList<LocalDateTime>();
        Event event;
        for(int i = 0; i < events.size(); i++){
            event = events.get(i);
            if(!event.isRepeating){
                var date = event.getStartDate();
                if(date.isBefore(date2) && date.isAfter(date1))
                    eventsToShow.add(event);
            }
            else {
                dates = event.showDatesOfEvent(date1, date2);
                for (int j = 0; j < dates.size(); i++) {
                    eventsToShow.add(event.repeatEvent(dates.get(j)));
                }
            }
        }
        return eventsToShow;
    }

    public ArrayList<Task> showTasks(LocalDateTime date1, LocalDateTime date2){
        var taskToShow = new ArrayList<Task>();
        for (var task : this.tasks){
            LocalDateTime taskStartDate = task.getStartDate();
            LocalDateTime taskExpiretionDate = task.getExpirationDate();
            if ((taskStartDate.isAfter(date1) && taskStartDate.isBefore(date2))
                    || taskExpiretionDate.isAfter(date1) && taskExpiretionDate.isBefore(date2)){
                taskToShow.add(task);

            }
        }
        return taskToShow;
    }


}
