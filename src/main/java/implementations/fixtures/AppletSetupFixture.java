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
import utils.Platform;
import utils.ResourceManager;

import javax.swing.*;

public class AppletSetupFixture implements SetupFixture {

    private AppletViewer applet;
    private FrameFixture frame;
    private ViewFactory factory;

    @Override
    public View init() {
        resetLookAndFeel();
        applet = GuiActionRunner.execute(new GuiQuery<AppletViewer>() {
            @Override
            protected AppletViewer executeInEDT() throws Throwable {
                return AppletLauncher.applet(Platform.getConfigProp("sutStartClass").String()).start();
            }
        });
        frame = new FrameFixture(applet);
        frame.robot.settings().delayBetweenEvents(Platform.getConfigProp("delayBetweenEvents").Int());
        factory = new BasicViewFactory(frame);
        ResourceManager.loadConfigProperties();
        ResourceManager.loadSpecificationProperties();
        return new DefaultView(factory);
    }

    private void resetLookAndFeel() {
        try {
            String defaultLook;
            if(Platform.getConfigProp("systemLook").Boolean()){
                defaultLook = UIManager.getSystemLookAndFeelClassName();
            } else {
                defaultLook = UIManager.getCrossPlatformLookAndFeelClassName();
            }
            UIManager.setLookAndFeel(defaultLook);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dispose(){
        try {
            applet.unloadApplet();
            frame.cleanUp();

            Pause.pause(new Condition("Check if applet is active") {
                @Override
                public boolean test() {
                    return !applet.appletLoaded();
                }
            }, Timeout.timeout(Platform.getConfigProp("disposeTimeout").Int()));
        }catch (NullPointerException e){
            throw new FixtureNotInitializedException("You need to call init() first to create applet and frame");
        }

    }
}
