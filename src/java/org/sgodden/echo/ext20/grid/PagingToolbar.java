package org.sgodden.echo.ext20.grid;

import javax.swing.table.TableModel;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import org.sgodden.echo.ext20.Button;
import org.sgodden.echo.ext20.Panel;
import org.sgodden.echo.ext20.TextField;
import org.sgodden.echo.ext20.Toolbar;
import org.sgodden.echo.ext20.ToolbarTextItem;

/**
 * A toolbar which contains paging controls for a grid.
 * @author sgodden
 */
public class PagingToolbar extends Toolbar {

    private Button firstButton;
    private Button previousButton;
    private Button nextButton;
    private Button lastButton;
    private TextField currentPageTextField;
    private ToolbarTextItem totalPagesTextItem;

    private int pageOffset;
    private int pageSize;

    private PagingToolbarClient client;
    private TableModel model;

    PagingToolbar(
            TableModel model,
            int pageSize,
            PagingToolbarClient client) {

        this.pageOffset = 0;
        this.pageSize = pageSize;
        this.client = client;
        this.model = model;

        createComponents();
        setTableModel(model);
        
    }

    private void createComponents() {

        firstButton = new Button();
        add(firstButton);
        firstButton.setIconClass("x-tbar-page-first");

        previousButton = new Button();
        add(previousButton);
        previousButton.setIconClass("x-tbar-page-prev");

        addSeparator();

        addTextItem("Page");

        Panel p = new Panel();
        add(p);

        p.addKeyPressListener("enter", new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                setPage();
            }
        });
        currentPageTextField = new TextField();
        p.add(currentPageTextField);

        addTextItem(" of ");
        totalPagesTextItem = new ToolbarTextItem();
        add(totalPagesTextItem);

        addSeparator();

        nextButton = new Button();
        add(nextButton);
        nextButton.setIconClass("x-tbar-page-next");

        lastButton = new Button();
        add(lastButton);
        lastButton.setIconClass("x-tbar-page-last");

        /*
         * Since we start on the first page, the previous button will
         * be disabled to start with.
         */
        firstButton.setEnabled(false);
        previousButton.setEnabled(false);

        firstButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                setPageOffset(0);
                currentPageTextField.setValue("1");
                previousButton.setEnabled(false);
                firstButton.setEnabled(false);
                nextButton.setEnabled(true);
                lastButton.setEnabled(true);
            }
        });

        previousButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                setPageOffset(pageOffset - pageSize);
                currentPageTextField.setValue(
                        String.valueOf((pageOffset / pageSize) + 1));
                /*
                 * If we're on the first page, disable the previous
                 * button.
                 */
                if (pageOffset == 0) {
                    previousButton.setEnabled(false);
                    firstButton.setEnabled(false);
                }
                nextButton.setEnabled(true);
                lastButton.setEnabled(true);
            }
        });

        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                setPageOffset(pageOffset + pageSize);
                currentPageTextField.setValue(
                        String.valueOf((pageOffset / pageSize) + 1));
                int finalPageOffset = (
                        (model.getRowCount() -1) / pageSize)
                        * pageSize;
                /*
                 * If we're on the last page, disable the next button.
                 */
                if (pageOffset == finalPageOffset) {
                    nextButton.setEnabled(false);
                    lastButton.setEnabled(false);
                }
                previousButton.setEnabled(true);
                firstButton.setEnabled(true);
            }
        });

        lastButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                setPageOffset(
                        ( (model.getRowCount() -1) / pageSize)
                        * pageSize
                    );
                currentPageTextField.setValue(
                        String.valueOf((pageOffset / pageSize) + 1));
                nextButton.setEnabled(false);
                lastButton.setEnabled(false);
                previousButton.setEnabled(true);
                firstButton.setEnabled(true);
            }
        });
    }

    private void setPage() {
        int requestedPage = Integer.parseInt(currentPageTextField.getValue());
        int requestedPageOffset = (requestedPage - 1) * pageSize;
        int maxPageOffset = ( (model.getRowCount() -1) / pageSize)
                        * pageSize;
        if (requestedPageOffset > maxPageOffset) {
            requestedPageOffset = maxPageOffset;
            currentPageTextField.setValue(
                    String.valueOf( (maxPageOffset / pageSize) + 1) ) ;
        }
        setPageOffset(requestedPageOffset);
    }

    private void setPageOffset(int pageOffset) {
        this.pageOffset = pageOffset;
        client.setPageOffset(pageOffset);
    }

    public void setTableModel(TableModel model) {
        this.model = model;
        setPageOffset(0);

        firstButton.setEnabled(false);
        previousButton.setEnabled(false);
        nextButton.setEnabled(true);
        lastButton.setEnabled(true);

        currentPageTextField.setValue("1");

        int totalPages = (model.getRowCount() / pageSize);
        if (model.getRowCount() % pageSize > 0) {
            totalPages++;
        }

        totalPagesTextItem.setText(String.valueOf(totalPages));
    }

}