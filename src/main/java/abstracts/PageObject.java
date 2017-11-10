package abstracts;

import org.fest.swing.fixture.FrameFixture;
import utils.ComponentHunter;

public abstract class PageObject {
    protected FrameFixture frame;
    protected ComponentHunter hunter;

    protected PageObject(FrameFixture frame){
        this.frame = frame;
        hunter = new ComponentHunter();
        InitComponents();
    }

    protected abstract void InitComponents();
}
