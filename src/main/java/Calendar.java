import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.Reader;
import java.io.Writer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Calendar {

    private final HashMap<Integer, Reminder> reminders;

    public Calendar(){
        this.reminders = new HashMap<>();
    }

    public boolean addReminder(Reminder reminder){
        if(reminder == null)
            return false;

        int key = reminder.hashCode();
        return reminders.putIfAbsent(key, reminder) == null;
    }




    public void deleteReminder(int ID){
        System.out.println("holaa");
        System.out.println(reminders.remove(ID));
    }


    public Alarm getNextAlarm(LocalDateTime actual){
        Alarm alarm;
        Alarm nextAlarm = null;
        for (Reminder reminder : reminders.values()){
            for (int j = 0; j < reminder.getAlarms().size(); j++) {
                alarm = reminder.getAlarms().get(j);
                if (nextAlarm == null ||(alarm.getGoOffTime().isBefore(nextAlarm.getGoOffTime()) && alarm.getGoOffTime().isAfter(actual)))
                    nextAlarm = alarm;
            }
        }
        return nextAlarm;
    }

    public ArrayList<Reminder> getListOfReminder(){
        return reminders.values().stream().collect(Collectors.toCollection(ArrayList::new));
    }

    public Reminder getReminder(int ID){
        return reminders.get(ID);
    }

    public int getID(Reminder reminder){
        return reminder.hashCode();
    }

    public void addAlarmToExistentReminder(int ID, Alarm alarm){
        var reminder = getReminder(ID);
        reminder.addAlarm(alarm);
    }

    private void addAlarms(int ID, ArrayList<Alarm> alarms){
        var reminder =  getReminder(ID);

        for (Alarm alarm : alarms) {
            reminder.addAlarm(alarm);
        }

    }

    private void replaceReminder(Reminder newReminder,int ID){
        reminders.replace(ID, newReminder);
        var alarms = newReminder.getAlarms();
        addAlarms(ID, alarms);
    }

    //PRE: El ID debe ser de un evento
    public Event addRepetitionByDateToExistentEvent(int ID, LocalDateTime expirationDate, FrequencyStrategy frequencyStrategy){
        var searchedReminder =  (Event) getReminder(ID);
        if( searchedReminder == null)
            return null;
        var newReminder = searchedReminder.addRepetitionByDate(expirationDate, frequencyStrategy);
        replaceReminder(newReminder, ID);
        return newReminder;
    }

    public Event addOcurrencesRepetitionToExistentEvent(int ID, int ocurrences, FrequencyStrategy frequencyStrategy){
        var searchedReminder = (Event) getReminder(ID);
        if(searchedReminder == null)
            return null;
        var newReminder = searchedReminder.addOcurrencesRepetition(ocurrences, frequencyStrategy);
        replaceReminder(newReminder, ID);

        return newReminder;
    }

    public Event addInfiniteRepetitionToExistentEvent(int ID, FrequencyStrategy frequencyStrategy) {
        var searchedReminder = (Event) getReminder(ID);
        if(searchedReminder == null)
            return null;

        var newReminder = searchedReminder.addInfiniteRepetition(frequencyStrategy);

        replaceReminder(newReminder, ID);

        return newReminder;
    }


    public ArrayList<Reminder> remindersBetweenTwoDates(LocalDateTime date1, LocalDateTime date2){
        var toReturn = new ArrayList<Reminder>();
        var dates = new ArrayList<LocalDateTime>();

        for(Reminder reminder : reminders.values()){
            dates = reminder.showDatesOfReminder(date1, date2);
            if (dates == null)
                continue;
            for (LocalDateTime date : dates) {
                toReturn.add(reminder.repeatReminder(date));
            }
        }

        return toReturn;
    }

    public String writeCalendar(Writer out) throws Exception{
        var gsonBuilder = setBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        final String representationJson = gson.toJson(this);
        out.write(representationJson);
        return representationJson;
    }

    public static Calendar readCalendar(Reader reader) throws RuntimeException{
        var gsonBuilder = setBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();

        return gson.fromJson(reader,Calendar.class);

    }

    private static GsonBuilder setBuilder(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        gsonBuilder.registerTypeAdapter(Reminder.class, new ReminderAdapter());
        gsonBuilder.registerTypeAdapter(FrequencyStrategy.class, new FrequencyAdapter());
        gsonBuilder.registerTypeAdapter(Effect.class, new EffectAdapter());
        return gsonBuilder;
    }
}

















