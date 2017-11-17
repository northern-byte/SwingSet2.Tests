package implementations.helpers;

import org.fest.swing.driver.JTableHeaderLocation;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.util.Pair;
import org.fest.swing.util.TextMatcher;

import javax.swing.table.JTableHeader;
import java.awt.*;

import static org.fest.swing.driver.ComponentStateValidator.validateIsEnabledAndShowing;
import static org.fest.swing.edt.GuiActionRunner.execute;

public class TableHeaderHelper {
    public Point pointAtName(final JTableHeader tableHeader, final TextMatcher matcher) {
        return execute(new GuiQuery<Point>() {
            protected Point executeInEDT() {
                Pair<Integer, Point> indexAndLocation = getIndexAndLocation(tableHeader, matcher);
                return getPoint(indexAndLocation, tableHeader);
            }
        });
    }

    public Point leftBorder(final JTableHeader tableHeader, final TextMatcher matcher) {
        return execute(new GuiQuery<Point>() {
            @Override
            protected Point executeInEDT() {
                final Integer i = getIndexAndLocation(tableHeader, matcher).i;
                Rectangle r = tableHeader.getHeaderRect(i);
                Point p = new Point(r.x, r.y + r.height / 2);
                Pair<Integer, Point> indexAndLocation = new Pair<>(i, p);
                return getPoint(indexAndLocation, tableHeader);
            }
        });
    }

    public Point rightBorder(final JTableHeader tableHeader, final TextMatcher matcher) {
        return execute(new GuiQuery<Point>() {
            @Override
            protected Point executeInEDT() {
                final Integer i = getIndexAndLocation(tableHeader, matcher).i;
                Rectangle r = tableHeader.getHeaderRect(i);
                Point p = new Point(r.x + r.width, r.y + r.height / 2);
                Pair<Integer, Point> indexAndLocation = new Pair<>(i, p);
                return getPoint(indexAndLocation, tableHeader);
            }
        });
    }

    private Pair<Integer, Point> getIndexAndLocation(JTableHeader tableHeader, TextMatcher matcher) {
        return new JTableHeaderLocation().pointAt(tableHeader, matcher);
    }

    private Point getPoint(Pair<Integer, Point> indexAndLocation, JTableHeader tableHeader) {
        validateIsEnabledAndShowing(tableHeader);
        tableHeader.getTable().scrollRectToVisible(tableHeader.getHeaderRect(indexAndLocation.i));
        return indexAndLocation.ii;
    }
}
