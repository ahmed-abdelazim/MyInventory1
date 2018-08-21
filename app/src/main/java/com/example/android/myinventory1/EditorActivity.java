package com.example.android.myinventory1;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.myinventory1.data.DbHelper;
import com.example.android.myinventory1.data.Contract.Entry;


public class EditorActivity extends AppCompatActivity {

    public DbHelper dbHelper;
    private EditText productNameEditText;
    private EditText productPriceEditText;
    private EditText productQuantityEditText;
    private Spinner supplierNameEditText;
    private EditText supplierContactEditText;
    private int mSupplieName = Entry.SUPPLIER_UNKNOWN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Find all relevant views that we will need to read user input from
        productNameEditText = findViewById(R.id.product_name_edit_text);
        productPriceEditText = findViewById(R.id.product_price_edit_text);
        productQuantityEditText = findViewById(R.id.product_quantity_edit_text);
        supplierNameEditText = findViewById(R.id.product_supplier_name_spinner);
        supplierContactEditText = findViewById(R.id.product_supplier_phone_number_edit_text);
        setupSpinner();
        dbHelper = new DbHelper(this);
    }


    private void setupSpinner() {

        ArrayAdapter productSupplieNameSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_supplier_options, android.R.layout.simple_spinner_item);

        productSupplieNameSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        supplierNameEditText.setAdapter(productSupplieNameSpinnerAdapter);

        supplierNameEditText.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.supplier_one))) {
                        mSupplieName = Entry.SUPPLIER_TWO;
                    } else if (selection.equals(getString(R.string.supplier_three))) {
                        mSupplieName = Entry.SUPPLIER_THREE;
                    } else {
                        mSupplieName = Entry.SUPPLIER_UNKNOWN;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mSupplieName = Entry.SUPPLIER_UNKNOWN;
            }
        });
    }


    private void insertItem() {
        String productNameString = productNameEditText.getText().toString().trim();

        String productPriceString = productPriceEditText.getText().toString().trim();
        int productPriceInteger = Integer.parseInt(productPriceString);

        String productQuantityString = productQuantityEditText.getText().toString().trim();
        int productQuantityInteger = Integer.parseInt(productQuantityString);

        String productSupplierPhoneNumberString = productPriceString.toString().trim();
        int productSupplierPhoneNumberInteger = Integer.parseInt(productSupplierPhoneNumberString);

        DbHelper mDbHelper = new DbHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(Entry.COLUMN_PRODUCT_NAME, productNameString);
        values.put(Entry.COLUMN_PRODUCT_PRICE, productPriceInteger);
        values.put(Entry.COLUMN_PRODUCT_QUANTITY, productQuantityInteger);
        values.put(Entry.COLUMN_PRODUCT_SUPPLIER_NAME, mSupplieName);
        values.put(Entry.COLUMN_PRODUCT_SUPPLIER_PHONE_NUMBER, productSupplierPhoneNumberInteger);

        long newRowId = db.insert(Entry.TABLE_NAME, null, values);

        if (newRowId == -1) {
            Toast.makeText(this, "Error with saving product", Toast.LENGTH_SHORT).show();
            Log.d("Error message", "Doesn't insert row on table");

        } else {
            Toast.makeText(this, "Product saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
            Log.d("successfully message", "insert row on table");
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        Log.d("message", "open Add Activity");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                insertItem();
                finish();
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
