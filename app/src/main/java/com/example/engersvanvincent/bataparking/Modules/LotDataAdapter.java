package com.example.engersvanvincent.bataparking.Modules;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.engersvanvincent.bataparking.MainActivity;
import com.example.engersvanvincent.bataparking.PaymentActivity;
import com.example.engersvanvincent.bataparking.R;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.ArrayList;

public class LotDataAdapter extends RecyclerView.Adapter<LotDataAdapter.LotDataViewHolder>{
    private MainActivity context;
    private ArrayList<LotData> mDataSet;

    public static class LotDataViewHolder extends RecyclerView.ViewHolder{
        private TextView estWaitingTimeView;
        private TextView lotNameView;
        private TextView spotsLeftView;
        private CircularProgressBar progBar;
        private Button navigateButton;
        private Button reserveButton;


        private LotDataViewHolder(View v){
            super(v);
            estWaitingTimeView = v.findViewById(R.id.estWaitingTime);
            lotNameView = v.findViewById(R.id.lotName);
            spotsLeftView = v.findViewById(R.id.spotsLeft);
            progBar = v.findViewById(R.id.lotProgBar);
            reserveButton = v.findViewById(R.id.reserve_button);
            navigateButton = v.findViewById(R.id.navigate_button);
            }
        }

    public void updateDataSet(ArrayList<LotData> data){
        mDataSet.clear();
        mDataSet.addAll(data);
        this.notifyDataSetChanged();
    }

    public LotDataAdapter(MainActivity context, ArrayList<LotData> dataSet){
        mDataSet = new ArrayList<>();
        mDataSet.addAll(dataSet);
        this.context = context;
    }

    @NonNull
    @Override
    public LotDataAdapter.LotDataViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
      View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.parking_lot, viewGroup, false);
      return  new LotDataViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final LotDataAdapter.LotDataViewHolder lotDataViewHolder, int i) {
        final LotData data = mDataSet.get(i);

       lotDataViewHolder.navigateButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               //Open Google Maps
               Uri gmmIntentUri = Uri.parse("google.navigation:q=" + data.getLatitude() + "," + data.getLongitude());
               Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
               mapIntent.setPackage("com.google.android.apps.maps");
               context.startActivity(mapIntent);
           }
       });

       lotDataViewHolder.reserveButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(context, PaymentActivity.class);
               intent.putExtra("data",  data);
               context.startActivity(intent);
           }
       });

        lotDataViewHolder.lotNameView.setText(data.getParkingLotName());

        int totalParkedCar = data.getParkedCarsId().size();
        float relation = (float) totalParkedCar / data.getTotParkingSpaces();
        String color = "#A50000";
        if(relation < 0.3){
            color = "#00A50B";
        }else if(relation < 0.5){
            color = "#74A500";
        }else if(relation < 0.7){
            color = "#A5A500";
        }else if (relation < 0.8){
            color = "#A55800";
        }
        lotDataViewHolder.progBar.setProgressMax(data.getTotParkingSpaces());
        lotDataViewHolder.progBar.setColor(Color.parseColor(color));
        lotDataViewHolder.progBar.setBackgroundColor(Color.parseColor("#D8D8D8"));
        lotDataViewHolder.progBar.setProgress(totalParkedCar);



        int spotsLeft =  data.getTotParkingSpaces() - totalParkedCar;
        lotDataViewHolder.spotsLeftView.setText(
                Integer.toString(spotsLeft) + ((spotsLeft == 1) ? "spot" : "spots") + " left.");

        //TODO: don't just multiply times the avaParkingTime but take into acount how long the car has been parked there.
        int waitingTime =  (data.getComingCarsId().size() - spotsLeft) * data.getAvaParkingTime();
        waitingTime = (waitingTime < 0) ? 0 : waitingTime;
        lotDataViewHolder.estWaitingTimeView.setText(
                Integer.toString(waitingTime) + " minutes waiting time.");

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }



}
