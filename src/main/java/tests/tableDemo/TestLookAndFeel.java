package tests.tableDemo;

import implementations.fixtures.AppletSetupFixture;
import implementations.fixtures.TableDemoPrepareFixture;
import interfaces.fixtures.PrepareFixture;
import interfaces.fixtures.SetupFixture;
import interfaces.pageObjects.Menu;
import interfaces.pageObjects.TableDemo;
import interfaces.pageObjects.View;
import org.junit.*;
import org.junit.rules.Timeout;
import org.pmw.tinylog.Logger;
import utils.Platform;
import utils.Specification;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class TestLookAndFeel {
    private final SetupFixture setupFixture = new AppletSetupFixture();
    private final PrepareFixture<TableDemo> prepareDemo = new TableDemoPrepareFixture();
    private View view;

    @Rule
    public Timeout globalTimeout = Timeout.millis(Platform.getConfigProp("testTimeout").Int());

    @Before
    public void Setup() {
        view = setupFixture.init();
        prepareDemo.prepair(view);
    }

    @After
    public void Close() {
        setupFixture.dispose();
    }

    /**
     * Go to Table Demo
     * Get all specification names from config
     * Change to different looks in original listed order
     * Change to different looks in reverse of listed order
     * Check that they apply special behaviour to Table Demo(e.g. color of selection in a table)
     */
    @Test
    public void TestSwitchLookAndFeel() {
        Collection<String> specifications = Specification.getSpecifications();
        TableDemo demo = view.getTableDemo();
        Menu menu = view.getMenu();

        Consumer<Collection<String>> checkDefaults = (looks) -> {
            for (String look : looks) {
                Logger.info(look);
                Specification spec = new Specification(look);
                menu.selectLookAndFeel(spec.get("menu.lookAndFeel").String());

                Color gridColor = demo.getGridColor();
                Color actualColor = demo.getCellBackgroundColor(0, 0);
                boolean showGridByDefault = demo.horizontalLinesEnabled() || demo.verticalLinesEnabled();

                Assert.assertEquals(spec.get("tableDemo.showGrid").Boolean(), showGridByDefault);
                Assert.assertEquals(spec.get("tableDemo.gridColor").Color().getRGB(), gridColor.getRGB());
                Assert.assertEquals(spec.get("tableDemo.selectionColor").Color().getRGB(), actualColor.getRGB());
            }
        };

        demo.selectRows(0);

        List<String> original = new ArrayList<>(specifications);
        List<String> reversed = new ArrayList<>(specifications);
        Collections.reverse(reversed);

        checkDefaults.accept(original);
        checkDefaults.accept(reversed);
    }
}
