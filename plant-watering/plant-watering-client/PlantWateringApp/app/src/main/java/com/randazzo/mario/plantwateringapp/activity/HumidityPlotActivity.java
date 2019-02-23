package com.randazzo.mario.plantwateringapp.activity;

import com.randazzo.mario.plantWatering.dto.MeasureDTO;

import java.util.List;

public class HumidityPlotActivity extends PlotActivity {

    @Override
    protected void setListNumbers(List<MeasureDTO> measures) {
        for (MeasureDTO m : measures) {
            internal.add(m.getInternalHumidity());
            external.add(m.getExternalHumidity());
        }
    }

    @Override
    protected void setPlotProperties() {
        plot.setTitle("Humidity");
    }
}
