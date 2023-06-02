import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Calendar {

    private HashMap<Integer, Reminder> reminders;

    public Calendar(){
        this.reminders = new HashMap<Integer, Reminder>();
    }

    public boolean addReminder(Reminder reminder){
        if(reminder == null)
            return false;

        int key = reminder.hashCode();

        if(reminders.putIfAbsent(key,reminder) != null)
            return false;

        return true;
    }
    public void deleteReminder(int ID){
            reminders.remove(ID);
    }


    public Alarm getNextAlarm(LocalDateTime actual){
        Alarm alarm = null;
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

        for(int i = 0; i < alarms.size();i++){
            reminder.addAlarm(alarms.get(i));
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
            for(int i = 0; i < dates.size(); i++){
                toReturn.add(reminder.repeatReminder(dates.get(i)));
            }
        }

        return toReturn;
    }

    public String writeCalendar(String path, Writer out){
        var gsonBuilder = setBuilder();

        if(path == null)
            path = "Calendar.json";

        Gson gson = gsonBuilder.setPrettyPrinting().create();

        final String representationJson = gson.toJson(this);
        try {
            out.write(representationJson);
            return representationJson;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static GsonBuilder setBuilder(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        gsonBuilder.registerTypeAdapter(Reminder.class, new ReminderAdapter());
        gsonBuilder.registerTypeAdapter(FrequencyStrategy.class, new FrequencyAdapter());
        gsonBuilder.registerTypeAdapter(Effect.class, new EffectAdapter());
        gsonBuilder.registerTypeAdapter(HashMap.class, new MapAdapter());
        return gsonBuilder;
    }

    public static Calendar readCalendar(String path, Reader reader){
        try {
            var gsonBuilder = setBuilder();
            Gson gson = gsonBuilder.setPrettyPrinting().create();
            if(path == null)
                path = "Calendar.json";
            // create a reader

            // convert a JSON string to a User object
            Calendar user = gson.fromJson(reader,Calendar.class);
            System.out.println(user);
            // close reader
            reader.close();
            return user;


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}

















