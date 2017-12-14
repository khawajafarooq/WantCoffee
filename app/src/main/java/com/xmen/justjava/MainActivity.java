/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.xmen.justjava;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int basePrice = 5;
    int whippedCreadPrice = 1;
    int cholocatePrice = 2;
    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        quantity = getInteger(getQuantityText());
    }


    /**
     * Event handlers
     */
    public void submitOrder(View view) {

        //Log.v("MainActivity","The price is " + price);
        CheckBox whippedCreamCheckbox = findViewById(R.id.whipped_cream_checkbox);
        CheckBox chocolateCheckbox = findViewById(R.id.chocolate_checkbox);
        EditText nameEditText = findViewById(R.id.edit_text_name);

        String name = nameEditText.getText().toString();
        String subject = getString(R.string.email_subject, name);
        int price = calculate(whippedCreamCheckbox.isChecked(), chocolateCheckbox.isChecked());
        String message = createOrderSummary(name, price, whippedCreamCheckbox.isChecked(), chocolateCheckbox.isChecked());

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void increment(View view) {

        if (quantity == 100)
            return;

        quantity++;
        display(quantity);
    }

    public void decrement(View view) {

        if (quantity == 1)
            return;

        quantity--;
        display(quantity);
    }

    /**
     * Helper Methods
     */
    private int calculate(boolean hasWhippedCream, boolean hasChocolate) {

        int price = basePrice;

        if (hasWhippedCream) {
            price += whippedCreadPrice;
        }

        if (hasChocolate) {
            price += cholocatePrice;
        }

        return quantity * price;
    }

    private String createOrderSummary(String name, int price, boolean hasWhippedCream, boolean hasChocolate) {

        String orderSummary = getString(R.string.order_summary_name, name);
        orderSummary += "\n" + getString(R.string.add_whipped_cream, hasWhippedCream);
        orderSummary += "\n" + getString(R.string.add_chocolate, hasChocolate);
        orderSummary += "\n" + getString(R.string.quantity_summary, quantity);
        orderSummary += "\n" + getString(R.string.total, NumberFormat.getCurrencyInstance().format(price));
        orderSummary += "\n" + getString(R.string.thank_you);
        return orderSummary;
    }

    private void display(int number) {

        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    private String getQuantityText() {

        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        return quantityTextView.getText().toString();
    }

    private Integer getInteger(String str) {

        if (!str.equals("")) {
            return Integer.parseInt(str);
        }

        return 0;
    }

}