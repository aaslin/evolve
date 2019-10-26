import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TollCalculator2 {

    class VehicleRule {

        final List<String> tollFreeVehicles = Collections.unmodifiableList(
                Arrays.asList("Motorbike", "Tractor", "Emergency", "Diplomat", "Foreign", "Military")
        );

        boolean isVehicleTollFree(String vehicle) {
            return tollFreeVehicles.contains(vehicle);
        }
    }

    class DateRule {
        final List<LocalDate> tollFreeDates = Collections.unmodifiableList(
                Stream.of(
                        dates(2013, Month.JANUARY, IntStream.of(1)),
                        dates(2013, Month.MARCH, IntStream.of(28, 29)),
                        dates(2013, Month.APRIL, IntStream.of(1, 30)),
                        dates(2013, Month.MAY, IntStream.of(1, 8, 9)),
                        dates(2013, Month.JUNE, IntStream.of(5, 6, 21)),
                        dates(2013, Month.JULY, IntStream.range(1, 31)),
                        dates(2013, Month.NOVEMBER, IntStream.of(1)),
                        dates(2013, Month.DECEMBER, IntStream.of(24, 25, 26, 31))
                        ).flatMap(Collection::stream).collect(Collectors.toList())
        );

        private List<LocalDate> dates(int year, Month month, IntStream dates) {
            return dates
                    .mapToObj(date -> LocalDate.of(year, month, date))
                    .collect(Collectors.toList());
        }

        boolean isDateTollFree(LocalDate date) {
            return tollFreeDates.contains(date);
        }
    }

    class TimeRule {

        class Interval {
            int cost;
            LocalTime start;
            LocalTime end;

            Interval(int cost, LocalTime start, LocalTime end) {
                this.cost = cost;
                this.start = start;
                this.end = end;
            }
        }

        final List<Interval> costInterval = Collections.unmodifiableList(
                Arrays.asList(
                        new Interval(8, LocalTime.of(6, 0), LocalTime.of(6, 30)),

                )
        );


        int getTollFee(LocalTime time) {

        }
    }
}
