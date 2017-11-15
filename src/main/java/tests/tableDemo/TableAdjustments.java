package tests.tableDemo;

import implementations.fixtures.AppletSetupFixture;
import implementations.fixtures.TableDemoPrepareFixture;
import interfaces.fixtures.PrepareFixture;
import interfaces.fixtures.SetupFixture;
import interfaces.pageObjects.TableDemo;
import interfaces.pageObjects.View;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utils.Specification;

import java.awt.*;

public class TableAdjustments {
    private SetupFixture setupFixture = new AppletSetupFixture();
    private PrepareFixture prepairDemo = new TableDemoPrepareFixture();
    private final Specification spec = new Specification();
    private View view;

    @Before
    public void Setup() {
        view = setupFixture.init();
        prepairDemo.prepair(view);
    }

    @After
    public void Close() {
        setupFixture.dispose();
    }

    @Test
    public void IntercellSpacingChangesToMax() {
        TableDemo demo = view.getTableDemo();

        demo.setIntercellSpacingToMax();
        Dimension newSpacing = demo.getTableIntercellSpacing();

        int maxHight = spec.get("tableDemo.intercellMaxHeight").Int();
        int maxHWidth = spec.get("tableDemo.intercellMaxWidth").Int();

        Assert.assertTrue(newSpacing.height == maxHight && newSpacing.width == maxHWidth);
    }

    @Test
    public void IntercellSpacingChangesToMin() {
        TableDemo demo = view.getTableDemo();

        demo.setIntercellSpacingToMin();
        Dimension newSpacing = demo.getTableIntercellSpacing();

        int minHight = spec.get("tableDemo.intercellMinHeight").Int();
        int minHWidth = spec.get("tableDemo.intercellMinWidth").Int();

        Assert.assertTrue(newSpacing.height == minHight && newSpacing.width == minHWidth);
    }

    @Test
    public void IntercellScalesLinearly() {
        TableDemo demo = view.getTableDemo();

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
