package implementations.pageObjects;

import abstracts.PageObject;
import implementations.wrappers.Lazy;
import interfaces.pageObjects.DemoSwitcher;
import interfaces.pageObjects.TableDemo;
import interfaces.pageObjects.View;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.JToggleButtonFixture;
import org.pmw.tinylog.Logger;
import utils.ResourceManager;

public class BasicDemoSwitcher extends PageObject implements DemoSwitcher {

    //region Expected Texts
    private final String tableDemoButtonTooltip = ResourceManager.getResString("TableDemo.tooltip");
    //endregion

    //region Components
    private Lazy<JToggleButtonFixture> tableDemoButton = wait.lazy(() ->
            frame.toggleButton(getToggleButtonTooltipMatcher(tableDemoButtonTooltip)));
    //endregion

    public BasicDemoSwitcher(FrameFixture frame, View view) {
        super(frame, view);
    }

    @Override
    public TableDemo goToTableDemo(){
        tableDemoButton.get().click();
        Logger.info(String.format("To to Table Demo"));
        return view.getTableDemo();
    }
}
