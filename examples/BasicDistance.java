import com.penningtonlabs.lars.LARS;

public final class BasicDistance {
    private final LARS lars = new LARS(0);

    public void periodic() {
        lars.requestDistance();

        if (lars.isMeasurementValid()) {
            double distanceMm = lars.getDistanceMillimeters();
            System.out.println("LARS distance: " + distanceMm + " mm");
        }
    }
}
