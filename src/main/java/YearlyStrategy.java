import java.time.LocalDateTime;
import java.util.ArrayList;

public class YearlyStrategy implements FrequencyStrategy{

    @Override
    public ArrayList<LocalDateTime> showDatesOfEvents(LocalDateTime date1, LocalDateTime date2, LocalDateTime eventDate){
        if(eventDate.isAfter(date2))
            return null;

        var dates = new ArrayList<LocalDateTime>();

        LocalDateTime repetition  = getFirstRepetitionWithinTwoDates(date1, date2, eventDate);

        while(repetition.isBefore(date2)){
            dates.add(repetition);
            repetition = repetition.plusYears(1);
        }

        return dates;

    }

    @Override
    public LocalDateTime lastDateWithOcurrences(int ocurrences, LocalDateTime starDate) {
        LocalDateTime lastPossibleDay = null;
        for (int i=0; i<ocurrences; i++){
            lastPossibleDay =starDate.plusYears(1);
        }
        return lastPossibleDay;
    }

    @Override
    public LocalDateTime getFirstRepetitionWithinTwoDates(LocalDateTime date1, LocalDateTime date2, LocalDateTime startDate) {
        LocalDateTime firstRepetition = startDate;

        while(firstRepetition.isBefore(date1)) {
            firstRepetition = firstRepetition.plusYears(1);
        }

        return firstRepetition;
    }

}
