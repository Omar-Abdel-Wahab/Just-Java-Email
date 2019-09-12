package com.example.dell.justjavaemail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.justjavaemail.R;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        quantity = quantity + 1;
        display(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        quantity = quantity - 1;
        if(quantity==0){
            quantity=1;
            Context context = getApplicationContext();
            CharSequence text = "Can't have less than 1 coffee";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        display(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCream = (CheckBox) findViewById(R.id.whippedCream);
        CheckBox Chocolate = (CheckBox) findViewById(R.id.chocolate);
        int price=5;
        if(Chocolate.isChecked() && whippedCream.isChecked())
            price=8;
        else if(Chocolate.isChecked())
            price=7;
        else if(whippedCream.isChecked())
            price=6;
        displayPrice(quantity * price);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price value on the screen.
     */
    private void displayPrice(int number) {
        CheckBox whippedCream = (CheckBox) findViewById(R.id.whippedCream);
        CheckBox Chocolate = (CheckBox) findViewById(R.id.chocolate);
        EditText nameview = (EditText) findViewById(R.id.name_view);
        String name = nameview.getText().toString();
        String message = "Name: "+name+"\nAdd Whipped Cream? "+whippedCream.isChecked()+"\nAdd Chocolate? "+
                Chocolate.isChecked()+"\nQuantity: "+quantity+ "\nTotal: $"+number+"\nThank You!";
        composeEmail(name, message);
    }
    public void composeEmail(String name, String message) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT,"JustJava order for "+name);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}