package com.randazzo.mario.plantwateringapp.activity;

import com.androidplot.xy.BoundaryMode;
import com.randazzo.mario.plantWatering.dto.MeasureDTO;

import java.util.List;

public class TemperaturePlotActivity extends PlotActivity {

    @Override
    protected void setListNumbers(List<MeasureDTO> measures) {

        for (MeasureDTO m : measures) {
            domainLabels.add(m.getDate());
            internal.add(m.getInternalTemperature());
            external.add(m.getExternalTemperature());
        }
    }

    @Override
    protected void setPlotProperties() {
        plot.setTitle("Temperature");
        plot.setRangeBoundaries(-30, 60, BoundaryMode.FIXED);
    }


}
