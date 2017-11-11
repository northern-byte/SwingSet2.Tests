package implementations.pageObjects;

import abstracts.PageObject;
import implementations.wrappers.Lazy;
import interfaces.pageObjects.TableDemo;
import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.fixture.*;
import utils.ResourceManager;

import javax.swing.*;
import java.util.Objects;

public class BasicTableDemo extends PageObject implements TableDemo {

    public BasicTableDemo(FrameFixture frame) {
        super(frame);
    }

    //region Matchers
    private final GenericTypeMatcher<JCheckBox> reorderingCheckBoxMatcher = getMatcher(JCheckBox.class,
            c -> Objects.equals(c.getText(), ResourceManager.getString("TableDemo.reordering_allowed")));
    //endregion

    //region Components
    Lazy<JTableFixture> tableView = wait.lazy(() -> frame.table());
    Lazy<JScrollPaneFixture> scrollpane = wait.lazy(() -> frame.scrollPane());

    Lazy<JCheckBoxFixture> isColumnReorderingAllowedCheckBox = wait.lazy(() -> frame.checkBox(reorderingCheckBoxMatcher));
    JCheckBoxFixture showHorizontalLinesCheckBox;
    JCheckBoxFixture showVerticalLinesCheckBox;

    JCheckBoxFixture isColumnSelectionAllowedCheckBox;
    JCheckBoxFixture isRowSelectionAllowedCheckBox;

    JLabelFixture interCellSpacingLabel;
    JLabelFixture rowHeightLabel;

    JSliderFixture interCellSpacingSlider;
    JSliderFixture rowHeightSlider;

    JComboBoxFixture selectionModeComboBox = null;
    JComboBoxFixture resizeModeComboBox = null;

    JLabelFixture headerLabel;
    JLabelFixture footerLabel;

    JTextComponentFixture headerTextField;
    JTextComponentFixture footerTextField;

    JCheckBoxFixture fitWidth;
    JButtonFixture printButton;

    JPanelFixture controlPanel;
    //endregion

    @Override
    public void clickTable() {
        tableView.get().click();
        scrollpane.get().verticalScrollBar().scrollBlockDown(6);
        isColumnReorderingAllowedCheckBox.get().uncheck();
        isColumnReorderingAllowedCheckBox.get().click();
    }
}
