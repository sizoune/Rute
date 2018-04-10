package idev.com.drawline;

import android.Manifest;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.EntryXComparator;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import idev.com.drawline.Model.Building;
import idev.com.drawline.Model.Corridor;
import idev.com.drawline.Model.Room;

public class MainActivity extends AppCompatActivity {

    private LineChart chart;
    private CustomView customView;
    Application application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chart = findViewById(R.id.chart);

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        application = new Application(MainActivity.this);
                        Toast.makeText(MainActivity.this, "tadaaaa", Toast.LENGTH_SHORT).show();
                        buildRoute1Building();
//                        buildRoute();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(MainActivity.this, "Ijin membaca file tidak diberikan !", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();
    }

    private void buildRoute() {
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        ArrayList<Entry> jalur = new ArrayList<>();
        for (int i = 0; i < application.getRoomlist().size(); i++) {
            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            ArrayList<Entry> garis = new ArrayList<>();
            float y = Float.valueOf(String.valueOf(application.getRoomlist().get(i).getLongi()));
            float x = Float.valueOf(String.valueOf(application.getRoomlist().get(i).getLati()));
            garis.add(new Entry(x, y, application.getRoomlist().get(i).getRoom_name()));
            jalur.add(new Entry(x, y, application.getRoomlist().get(i).getRoom_name()));
            LineDataSet dataset = new LineDataSet(garis, application.getRoomlist().get(i).getRoom_name());
            dataset.setCircleColor(color);
            dataset.setColor(color);
            dataset.setDrawValues(false);
            dataSets.add(dataset);
        }
        LineDataSet datasetjalur = new LineDataSet(jalur, "");
        datasetjalur.setDrawValues(false);
        datasetjalur.setDrawCircles(false);
        dataSets.add(datasetjalur);
        ArrayList<Entry> garis = new ArrayList<>();
        for (Corridor corridor : application.getCorridorlist()) {
            float y = Float.valueOf(String.valueOf(corridor.getLongi()));
            float x = Float.valueOf(String.valueOf(corridor.getLati()));
            garis.add(new Entry(x, y));
        }
//                        chart.getXAxis().setEnabled(false);
//                        chart.getAxisLeft().setEnabled(false);
//                        chart.getAxisRight().setEnabled(false);
        LineDataSet datasetcoridor = new LineDataSet(garis, "");
        datasetcoridor.setColor(Color.BLACK);
        datasetcoridor.setDrawValues(false);
        dataSets.add(datasetcoridor);
        chart.setData(new LineData(dataSets));
        chart.invalidate();
        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                try {
                    Toast.makeText(MainActivity.this, e.getData().toString(), Toast.LENGTH_SHORT).show();
                } catch (NullPointerException raono) {

                }
            }

            @Override
            public void onNothingSelected() {

            }
        });
        Description description = new Description();
        description.setText("");
        chart.setGridBackgroundColor(128);
        chart.setBorderColor(255);
        chart.getAxisRight().setEnabled(false);
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setEnabled(false);
        chart.setDrawGridBackground(true);
        chart.getAxisRight().setDrawLabels(false);
        chart.getAxisLeft().setDrawLabels(false);
        chart.getLegend().setEnabled(false);
        chart.setPinchZoom(false);
        chart.setDescription(description);
        chart.setDoubleTapToZoomEnabled(false);
        chart.getXAxis().setEnabled(false);
        chart.setDrawGridBackground(true);//enable this too
        chart.getXAxis().setDrawGridLines(true);//enable for grid line
        chart.invalidate();
    }

    private void buildRoute1Building() {
        Building if1 = application.getBuildingbyID("IF-1");
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        ArrayList<Entry> koridor = new ArrayList<>();

        Collections.sort(if1.getRoom(), new Comparator<Room>() {
            @Override
            public int compare(Room room, Room t1) {
                if (room.getLongi() > t1.getLongi())
                    return -1;
                else if (room.getLongi() < t1.getLongi())
                    return 1;
                return 0;
            }
        });
        System.out.println("sebelum sort");
        for (Corridor corridor : if1.getCorridor()) {
            System.out.println("longitude : " + corridor.getLongi());
        }
        Collections.sort(if1.getCorridor(), new Comparator<Corridor>() {
            @Override
            public int compare(Corridor corridor, Corridor t1) {
                if (corridor.getLongi() > t1.getLongi())
                    return -1;
                else if (corridor.getLongi() < t1.getLongi())
                    return 1;
                return 0;
            }
        });
        System.out.println("sesudah sort");
        for (Corridor corridor : if1.getCorridor()) {
            System.out.println("longitude : " + corridor.getLongi());
        }
        ArrayList<Entry> jalur = new ArrayList<>();
        for (Room room1 : if1.getRoom()) {
            ArrayList<Entry> room = new ArrayList<>();
            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            float y = Float.valueOf(String.valueOf(room1.getLongi()));
            float x = Float.valueOf(String.valueOf(room1.getLati()));
            room.add(new Entry(x, y, room1.getRoom_name()));
            jalur.add(new Entry(x, y, room1.getRoom_name()));
            LineDataSet dataset = new LineDataSet(room, room1.getRoom_name());
            dataset.setCircleColor(color);
            dataset.setColor(color);
            dataset.setDrawValues(false);
            dataSets.add(dataset);
        }
        LineDataSet datasetjalur = new LineDataSet(jalur, "");
        datasetjalur.setDrawValues(false);
        datasetjalur.setDrawCircles(false);
        dataSets.add(datasetjalur);
        ArrayList<Entry> garis = new ArrayList<>();
        for (Corridor corridor : if1.getCorridor()) {
            float y = Float.valueOf(String.valueOf(corridor.getLongi()));
            float x = Float.valueOf(String.valueOf(corridor.getLati()));
            garis.add(new Entry(x, y));
        }
        LineDataSet datasetcoridor = new LineDataSet(garis, "");
        datasetcoridor.setColor(Color.BLACK);
        datasetcoridor.setDrawValues(false);
        dataSets.add(datasetcoridor);
        chart.setData(new LineData(dataSets));
        chart.invalidate();
        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                try {
                    Toast.makeText(MainActivity.this, e.getData().toString(), Toast.LENGTH_SHORT).show();
                } catch (NullPointerException raono) {

                }
            }

            @Override
            public void onNothingSelected() {

            }
        });
        Description description = new Description();
        description.setText("");
        chart.setGridBackgroundColor(128);
        chart.setBorderColor(255);
        chart.getAxisRight().setEnabled(false);
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setEnabled(false);
        chart.setDrawGridBackground(true);
        chart.getAxisRight().setDrawLabels(false);
        chart.getAxisLeft().setDrawLabels(false);
        chart.getLegend().setEnabled(false);
        chart.setPinchZoom(false);
        chart.setDescription(description);
        chart.setDoubleTapToZoomEnabled(false);
        chart.getXAxis().setEnabled(false);
        chart.setDrawGridBackground(true);//enable this too
        chart.getXAxis().setDrawGridLines(true);//enable for grid line
        chart.invalidate();
    }
}
