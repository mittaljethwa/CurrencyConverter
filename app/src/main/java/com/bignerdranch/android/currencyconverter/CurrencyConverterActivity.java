package com.bignerdranch.android.currencyconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_converter);

        Button convert_button = (Button) findViewById(R.id.convertButtonId);
        usdAmountObject = (EditText) findViewById(R.id.usdInputId);
        inrAmountObject = (EditText) findViewById(R.id.inrInputId);

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
                    double usdAmount = Double.parseDouble(usdAmountObject.getText().toString());
                    convertUsdToInr(usdAmount);
                }
                else if(!inrAmountString.isEmpty()) {
                    double inrAmount = Double.parseDouble(inrAmountObject.getText().toString());
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

    private void disableEdit(EditText objectToDisable,TextWatcher listenerName) {
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
    private void convertUsdToInr(double usdAmount) {
        double inrAmount = usdAmount * 63.89;
//        inrAmountObject.setText(Double.toString(inrAmount));
        inrAmountObject.setText(String.format("%.2f",inrAmount));
    }

    private void convertInrToUsd(double inrAmount) {
        double usdAmount = inrAmount / 63.89;
//        usdAmountObject.setText(Double.toString(usdAmount));
        usdAmountObject.setText(String.format("%.2f",usdAmount));
    }
}
