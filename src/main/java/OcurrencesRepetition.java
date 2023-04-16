import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class OcurrencesRepetition extends Repetition{
    private final int ocurrences;

    public OcurrencesRepetition(FrequencyType frequencyType, int frequency, ArrayList<DayOfWeek> weekDays, int ocurrences){ //BY_OCURRENCES
        this.repeatType = RepeatType.BY_OCURRENCES;
        this.frequencyType = frequencyType;
        this.frequency = frequency;
        this.weekDays = weekDays;
        this.ocurrences = ocurrences;
    }

    @Override
    public boolean isRepeating(){
        return ocurrences != 0;
    }

    public  ArrayList<LocalDateTime> showDatesOfEvents(LocalDateTime startDate, LocalDateTime endDate){
        return null;
    }


}
