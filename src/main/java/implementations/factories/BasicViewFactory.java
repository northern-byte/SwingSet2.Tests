package implementations.factories;

import implementations.pageObjects.BasicDemoSwitcher;
import implementations.pageObjects.BasicMenu;
import implementations.pageObjects.BasicTableDemo;
import interfaces.factories.ViewFactory;
import interfaces.pageObjects.DemoSwitcher;
import interfaces.pageObjects.Menu;
import interfaces.pageObjects.TableDemo;

public class BasicViewFactory implements ViewFactory {

    @Override
    public Menu createMenu() {
        return new BasicMenu();
    }

    @Override
    public DemoSwitcher createDemoSwitcher() {
        return new BasicDemoSwitcher();
    }

    @Override
    public TableDemo createTableDemo() {
        return new BasicTableDemo();
    }
}