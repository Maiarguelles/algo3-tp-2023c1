import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class OcurrencesRepetition extends Repetition{
    private final int totalOcurrences;
    private int actualOcurrences;

    public OcurrencesRepetition(FrequencyType frequencyType, int frequency, ArrayList<DayOfWeek> weekDays, int ocurrences){ //BY_OCURRENCES
        this.repeatType = RepeatType.BY_OCURRENCES;
        this.frequencyType = frequencyType;
        this.frequency = frequency;
        this.weekDays = weekDays;
        this.totalOcurrences = ocurrences;
        this.actualOcurrences = ocurrences;
    }

    @Override
    public boolean isRepeating(LocalDateTime date){
    return false;

    }

    private void refreshOcurrences(){
        actualOcurrences = totalOcurrences;
    }
    public void oneLessOcurrence(){
        actualOcurrences = actualOcurrences - 1;
    }

    public  ArrayList<LocalDateTime> showDatesOfEvents(LocalDateTime startDate, LocalDateTime endDate){
        return null;
    }


}
