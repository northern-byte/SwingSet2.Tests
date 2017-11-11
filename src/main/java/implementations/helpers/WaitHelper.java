package implementations.helpers;

import implementations.wrappers.Lazy;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.fixture.JComponentFixture;
import org.fest.swing.timing.Condition;
import org.fest.swing.timing.Pause;
import org.fest.swing.timing.Timeout;
import utils.ResourceManager;

import java.util.function.Supplier;

public class WaitHelper {
    public <T extends JComponentFixture> Lazy<T> lazy(Supplier<T> supplier) {
        return new Lazy<>(() -> active(supplier));
    }

    public <T extends JComponentFixture> T active(Supplier<T> supplier) {
        Pause.pause(new Condition("Wait until expected component is there") {
            @Override
            public boolean test() {
                try {
                    supplier.get();
                    return true;
                } catch (ComponentLookupException ignored) {

                }
                return false;
            }
        }, Timeout.timeout(ResourceManager.getPropInt("timeout")));
        return supplier.get();
    }
}
