package implementations.helpers;

import org.fest.swing.core.ComponentDragAndDrop;
import org.fest.swing.core.Robot;
import org.fest.swing.driver.JTableHeaderLocation;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.util.Pair;
import org.fest.swing.util.TextMatcher;

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
                Pair<Integer, Point> indexAndLocation = new JTableHeaderLocation().pointAt(tableHeader, matcher);
                validateIsEnabledAndShowing(tableHeader);
                tableHeader.getTable().scrollRectToVisible(tableHeader.getHeaderRect(indexAndLocation.i));
                return indexAndLocation.ii;
            }
        });
    }

    public void drag(JTableHeader target, TextMatcher matcher){
        dragAndDrop.drag(target, pointAtName(target, matcher));
    }

    public void drop(JTableHeader target, TextMatcher matcher){
        dragAndDrop.drop(target, pointAtName(target, matcher));
    }

    public TextMatcher exactText(String expected){
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

    public TextMatcher containsText(String substring){
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
