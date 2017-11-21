package tests.tableDemo;

import implementations.fixtures.AppletSetupFixture;
import implementations.fixtures.TableDemoPrepareFixture;
import interfaces.fixtures.PrepareFixture;
import interfaces.fixtures.SetupFixture;
import interfaces.pageObjects.TableDemo;
import org.junit.*;
import org.junit.rules.Timeout;
import utils.Platform;
import utils.Specification;

import java.awt.*;

public class TestTableIntercellSpace {
    private final SetupFixture setupFixture = new AppletSetupFixture();
    private final PrepareFixture<TableDemo> prepareDemo = new TableDemoPrepareFixture();
    private final Specification spec = new Specification();
    private TableDemo demo;

    @Rule
    public Timeout globalTimeout = Timeout.millis(Platform.getConfigProp("testTimeout").Int());

    @Before
    public void Setup() {
        demo = prepareDemo.prepair(setupFixture.init());
    }

    @After
    public void Close() {
        setupFixture.dispose();
    }

    /**
     * Go to the Table Demo
     * Change table's inter-cell spacing to maximum via controller
     * Check that actual maximum values of inter-cell spacing correspond to those from specification
     */
    @Test
    public void TestIntercellSpacingChangesToMax() {
        demo.setIntercellSpacingToMax();
        Dimension newSpacing = demo.getTableIntercellSpacing();

        int maxHight = spec.get("tableDemo.intercellMaxHeight").Int();
        int maxHWidth = spec.get("tableDemo.intercellMaxWidth").Int();

        Assert.assertTrue(newSpacing.height == maxHight && newSpacing.width == maxHWidth);
    }

    /**
     * Go to the Table Demo
     * Change table's inter-cell spacing to minimum via controller
     * Check that actual minimum values of inter-cell spacing correspond to those from specification
     */
    @Test
    public void TestIntercellSpacingChangesToMin() {
        demo.setIntercellSpacingToMin();
        Dimension newSpacing = demo.getTableIntercellSpacing();

        int minHight = spec.get("tableDemo.intercellMinHeight").Int();
        int minHWidth = spec.get("tableDemo.intercellMinWidth").Int();

        Assert.assertTrue(newSpacing.height == minHight && newSpacing.width == minHWidth);
    }

    /**
     * Go to the Table Demo
     * Change table's inter-cell spacing to maximum via controller
     * Calculate table's to controller's values ration
     * Get minimum value from controller
     * Decrease inter-cell spacing using controller by a step of one until we hit minimum value
     * At each value, check that table's inter-cell spacing changed in linear manner in comparison to control
     */
    @Test
    public void TestIntercellScalesLinearly() {
        int position = demo.setIntercellSpacingToMax();
        Dimension maxSpacing = demo.getTableIntercellSpacing();
        int heightRatio = maxSpacing.height / position;
        int widthRatio = maxSpacing.width / position;

        int min = demo.getIntercellSpacingMin();
        while (position > min) {
            demo.setIntercellSpacingTo(--position);
            Dimension spacing = demo.getTableIntercellSpacing();
            Assert.assertTrue(spacing.height == position * heightRatio &&
                    spacing.width == position * widthRatio);
        }
    }
}
