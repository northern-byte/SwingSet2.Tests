package implementations.factories;

import implementations.pageObjects.BasicDemoSwitcher;
import implementations.pageObjects.BasicMenu;
import implementations.pageObjects.HeadlessTableDemo;
import interfaces.factories.ViewFactory;
import interfaces.pageObjects.DemoSwitcher;
import interfaces.pageObjects.Menu;
import interfaces.pageObjects.TableDemo;
import interfaces.pageObjects.View;
import org.fest.swing.fixture.FrameFixture;
import utils.Platform;

public class HeadlessViewFactory implements ViewFactory {

    private final int _maxWindowWidth;
    private final int _maxWindowHeight;
    private FrameFixture _frame;

    public HeadlessViewFactory(FrameFixture frame) {
        _frame = frame;
        _maxWindowWidth = Platform.getConfigProp("maxWindowWidth").asInt();
        _maxWindowHeight = Platform.getConfigProp("maxWindowHeight").asInt();
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
        return new HeadlessTableDemo(_frame, view, _maxWindowWidth, _maxWindowHeight);
    }
}