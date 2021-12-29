package com.example.star;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Map extends AppCompatActivity {
    private MapView         mMapView;
    private MapController   mMapController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Configuration.getInstance().setUserAgentValue(getPackageName());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mMapView = (MapView) findViewById(R.id.mapview);
        mMapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
        mMapView.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.SHOW_AND_FADEOUT);
        mMapController = (MapController) mMapView.getController();
        mMapController.setZoom(13);

        GeoPoint gPt = new GeoPoint(51759445, 19457216);
        mMapController.setCenter(gPt);
    }


}