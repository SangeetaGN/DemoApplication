package com.example.demoapplication.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.demoapplication.R;
import com.example.demoapplication.activity.ViewBooking;
import com.example.demoapplication.model.ParkingCar;
import com.example.demoapplication.network.GetDataService;
import com.example.demoapplication.network.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomAdapter extends ArrayAdapter<ParkingCar> implements View.OnClickListener{

    private List<ParkingCar> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        Button button;

    }

    public CustomAdapter(List<ParkingCar> data, Context context) {
        super(context, R.layout.row_item, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {
        int pos =(int)v.getTag();
        if(dataSet != null && !dataSet.isEmpty()){
            boolean isAllotedSlot = false;
            ParkingCar parkingCar = new ParkingCar();
            for(ParkingCar p:dataSet){
                if(p.getSlotNo() == pos+1){
                    if(p.getIsAlloted()){
                        parkingCar = p;
                        isAllotedSlot = true;
                    }
                }
            }
            if(isAllotedSlot){
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                String title = "This Slot id alloted for Car Number - "+ parkingCar.getRegNo();
                String message = "Do you want to remove this allotment";
                if (title != null)
                    builder.setTitle(title);

                builder.setMessage(message);
                final ParkingCar finalParkingCar = parkingCar;
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        callRemoveAPI(finalParkingCar);
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.show();
            }else{
                Toast.makeText(mContext,"Empty Slot",Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(mContext,"Empty Slot",Toast.LENGTH_SHORT).show();
        }
    }
    private void callRemoveAPI(ParkingCar finalParkingCar) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<Boolean> call = service.remove(String.valueOf(finalParkingCar.getSlotNo()));
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.body()){
                    Toast.makeText(mContext,"Removed Vehicle from parking Area",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext,ViewBooking.class);
                    mContext.startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(mContext, "Somthing went Wrong,try after some times",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ParkingCar parkingCar = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.button = (Button) convertView.findViewById(R.id.b);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        lastPosition = position;
        if(parkingCar.getIsAlloted()){
            viewHolder.button.setBackground(mContext.getResources().getDrawable(R.drawable.slot_design));
            viewHolder.button.setTextColor(mContext.getResources().getColor(R.color.white));
        }
        else{
            viewHolder.button.setBackground(mContext.getResources().getDrawable(R.drawable.slot_design_grey));
            viewHolder.button.setTextColor(mContext.getResources().getColor(R.color.white));
        }
        viewHolder.button.setText("Slot No. - "+parkingCar.getSlotNo());
        viewHolder.button.setOnClickListener(this);
        viewHolder.button.setTag(position);
        return convertView;
    }
}
