import java.net.InetSocketAddress;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Timer;

public class Calendar {
    private final ArrayList<Event> events;
    private final ArrayList<Task> tasks;
    private Alarm nextAlarm;
    private final Timer timer;

    public Calendar(){
        events = new ArrayList<Event>();
        tasks = new ArrayList<Task>();
        timer = new Timer();
        nextAlarm = null;
    }

    public void addEvent(Event event){
        events.add((event));
        var alarms = event.getAlarms();
        if(alarms != null)
            updateNextAlarm(event.getAlarms());
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

    private void sortEvents(){

    }

    public ArrayList<Event> showEvents(LocalDateTime date1, LocalDateTime date2){
        var eventsToShow = new ArrayList<Event>();
        sortEvents();
        for(int i = 0; i < events.size(); i++){
            var event = events.get(i);
            if(event.getStartDate().isAfter(date1.toLocalDate().atStartOfDay())
                    || event.getStartDate().isBefore(date2.toLocalDate().plusDays(1).atStartOfDay())){
                eventsToShow.add(event);
            }
        }
        return eventsToShow;
    }



}
