package br.com.everson.cerveja;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.media.Image;
import android.os.Bundle;

import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.location.LocationManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class NewPubActivity extends AppCompatActivity{

    private EditText editBeerName;
    private EditText editBeerDesc;
    private RatingBar editBeerRating;
    private Switch editStories;
    private Button submitImg;
    private Button submitLocation;
    private ImageView imgBeer;
    private LocationManager locationMananger;
    private LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pub);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editBeerName = (EditText) findViewById(R.id.editBeerName);
        editBeerDesc = (EditText) findViewById(R.id.editBeerDesc);
        editBeerRating = (RatingBar) findViewById(R.id.editBeerRating);
        editStories = (Switch) findViewById(R.id.editStories);
        submitImg = (Button) findViewById(R.id.submitImg);
        imgBeer = (ImageView) findViewById(R.id.imgBeer);
        submitLocation = (Button) findViewById(R.id.submitLocation);
        locationMananger = (LocationManager) getSystemService(LOCATION_SERVICE);

        submitLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ActivityCompat.checkSelfPermission(NewPubActivity.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(NewPubActivity.this, "Você precisa permitr acesso à localização", Toast.LENGTH_LONG).show();
                    return;
                }
                Location location = locationMananger.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();
                if(location!=null){
                    TextView textLatitude = findViewById(R.id.textLatitude);
                    textLatitude.setText("Latitude: " + String.valueOf(latitude));
                    TextView textLongitude = findViewById(R.id.textLongitude);
                    textLongitude.setText("Longitude: " + String.valueOf(longitude));
                }
            }
        });


        submitImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
        imgBeer.setImageBitmap(bitmap);
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
    }

}
