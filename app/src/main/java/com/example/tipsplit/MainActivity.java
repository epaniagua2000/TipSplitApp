package com.example.tipsplit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText billAmount;
    private EditText numOfPeople;
    private TextView tipAmount;
    private TextView totWithTip;
    private TextView totPerPerson;
    private TextView overageText;
    private double totalWithTip;
    private RadioGroup tipPercent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        billAmount = findViewById(R.id.billTyped);
        numOfPeople = findViewById(R.id.numPeopleHere);
        tipAmount = findViewById(R.id.tipAmountHere);
        totWithTip = findViewById(R.id.totWithTipHere);
        totPerPerson = findViewById(R.id.totPerPersonHere);
        overageText = findViewById(R.id.overageHere);
        tipPercent= findViewById(R.id.tipPercentRG);
    }

    public void billCalculator(double bAmount, double tipPercentage){
        double tipTot = bAmount*tipPercentage;
        String tipAmountString = (String.format("$%.2f", tipTot));
        tipAmount.setText(tipAmountString);
        String totWTipString = (String.format("$%.2f", bAmount + tipTot));
        totalWithTip = bAmount + tipTot;
        totWithTip.setText(totWTipString);
    }

    public void radioClicked(View v){

        if (billAmount.getText().toString().isEmpty()){
            tipPercent.clearCheck();
            
        }
            else {
                Double billAmountDouble = Double.parseDouble(billAmount.getText().toString());

                if(billAmountDouble < 0){
                    tipPercent.clearCheck();
                  
                } else if (v.getId() == R.id.twelveRB ){
                    billCalculator(billAmountDouble, 0.12);

                } else if (v.getId() == R.id.fifteenRB){
                    billCalculator(billAmountDouble, 0.15);

                } else if (v.getId() == R.id.eighteenRB){
                    billCalculator(billAmountDouble, 0.18);

                } else if (v.getId() == R.id.twentyRB){
                    billCalculator(billAmountDouble, 0.2);

                }
            }
    }

    public void buttonClicked(View v){

        numOfPeople.setInputType(InputType.TYPE_CLASS_NUMBER);
        if(v.getId() == R.id.goButton && Integer.parseInt(numOfPeople.getText().toString()) > 0){
            double numOfP = Integer.parseInt(numOfPeople.getText().toString());
            double totalPP = (Math.ceil((totalWithTip/numOfP)*100))/100;
            totPerPerson.setText(String.format("$%.2f", totalPP));
            String tpp = String.format("%.2f", totalPP);
            double overage = (Double.parseDouble(tpp)*numOfP) - totalWithTip;
            overageText.setText(String.format("$%.2f", overage));
        }

        if(v.getId() == R.id.clearButton){
            billAmount.setText("");
            tipAmount.setText("");
            totWithTip.setText("");
            numOfPeople.setText("");
            totPerPerson.setText("");
            overageText.setText("");
            tipPercent.clearCheck();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        // place all texts here

        outState.putString("TOTAL AMOUNT", tipAmount.getText().toString());
        outState.putString("TOTAL WITH TIP", totWithTip.getText().toString());
        outState.putString("TOTAL PER PERSON", totPerPerson.getText().toString());
        outState.putString("OVERAGE", overageText.getText().toString());
        //outState.putInt("TIP PERCENT", tipPercent.getCheckedRadioButtonId());

        super.onSaveInstanceState(outState); //must go last
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState){
        //call super first
        super.onRestoreInstanceState(savedInstanceState);
        tipAmount.setText(savedInstanceState.getString("TOTAL AMOUNT"));
        totWithTip.setText(savedInstanceState.getString("TOTAL WITH TIP"));
        totPerPerson.setText(savedInstanceState.getString("TOTAL PER PERSON"));
        overageText.setText(savedInstanceState.getString("OVERAGE"));
    }
}