package implementations.pageObjects;

import abstracts.PageObject;
import interfaces.pageObjects.Menu;
import org.fest.swing.fixture.FrameFixture;

public class BasicMenu extends PageObject implements Menu {
    public BasicMenu(FrameFixture frame) {
        this.frame = frame;
    }
}
