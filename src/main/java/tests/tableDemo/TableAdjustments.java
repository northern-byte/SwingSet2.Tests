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

import java.awt.*;

public class TableAdjustments {
    SetupFixture setupFixture = new AppletSetupFixture();
    PrepareFixture prepairDemo = new TableDemoPrepareFixture();
    View view;


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

        Dimension originalSpacing = demo.getTableIntercellSpacing();

        demo.setIntercellSpacingToMax();
        Dimension newSpacing = demo.getTableIntercellSpacing();

        Assert.assertTrue(originalSpacing.height < newSpacing.height
                && originalSpacing.width < newSpacing.width);

        Assert.assertTrue(newSpacing.height == 10 && newSpacing.width == 10);
    }

    @Test
    public void IntercellSpacingChangesToMin() {
        TableDemo demo = view.getTableDemo();

        Dimension originalSpacing = demo.getTableIntercellSpacing();

        demo.setIntercellSpacingToMin();
        Dimension newSpacing = demo.getTableIntercellSpacing();

        Assert.assertTrue(originalSpacing.height > newSpacing.height
                && originalSpacing.width > newSpacing.width);

        Assert.assertTrue(newSpacing.height == 0 && newSpacing.width == 0);
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
