package com.smartgps.activities.navigation;

import java.util.Arrays;
import java.util.List;

import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MyLocationOverlay;
import org.osmdroid.views.overlay.PathOverlay;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;

import com.smartgps.R;
import com.smartgps.activities.BaseActivity;
import com.smartgps.models.SmartDestinationModel;
import com.smartgps.models.api.APINode;

public class SmartNavigationActivity extends BaseActivity{

	public static final String NODES = "nodes";
	public static final String TRAVEL_DATA = "travel_data";
	
	private MapView mapView;
	private MapController mapController;
	private MyLocationOverlay myLocationOverlay;
	private PathOverlay myPath;
	private List<APINode> nodes;
	private SmartDestinationModel travelData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_smart_navigation);
		setActionbarTitle(getString(R.string.smart_navigation));

		initData();
		initUI();
	}
	
	private void initData()
	{
		String nodeString = getIntent().getExtras().getString(NODES);
		nodes =  Arrays.asList(gson.fromJson(nodeString,APINode[].class));
		
		String travelDataString = getIntent().getExtras().getString(TRAVEL_DATA);
		travelData = gson.fromJson(travelDataString, SmartDestinationModel.class);
	}

	private void initUI(){
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true); 
        
        mapController = (MapController) mapView.getController();
        mapController.setZoom(18);
        
        myLocationOverlay = new MyLocationOverlay(this, mapView);
        myLocationOverlay.enableFollowLocation();
        myLocationOverlay.enableMyLocation();
        myLocationOverlay.setDrawAccuracyEnabled(true);
        myLocationOverlay.runOnFirstFix(new Runnable() {
        	
        public void run() {
                mapController.animateTo(new GeoPoint(travelData.getLatitude(), travelData.getLongitude()));
            }
        });
        mapView.getOverlays().add(myLocationOverlay);
        
        getNewPathOverlay();
        setGeoPoints();
        
        mapView.getOverlays().add(myPath);
        
        Marker startMarker = new Marker(mapView);
        startMarker.setPosition(new GeoPoint(travelData.getLatitude(), travelData.getLongitude()));
        startMarker.setAnchor(Marker.ANCHOR_CENTER, 1.0f);
        startMarker.setTitle(travelData.getAddress());

        Marker endMarker = new Marker(mapView);
        endMarker.setPosition(new GeoPoint(travelData.getDestinationLatitude(), travelData.getDestinationLongitude()));
        endMarker.setAnchor(Marker.ANCHOR_CENTER, 1.0f);
        endMarker.setTitle(travelData.getDestinationAddress());
        
        mapView.getOverlays().add(endMarker);
        mapView.getOverlays().add(startMarker);
        mapView.invalidate();
	}
	
	private void setGeoPoints(){
		APINode node;
		for(int i = 0; i< nodes.size(); i++){
			node = nodes.get(i);
			myPath.addPoint(new GeoPoint(node.getLatitude(), node.getLongitude()));
		}
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		
		myLocationOverlay.disableMyLocation();
		myLocationOverlay.disableCompass();
		myLocationOverlay.disableFollowLocation();
	}
	
	/**
	 * Initialize path overlay
	 */
	private void getNewPathOverlay(){
		mapView.getOverlays().remove(myPath);
		myPath = new PathOverlay(Color.RED, this);
		Paint p = new Paint();
		p.setColor(Color.parseColor("#E84E10"));
		p.setStyle(Paint.Style.STROKE);
		p.setStrokeWidth(10);
		myPath.setPaint(p);
	}
}
