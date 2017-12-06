package implementations.fixtures;

import exceptions.FixtureNotInitializedException;
import implementations.factories.BasicViewFactory;
import implementations.pageObjects.DefaultView;
import interfaces.factories.ViewFactory;
import interfaces.fixtures.SetupFixture;
import interfaces.pageObjects.View;
import org.fest.swing.applet.AppletViewer;
import org.fest.swing.core.BasicRobot;
import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.core.MouseButton;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.finder.WindowFinder;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.launcher.ApplicationLauncher;
import org.fest.swing.security.ExitException;
import org.fest.swing.security.NoExitSecurityManager;
import org.fest.swing.security.NoExitSecurityManagerInstaller;
import org.fest.swing.timing.Condition;
import org.fest.swing.timing.Pause;
import org.fest.swing.timing.Timeout;
import utils.Platform;
import utils.ResourceManager;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class ApplicationSetupFixture implements SetupFixture {

    private FrameFixture frame;
    private ViewFactory factory;
    private Robot robot;
    private NoExitSecurityManagerInstaller noExitSecurityManagerInstaller;

    @Override
    public View init() {
        ResourceManager.loadBundle();

        robot = BasicRobot.robotWithNewAwtHierarchy();
        noExitSecurityManagerInstaller = NoExitSecurityManagerInstaller.installNoExitSecurityManager();
        GuiActionRunner.execute(new GuiTask() {
            @Override
            protected void executeInEDT() throws Throwable {
                ApplicationLauncher.application("SwingSet2").start();
            }
        });
        frame = WindowFinder.findFrame(new GenericTypeMatcher<Frame>(Frame.class) {
            @Override
            protected boolean isMatching(Frame component) {
                return Objects.equals(component.getTitle(), "SwingSet2");
            }
        }).using(robot);

        frame.show();
        String name = frame.target.getName();
        frame.robot.settings().delayBetweenEvents(Platform.getConfigProp("delayBetweenEvents").Int());
        factory = new BasicViewFactory(frame);
        ResourceManager.loadConfigProperties();
        ResourceManager.loadSpecificationProperties();
        return new DefaultView(factory);
    }

    //TODO Needs work, separate process probably
    @Override
    public void dispose(){
        try {
            frame.target.dispatchEvent(new WindowEvent(frame.target, WindowEvent.WINDOW_CLOSING));
        } catch (ExitException ignored){
            // Expected behaviour
        }
        try {
            Point closePoint = getClosePoint(frame.target);
            frame.robot.click(closePoint, MouseButton.LEFT_BUTTON, 1);

        }catch (NullPointerException e){
            throw new FixtureNotInitializedException("You need to call init() first to create applet and frame");
        } catch (ExitException ignored){
            // Expected behaviour
        }
        finally {
            noExitSecurityManagerInstaller.uninstall();
            frame.cleanUp();
        }
    }

    private Point getClosePoint(Container container) {
        Insets insets = container.getInsets();
        Point location = container.getLocation();
        if (Platform.isMac()) return new Point(location.x + insets.left + 15, location.y + insets.top / 2);
        return new Point(location.x + container.getWidth() - insets.right - 10, location.y + insets.top / 2);
    }
}
