import java.time.DayOfWeek;
import java.util.ArrayList;

public class WeeklyStrategy implements FrequencyStrategy{
    private int frequency;
    private ArrayList<DayOfWeek> weekDays;

    public WeeklyStrategy(int frequency, ArrayList<DayOfWeek> weekDays){
        this.frequency = frequency;
        this.weekDays = weekDays;
    }
}
