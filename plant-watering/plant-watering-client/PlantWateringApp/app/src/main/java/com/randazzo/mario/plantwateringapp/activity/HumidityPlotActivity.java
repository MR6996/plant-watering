package com.randazzo.mario.plantwateringapp.activity;

import com.androidplot.xy.BoundaryMode;
import com.randazzo.mario.plantWatering.dto.MeasureDTO;

import java.util.List;

public class HumidityPlotActivity extends PlotActivity {

    @Override
    protected void setListNumbers(List<MeasureDTO> measures) {
        for (MeasureDTO m : measures) {
            domainLabels.add(m.getDate());
            internal.add(m.getInternalHumidity());
            external.add(m.getExternalHumidity());
        }
    }

    @Override
    protected void setPlotProperties() {
        plot.setTitle("Humidity");
        plot.setRangeBoundaries(0, 100, BoundaryMode.FIXED);

    }
}
