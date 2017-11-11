package abstracts;

import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.JComponentFixture;
import org.fest.swing.timing.Condition;
import org.fest.swing.timing.Pause;
import org.fest.swing.timing.Timeout;
import utils.ResourceManager;

import javax.swing.*;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class PageObject {
    protected FrameFixture frame;

    protected PageObject(FrameFixture frame){
        this.frame = frame;
        InitComponents();
    }

    protected abstract void InitComponents();

    protected <T extends JComponent> GenericTypeMatcher<T> getMatcher(Class<T> supportedType,
                                                                      Function<T, Boolean> condition){
        return new GenericTypeMatcher<T>(supportedType) {
            @Override
            protected boolean isMatching(T component) {
                if (condition.apply(component)) {
                    return true;
                }
                return false;
            }
        };
    }

    protected <T extends JComponentFixture> T wait(Supplier<T> supplier) {
        Pause.pause(new Condition("Wait until expected component is there") {
            @Override
            public boolean test() {
                try {
                    supplier.get();
                    return true;
                } catch (ComponentLookupException ex) {

                }
                return false;
            }
        }, Timeout.timeout(ResourceManager.getPropInt("timeout")));
        return supplier.get();
    }
}
