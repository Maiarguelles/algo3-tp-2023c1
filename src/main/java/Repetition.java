import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public abstract class  Repetition {

    protected  FrequencyType frequencyType;
    protected  int frequency;
    protected  ArrayList<DayOfWeek> weekDays;

    protected RepeatType repeatType;


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


