package implementations.helpers;

import org.fest.swing.core.ComponentDragAndDrop;
import org.fest.swing.core.Robot;

import javax.swing.*;
import java.awt.*;

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
        Point current = new Point(from.x, from.y);
        drag(target, from);

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

        drop(target, to);
    }
}
