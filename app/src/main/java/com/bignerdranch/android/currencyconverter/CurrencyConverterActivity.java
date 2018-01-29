package com.bignerdranch.android.currencyconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.text.DecimalFormat;


public class CurrencyConverterActivity extends AppCompatActivity {

    private EditText usdAmountObject = null;
    private EditText inrAmountObject = null;
    private String lastEdited = "None";
    private double usdAmount = -1;
    private double inrAmount = -1;
    private final String amountsKey = "amountsKey";
    private double[] amountsToSave = new double[2];
    private double[] amountsToRestore = new double[2];
    private String usdAmountString;
    private String inrAmountString;
    private final DecimalFormat roundUptoTwoDecimals = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        Button convertButton = this.findViewById(R.id.convertButtonId);
        usdAmountObject = this.findViewById(R.id.usdInputId);
        inrAmountObject = this.findViewById(R.id.inrInputId);

        //To ensure usdAmount and inrAmount are changed from default values to last converted values
        if(savedInstanceState != null) {
            amountsToRestore = savedInstanceState.getDoubleArray(amountsKey);
            usdAmount = amountsToRestore[0];
            inrAmount = amountsToRestore[1];
        }

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                usdAmountString = usdAmountObject.getText().toString();
                inrAmountString = inrAmountObject.getText().toString();

                //Check if both the text fields are empty
                if(usdAmountString.isEmpty() && inrAmountString.isEmpty()) {
                    Toast.makeText(CurrencyConverterActivity.this,R.string.alertEitherFields, Toast.LENGTH_SHORT).show();
                }
                else if(usdAmountString.equals(".") || inrAmountString.equals(".")) {
                    Toast.makeText(CurrencyConverterActivity.this,R.string.alertInvalidInput, Toast.LENGTH_SHORT).show();
                }
                //If both the text fields are non-empty, check which field was edited last
                else if(!usdAmountString.isEmpty() && !inrAmountString.isEmpty()) {
                    if(lastEdited.equals("USDTextEdited")) {
                        usdAmount = Double.parseDouble(usdAmountObject.getText().toString());
                        convertUsdToInr(usdAmount);
                    }
                    else if(lastEdited.equals("INRTextEdited")) {
                        inrAmount = Double.parseDouble(inrAmountObject.getText().toString());
                        convertInrToUsd(inrAmount);
                    }
                }
                else if(!usdAmountString.isEmpty()) {
                    usdAmount = Double.parseDouble(usdAmountObject.getText().toString());
                    convertUsdToInr(usdAmount);
                }
                else if(!inrAmountString.isEmpty()) {
                    inrAmount = Double.parseDouble(inrAmountObject.getText().toString());
                    convertInrToUsd(inrAmount);
                }
            }
        });

        View.OnFocusChangeListener usdEditTextFocusListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus)
                    lastEdited = "USDTextEdited";
            }
        };

        View.OnFocusChangeListener inrEditTextFocusListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus)
                    lastEdited = "INRTextEdited";
            }
        };

        usdAmountObject.setOnFocusChangeListener(usdEditTextFocusListener);
        inrAmountObject.setOnFocusChangeListener(inrEditTextFocusListener);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        amountsToSave[0] = usdAmount;
        amountsToSave[1] = inrAmount;
        savedInstanceState.putDoubleArray(amountsKey,amountsToSave);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        amountsToRestore = savedInstanceState.getDoubleArray(amountsKey);
        //if text fields were blank before rotation then set text fields to blank instead of default values
        if (amountsToRestore[0] == -1 && amountsToRestore[1] == -1) {
            usdAmountObject.setText(null);
            inrAmountObject.setText(null);
        }
        else {
            usdAmountObject.setText(roundUptoTwoDecimals.format(amountsToRestore[0]));
            inrAmountObject.setText(roundUptoTwoDecimals.format(amountsToRestore[1]));
        }
    }

    //Convert values from USD to INR
    private void convertUsdToInr(double usdValue) {
        inrAmount = usdValue * 63.89;
        inrAmountObject.setText(roundUptoTwoDecimals.format(inrAmount));
    }

    //Convert values from INR to USD
    private void convertInrToUsd(double inrValue) {
        usdAmount = inrValue / 63.89;
        usdAmountObject.setText(roundUptoTwoDecimals.format(usdAmount));
    }
}
