package implementations.pageObjects;

import interfaces.pageObjects.View;
import org.fest.swing.fixture.FrameFixture;
import java.awt.*;

public class HeadlessTableDemo extends BasicTableDemo {

    private Dimension _maxScreenSize;

    public HeadlessTableDemo(FrameFixture frame, View view, int _maxWindowWidth, int _maxWindowHeight) {
        super(frame, view);
        _maxScreenSize = new Dimension(_maxWindowWidth, _maxWindowHeight);
    }

    @Override
    public void maximizeWindow() {
        super.resizeWindowTo(_maxScreenSize);
    }
}
