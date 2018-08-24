package br.com.fabricio.sdkmapasgoogle;

import android.graphics.Color;
import android.provider.CalendarContract;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        final LatLng sydney = new LatLng(-33.862187, 151.271533);


        //mudar exibicao do mapa
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Double lat = latLng.latitude;
                Double longitude = latLng.longitude;

                PolylineOptions polylineOptions = new PolylineOptions();
                polylineOptions.add(sydney);
                polylineOptions.add(latLng);
                polylineOptions.color(Color.GREEN);
                polylineOptions.width(20);

                mMap.addPolyline(polylineOptions);

                mMap.addMarker(new MarkerOptions()
                                .position(latLng)
                                .title("Click")
                                .snippet("Descrição")
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icone_carro_roxo_48px))
                );
            }
        });

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Double lat = latLng.latitude;
                Double longitude = latLng.longitude;

                mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("Long Click")
                        .snippet("Descrição 2")
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.icone_carro_roxo_96px))
                );
            }
        });


        //desenhando formas
//        CircleOptions circleOptions = new CircleOptions();
//        circleOptions.center(sydney);
//        circleOptions.radius(500); //medida em metros
//        circleOptions.fillColor(Color.BLUE);
//        circleOptions.strokeWidth(10); // borda
//        circleOptions.strokeColor(Color.GREEN);
//        mMap.addCircle(circleOptions);

        PolygonOptions polygonOptions = new PolygonOptions();
        polygonOptions.add(new LatLng(-33.862187, 151.271533));
        polygonOptions.add(new LatLng(-33.863681, 151.274618));
        polygonOptions.add(new LatLng(-33.860413, 151.274586));
//        polygonOptions.radius(500); //medida em metros
        polygonOptions.fillColor(Color.BLUE);
        polygonOptions.strokeWidth(10); // borda
        polygonOptions.strokeColor(Color.GREEN);
        mMap.addPolygon(polygonOptions);

        mMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney")
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.icone_loja))
        );
        //zoom com valores de 2.0 a 21.0
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));
    }
}
