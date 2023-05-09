package sg.edu.rp.c346.id22020860.billplease;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    // create variable
    EditText amtInput;
    EditText paxInput;
    ToggleButton svsBtn;
    ToggleButton gstBtn;
    EditText discInput;
    RadioGroup radioGroup;
    Button splitBtn;
    Button resetBtn;
    TextView billDisplay;
    TextView eachPaysDisplay;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //call out id to connect to the variable
        amtInput = findViewById(R.id.editAmount);
        paxInput = findViewById(R.id.editPax);
        svsBtn = findViewById(R.id.tbSvs);
        gstBtn = findViewById(R.id.tbGst);
        discInput = findViewById(R.id.editDiscount);
        radioGroup =findViewById(R.id.radioGroup);
        splitBtn =findViewById(R.id.split);
        resetBtn = findViewById(R.id.reset);
        billDisplay = findViewById(R.id.totalBill);
        eachPaysDisplay = findViewById(R.id.eachPays);




    splitBtn.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v){
            //getText(): Return textview , toString(): Return string , trim():Remove whitespace, length not equal to 0
            if(amtInput.getText().toString().trim().length()!=0 && paxInput.getText().toString().trim().length()!=0){
                //assign a double variable
                double newAmt = 0.0;
                // parseDouble return new double initialized ti value represented by specified String
                //when gst and svs is true, amount * 118% (10% svs & 8% gst).
                if (svsBtn.isChecked() && gstBtn.isChecked()){
                    newAmt = Double.parseDouble(amtInput.getText().toString()) * 1.1*1.08;
                }
                //when gst is true and svs false, amount * 108% (8% gst).
                else if(!svsBtn.isChecked()&&  gstBtn.isChecked()){
                    newAmt = Double.parseDouble(amtInput.getText().toString())* 1.08;
                }
                //when gst is false and svs is true, amount * 110% (10% svs).
                else if(svsBtn.isChecked() && !gstBtn.isChecked()){
                    newAmt = Double.parseDouble(amtInput.getText().toString())* 1.1;
                }
                else{
                    //when gst and svs is false,amount.
                    newAmt = Double.parseDouble(amtInput.getText().toString());
                }
                    //when discount not 0, amount * ((1- discount)/100)
                if(discInput.getText().toString().trim().length() != 0){
                    newAmt *= 1 - Double.parseDouble(discInput.getText().toString())/100;
                }
                    // set the text for display bill. "%.2f" is used for 2 decimal points
                billDisplay.setText("Total Bill: $" + String.format("%.2f", newAmt));
                    // create a variable for number of pax
                int numPerson =Integer.parseInt(paxInput.getText().toString());
                    //create a variable for the payment type group.
                int checkedRadioID = radioGroup.getCheckedRadioButtonId();
                //conditions to set the text for Each Pays
                    //when pax not equal 1 and payNow is checked
                if (numPerson!=1 && checkedRadioID == R.id.payNow) {
                    eachPaysDisplay.setText("Each Pays: $" + String.format("%.2f", newAmt / numPerson)+ " via PayNow to 912345678") ;
                }
                    //when pax is equal to 1 and PayNow is checked
                else if(numPerson==1 && checkedRadioID == R.id.payNow) {
                    eachPaysDisplay.setText("Each Pays: $" + String.format("%.2f",newAmt) + "via PayNow to 912345678");
                }
                    //when pax is not equal to 1 and cash is checked
                else if(numPerson!=1 && checkedRadioID == R.id.cash){
                    eachPaysDisplay.setText("Each Pays: $" + String.format("%.2f", newAmt / numPerson)+ " in Cash") ;
                }
                    //when pax i equal to 1 and cash is checked
                else{
                    eachPaysDisplay.setText("Each Pays: $" + String.format("%.2f",newAmt) + " in Cash");
                }

                //To make amount required
            }else if(amtInput.getText().toString().trim().length()==0 && paxInput.getText().toString().trim().length()!=0){
                amtInput.setError("Amount is required!");
                //To make pax required
            }else if(amtInput.getText().toString().trim().length()!=0 && paxInput.getText().toString().trim().length()==0){
                paxInput.setError("Number of pax is required!");
            }
            else{
                //To make both pax and amount required
                amtInput.setError("Amount is required!");
                paxInput.setError("Number of pax is required!");
            }
        }
    });
    //create reset button
    resetBtn.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v){
            //empty the text for amount and pax
            amtInput.setText("");
            paxInput.setText("");
            //reset the button for svs and gst to false
            svsBtn.setChecked(false);
            gstBtn.setChecked(false);
            //empty text for discount
            discInput.setText("");

        }
    });


}}