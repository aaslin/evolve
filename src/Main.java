import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Main {

    public static void main(String[] args) {
        LocalDateTime date = LocalDateTime.of(2019, 1, 1, 0, 0);

        while (date.getYear() == 2019) {
            Date currentDate = Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
            int oldResult = new TollCalculator().getTollFee(currentDate, () -> "Car");
//            int newResult = new TollCalculatorExample().getTollFee(() -> "Car", currentDate);
//            System.out.println(String.format("%s, %d - %d", date, oldResult, newResult));
            date = date.plusMinutes(30);
        }
    }
}
