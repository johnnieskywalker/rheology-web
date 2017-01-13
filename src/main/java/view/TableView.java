package view;

import view.wrappers.TableRowWrapper;

import javax.faces.bean.ManagedBean;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name="tableView")
public class TableView {

    List<TableRowWrapper> tableRowWrappers = new ArrayList<>();


    public List<TableRowWrapper> getTableRowWrappers() {
        return tableRowWrappers;
    }

    public void setTableRowWrappers(List<TableRowWrapper> tableRowWrappers) {
        this.tableRowWrappers = tableRowWrappers;
    }
}
