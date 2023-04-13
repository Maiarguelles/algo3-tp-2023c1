import java.net.InetSocketAddress;
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

    private void updateNextAlarm(ArrayList alarms){}



}
