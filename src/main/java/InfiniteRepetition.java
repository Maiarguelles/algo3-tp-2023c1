import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class InfiniteRepetition extends Repetition{
    public InfiniteRepetition(FrequencyType frequencyType,int frequency, ArrayList<DayOfWeek> weekDays){ //INFINITE
        this.repeatType = RepeatType.INFINITE;
        this.frequencyType = frequencyType;
        this.frequency = frequency;
        this.weekDays = weekDays;
    }

    public ArrayList<LocalDateTime> showDatesOfEvents(LocalDateTime startDate, LocalDateTime endDate){ //startdate debe coincidir con una fecha del evento mismo
        var dates = new ArrayList<LocalDateTime>();

        if(frequencyType == FrequencyType.DAILY){
            for(; startDate.isBefore(endDate); startDate.plusDays(frequency)){
                dates.add(startDate);
            }
        }

        if(frequencyType == FrequencyType.MONTHLY){
            for(; startDate.isBefore(endDate); startDate.plusMonths(frequency)){
                dates.add(startDate);
            }
        }

        if(frequencyType == FrequencyType.YEARLY){
            for(; startDate.isBefore(endDate); startDate.plusYears(frequency)){
                dates.add(startDate);
            }
        }

        if(frequencyType == FrequencyType.WEEKLY){
            for(; startDate.isBefore(endDate); startDate.plusDays(1)){
                if(weekDays.contains(startDate.getDayOfWeek()))
                    dates.add(startDate);
            }
        }

        return dates;
    }
}