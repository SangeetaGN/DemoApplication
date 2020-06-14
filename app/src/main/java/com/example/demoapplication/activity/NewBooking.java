package com.example.demoapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.demoapplication.R;
import com.example.demoapplication.model.ParkingCar;
import com.example.demoapplication.network.GetDataService;
import com.example.demoapplication.network.RetrofitClientInstance;
import com.example.demoapplication.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewBooking extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_booking);
        ActionBar a = this.getSupportActionBar();
        if(a != null){
            a.hide();
        }
        final EditText carNum = (EditText) findViewById(R.id.carnumber);

        final RadioGroup carClr= (RadioGroup) findViewById(R.id.radio_group);
        final Button submit = findViewById(R.id.submit);
        final TextView back = (TextView) findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewBooking.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String carRegNums = carNum.getText().toString();
                if(carRegNums == null || carRegNums.isEmpty() || !StringUtil.checkMatching(carRegNums)){
                    Toast.makeText(NewBooking.this, "Incorrect Registration Number", Toast.LENGTH_SHORT).show();
                    carNum.setText(null);
                    return;
                }
                int selectedId = carClr.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                String color = radioButton.getText().toString();
                allotParkingSlot(carRegNums,color);



            }
        });

    }
    private void allotParkingSlot(String carRegNums, String color) {
        /*Create handle for the RetrofitInstance interface*/
        Map<String,String> reqMap = new HashMap<>();
        reqMap.put("regNo",carRegNums);
        reqMap.put("color",color);

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<ParkingCar> call = service.allotNewparkingslot(reqMap);
        call.enqueue(new Callback<ParkingCar>() {
            @Override
            public void onResponse(Call<ParkingCar> call, Response<ParkingCar> response) {
                Toast.makeText(NewBooking.this,"Your slot Number is = "+ response.body().getSlotNo(),Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFailure(Call<ParkingCar> call, Throwable t) {
                Toast.makeText(NewBooking.this,"Something went wrong, try again",Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}

