import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class Repetition {

    private final FrequencyType frequencyType;
    private final RepeatType repeatType;
    private final int frequency;
    private final int ocurrences;
    private final ArrayList<DayOfWeek> weekDays;
    private final LocalDateTime expirationDate;

    public Repetition(FrequencyType frequencyType,int frequency, ArrayList<DayOfWeek> weekDays, int ocurrences){ //BY_OCURRENCES
        this.repeatType = RepeatType.BY_OCURRENCES;
        this.frequencyType = frequencyType;
        this.frequency = frequency;
        this.weekDays = weekDays;
        this.ocurrences = ocurrences;
        this.expirationDate = null;

    }

    public Repetition(FrequencyType frequencyType,int frequency, ArrayList<DayOfWeek> weekDays){ //INFINITE
        this.repeatType = RepeatType.INFINITE;
        this.frequencyType = frequencyType;
        this.frequency = frequency;
        this.weekDays = weekDays;
        this.expirationDate = null;
        this.ocurrences = 0;
    }

    public Repetition(FrequencyType frequencyType,int frequency, ArrayList<DayOfWeek> weekDays, LocalDateTime expirationDate){ //BY_DATE
        this.repeatType = RepeatType.BY_DATE;
        this.frequencyType = frequencyType;
        this.frequency = frequency;
        this.weekDays = weekDays;
        this.ocurrences = 0;
        this.expirationDate = expirationDate;
    }




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


