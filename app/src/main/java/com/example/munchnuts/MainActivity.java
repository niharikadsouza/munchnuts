package com.example.munchnuts;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.munchnuts.R.id.Offerlists;
import static com.example.munchnuts.R.id.mapping;

public class MainActivity<FusedLocationProviderClient> extends AppCompatActivity implements OnMapReadyCallback ,View.OnClickListener{



    private GoogleMap map;private ListView list;
    Location currentLocation;
    com.google.android.gms.location.FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    public View v;
    public ListView listView;
    ArrayList<Offersavailable> dataSet;
    public View convertView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        Button one = (Button) findViewById(mapping);
        one.setOnClickListener((View.OnClickListener) this);
        finish();
        Button two = (Button) findViewById(Offerlists);
        two.setOnClickListener((View.OnClickListener) this);
        finish();

    }

    private void displayofferlists() {
        ListView simpleListView;
        String[] OfferName={"diwali","New year"};
        int[] OfferImages={R.drawable.discount,R.drawable.price_tag};

         setContentView(R.layout.activity_main);
            simpleListView=(ListView)findViewById(R.id.simpleListView);

            ArrayList<HashMap<String,String>> arrayList=new ArrayList<>();
            for (int i=0;i<OfferName.length;i++)
            {
                HashMap<String,String> hashMap=new HashMap<>();//create a hashmap to store the data in key value pair
                hashMap.put("name",OfferName[i]);
                hashMap.put("image",OfferImages[i]+"");
                arrayList.add(hashMap);//add the hashmap into arrayList
            }
            String[] from={"name","image"};//string array
            int[] to={R.id.name,R.id.image};//int array of views id's
            OfflerListAdapter simpleAdapter=new OfflerListAdapter(this,arrayList,R.layout.offerlist,from,to);//Create object and set the parameters for simpleAdapter
            simpleListView.setAdapter((ListAdapter) simpleAdapter);//sets the adapter for listView

            //perform listView item click event
            simpleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(getApplicationContext(),OfferName[i],Toast.LENGTH_LONG).show();//show the selected image in toast according to position
                }
            });


    }

    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            {
                ActivityCompat.requestPermissions(this, new
                        String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE); return; }

        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>()
        { public void onSuccess(Location location) {
            if (location != null) { currentLocation = location;
                Toast.makeText(getApplicationContext(), currentLocation.getLatitude() + "" +
                        currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                SupportMapFragment supportMapFragment = (SupportMapFragment)
                        getSupportFragmentManager().findFragmentById(R.id.map);
                assert supportMapFragment != null;
                supportMapFragment.getMapAsync(MainActivity.this);
            }
        }
        });
    }
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            

            @Override
            public void onMapClick(LatLng latLng) {

                // Creating a marker
                MarkerOptions markerOptions = new MarkerOptions();

                // Setting the position for the marker
                markerOptions.position(latLng);

                // Setting the title for the marker.
                // This will be displayed on taping the marker
                markerOptions.title(latLng.latitude + " : " + latLng.longitude);

                // Clears the previously touched position
                googleMap.clear();

                // Animating to the touched position
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                // Placing a marker on the touched position
                googleMap.addMarker(markerOptions);
            }
        });
    }


        /*LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("I am here!");
        GoogleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        GoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5));
        GoogleMap.addMarker(markerOptions);
    }*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) { switch (requestCode)
    { case REQUEST_CODE:
        if (grantResults.length > 0 && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED) {
            fetchLocation();
        } break;
    }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mapping:
                setContentView(R.layout.mapsactivity);
                fetchLocation();
                break;
            case R.id.Offerlists:
                setContentView(R.layout.offerlist);
                displayofferlists();
                break;

            default:
                break;
        }

    }
}




