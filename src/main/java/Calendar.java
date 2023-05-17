import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Calendar {

    private ArrayList<Reminder> reminders;
    private Alarm nextAlarm;

    public Calendar(){
        this.reminders = new ArrayList<Reminder>();
        nextAlarm = null;
    }

    public boolean addReminder(Reminder reminder){
        if(reminder == null)
            return false;
        reminders.add(reminder);


        var alarms = reminder.getAlarms();
        if(alarms != null)
            updateNextAlarm();
        return true;
    }

    public void deleteReminder(Reminder reminder){
        if(reminder.getAlarms().contains(nextAlarm)){
            nextAlarm = null;
            reminders.remove(reminder);
            updateNextAlarm();
        }
        else
            reminders.remove(reminder);
    }


    private void updateNextAlarm(){
        Alarm alarm = null;
        for (Reminder reminder : reminders) {
            for (int j = 0; j < reminder.getAlarms().size(); j++) {
                alarm = reminder.getAlarms().get(j);
                if (nextAlarm == null)
                    nextAlarm = alarm;
                else if (alarm.getGoOffTime().isBefore(nextAlarm.getGoOffTime()))
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


    public boolean searchReminder(Reminder reminder){
        Reminder searchedReminder = null;
        for (Reminder existentReminder : reminders) {
            if (existentReminder.equals(reminder)) {
                searchedReminder = existentReminder;
                return true;
            }
        }
        return false;
    }


    public boolean addAlarmToExistentReminder(Reminder reminder, Alarm alarm){
        if(reminder == null)
            return false;

        if(searchReminder(reminder)) {
            reminder.addAlarm(alarm);
            updateNextAlarm();
            return true;
        }

        return false;
    }

    //PRE: El ID debe ser de un evento

    public Event addRepetitionByDateToExistentEvent(long ID, LocalDateTime expirationDate, FrequencyStrategy frequencyStrategy){
        var searchedReminder =  (Event) searchReminder(ID);
        if( searchedReminder == null)
            return null;
        var newReminder = searchedReminder.addRepetitionByDate(expirationDate, frequencyStrategy);
        reminders.remove(ID);
        reminders.add(newReminder);
        return newReminder;
    }

    public Event addOcurrencesRepetitionToExistentEvent(Event event, int ocurrences, FrequencyStrategy frequencyStrategy){
        if(!searchReminder(event))
            return null;
        var newReminder = event.addOcurrencesRepetition(ocurrences, frequencyStrategy);
        reminders.remove(event);
        reminders.add(newReminder);
        return newReminder;
    }

    public Event addInfiniteRepetitionToExistentEvent(long ID, FrequencyStrategy frequencyStrategy) {
        var searchedReminder = (Event) searchReminder(ID);
        if(searchedReminder == null)
            return null;

        var newReminder = searchedReminder.addInfiniteRepetition(frequencyStrategy);
        reminders.remove(searchedReminder);
        reminders.add(newReminder);
        return newReminder;
    }


    public boolean existReminder(LocalDate date){
        Reminder reminder = null;
        for (Reminder value : reminders) {
            reminder = value;
            if (date.isEqual(reminder.getStartDate().toLocalDate()))
                return true;
        }
        return false;
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

    public void serialize(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        gsonBuilder.registerTypeAdapter(Reminder.class, new Adapter());

        //String path = "/src/main/prueba.json";

        try (PrintWriter out = new PrintWriter(new FileWriter("text.json"))) {

            Gson gson = gsonBuilder.setPrettyPrinting().create();
            final String representationJson = gson.toJson(this);
            System.out.println(representationJson);
            out.write(representationJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Calendar deSerialize(){
        try {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());
            gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
            gsonBuilder.registerTypeAdapter(Reminder.class, new Adapter());
            Gson gson = gsonBuilder.setPrettyPrinting().create();
            // create a reader
            Reader reader = Files.newBufferedReader(Paths.get("text.json"));

            // convert a JSON string to a User object
            Calendar user = gson.fromJson(reader,Calendar.class);

            // print user object
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

















