package com.bignerdranch.android.currencyconverter;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class CurrencyConverterActivity extends AppCompatActivity {

    EditText usdAmountObject = null;
    EditText inrAmountObject = null;
    TextWatcher inrInputListener = null;
    TextWatcher usdInputListener = null;
    double usdAmount;
    double inrAmount;
    String usdSavedValue;
    String inrSavedValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        Button convert_button = (Button) findViewById(R.id.convertButtonId);
        usdAmountObject = (EditText) findViewById(R.id.usdInputId);
        inrAmountObject = (EditText) findViewById(R.id.inrInputId);


        if(savedInstanceState != null) {
            double amounts[];
            amounts = savedInstanceState.getDoubleArray(usdSavedValue);
            Log.d("MJ","USD Value on create(OLD):" + amounts[0]);
            Log.d("MJ","INR Value on create(OLD):" + amounts[1]);
            usdAmount = amounts[0];
            inrAmount = amounts[1];

        }

        convert_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String usdAmountString = usdAmountObject.getText().toString();
                String inrAmountString = inrAmountObject.getText().toString();

                if(!usdAmountString.isEmpty() && !inrAmountString.isEmpty()) {
                    Toast.makeText(CurrencyConverterActivity.this,"Please enter amount in only one of the fields", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(!usdAmountString.isEmpty()) {
                    usdAmount = Double.parseDouble(usdAmountObject.getText().toString());
                    convertUsdToInr(usdAmount);
                }
                else if(!inrAmountString.isEmpty()) {
                    inrAmount = Double.parseDouble(inrAmountObject.getText().toString());
                    convertInrToUsd(inrAmount);
                }
                else {
                    Toast.makeText(CurrencyConverterActivity.this,"Please enter amount in either of the two fields", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        usdInputListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
                Log.d("USD Text Changed",text.toString()+" Length"+text.length());
                if(text.length() > 0) {
                    disableEdit(inrAmountObject,inrInputListener);
                }
                else {
                    Log.d("In Else",text.toString()+" Length"+text.length());
                    enableEdit(inrAmountObject);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        inrInputListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
                Log.d("INR Text Changed",text.toString()+" Length"+text.length());
                if(text.length() > 0) {
                    disableEdit(usdAmountObject,usdInputListener);
                }
                else {
                    Log.d("In Else",text.toString()+" Length"+text.length());
                    enableEdit(usdAmountObject);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

//        usdAmountObject.addTextChangedListener(usdInputListener);
//        inrAmountObject.addTextChangedListener(inrInputListener);

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i("MJ", "onSaveInstanceState");
        double amounts[]=new double[2];
        Log.i("MJ: savedInstance Value",savedInstanceState.toString());

            amounts[0] = usdAmount;
            amounts[1] = inrAmount;
            Log.d("MJ","USD Value on save:" + usdAmount);
            Log.d("MJ","INR Value on save:" + inrAmount);
            savedInstanceState.putDoubleArray(usdSavedValue,amounts);


    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i("MJ", "onRestoreInstanceState");
        double amounts[] = savedInstanceState.getDoubleArray(usdSavedValue);
        Log.d("MJ","Double array:" + savedInstanceState.getDoubleArray(usdSavedValue).toString());
        Log.d("MJ","USD Value on restore:" + amounts[0]);
        Log.d("MJ","INR Value on restore:" + amounts[1]);
        usdAmountObject.setText(String.format("%.2f",amounts[0]));
        inrAmountObject.setText(String.format("%.2f",amounts[1]));
    }

    private void disableEdit(EditText objectToDisable, TextWatcher listenerName) {
        objectToDisable.setFocusable(false);
        objectToDisable.setEnabled(false);
        objectToDisable.setCursorVisible(false);
        objectToDisable.removeTextChangedListener(listenerName);
    }

    private void enableEdit(EditText objectToEnable) {
        objectToEnable.setEnabled(true);
        objectToEnable.setFocusableInTouchMode(true);
        objectToEnable.setFocusable(true);
        objectToEnable.setCursorVisible(true);
    }
    private void convertUsdToInr(double usdValue) {
        inrAmount = usdValue * 63.89;
//        inrAmountObject.setText(Double.toString(inrAmount));
        inrAmountObject.setText(String.format("%.2f",inrAmount));
    }

    private void convertInrToUsd(double inrValue) {
        usdAmount = inrValue / 63.89;
//        usdAmountObject.setText(Double.toString(usdAmount));
        usdAmountObject.setText(String.format("%.2f",usdAmount));
    }
}
