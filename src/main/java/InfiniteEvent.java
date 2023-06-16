import java.time.LocalDateTime;
import java.util.ArrayList;

public class InfiniteEvent extends Event{

    public InfiniteEvent(String title, String description, boolean completeDay, LocalDateTime startDate,LocalDateTime endDate){
        super(title, description, startDate, endDate, completeDay);
        this.frequencyStrategy = null;
    }


    @Override
    public boolean equals(Object o){
        if(o.getClass() != InfiniteEvent.class)
            return false;

        if(o == null)
            return false;

        if(this == o)
            return false;

        boolean isEqual = true;

        if(!(this.title.equals(((InfiniteEvent) o).title)))
            isEqual = false;
        if(!(this.description.equals(((InfiniteEvent) o).description)))
            isEqual = false;
        if(!(this.completeDay == ((InfiniteEvent) o).completeDay))
            isEqual = false;
        if(!(this.startDate.equals(((InfiniteEvent) o).startDate)))
            isEqual = false;
        if(!(this.endDate.equals(((InfiniteEvent) o).endDate)))
            isEqual = false;
        if(!(this.alarms.equals(((InfiniteEvent) o).alarms)))
            isEqual = false;
        if (!(this.frequencyStrategy.equals(((InfiniteEvent) o).frequencyStrategy)))
            isEqual = false;

        return isEqual;
    }

    @Override
    public int hashCode(){
        int x = 21;
        if (completeDay){
            x+=56;
        }
        int y = 4;
        for (int i = 0; i<title.length(); i++){
            y+= title.charAt(i);
        }

        int z = 9;
        for (int i = 0; i<description.length(); i++){
            z+= description.charAt(i);
        }

        int date1 = startDate.getMonthValue()*startDate.getDayOfMonth()*startDate.getYear();
        int date2 = endDate.getMonthValue()+endDate.getDayOfMonth()*endDate.getYear();

        return date1*x+date2*y+z;
    }


    @Override
    public void addFrequency(FrequencyStrategy frequencyStrategy){
        this.frequencyStrategy = frequencyStrategy;
    }

    @Override
    public ArrayList<LocalDateTime> showDatesOfReminder(LocalDateTime date1, LocalDateTime date2){
        return frequencyStrategy.showDatesOfEvents(date1, date2, startDate);
    }

}
