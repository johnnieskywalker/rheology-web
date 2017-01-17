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
        yAxis.setMax(200);
        yAxis.setLabel("Naprężenie (ε)");

        Axis xAxis = lineModel.getAxis(AxisType.X);
        xAxis.setLabel("Odkształcenie (σ)");
    }

    private LineChartModel initLinearModel() {

        return viewsDataController.loadDataFromTableToChart();
//        LineChartModel model = new LineChartModel();

//        LineChartSeries experimentalStressSeries = new LineChartSeries();
//        experimentalStressSeries.setLabel("Naprężenie zmierzone");

//        experimentalStressSeries.set(1, 2);
//        experimentalStressSeries.set(2, 1);
//        experimentalStressSeries.set(3, 3);
//        experimentalStressSeries.set(4, 6);
//        experimentalStressSeries.set(5, 8);

//        LineChartSeries calculatedStressSeries = new LineChartSeries();
//        calculatedStressSeries.setLabel("Naprężenie wyliczone");

//        calculatedStressSeries.set(1, 6);
//        calculatedStressSeries.set(2, 3);
//        calculatedStressSeries.set(3, 2);
//        calculatedStressSeries.set(4, 7);
//        calculatedStressSeries.set(5, 9);

//        model.addSeries(experimentalStressSeries);
//        model.addSeries(calculatedStressSeries);
//
//        return model;
    }

    public void setLineModel(LineChartModel lineModel) {
        this.lineModel = lineModel;
    }

    public LineChartModel getLineModel() {
        return lineModel;
    }
}