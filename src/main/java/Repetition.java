import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public abstract class  Repetition {
    protected  LocalDateTime beginDate;
    protected  FrequencyType frequencyType;
    protected  int frequency;
    protected  ArrayList<DayOfWeek> weekDays;

    public FrequencyType getFrequencyType() {
        return frequencyType;
    }

    public int getFrequency() {
        return frequency;
    }

    public ArrayList<DayOfWeek> getWeekDays() {
        return weekDays;
    }

    public RepeatType getRepeatType() {
        return repeatType;
    }

    protected RepeatType repeatType;

    public abstract boolean isRepeating(LocalDateTime date);

    public abstract ArrayList<LocalDateTime> showDatesOfEvents(LocalDateTime startDate, LocalDateTime endDate); //da las fechas del evento entre el startDate y expirationDate


    public enum FrequencyType{
        DAILY,
        WEEKLY,
        MONTHLY,
        YEARLY
    }

    public enum RepeatType{
        INFINITE,
        BY_OCURRENCES,
        BY_DATE
    }
}


