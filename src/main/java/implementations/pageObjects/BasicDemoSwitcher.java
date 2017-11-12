package implementations.pageObjects;

import abstracts.PageObject;
import implementations.wrappers.Lazy;
import interfaces.pageObjects.DemoSwitcher;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.JToggleButtonFixture;
import utils.ResourceManager;

public class BasicDemoSwitcher extends PageObject implements DemoSwitcher {

    //region Expected Texts
    private final String tableDemoButtonTooltip = ResourceManager.getResString("TableDemo.tooltip");
    //endregion

    //region Components
    private Lazy<JToggleButtonFixture> tableDemoButton = wait.lazy(() ->
            frame.toggleButton(getToggleButtonTooltipMatcher(tableDemoButtonTooltip)));
    //endregion

    public BasicDemoSwitcher(FrameFixture frame) {
        super(frame);
    }

    @Override
    public void goToTableDemo(){
        tableDemoButton.get().click();
    }
}
