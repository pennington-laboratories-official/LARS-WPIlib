import com.penningtonlabs.lars.LARS;
import com.penningtonlabs.lars.LARSMode;

public final class LimitSwitch {
    private final LARS lars = new LARS(0);

    public LimitSwitch() {
        lars.setMode(LARSMode.LIMIT);
    }

    public void periodic() {
        lars.requestLimitState();

        if (lars.isForwardLimitHit()) {
            // Stop or constrain the mechanism.
        }

        if (lars.isReverseLimitHit()) {
            // Stop or constrain the mechanism.
        }
    }
}
