import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BusinessLogicTest {

    @Test
    public void givenATollFreeVehicle_whenIsVehicleTollFree_thenReturnTrue() {
        String tollFreeVehicle = "TollFreeVehicel";

        BusinessLogic.VehicleLogic vehicleLogic = new BusinessLogic.VehicleLogic(Collections.singletonList(tollFreeVehicle));
        boolean actual = vehicleLogic.isVehicleTollFree(tollFreeVehicle);

        Assert.assertTrue(actual);
    }

    @Test
    public void givenANonTollFreeVehicle_whenIsVehicleTollFree_thenReturnFalse() {
        String tollFreeVehicle = "TollFreeVehicel";
        String nonTollFreeVehicle = "NonTollFreeVehicle";

        BusinessLogic.VehicleLogic vehicleLogic = new BusinessLogic.VehicleLogic(Collections.singletonList(tollFreeVehicle));
        boolean actual = vehicleLogic.isVehicleTollFree(nonTollFreeVehicle);

        Assert.assertFalse(actual);
    }

    @Test
    public void givenATollFreeDate_whenIsDateTollFree_thenReturnTrue() {
        LocalDate tollFreeDate = LocalDate.of(2019, 12, 24);

        BusinessLogic.DateLogic dateLogic = new BusinessLogic.DateLogic(Collections.singletonList(tollFreeDate));
        boolean actual = dateLogic.isDateTollFree(tollFreeDate);

        Assert.assertTrue(actual);
    }

    @Test
    public void givenANonTollFreeDate_whenIsDateTollFree_thenReturnFalse() {
        LocalDate tollFreeDate = LocalDate.of(2019, 12, 24);
        LocalDate nonTollFreeDate = LocalDate.of(2019, 12, 23);

        BusinessLogic.DateLogic dateLogic = new BusinessLogic.DateLogic(Collections.singletonList(tollFreeDate));
        boolean actual = dateLogic.isDateTollFree(nonTollFreeDate);

        Assert.assertFalse(actual);
    }

    @Test
    public void givenATimeInATollFeeIntervalWithCost20_whenGetTollFee_thenReturn20() {
        int expected = 20;
        List<BusinessLogic.TollFeeInterval> intervals = Collections.singletonList(
                new BusinessLogic.TollFeeInterval(
                        LocalTime.of(16, 0),
                        LocalTime.of(17, 30),
                        expected)
        );
        LocalTime timeInTollFeeInterval = LocalTime.of(16, 0);

        BusinessLogic.TollFeeIntervalLogic tollFeeIntervalLogic = new BusinessLogic.TollFeeIntervalLogic(intervals);
        int actual = tollFeeIntervalLogic.getTollFee(timeInTollFeeInterval);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void givenATimeBeforeTheFirstTollFeeInterval_whenGetTollFee_theReturn0() {
        int expected = 0;
        List<BusinessLogic.TollFeeInterval> intervals = Collections.singletonList(
                new BusinessLogic.TollFeeInterval(
                        LocalTime.of(16, 0),
                        LocalTime.of(17, 30),
                        expected)
        );
        LocalTime timeOutsideFeeInterval = LocalTime.of(15, 59);

        BusinessLogic.TollFeeIntervalLogic tollFeeIntervalLogic = new BusinessLogic.TollFeeIntervalLogic(intervals);
        int actual = tollFeeIntervalLogic.getTollFee(timeOutsideFeeInterval);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void givenATimeAfterTheLastTollFeeInterval_whenGetTollFee_theReturn0() {
        int expected = 0;
        List<BusinessLogic.TollFeeInterval> intervals = Collections.singletonList(
                new BusinessLogic.TollFeeInterval(
                        LocalTime.of(16, 0),
                        LocalTime.of(17, 30),
                        expected)
        );
        LocalTime timeOutsideFeeInterval = LocalTime.of(17, 30);

        BusinessLogic.TollFeeIntervalLogic tollFeeIntervalLogic = new BusinessLogic.TollFeeIntervalLogic(intervals);
        int actual = tollFeeIntervalLogic.getTollFee(timeOutsideFeeInterval);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void given2PassesWithinTheSameHour_whenSummarizePasses_thenReturnTheHighestCost() {
        int expected = 20;
        List<BusinessLogic.TollPass> tollPasses = Arrays.asList(
                new BusinessLogic.TollPass(LocalTime.of(15, 31), 20),
                new BusinessLogic.TollPass(LocalTime.of(16, 30), 15)
        );

        BusinessLogic.TollFeeSummarizer tollFeeSummarizer = new BusinessLogic.TollFeeSummarizer(1);
        int actual = tollFeeSummarizer.summarizePasses(tollPasses);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void given2PassesWithinTheSameHourAnd1Pass1HourLater_whenSummarizePasses_thenReturnSumTheHighestCostForEachHour() {
        int expected = 30;
        List<BusinessLogic.TollPass> tollPasses = Arrays.asList(
                new BusinessLogic.TollPass(LocalTime.of(15, 31), 20),
                new BusinessLogic.TollPass(LocalTime.of(16, 30), 15),
                new BusinessLogic.TollPass(LocalTime.of(16, 31), 10)
        );

        BusinessLogic.TollFeeSummarizer tollFeeSummarizer = new BusinessLogic.TollFeeSummarizer(1);
        int actual = tollFeeSummarizer.summarizePasses(tollPasses);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void given2PassesWithinTheSameHourAnd1Pass1HourEarlier_whenSummarizePasses_thenReturnTheSumOfTheHighestCostForEachHour() {
        int expected = 35;
        List<BusinessLogic.TollPass> tollPasses = Arrays.asList(
                new BusinessLogic.TollPass(LocalTime.of(15, 30), 20),
                new BusinessLogic.TollPass(LocalTime.of(16, 30), 15),
                new BusinessLogic.TollPass(LocalTime.of(16, 31), 10)
        );

        BusinessLogic.TollFeeSummarizer tollFeeSummarizer = new BusinessLogic.TollFeeSummarizer(1);
        int actual = tollFeeSummarizer.summarizePasses(tollPasses);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void giventTollFeeBelowCap_whenGetCappedCost_thenReturnInputCost() {
        int expected = 40;

        BusinessLogic.TollFeeCapper tollFeeCapper = new BusinessLogic.TollFeeCapper(50);
        int actual = tollFeeCapper.getCappedCost(expected);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void giventTollFeeAboveCap_whenGetCappedCost_thenReturnCappedCost() {
        int expected = 40;

        BusinessLogic.TollFeeCapper tollFeeCapper = new BusinessLogic.TollFeeCapper(expected);
        int actual = tollFeeCapper.getCappedCost(41);

        Assert.assertEquals(expected, actual);
    }
}