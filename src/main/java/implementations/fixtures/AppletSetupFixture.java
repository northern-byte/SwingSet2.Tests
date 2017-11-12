package implementations.fixtures;

import exceptions.FixtureNotInitializedException;
import implementations.factories.BasicViewFactory;
import implementations.pageObjects.DefaultView;
import interfaces.factories.ViewFactory;
import interfaces.fixtures.SetupFixture;
import interfaces.pageObjects.View;
import org.fest.swing.applet.AppletViewer;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.launcher.AppletLauncher;

public class AppletSetupFixture implements SetupFixture {

    private FrameFixture frame;
    private ViewFactory factory;

    @Override
    public View init() {
        AppletViewer viewer = AppletLauncher.applet("SwingSet2Applet").start();
        frame = new FrameFixture(viewer);
        factory = new BasicViewFactory(frame);
        return new DefaultView(factory);
    }

    @Override
    public void dispose(){
        try {
            frame.close();
        }catch (NullPointerException e){
            throw new FixtureNotInitializedException("You need to call init() first to create applet and frame");
        }

    }
}
