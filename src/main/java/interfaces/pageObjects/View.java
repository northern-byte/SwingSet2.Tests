package interfaces.pageObjects;

public interface View<T extends Demo> {
    Menu getMenu();
    DemoSwitcher getDemoSwitcher();
    T getDemo();
}
