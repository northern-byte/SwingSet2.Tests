package implementations.pageObjects;

import abstracts.PageObject;
import interfaces.pageObjects.Menu;
import interfaces.pageObjects.View;
import org.fest.swing.fixture.FrameFixture;
import utils.Specification;

public class BasicMenu extends PageObject implements Menu {
    public BasicMenu(FrameFixture frame, View view) {
        super(frame, view);
    }

    protected Specification spec = new Specification();

    public View selectLookAndFeel(String lookAndFeelText) {
        wait.active(() -> frame.menuItemWithPath(spec.get("menu.lookAndFeelMenu").String(), lookAndFeelText)).click();
        return view;
    }
}
