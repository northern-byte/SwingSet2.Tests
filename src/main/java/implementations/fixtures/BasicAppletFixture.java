package implementations.fixtures;

import exceptions.FixtureNotInitializedException;
import implementations.factories.BasicViewFactory;
import implementations.pageObjects.DefaultView;
import interfaces.factories.ViewFactory;
import interfaces.fixtures.Fixture;
import interfaces.pageObjects.View;
import org.fest.swing.applet.AppletViewer;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.launcher.AppletLauncher;

public class BasicAppletFixture implements Fixture{

    private FrameFixture frame;
    private ViewFactory factory;

    @Override
    public View Init() {
        AppletViewer viewer = AppletLauncher.applet("SwingSet2Applet").start();
        frame = new FrameFixture(viewer);
        factory = new BasicViewFactory(frame);
        return new DefaultView(factory);
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
