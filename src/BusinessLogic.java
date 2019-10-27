import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class BusinessLogic {

    static class VehicleLogic {
        final List<String> tollFreeVehicles;

        VehicleLogic(List<String> tollFreeVehicles) {
            this.tollFreeVehicles = tollFreeVehicles;
        }

        boolean isVehicleTollFree(String vehicle) {
            return tollFreeVehicles.contains(vehicle);
        }
    }

    static class DateLogic {
        final List<LocalDate> tollFreeDates;

        DateLogic(List<LocalDate> tollFreeDates) {
            this.tollFreeDates = tollFreeDates;
        }

        boolean isDateTollFree(LocalDate date) {
            return tollFreeDates.contains(date);
        }
    }

    static class TollFeeInterval {
        LocalTime startInclusive;
        LocalTime endExlusive;
        int cost;

        TollFeeInterval(LocalTime start, LocalTime end, int cost) {
            this.startInclusive = start;
            this.endExlusive = end;
            this.cost = cost;
        }
    }
    
    static class TollFeeIntervalLogic {

        final List<TollFeeInterval> feeIntervals;

        TollFeeIntervalLogic(List<TollFeeInterval> feeIntervals) {
            this.feeIntervals = feeIntervals;
        }

        int getTollFee(LocalTime time) {
            for (TollFeeInterval feeInterval : feeIntervals) {
                if (time.equals(feeInterval.startInclusive)) {
                    return feeInterval.cost;
                }
                if (time.isAfter(feeInterval.startInclusive) && time.isBefore(feeInterval.endExlusive)) {
                    return feeInterval.cost;
                }
            }
            return 0;
        }
    }

    static class TollPass {
        final LocalTime passTime;
        final int cost;

        TollPass(LocalTime passTime, int cost) {
            this.passTime = passTime;
            this.cost = cost;
        }
    }

    static class TollFeeSummarizer {
        final long feePeriodInHours;

        TollFeeSummarizer(long feePeriodInHours) {
            this.feePeriodInHours = feePeriodInHours;
        }

        int summarizePasses(List<TollPass> tollPasses) {
            List<TollPass> tmpTollPasses = new ArrayList<>(tollPasses);
            return sum(tmpTollPasses.remove(0), tmpTollPasses);
        }

        private int sum(TollPass first, List<TollPass> rest) {
            if (rest.size() == 0) {
                return first.cost;
            }
            TollPass next = rest.remove(0);
            if (next.passTime.isBefore(first.passTime.plusHours(feePeriodInHours))) {
                return sum(new TollPass(first.passTime, Math.max(first.cost, next.cost)), rest);
            } else {
                return first.cost + sum(next, rest);
            }
        }
    }

    static class TollFeeCapper {
        final int costCap;

        TollFeeCapper(int costCap) {
            this.costCap = costCap;
        }

        int getCappedCost(int cost) {
            return Math.min(costCap, cost);
        }
    }
}