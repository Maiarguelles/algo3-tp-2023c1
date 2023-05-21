import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Calendar {

    private ArrayList<Reminder> reminders;
    //private Alarm nextAlarm;

    public Calendar(){
        this.reminders = new ArrayList<Reminder>();
    }

    public boolean addReminder(Reminder reminder){
        if(reminder == null)
            return false;

        if(reminder.getID() == -1) {
            setID(reminder);
            reminders.add(reminder);
        }
        else
            reminders.add((int)reminder.getID(), reminder);


        return true;
    }

    public void deleteReminder(int ID){
        var reminder = searchReminder(ID);
            reminders.remove(reminder);
    }

    //hola
    private void setID(Reminder reminder){
        reminder.setID(reminders.size());
    }

    private Alarm getNextAlarm(){
        Alarm alarm = null;
        Alarm nextAlarm = null;
        for (Reminder reminder : reminders) {
            for (int j = 0; j < reminder.getAlarms().size(); j++) {
                alarm = reminder.getAlarms().get(j);
                if (nextAlarm == null)
                    nextAlarm = alarm;
                else if (alarm.getGoOffTime().isBefore(nextAlarm.getGoOffTime()))
                    nextAlarm = alarm;
            }
        }
        return nextAlarm;
    }



    public LocalDateTime nextAlarm(){
        var nextAlarm = getNextAlarm();
        if(nextAlarm != null)
            return nextAlarm.getGoOffTime();
        else
            return null;
    }


    public Reminder searchReminder(long ID){
        Reminder searchedReminder = null;
        for (Reminder existentReminder : reminders) {
            if (existentReminder.getID() == ID) {
                searchedReminder = existentReminder;
                return searchedReminder;
            }
        }
        return searchedReminder;
    }


    public boolean addAlarmToExistentReminder(long ID, Alarm alarm){
        var reminder =  searchReminder(ID);

        if(reminder == null)
            return false;

        reminder.addAlarm(alarm);

        return true;

    }

    private void addAlarms(long ID, ArrayList<Alarm> alarms){
        var reminder =  searchReminder(ID);

        for(int i = 0; i < alarms.size();i++){
            reminder.addAlarm(alarms.get(i));
        }

    }

    //PRE: El ID debe ser de un evento
    public Event addRepetitionByDateToExistentEvent(long ID, LocalDateTime expirationDate, FrequencyStrategy frequencyStrategy){
        var searchedReminder =  (Event) searchReminder(ID);
        if( searchedReminder == null)
            return null;
        var newReminder = searchedReminder.addRepetitionByDate(expirationDate, frequencyStrategy);
        var alarms = searchedReminder.getAlarms();

        deleteReminder((int)ID);
        newReminder.setID(ID);
        addReminder(newReminder);

        addAlarms(ID, alarms);

        return newReminder;
    }

    public Event addOcurrencesRepetitionToExistentEvent(long ID, int ocurrences, FrequencyStrategy frequencyStrategy){
        var searchedReminder = (Event) searchReminder(ID);
        if(searchedReminder == null)
            return null;
        var newReminder = searchedReminder.addOcurrencesRepetition(ocurrences, frequencyStrategy);
        var alarms = searchedReminder.getAlarms();

        deleteReminder((int)ID);
        newReminder.setID(ID);
        addReminder(newReminder);
        addAlarms(ID, alarms);

        return newReminder;
    }

    public Event addInfiniteRepetitionToExistentEvent(long ID, FrequencyStrategy frequencyStrategy) {
        var searchedReminder = (Event) searchReminder(ID);
        if(searchedReminder == null)
            return null;


        var newReminder = searchedReminder.addInfiniteRepetition(frequencyStrategy);

        var alarms = searchedReminder.getAlarms();

        deleteReminder((int)ID);
        newReminder.setID(ID);
        addReminder(newReminder);

        addAlarms(ID, alarms);

        return newReminder;
    }


    public ArrayList<Reminder> remindersBetweenTwoDates(LocalDateTime date1, LocalDateTime date2){
        var reminders = new ArrayList<Reminder>();
        var dates = new ArrayList<LocalDateTime>();
        for (Reminder reminder : this.reminders){
            dates = reminder.showDatesOfReminder(date1, date2);
            if (dates == null)
                continue;
            for (int i = 0; i< dates.size(); i++){
                reminders.add(reminder.repeatReminder(dates.get(i)));

            }
        }
        return reminders;
    }

    public String writeCalendar(String path){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        gsonBuilder.registerTypeAdapter(Reminder.class, new Adapter());
        gsonBuilder.registerTypeAdapter(FrequencyStrategy.class, new Adapter());
        gsonBuilder.registerTypeAdapter(Effect.class, new Adapter());

        if(path == null)
            path = "Calendar.json";

        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {

            Gson gson = gsonBuilder.setPrettyPrinting().create();

            final String representationJson = gson.toJson(this);
            //System.out.println(representationJson);
            out.write(representationJson);
            return representationJson;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Calendar readCalendar(String path){
        try {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());
            gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
            gsonBuilder.registerTypeAdapter(Reminder.class, new Adapter());
            gsonBuilder.registerTypeAdapter(FrequencyStrategy.class, new Adapter());
            gsonBuilder.registerTypeAdapter(Effect.class, new Adapter());
            Gson gson = gsonBuilder.setPrettyPrinting().create();

            if(path == null)
                path = "Calendar.json";
            // create a reader
            Reader reader = Files.newBufferedReader(Paths.get(path));

            // convert a JSON string to a User object
            Calendar user = gson.fromJson(reader,Calendar.class);

            // print user object
            //System.out.println(user);

            // close reader
            reader.close();
            return user;


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}

















