package implementations.pageObjects;

import abstracts.PageObject;
import interfaces.pageObjects.DemoSwitcher;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.JToggleButtonFixture;
import utils.ResourceManager;

import javax.swing.*;

public class BasicDemoSwitcher extends PageObject implements DemoSwitcher {

    JToggleButtonFixture tableDemoButton;

    public BasicDemoSwitcher(FrameFixture frame) {
        super(frame);
    }

    @Override
    protected void InitComponents() {
        tableDemoButton = new JToggleButtonFixture(frame.robot, hunter.obtainByToolTipText(JToggleButton.class,
                ResourceManager.getString("TableDemo.tooltip")));
    }

    @Override
    public void goToTableDemo(){
        tableDemoButton.click();
    }
}
