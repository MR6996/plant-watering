package com.randazzo.mario.plantwateringapp.activity;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.FrameLayout;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.androidplot.xy.CatmullRomInterpolator;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.randazzo.mario.plantWatering.dto.MeasureDTO;
import com.randazzo.mario.plantwateringapp.R;
import com.randazzo.mario.plantwateringapp.fragment.PlantDetailFragment;
import com.randazzo.mario.plantwateringapp.network.NetworkController;
import com.randazzo.mario.plantwateringapp.util.Session;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class PlotActivity extends BaseAuthorizedActivity {

    protected XYPlot plot;
    protected List<Number> internal = new ArrayList<>();
    protected List<Number> external = new ArrayList<>();
    protected List<Date> domainLabels = new ArrayList<>();

    private Long plantId;
    private FrameLayout loadingFrame;
    private FrameLayout plotFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plots);

        plantId = getIntent().getLongExtra(PlantDetailFragment.PLANT_ID_KEY, -1);

        loadingFrame = findViewById(R.id.plots_loading_frame);
        plotFrame = findViewById(R.id.plots_plot_frame);
        plot = findViewById(R.id.plots_plot);

        if (plantId > 0) {
            NetworkController.getInstance(this).addToRequestQueue(new AllMeasureRequest());
        } else
            showOkDialog(getString(R.string.error), "Could not retrieve data for this plant!", true);
    }

    protected abstract void setListNumbers(List<MeasureDTO> measures);

    protected abstract void setPlotProperties();

    private void plotMeasures(List<MeasureDTO> measures) {
        if (measures.size() < 3) {
            showOkDialog(getString(R.string.error), "No enough data!", true);
            return;
        }

        setListNumbers(measures);

        XYSeries internalSeries = new SimpleXYSeries(
                internal, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Internal");
        XYSeries externalSeries = new SimpleXYSeries(
                external, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "External");

        LineAndPointFormatter internalSeriesFormat =
                new LineAndPointFormatter(this, R.xml.internal_line_formatter);

        LineAndPointFormatter externalSeriesFormat =
                new LineAndPointFormatter(this, R.xml.external_line_formatter);

        //Interpolation
        CatmullRomInterpolator.Params interpParams = new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal);
        internalSeriesFormat.setInterpolationParams(interpParams);
        externalSeriesFormat.setInterpolationParams(interpParams);

        plot.addSeries(internalSeries, internalSeriesFormat);
        plot.addSeries(externalSeries, externalSeriesFormat);

        plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setFormat(new Format() {
            @Override
            public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
                int i = Math.round(((Number) obj).floatValue());
                return toAppendTo.append(DateFormat.getDateFormat(getApplicationContext()).format(domainLabels.get(i)));
            }

            @Override
            public Object parseObject(String source, ParsePosition pos) {
                return null;
            }
        });

        setPlotProperties();
    }


    class AllMeasureRequest extends JsonRequest<List<MeasureDTO>> {

        private Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                    @Override
                    public Date deserialize(JsonElement json, Type typeOfT,
                                            JsonDeserializationContext context) throws JsonParseException {
                        return json == null ? null : new Date(json.getAsLong());
                    }
                })
                .create();

        AllMeasureRequest() {
            super(
                    Request.Method.GET,
                    urlServer + "private/measure/" + Long.toString(plantId),
                    null,
                    new Response.Listener<List<MeasureDTO>>() {
                        @Override
                        public void onResponse(List<MeasureDTO> response) {
                            loadingFrame.setVisibility(View.GONE);
                            plotFrame.setVisibility(View.VISIBLE);
                            plotMeasures(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            doOnErrorResponse(error, true);
                        }
                    });
        }

        @Override
        public Map<String, String> getHeaders() {
            Map<String, String> params = new HashMap<>();
            params.put("Authorization", "Token " + Session.getInstance().getSessionToken());
            return params;
        }

        @Override
        protected Response<List<MeasureDTO>> parseNetworkResponse(NetworkResponse response) {
            try {
                String jsonString =
                        new String(
                                response.data,
                                HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
                List<MeasureDTO> responseList = Arrays.asList(gson.fromJson(jsonString, MeasureDTO[].class));
                return Response.success(
                        responseList, HttpHeaderParser.parseCacheHeaders(response));
            } catch (UnsupportedEncodingException | JsonSyntaxException e) {
                return Response.error(new ParseError(e));
            }

        }

    }

}
