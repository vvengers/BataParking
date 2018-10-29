package com.example.engersvanvincent.bataparking;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.engersvanvincent.bataparking.Modules.LotData;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentActivity extends AppCompatActivity {

    LotData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        setTitle("Checkout");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        data = getIntent().getParcelableExtra("data");
        TextView name = findViewById(R.id.payment_parking_lot_name);
        name.setText(data.getParkingLotName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    private static JSONObject getBaseRequest() throws  JSONException {
        return new JSONObject()
                .put("apiVersion", 2)
                .put("apiVersionMinor", 0);
    }

    private static JSONObject getTokenizationSpecification() throws JSONException {
        JSONObject tokenizationSpecification = new JSONObject();
        tokenizationSpecification.put("type", "PAYMENT_GATEWAY");
        tokenizationSpecification.put(
                "parameters",
                new JSONObject()
                        .put("gateway", "example")
                        .put("gatewayMerchantId", "exampleMerchantId"));

        return tokenizationSpecification;
    }

}
