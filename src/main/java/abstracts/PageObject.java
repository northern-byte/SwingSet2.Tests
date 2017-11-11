package abstracts;

import implementations.helpers.WaitHelper;
import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.fixture.FrameFixture;

import javax.swing.*;
import java.util.Objects;
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

    protected GenericTypeMatcher<JCheckBox> getCheckBoxTextMatcher(String expectedText){
        Function<JCheckBox, Boolean> condition = c -> Objects.equals(c.getText(), expectedText);
        return getMatcher(JCheckBox.class, condition);
    }

    protected GenericTypeMatcher<JLabel> getLabelTextMatcher(String expectedText){
        Function<JLabel, Boolean> condition = c -> Objects.equals(c.getText(), expectedText);
        return getMatcher(JLabel.class, condition);
    }

    protected GenericTypeMatcher<JSlider> getSliderAccessNameMatcher(String expectedName){
        Function<JSlider, Boolean> condition = c -> Objects.equals(c.getAccessibleContext().getAccessibleName(), expectedName);
        return getMatcher(JSlider.class, condition);
    }

    protected GenericTypeMatcher<JComboBox> getComboBoxSelectedMatcher(String expectedValue){
        Function<JComboBox, Boolean> condition = c -> Objects.equals(c.getSelectedItem().toString(), expectedValue);
        return getMatcher(JComboBox.class, condition);
    }
}
