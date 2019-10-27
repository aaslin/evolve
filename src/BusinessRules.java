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

public class BusinessRules {

    static List<String> getTollFreeVehicles() {
        return Collections.unmodifiableList(
                Arrays.asList("Motorbike", "Tractor", "Emergency", "Diplomat", "Foreign", "Military")
        );
    }

    static List<LocalDate> getTollFreeDates() {
        return Collections.unmodifiableList(
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
    }

    private static List<LocalDate> dates(int year, Month month, IntStream dates) {
        return dates
                .mapToObj(date -> LocalDate.of(year, month, date))
                .collect(Collectors.toList());
    }

    static List<BusinessLogic.TollFeeInterval> getTollFeeIntervals() {
        return Collections.unmodifiableList(
                Arrays.asList(
                        // Making my own assumptions :)
                        new BusinessLogic.TollFeeInterval(LocalTime.of(6, 0), LocalTime.of(6, 30), 8),
                        new BusinessLogic.TollFeeInterval(LocalTime.of(6, 30), LocalTime.of(7, 0), 13),
                        new BusinessLogic.TollFeeInterval(LocalTime.of(7, 0), LocalTime.of(8, 0), 18),
                        new BusinessLogic.TollFeeInterval(LocalTime.of(8, 0), LocalTime.of(8, 30), 13),
                        new BusinessLogic.TollFeeInterval(LocalTime.of(8, 30), LocalTime.of(15, 0), 8),
                        new BusinessLogic.TollFeeInterval(LocalTime.of(15, 0), LocalTime.of(15, 30), 13),
                        new BusinessLogic.TollFeeInterval(LocalTime.of(15, 30), LocalTime.of(17, 0), 18),
                        new BusinessLogic.TollFeeInterval(LocalTime.of(17, 0), LocalTime.of(18, 0), 13),
                        new BusinessLogic.TollFeeInterval(LocalTime.of(18, 0), LocalTime.of(18, 30), 8)
                )
        );
    }
}
