package implementations.helpers;

import org.fest.swing.core.ComponentDragAndDrop;
import org.fest.swing.core.Robot;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DragAndDropHelper {

    private final ComponentDragAndDrop dragAndDrop;

    public DragAndDropHelper(Robot robot) {
        this.dragAndDrop = new ComponentDragAndDrop(robot);
    }

    public void drag(JComponent target, Point from) {
        dragAndDrop.drag(target, from);
    }

    public void drop(JComponent target, Point to) {
        dragAndDrop.drop(target, to);
    }

    public void dragAndDrop(JComponent target, Point from, Point to){
        drag(target, from);
        dragOver(target, from, to);
        drop(target, to);
    }

    public void dragAndDrop(JComponent target, Point from, List<Point> to){
        drag(target, from);
        dragOver(target, from, to);
        drop(target, to.get(to.size() - 1));
    }

    public void dragOver(JComponent target, Point from, List<Point> points){
        Point currentFrom = from;
        for(Point to : points){
            dragOver(target, currentFrom, to);
            currentFrom = to;
        }
    }

    public void dragOver(JComponent target, Point from, Point to) {
        Point current = new Point(from.x, from.y);

        int stepX = (to.x - current.x) / 5;
        int stepY = (to.y - current.y) / 5;

        while (Math.abs(current.x - to.x) > Math.abs(stepX) ||
                Math.abs(current.y - to.y) > Math.abs(stepY)){
            if(Math.abs(current.x - to.x) > Math.abs(stepX)){
                current.x += stepX;
            }
            if(Math.abs(current.y - to.y) > Math.abs(stepY)){
                current.y += stepY;
            }
            dragAndDrop.dragOver(target, current);
        }
    }
}
