package implementations.pageObjects;

import abstracts.PageObject;
import implementations.wrappers.Lazy;
import interfaces.pageObjects.DemoSwitcher;
import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.JToggleButtonFixture;
import utils.ResourceManager;

import javax.swing.*;
import java.util.Objects;

public class BasicDemoSwitcher extends PageObject implements DemoSwitcher {

    //region Matchers
    private final GenericTypeMatcher<JToggleButton> tableDemoButtonMatcher = getMatcher(JToggleButton.class,
            b -> Objects.equals(b.getToolTipText(), ResourceManager.getString("TableDemo.tooltip")));
    //endregion

    //region Components
    private Lazy<JToggleButtonFixture> tableDemoButton = wait.lazy(() -> frame.toggleButton(tableDemoButtonMatcher));
    //endregion

    public BasicDemoSwitcher(FrameFixture frame) {
        super(frame);
    }

    @Override
    public void goToTableDemo(){
        tableDemoButton.get().click();
    }
}
