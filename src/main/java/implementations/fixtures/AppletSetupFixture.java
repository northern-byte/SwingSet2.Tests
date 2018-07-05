package implementations.fixtures;

import exceptions.FixtureNotInitializedException;
import exceptions.NoSuchConfigurationException;
import implementations.pageObjects.DefaultView;
import interfaces.factories.ViewFactory;
import interfaces.fixtures.SetupFixture;
import interfaces.pageObjects.View;
import org.fest.reflect.core.Reflection;
import org.fest.reflect.exception.ReflectionError;
import org.fest.swing.applet.AppletViewer;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.launcher.AppletLauncher;
import org.fest.swing.timing.Condition;
import org.fest.swing.timing.Pause;
import org.fest.swing.timing.Timeout;
import org.pmw.tinylog.Logger;
import utils.Platform;
import utils.ResourceManager;

import javax.swing.*;

public class AppletSetupFixture implements SetupFixture {

    private AppletViewer applet;
    private FrameFixture frame;

    @Override
    public View init() {
        ResourceManager.loadBundle();

        resetLookAndFeel();
        applet = GuiActionRunner.execute(new GuiQuery<AppletViewer>() {
            @Override
            protected AppletViewer executeInEDT() throws Throwable {
                return AppletLauncher.applet(Platform.getConfigProp("sutStartClass").asString()).start();
            }
        });
        frame = new FrameFixture(applet);
        frame.robot.settings().delayBetweenEvents(Platform.getConfigProp("delayBetweenEvents").asInt());

        String configurationName = Platform.getConfigProp("configuration").asString();

        ViewFactory factory = getFactory(configurationName);

        ResourceManager.loadConfigProperties();
        ResourceManager.loadSpecificationProperties();
        Logger.info("Loaded properties");
        return new DefaultView(factory);
    }

    private ViewFactory getFactory(String configurationName) {
        ViewFactory factory;

        Class<? extends ViewFactory> factoryClass;
        try {
            factoryClass = Reflection.type(String.format("implementations.factories.%sViewFactory", configurationName)).loadAs(ViewFactory.class);
        } catch (ReflectionError e) {
            throw new NoSuchConfigurationException(String.format("There is no configuration %s", configurationName));
        }

        factory = Reflection.constructor().withParameterTypes(FrameFixture.class).in(factoryClass).newInstance(frame);
        Logger.info(String.format("Created page object factory for configuration: %s", configurationName));
        return factory;
    }

    private void resetLookAndFeel() {
        try {
            String defaultLook;
            if(Platform.getConfigProp("systemLook").asBoolean()){
                defaultLook = UIManager.getSystemLookAndFeelClassName();
            } else {
                defaultLook = UIManager.getCrossPlatformLookAndFeelClassName();
            }
            Logger.info(String.format("Default look : %s", defaultLook));
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
            }, Timeout.timeout(Platform.getConfigProp("disposeTimeout").asInt()));
        }catch (NullPointerException e){
            throw new FixtureNotInitializedException("You need to call init() first to create applet and frame");
        }

    }
}
