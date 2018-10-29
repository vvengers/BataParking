package com.example.engersvanvincent.bataparking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.engersvanvincent.bataparking.Modules.Car;
import com.example.engersvanvincent.bataparking.Modules.LotData;
import com.example.engersvanvincent.bataparking.Modules.LotDataAdapter;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private LotDataAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FirebaseFirestore database;
//    private LinearLayout goingLayout;
    private MainActivity context;
    private String collectionString = "parking_lots";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.main_activity_recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        database = FirebaseFirestore.getInstance();

//        new LotData()

        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new LotDataAdapter(context, new ArrayList<LotData>());
        mRecyclerView.setAdapter(mAdapter);
        database.collection(collectionString).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (snapshot != null ) {
                   updateAdapterData(snapshot);
                } else {

                }
            }
        });



    }

    private void updateAdapterData(QuerySnapshot q){
        ArrayList<LotData> myDataSet = new ArrayList<>();
        for(QueryDocumentSnapshot document : q){
            LotData l = LotData.createLdObject(document.getData());
            myDataSet.add(l);
        }
        mAdapter.updateDataSet(myDataSet);

    }
}
