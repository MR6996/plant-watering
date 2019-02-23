package com.randazzo.mario.plantwateringapp.activity;

import com.randazzo.mario.plantWatering.dto.MeasureDTO;

import java.util.List;

public class TemperaturePlotActivity extends PlotActivity {

    @Override
    protected void setListNumbers(List<MeasureDTO> measures) {

        for (MeasureDTO m : measures) {
            internal.add(m.getInternalTemperature());
            external.add(m.getExternalTemperature());
        }
    }

    @Override
    protected void setPlotProperties() {
        plot.setTitle("Temperature");
    }


}
