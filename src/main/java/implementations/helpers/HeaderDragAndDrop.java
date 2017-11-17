package implementations.helpers;

import org.fest.swing.core.ComponentDragAndDrop;
import org.fest.swing.core.Robot;
import org.fest.swing.driver.JTableHeaderLocation;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.util.Pair;
import org.fest.swing.util.TextMatcher;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.Objects;

import static org.fest.swing.driver.ComponentStateValidator.validateIsEnabledAndShowing;
import static org.fest.swing.edt.GuiActionRunner.execute;

public class HeaderDragAndDrop {

    private final ComponentDragAndDrop dragAndDrop;

    public HeaderDragAndDrop(Robot robot) {
        this.dragAndDrop = new ComponentDragAndDrop(robot);
    }

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

    public void drag(JComponent target, Point from) {
        dragAndDrop.drag(target, from);
    }

    public void drop(JComponent target, Point to) {
        dragAndDrop.drop(target, to);
    }

    public void dragAndDrop(JComponent target, Point from, Point to){
        drag(target, from);
        Point current = from;
        int stepX = (to.x - from.x) / 5;
        int stepY = (to.y - from.y) / 5;
        while (current.x < to.x - stepX || current.y < to.y - stepY){
            if(current.x < to.x - stepX){
                current.x += stepX;
            }
            if(current.y < to.y - stepY){
                current.y += stepY;
            }
            dragAndDrop.dragOver(target, current);
        }
        drop(target, to);
    }

    public TextMatcher exactText(String expected) {
        return new TextMatcher() {
            @Override
            public boolean isMatching(String text) {
                return Objects.equals(text, expected);
            }

            @Override
            public String description() {
                return "Text must be the same";
            }

            @Override
            public String formattedValues() {
                return null;
            }
        };
    }

    public TextMatcher containsText(String substring) {
        return new TextMatcher() {
            @Override
            public boolean isMatching(String text) {
                return text.contains(substring);
            }

            @Override
            public String description() {
                return "Text must contain a substring";
            }

            @Override
            public String formattedValues() {
                return null;
            }
        };
    }
}
