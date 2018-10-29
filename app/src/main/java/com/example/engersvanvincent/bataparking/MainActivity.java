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
/*
        database.collection(collectionString).add(LotData.lotCreator("Eiermarkt", "Nijmegen", 120, "51.847442", "5.866616",55));
        database.collection(collectionString).add(LotData.lotCreator("Waalkade", "Nijmegen", 80, "51.850000", "5.863303", 76));
        database.collection(collectionString).add(LotData.lotCreator( "Kelfkensbos", "Nijmegen", 100, "51.846366", "5.870570", 88)
        );
        database.collection(collectionString).add(LotData.lotCreator("Huygensgebouw", "Nijmegen", 200, "51.824738", "5.869104", 105)
        );
        database.collection(collectionString).add(LotData.lotCreator("Kerkweg" , "Beek-Ubbergen", 75, "51.829649", "5.925439", 45)
        );
        database.collection(collectionString).add(LotData.lotCreator("Tiergarten", "Rindern", 150, "51.796909", "6.123122", 55)
        );
        database.collection(collectionString).add(LotData.lotCreator( "Elsepasweg", "'s-Heerenberg", 100, "51.875105", "6.270636",10)
        );
        database.collection(collectionString).add(LotData.lotCreator("De Hartjens", "Azewijn", 50, "51.887484", "6.299285", 44)
        );
        database.collection(collectionString).add(LotData.lotCreator("De Blenk", "Ulft", 70, "51.891358", "6.377967", 56)
        );
        database.collection(collectionString).add(LotData.lotCreator("Van Pallant", "Varsseveld", 50, "51.943944", "6.466662", 49)
        );
        database.collection(collectionString).add(LotData.lotCreator( "Guldenweg", "Varsseveld", 50, "51.940343", "6.480844", 50));
        database.collection(collectionString).add(LotData.lotCreator("De Betteld", "De Betteld", 45, "52.011718", "6.388099", 39)
        );
        database.collection(collectionString).add(LotData.lotCreator("‘t Haller Vorden", "Vorden", 30, "52.077510", "6.380187", 30)
        );
        database.collection(collectionString).add(LotData.lotCreator( "Beukenlaan", "Barchem", 20, "52.125436", "6.442512", 20)
        );
        database.collection(collectionString).add(LotData.lotCreator( "Neede", "Noordijk", 35, "52.148035", "6.577415", 20)
        );
        database.collection(collectionString).add(LotData.lotCreator("SVR Camping", "Diepenheim", 40, "52.178088", "6.631762", 18)
        );
        database.collection(collectionString).add(LotData.lotCreator("Burgemeester plein", "Bentelo", 55, "52.224481", "6.681398", 20)
        );
        database.collection(collectionString).add(LotData.lotCreator( "De Groote Plooij", "Enschede", 60, "52.208491", "6.813955", 24));
        database.collection(collectionString).add(LotData.lotCreator("Q-Park", "Enschede", 300, "52.218547", "6.893358", 45)
        );
        database.collection(collectionString).add(LotData.lotCreator("Keypoint", "Enschede", 75, "52.238110", "6.846036", 34)
        );
        database.collection(collectionString).add(LotData.lotCreator("Hengelosestraat", "Enschede", 50, "52.235698", "6.852903", 28)
        );
*/



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
