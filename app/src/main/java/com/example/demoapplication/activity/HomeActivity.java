package com.example.demoapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.demoapplication.R;
import com.example.demoapplication.model.ParkingCar;
import com.example.demoapplication.network.GetDataService;
import com.example.demoapplication.network.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ActionBar a = this.getSupportActionBar();
        if(a != null){
            a.hide();
        }
        final Button btnLogout = (Button) findViewById(R.id.logout);
        final Button btnNewBooking = (Button) findViewById(R.id.newbookings);
        final Button btnViewBookings = (Button) findViewById(R.id.viewbookings);
        getParkingSlots();
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnNewBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isSlotsAvailable = checkSlotsAreAvailableOrNot();
                if (isSlotsAvailable) {
                    Intent intent = new Intent(HomeActivity.this, NewBooking.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(HomeActivity.this, "Slots are not Available, Please remove some cars and then try again.", Toast.LENGTH_LONG).show();
                }

            }
        });
        btnViewBookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ViewBooking.class);
                startActivity(intent);
            }
        });
    }

    private boolean checkSlotsAreAvailableOrNot() {
        return count < 10;
    }

    private void getParkingSlots() {
        /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<ParkingCar>> call = service.getAllParkingSlots();
        call.enqueue(new Callback<List<ParkingCar>>() {
            @Override
            public void onResponse(Call<List<ParkingCar>> call, Response<List<ParkingCar>> response) {
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<ParkingCar>> call, Throwable t) {
                count = 0;
            }
        });
    }

    private void generateDataList(List<ParkingCar> body) {
        if(body == null || body.isEmpty()) {
            count =0;
            return;
        }
        List<ParkingCar> newList = new ArrayList<>();
        for(ParkingCar p : body){
            if(!p.getIsAlloted()){
                newList.add(p);
            }
        }
        count = newList.size();
    }

}
