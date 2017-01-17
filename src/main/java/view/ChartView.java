package view;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.LineChartModel;
import view.cache.ViewsDataController;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;

@ManagedBean
@SessionScoped
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
        lineModel = initLinearModel();
        lineModel.setTitle("ε(σ)");
        lineModel.setLegendPosition("e");
        Axis yAxis = lineModel.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(140);
        yAxis.setLabel("Naprężenie (ε)");

        Axis xAxis = lineModel.getAxis(AxisType.X);
        xAxis.setLabel("Odkształcenie (σ)");
    }

    private LineChartModel initLinearModel() {

        return viewsDataController.loadDataFromTableToChart();
    }

    public void setLineModel(LineChartModel lineModel) {
        this.lineModel = lineModel;
    }

    public LineChartModel getLineModel() {
        return lineModel;
    }
}