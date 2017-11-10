package implementations.fixtures;

import exceptions.FixtureNotInitializedException;
import interfaces.fixtures.Fixture;
import org.fest.swing.applet.AppletViewer;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.launcher.AppletLauncher;

public class BasicAppletFixture implements Fixture<FrameFixture>{

    private FrameFixture frame;

    @Override
    public FrameFixture Init() {
        AppletViewer viewer = AppletLauncher.applet("SwingSet2Applet").start();
        frame = new FrameFixture(viewer);
        return frame;
    }

    @Override
    public void Dispose(){
        try {
            frame.close();
        }catch (NullPointerException e){
            throw new FixtureNotInitializedException("You need to call Init() first to create applet and frame");
        }

    }
}
