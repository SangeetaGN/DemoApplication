package com.example.demoapplication.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.demoapplication.R;
import com.example.demoapplication.adapter.CustomAdapter;
import com.example.demoapplication.model.ParkingCar;
import com.example.demoapplication.network.GetDataService;
import com.example.demoapplication.network.RetrofitClientInstance;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewBooking extends AppCompatActivity {
    private int totalSlot = 10;
    private  int available = 0;
    private List<ParkingCar> parkingCars;
    private ListView buttonLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_slots);
        ActionBar a = this.getSupportActionBar();
        if(a != null){
            a.hide();
        }
        final TextView tvAvailable = (TextView) findViewById(R.id.available);
        buttonLayout = (ListView) findViewById(R.id.buttonLayout);
        final TextView back = (TextView) findViewById(R.id.back);
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<ParkingCar>> call = service.getAllParkingSlots();
        call.enqueue(new Callback<List<ParkingCar>>() {
            @Override
            public void onResponse(Call<List<ParkingCar>> call, Response<List<ParkingCar>> response) {
                parkingCars = response.body();
                if(parkingCars == null || parkingCars.isEmpty()) {
                    available = 10;
                }else{
                    available = 10 - parkingCars.size();
                    List<ParkingCar> newList = new ArrayList<>();
                    for(ParkingCar p : parkingCars){
                        if(!p.getIsAlloted()){
                            newList.add(p);
                        }
                    }
                    available += newList.size();
                }
                tvAvailable.setText("Available Slots : "+available);
                List<ParkingCar> list =  new ArrayList<>();
                for(int i =1;i<= 10;i++) {
                    boolean entryAvail = false;
                    for (ParkingCar p : parkingCars) {
                        if (p.getSlotNo() == i) {
                            list.add(p);
                            entryAvail = true;
                            break;
                        }
                    }
                    if(!entryAvail){
                        ParkingCar p = new ParkingCar(i,null,null,false);
                        list.add(p);
                    }
                }
                CustomAdapter customAdapter = new CustomAdapter(list,ViewBooking.this);
                buttonLayout.setAdapter(customAdapter);
            }

            @Override
            public void onFailure(Call<List<ParkingCar>> call, Throwable t) {
                available =10;
                tvAvailable.setText("Available Slots : "+available);
                for(int i =1;i<= 10;i++) {
                    Button b = new Button(ViewBooking.this);
                    b.setText("Slot No. - " + i);
                    buttonLayout.addView(b);
                    b.setBackgroundColor(ViewBooking.this.getResources().getColor(R.color.FFDAD4D4));
                    b.setTextColor(ViewBooking.this.getResources().getColor(R.color._000000));
                }
            }
        });

        buttonLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewBooking.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void callRemoveAPI(ParkingCar finalParkingCar) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<Boolean> call = service.remove(String.valueOf(finalParkingCar.getSlotNo()));
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.body()){
                    Toast.makeText(ViewBooking.this,"Removed Vehicle from parking Area",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ViewBooking.this,ViewBooking.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(ViewBooking.this, "Somthing went Wrong,try after some times",Toast.LENGTH_SHORT).show();
            }
        });
    }

}

