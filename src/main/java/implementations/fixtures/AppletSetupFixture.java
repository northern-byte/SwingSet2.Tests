package implementations.fixtures;

import exceptions.FixtureNotInitializedException;
import implementations.factories.BasicViewFactory;
import implementations.pageObjects.DefaultView;
import interfaces.factories.ViewFactory;
import interfaces.fixtures.SetupFixture;
import interfaces.pageObjects.View;
import org.fest.swing.applet.AppletViewer;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.launcher.AppletLauncher;
import org.fest.swing.timing.Condition;
import org.fest.swing.timing.Pause;
import org.fest.swing.timing.Timeout;
import utils.ResourceManager;

public class AppletSetupFixture implements SetupFixture {

    private AppletViewer applet;
    private FrameFixture frame;
    private ViewFactory factory;

    @Override
    public View init() {
        applet = GuiActionRunner.execute(new GuiQuery<AppletViewer>() {
            @Override
            protected AppletViewer executeInEDT() throws Throwable {
                return AppletLauncher.applet("SwingSet2Applet").start();
            }
        });
        frame = new FrameFixture(applet);
        factory = new BasicViewFactory(frame);
        ResourceManager.loadProps();
        return new DefaultView(factory);
    }

    @Override
    public void dispose(){
        try {
            applet.unloadApplet();
            frame.cleanUp();

            Pause.pause(new Condition("Check if applet is active") {
                @Override
                public boolean test() {
                    return applet.isActive();
                }
            }, Timeout.timeout(ResourceManager.getProp("disposeTimeout").Int()));
        }catch (NullPointerException e){
            throw new FixtureNotInitializedException("You need to call init() first to create applet and frame");
        }

    }
}
