package abstracts;

import implementations.helpers.WaitHelper;
import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.fixture.FrameFixture;

import javax.swing.*;
import java.util.function.Function;

public abstract class PageObject {
    protected FrameFixture frame;
    protected WaitHelper wait = new WaitHelper();

    protected PageObject(FrameFixture frame){
        this.frame = frame;
    }

    protected <T extends JComponent> GenericTypeMatcher<T> getMatcher(Class<T> supportedType,
                                                                      Function<T, Boolean> condition){
        return new GenericTypeMatcher<T>(supportedType) {
            @Override
            protected boolean isMatching(T component) {
                return condition.apply(component);
            }
        };
    }
}
