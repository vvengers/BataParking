package com.example.engersvanvincent.bataparking;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.engersvanvincent.bataparking.Modules.GooglePay;
import com.example.engersvanvincent.bataparking.Modules.LotData;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;

import org.json.JSONObject;

import java.util.Optional;

public class PaymentActivity extends AppCompatActivity {

    LotData data;
    private PaymentsClient mPaymentsClient;
    private Button payButton;
    private static final int LOAD_PAYMENT_DATA_REQUEST_CODE = 42;
    private EditText plateEdit;
    private Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        setTitle("Checkout");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        plateEdit = findViewById(R.id.plateEdit);
        data = getIntent().getParcelableExtra("data");
        TextView name = findViewById(R.id.payment_parking_lot_name);
        name.setText(data.getParkingLotName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        startBuyProcess();

    }

    private void startBuyProcess(){
        mPaymentsClient =
                Wallet.getPaymentsClient(
                        this,
                        new Wallet.WalletOptions.Builder()
                                .setEnvironment(WalletConstants.ENVIRONMENT_TEST)
                                .build());
        possiblyShowGooglePayButton();

    }

    private void possiblyShowGooglePayButton() {
        final Optional<JSONObject> isReadyToPayJson = GooglePay.getIsReadyToPayRequest();
        if (!isReadyToPayJson.isPresent()) {
            return;
        }
        IsReadyToPayRequest request = IsReadyToPayRequest.fromJson(isReadyToPayJson.get().toString());
        if (request == null) {
            return;
        }
        Task<Boolean> task = mPaymentsClient.isReadyToPay(request);
        task.addOnCompleteListener(
                new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        try {
                            boolean result = task.getResult(ApiException.class);
                            if (result) {
                                // show Google as a payment option
                                payButton = findViewById(R.id.buyButton);
                                payButton.setOnClickListener(
                                        new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                // If a licence plate has been entered start
                                                // request payment, otherwise toast.
                                                if(!plateEdit.getText().toString().equals("")){
                                                    requestPayment(view);
                                                }else{
                                                    Toast.makeText(
                                                            context,
                                                            "Place enter a licence plate number.",
                                                            Toast.LENGTH_LONG)
                                                            .show();
                                                }
                                            }
                                        });
                                payButton.setVisibility(View.VISIBLE);
                            }
                        } catch (ApiException exception) {
                            // handle developer errors
                        }
                    }
                });
    }

    public void requestPayment(View view) {
        Optional<JSONObject> paymentDataRequestJson = GooglePay.getPaymentDataRequest();
        if (!paymentDataRequestJson.isPresent()) {
            return;
        }
        PaymentDataRequest request =
                PaymentDataRequest.fromJson(paymentDataRequestJson.get().toString());
        if (request != null) {
            AutoResolveHelper.resolveTask(
                    mPaymentsClient.loadPaymentData(request), this, LOAD_PAYMENT_DATA_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // value passed in AutoResolveHelper
            case LOAD_PAYMENT_DATA_REQUEST_CODE:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        PaymentData paymentData = PaymentData.getFromIntent(data);
                        String json = paymentData.toJson();
                        break;
                    case Activity.RESULT_CANCELED:
                        break;
                    case AutoResolveHelper.RESULT_ERROR:
                        Status status = AutoResolveHelper.getStatusFromIntent(data);
                        // Log the status for debugging.
                        // Generally, there is no need to show an error to the user.
                        // The Google Pay payment sheet will present any account errors.
                        break;
                    default:
                        // Do nothing.
                }
                break;
            default:
                // Do nothing.
        }
    }


}
