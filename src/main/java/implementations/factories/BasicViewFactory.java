package implementations.factories;

import implementations.pageObjects.BasicDemoSwitcher;
import implementations.pageObjects.BasicMenu;
import implementations.pageObjects.BasicTableDemo;
import interfaces.factories.ViewFactory;
import interfaces.pageObjects.DemoSwitcher;
import interfaces.pageObjects.Menu;
import interfaces.pageObjects.TableDemo;
import interfaces.pageObjects.View;
import org.fest.swing.fixture.FrameFixture;

public class BasicViewFactory implements ViewFactory {

    private FrameFixture _frame;
    public BasicViewFactory(FrameFixture frame) {
        _frame = frame;
    }

    @Override
    public Menu createMenu(View view) {
        return new BasicMenu(_frame, view);
    }

    @Override
    public DemoSwitcher createDemoSwitcher(View view) {
        return new BasicDemoSwitcher(_frame, view);
    }

    @Override
    public TableDemo createTableDemo(View view) {
        return new BasicTableDemo(_frame, view);
    }
}