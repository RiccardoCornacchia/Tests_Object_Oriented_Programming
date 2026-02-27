package unAltroEs1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TimeSheetFactoryImpl implements TimeSheetFactory{

    private abstract class TimeSheetExample implements TimeSheet{

        List<Pair<String, String>> list = new ArrayList<>();

        public TimeSheetExample(List<Pair<String, String>> data){
            this.list = data;
        }

        @Override
        public Set<String> activities() {
            return list.stream().map(pair -> pair.get1()).collect(Collectors.toSet());
        }

        @Override
        public Set<String> days() {
            return list.stream().map(pair -> pair.get2()).collect(Collectors.toSet());
        }

        @Override
        public int getSingleData(String activity, String day) {
            Pair<String, String> pair = new Pair<>(activity, day);
            return (int) list.stream().filter(p -> p.equals(pair)).count();
        }

        @Override
        public abstract boolean isValid();
    }

    public TimeSheet ofRawData(List<Pair<String, String>> data) {
        return new TimeSheetExample(data){

            @Override
            public boolean isValid() {
                return true;
            }
        };
    }

    @Override
    public TimeSheet withBoundsPerActivity(List<Pair<String, String>> data, Map<String, Integer> boundsOnActivities) {
        return new TimeSheetExample(data){
            @Override
            public boolean isValid() {
                for(Map.Entry<String,Integer> entry : boundsOnActivities.entrySet()){
                    int count = 0;
                    count = (int) list.stream().filter(p -> p.get1().equals(entry.getKey())).count();
                    if(count > entry.getValue()){
                        return false;
                    }
                }
                return true;
            }
        };
    }

    @Override
    public TimeSheet withBoundsPerDay(List<Pair<String, String>> data, Map<String, Integer> boundsOnDays) {
        return new TimeSheetExample(data) {
            @Override
            public boolean isValid() {
                for(Map.Entry<String,Integer> entry : boundsOnDays.entrySet()){
                    int count = 0;
                    count = (int) list.stream().filter(p -> p.get2().equals(entry.getKey())).count();
                    if(count > entry.getValue()){
                        return false;
                    }
                }
                return true;
            }  
        };
    }

    @Override
    public TimeSheet withBounds(List<Pair<String, String>> data, Map<String, Integer> boundsOnActivities, Map<String, Integer> boundsOnDays) {
        List<Pair<String, String>> d = data;
        TimeSheet dayL = withBoundsPerDay(d, boundsOnDays);
        TimeSheet actL = withBoundsPerActivity(d, boundsOnActivities);
        return new TimeSheetExample(d){
            @Override
            public boolean isValid() {
                if(dayL.isValid() && actL.isValid()){
                    return true;
                }
                return false;
            }
        };
    }
}
