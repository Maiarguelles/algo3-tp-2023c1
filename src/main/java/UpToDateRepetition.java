import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class UpToDateRepetition extends Repetition{
    private final LocalDateTime expirationDate;

    public UpToDateRepetition(FrequencyType frequencyType, int frequency, ArrayList<DayOfWeek> weekDays, LocalDateTime expirationDate){ //BY_DATE
        this.repeatType = RepeatType.BY_DATE;
        this.frequencyType = frequencyType;
        this.frequency = frequency;
        this.weekDays = weekDays;
        this.expirationDate = expirationDate;
    }


    @Override
    public boolean isRepeating() {
        return false;
    }

    public ArrayList<LocalDateTime> showDatesOfEvents(LocalDateTime startDate, LocalDateTime endDate){
        return null;
    }



}
