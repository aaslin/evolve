import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TollCalculatorExample {
    public static final int NO_TOLL_FEE = 0;

    final BusinessLogic.VehicleLogic vehicleLogic;
    final BusinessLogic.DateLogic dateLogic;
    final BusinessLogic.TollFeeIntervalLogic tollFeeIntervalLogic;
    final BusinessLogic.TollFeeSummarizer tollFeeSummarizer;
    final BusinessLogic.TollFeeCapper tollFeeCapper;


    public TollCalculatorExample() {
        this.vehicleLogic = new BusinessLogic.VehicleLogic(BusinessRules.getTollFreeVehicles());
        this.dateLogic = new BusinessLogic.DateLogic(BusinessRules.getTollFreeDates());
        this.tollFeeIntervalLogic = new BusinessLogic.TollFeeIntervalLogic(BusinessRules.getTollFeeIntervals());
        this.tollFeeSummarizer = new BusinessLogic.TollFeeSummarizer(1);
        this.tollFeeCapper = new BusinessLogic.TollFeeCapper(60);
    }

    public int getTollFee(Vehicle vehicle, LocalDate date, LocalTime... times) {
        if (vehicleLogic.isVehicleTollFree(vehicle.getType())) {
            return NO_TOLL_FEE;
        }
        if (dateLogic.isDateTollFree(date)) {
            return NO_TOLL_FEE;
        }

        List<BusinessLogic.TollPass> tollPasses = Stream.of(times)
                .sorted()
                .map(time -> new BusinessLogic.TollPass(time, tollFeeIntervalLogic.getTollFee(time)))
                .collect(Collectors.toList());

        int totalTollFee = tollFeeSummarizer.summarizePasses(tollPasses);

        return tollFeeCapper.getCappedCost(totalTollFee);
    }
}
