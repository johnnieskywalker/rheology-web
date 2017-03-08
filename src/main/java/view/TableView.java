package view;

import view.wrappers.TableRowWrapper;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "tableView")
@SessionScoped
public class TableView {

    private List<TableRowWrapper> tableRowWrappers = new ArrayList<>();

    private Double sumMeanSquaredError;

    private String parametersInfo="";

    public List<TableRowWrapper> getTableRowWrappers() {
        return tableRowWrappers;
    }

    public void setTableRowWrappers(List<TableRowWrapper> tableRowWrappers) {
        this.tableRowWrappers = tableRowWrappers;
    }

    public Double getSumMeanSquaredError() {
        return sumMeanSquaredError;
    }

    public void setSumMeanSquaredError(Double sumMeanSquaredError) {
        this.sumMeanSquaredError = sumMeanSquaredError;
    }

    public String getParametersInfo() {
        return parametersInfo;
    }

    public void setParametersInfo(String parametersInfo) {
        this.parametersInfo = parametersInfo;
    }
}
