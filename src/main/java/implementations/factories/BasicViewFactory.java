package implementations.factories;

import implementations.pageObjects.BasicDemoSwitcher;
import implementations.pageObjects.BasicMenu;
import implementations.pageObjects.BasicTableDemo;
import implementations.pageObjects.DefaultView;
import interfaces.factories.ViewFactory;
import interfaces.pageObjects.DemoSwitcher;
import interfaces.pageObjects.Menu;
import interfaces.pageObjects.TableDemo;
import interfaces.pageObjects.View;
import org.fest.swing.fixture.FrameFixture;

public class BasicViewFactory implements ViewFactory {

    private FrameFixture frame;
    private View view = new DefaultView(this);

    public BasicViewFactory(FrameFixture frame) {
        this.frame = frame;
    }

    @Override
    public Menu createMenu() {
        return new BasicMenu(frame, view);
    }

    @Override
    public DemoSwitcher createDemoSwitcher() {
        return new BasicDemoSwitcher(frame, view);
    }

    @Override
    public TableDemo createTableDemo() {
        return new BasicTableDemo(frame, view);
    }
}