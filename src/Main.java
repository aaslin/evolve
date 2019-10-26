import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Main {

    public static void main(String[] args) {
        LocalDateTime date = LocalDateTime.of(2019, 1, 1, 0, 0);

        while (date.getYear() == 2019) {
            System.out.println(String.format("%s, %d", date, new TollCalculator().getTollFee(Date.from(date.atZone(ZoneId.systemDefault()).toInstant()), () -> "Car")));
            date = date.plusMinutes(30);
        }
    }
}
