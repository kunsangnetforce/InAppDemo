package com.netforceinfotech.inappdemo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;

public class MainActivity extends AppCompatActivity implements BillingProcessor.IBillingHandler, View.OnClickListener {

    private BillingProcessor bp;
    Context context;
    TextView textViewAd, textViewFuel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        bp = new BillingProcessor(this, getString(R.string.lisence_key_inapp), this);
        boolean isAvailable = BillingProcessor.isIabServiceAvailable(context);
        if (!isAvailable) {
            // continue
            showMessage("In app not supported");
            return;
        }
        initView();

    }

    private void initView() {
        findViewById(R.id.buttonBuyFuel).setOnClickListener(this);
        findViewById(R.id.buttonConsume).setOnClickListener(this);
        findViewById(R.id.buttonRemoveAd).setOnClickListener(this);
        textViewAd = (TextView) findViewById(R.id.textViewAd);
        textViewFuel = (TextView) findViewById(R.id.textViewStatus);
        bp.purchase(this, getString(R.string.remove_ads));
        TransactionDetails td = bp.getPurchaseTransactionDetails(getString(R.string.remove_ads));
        try {
            Log.i("in app detail", td.toString());
        } catch (Exception ex) {
            Log.i("in app detail", "null");
        }

    }

    private void showMessage(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProductPurchased(String productId, TransactionDetails details) {
        Log.i("inapp", details.toString());
    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, Throwable error) {

    }

    @Override
    public void onBillingInitialized() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data))
            super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        if (bp != null)
            bp.release();

        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonBuyFuel:
                break;
            case R.id.buttonConsume:
                break;
            case R.id.buttonRemoveAd:

                bp.purchase(this, getString(R.string.remove_ads));

                break;
        }
    }
}
