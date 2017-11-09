package tests;

import org.fest.swing.applet.AppletViewer;
import org.fest.swing.core.BasicComponentFinder;
import org.fest.swing.core.ComponentFinder;
import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.JToggleButtonFixture;
import org.fest.swing.launcher.AppletLauncher;
import org.fest.swing.timing.Condition;
import org.fest.swing.timing.Pause;
import org.fest.swing.timing.Timeout;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.util.Objects;

public class ReorderingColumns {
    FrameFixture applet;
    AppletViewer viewer;

    @Before
    public void Setup() {
        viewer = AppletLauncher.applet("SwingSet2Applet").start();
        applet = new FrameFixture(viewer);
        applet.show();
    }

    @Test //Prototype
    public void DragColumnsLeft(){
        String expected = "JTable demo";

        GenericTypeMatcher<JToggleButton> toggleButtonMatcher = new GenericTypeMatcher<JToggleButton>(JToggleButton.class) {
            @Override
            protected boolean isMatching(JToggleButton component) {
                if(Objects.equals(component.getToolTipText(), expected)) {
                    return true;
                }
                return false;
            }
        };
        ComponentFinder finder = BasicComponentFinder.finderWithCurrentAwtHierarchy();
        Pause.pause(new Condition("Wait until expected button is there") {
            @Override
            public boolean test() {
                try {
                    finder.find(toggleButtonMatcher);
                    return true;
                }catch (ComponentLookupException ex){

                }
                return false;
            }
        }, Timeout.timeout(2000));

        JToggleButtonFixture button = new JToggleButtonFixture(applet.robot, finder.find(toggleButtonMatcher));
        button.click();
    }
}
