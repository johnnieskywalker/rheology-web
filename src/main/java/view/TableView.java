package view;

import utils.ConstantValues;
import view.wrappers.TableRowWrapper;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "tableView")
@SessionScoped
public class TableView {

    private List<TableRowWrapper> tableRowWrappers = new ArrayList<>();

    private Double optimizedParameterK = ConstantValues.STARTING_K_VALUE;

    private Double optimizedParameterN = ConstantValues.STARTING_N_VALUE;

    private Double sumMeanSquaredError;

    public List<TableRowWrapper> getTableRowWrappers() {
        return tableRowWrappers;
    }

    public void setTableRowWrappers(List<TableRowWrapper> tableRowWrappers) {
        this.tableRowWrappers = tableRowWrappers;
    }

    public Double getOptimizedParameterK() {
        return optimizedParameterK;
    }

    public void setOptimizedParameterK(Double optimizedParameterK) {
        this.optimizedParameterK = optimizedParameterK;
    }

    public Double getOptimizedParameterN() {
        return optimizedParameterN;
    }

    public void setOptimizedParameterN(Double optimizedParameterN) {
        this.optimizedParameterN = optimizedParameterN;
    }

    public Double getSumMeanSquaredError() {
        return sumMeanSquaredError;
    }

    public void setSumMeanSquaredError(Double sumMeanSquaredError) {
        this.sumMeanSquaredError = sumMeanSquaredError;
    }
}
