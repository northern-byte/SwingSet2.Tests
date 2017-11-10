package implementations.factories;

import implementations.pageObjects.BasicDemoSwitcher;
import implementations.pageObjects.BasicMenu;
import implementations.pageObjects.BasicTableDemo;
import interfaces.factories.ViewFactory;
import interfaces.pageObjects.DemoSwitcher;
import interfaces.pageObjects.Menu;
import interfaces.pageObjects.TableDemo;
import org.fest.swing.fixture.FrameFixture;

public class BasicViewFactory implements ViewFactory {

    FrameFixture frame;

    public BasicViewFactory(FrameFixture frame) {
        this.frame = frame;
    }

    @Override
    public Menu createMenu() {
        return new BasicMenu(frame);
    }

    @Override
    public DemoSwitcher createDemoSwitcher() {
        return new BasicDemoSwitcher(frame);
    }

    @Override
    public TableDemo createTableDemo() {
        return new BasicTableDemo(frame);
    }
}