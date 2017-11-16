package implementations.pageObjects;

import abstracts.PageObject;
import interfaces.pageObjects.Menu;
import interfaces.pageObjects.View;
import org.fest.swing.fixture.FrameFixture;

public class BasicMenu extends PageObject implements Menu {
    public BasicMenu(FrameFixture frame, View view) {
        super(frame, view);
    }
}
