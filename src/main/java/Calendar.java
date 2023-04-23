import java.net.InetSocketAddress;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Timer;

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


    private Reminder searchReminder(Reminder reminder){
        Reminder searchedReminder = null;
        for (Reminder existentReminder : reminders) {
            if (existentReminder.equals(reminder)) {
                searchedReminder = existentReminder;
                break;
            }
        }
        return searchedReminder;
    }



    public boolean addAlarmToExistentReminder(Reminder reminder, Alarm alarm){
        if(reminder == null)
            return false;

        Reminder existentReminder = searchReminder(reminder);

        if(existentReminder != null) {
            existentReminder.addAlarm(alarm);
            updateNextAlarm();
            return true;
        }

        return false;
    }

    public void changeReminderTitle(Reminder reminder, String name){
        searchReminder(reminder).setTitle(name);
    }

    public void changeReminderDescription(Reminder reminder, String description){
        searchReminder(reminder).setDescription(description);
    }

    public void changeReminderCompleteDay(Reminder reminder, LocalDateTime newEndDate){
        searchReminder(reminder).changeCompleteDay(newEndDate);
    }

    public void makeReminderCompleteDay(Reminder reminder){
        searchReminder(reminder).makeReminderCompleteDay();
    }


    public void addRepetitionByDate(Reminder reminder, LocalDateTime expirationDate, FrequencyStrategy frequencyStrategy){
        var newReminder = searchReminder(reminder).addRepetitionByDate(reminder, expirationDate, frequencyStrategy);
        reminders.remove(reminder);
        reminders.add(newReminder);
    }

    public void addOcurrencesRepetition(Reminder reminder, int ocurrences, FrequencyStrategy frequencyStrategy){
        var newReminder = searchReminder(reminder).addOcurrencesRepetition(reminder, ocurrences, frequencyStrategy);
        reminders.remove(reminder);
        reminders.add(newReminder);
    }

    public void addInfiniteRepetition(Reminder reminder, FrequencyStrategy frequencyStrategy){
        var newReminder = searchReminder(reminder).addInfiniteRepetition(reminder, frequencyStrategy);
        reminders.remove(reminder);
        reminders.add(newReminder);
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
        for (Reminder reminder : reminders){
            dates = reminder.showDatesOfReminder(date1, date2);
            for (int i = 0; i< dates.size(); i++){
                reminders.add(reminder.repeatReminder(dates.get(i)));
            }
        }
        return reminders;
    }


}

















