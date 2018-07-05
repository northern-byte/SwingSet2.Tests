package interfaces.factories;

import interfaces.pageObjects.DemoSwitcher;
import interfaces.pageObjects.Menu;
import interfaces.pageObjects.TableDemo;
import interfaces.pageObjects.View;

public interface ViewFactory {
    Menu createMenu(View view);
    DemoSwitcher createDemoSwitcher(View view);
    TableDemo createTableDemo(View view);
}
