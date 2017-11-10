package implementations.pageObjects;

import abstracts.PageObject;
import interfaces.pageObjects.TableDemo;
import org.fest.swing.fixture.FrameFixture;

public class BasicTableDemo extends PageObject implements TableDemo{

    public BasicTableDemo(FrameFixture frame) {
        super(frame);
    }

    @Override
    protected void InitComponents() {

    }
}
