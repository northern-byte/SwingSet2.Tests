package interfaces.factories;

import interfaces.pageObjects.DemoSwitcher;
import interfaces.pageObjects.Menu;
import interfaces.pageObjects.TableDemo;

public interface ViewFactory {
    Menu createMenu();
    DemoSwitcher createDemoSwitcher();
    TableDemo createTableDemo();
}
