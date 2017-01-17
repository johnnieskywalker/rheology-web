package view;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.LineChartModel;
import view.cache.ViewsDataController;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.io.Serializable;

@ManagedBean
public class ChartView implements Serializable {

    private LineChartModel lineModel;

    private FacesContext facesContext;

    private ViewsDataController viewsDataController;

    @PostConstruct
    public void init() {
        facesContext = FacesContext.getCurrentInstance();
        viewsDataController
                = (ViewsDataController) facesContext.getApplication()
                .createValueBinding("#{viewsDataController}").getValue(facesContext);

        createLineModels();
    }

    private void createLineModels() {
        lineModel = loadDataFromTableToChart();
        lineModel.setTitle("ε(σ)");
        lineModel.setLegendPosition("e");
        createAxes();
    }

    private void createAxes() {
        Axis yAxis = lineModel.getAxis(AxisType.Y);
        yAxis.setLabel("Naprężenie (ε)");

        Axis xAxis = lineModel.getAxis(AxisType.X);
        xAxis.setLabel("Odkształcenie (σ)");
    }

    public LineChartModel loadDataFromTableToChart() {

        return viewsDataController.loadDataFromTableToChart();
    }

    public void setLineModel(LineChartModel lineModel) {
        this.lineModel = lineModel;
    }

    public LineChartModel getLineModel() {
        return lineModel;
    }
}