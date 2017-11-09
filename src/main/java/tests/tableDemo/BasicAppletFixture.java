package tests.tableDemo;

import interfaces.fixtures.Fixture;
import org.fest.swing.applet.AppletViewer;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.launcher.AppletLauncher;

class BasicAppletFixture implements Fixture<FrameFixture>{

    private FrameFixture frame;

    @Override
    public FrameFixture Init() {
        AppletViewer viewer = AppletLauncher.applet("SwingSet2Applet").start();
        frame = new FrameFixture(viewer);
        return frame;
    }

    @Override
    public void Dispose() {
        frame.close();
    }
}
