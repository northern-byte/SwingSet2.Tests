package utils;

import org.fest.swing.core.BasicComponentFinder;
import org.fest.swing.core.ComponentFinder;
import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.timing.Condition;
import org.fest.swing.timing.Pause;
import org.fest.swing.timing.Timeout;

import javax.swing.*;
import java.util.Objects;

public class ComponentHunter {

    public <T extends JComponent> T obtainByToolTipText(Class<T> supportedType, String expectedText) {
        GenericTypeMatcher<T> matcher = getMatcher(supportedType, expectedText);

        ComponentFinder finder = BasicComponentFinder.finderWithCurrentAwtHierarchy();

        Pause.pause(new Condition("Wait until expected button is there") {
            @Override
            public boolean test() {
                try {
                    finder.find(matcher);
                    return true;
                } catch (ComponentLookupException ex) {

                }
                return false;
            }
        }, Timeout.timeout(2000));

        T res = finder.find(matcher);
        return res;
    }

    private <T extends JComponent> GenericTypeMatcher<T> getMatcher(Class<T> supportedType, String expectedText) {
        return new GenericTypeMatcher<T>(supportedType) {
                @Override
                protected boolean isMatching(T component) {
                    if (Objects.equals(component.getToolTipText(), expectedText)) {
                        return true;
                    }
                    return false;
                }
            };
    }
}
