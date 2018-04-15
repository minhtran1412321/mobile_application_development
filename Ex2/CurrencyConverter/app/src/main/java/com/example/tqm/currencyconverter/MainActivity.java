package com.example.tqm.currencyconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView resultTv;
    private EditText amountTv;
    private Spinner convertFromSpinner;
    private Spinner convertToSpinner;

    private List<Currency> currencies = new ArrayList<>();
    private Double result = -1.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        createCurrencyList();
    }

    private void initView() {
        resultTv = (TextView) findViewById(R.id.tv_main_result);
        amountTv = (EditText) findViewById(R.id.et_main_amount);
        convertFromSpinner = (Spinner) findViewById(R.id.sp_main_converted_from);
        convertToSpinner = (Spinner) findViewById(R.id.sp_main_converted_to);
    }

    public void onClickConvert(View view) {
        if (inputIsValidated()) {
            result = calculateResult();
            resultTv.setText(result.toString() + " " + convertToSpinner.getSelectedItem().toString());
        } else {
            Toast.makeText(this, "Incorrect Input!", Toast.LENGTH_LONG).show();
        }
    }

    public void onClickRound(View view) {
        if (result >= 0) {
            result = roundNumber(result);
            resultTv.setText(result.toString() + " " + convertToSpinner.getSelectedItem().toString());
        }
    }
    private boolean inputIsValidated() {
        String input = amountTv.getText().toString();
        return isNumeric(input) && Double.parseDouble(input) >= 0;
    }

    public static boolean isNumeric(String str)
    {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

    private Double calculateResult() {
        String fromCurrency = convertFromSpinner.getSelectedItem().toString();
        String toCurrency = convertToSpinner.getSelectedItem().toString();
        Double amount = Double.parseDouble(amountTv.getText().toString());
        double fromPrice = 0;
        double toPrice = 0;
        for (Currency currency: currencies) {
            if (fromCurrency.equals(currency.getCode())) {
                fromPrice = currency.getPrice();
            }
            if (toCurrency.equals(currency.getCode())) {
                toPrice = currency.getPrice();
            }
        }
        return amount*fromPrice/toPrice;
    }

    private Double roundNumber(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.valueOf(twoDForm.format(d));
    }

    private void createCurrencyList() {
        currencies.add(new Currency("VND", 1));
        currencies.add(new Currency("USD", 22786));
        currencies.add(new Currency("EUR", 28135));
        currencies.add(new Currency("GBP", 32452));
        currencies.add(new Currency("AUD", 17691));
        currencies.add(new Currency("JPY", 212));
        currencies.add(new Currency("CNY", 3631));

    }


}
