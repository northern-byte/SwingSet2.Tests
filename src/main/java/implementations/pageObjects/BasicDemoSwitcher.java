package implementations.pageObjects;

import abstracts.PageObject;
import interfaces.pageObjects.DemoSwitcher;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.JToggleButtonFixture;
import utils.ResourceManager;

import javax.swing.*;
import java.util.Objects;

public class BasicDemoSwitcher extends PageObject implements DemoSwitcher {

    JToggleButtonFixture tableDemoButton;

    public BasicDemoSwitcher(FrameFixture frame) {
        super(frame);
    }

    @Override
    protected void InitComponents() {
        tableDemoButton = wait(() -> frame.toggleButton(getMatcher(JToggleButton.class,
                b -> Objects.equals(b.getToolTipText(), ResourceManager.getString("TableDemo.tooltip")))));
    }

    @Override
    public void goToTableDemo(){
        tableDemoButton.click();
    }
}
