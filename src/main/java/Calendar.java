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
        tasks = new ArrayList<Task>();;
        nextAlarm = null;
    }

    public boolean addEvent(Event event){
        if(event == null)
            return false;
        events.add((event));
        var alarms = event.getAlarms();
        if(alarms != null)
            updateNextAlarm(event.getAlarms());
        return true;
    }

    public void deleteEvent(Event event){
        events.remove(event);
    }

    public void addTask(Task task){
        tasks.add(task);
    }

    public void deleteTask(Task task){
        tasks.remove(task);
    }

    private void updateNextAlarm(ArrayList<Alarm> alarms){
        for(int i = 0; i < alarms.size(); i++){
            var alarm = alarms.get(i);
            if(nextAlarm.calculateGoOffTime().isAfter(alarm.calculateGoOffTime()))
                nextAlarm = alarm;
        }
    }

    public boolean existEvent(LocalDateTime date){return false;}

    public boolean existTask(LocalDateTime date){return false;}


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




}
