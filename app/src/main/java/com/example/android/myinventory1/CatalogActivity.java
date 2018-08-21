package com.example.android.myinventory1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.android.myinventory1.data.Contract.Entry;
import com.example.android.myinventory1.data.DbHelper;

public class CatalogActivity extends AppCompatActivity {

    public DbHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        dbHelper = new DbHelper(this);


        FloatingActionButton plus = findViewById(R.id.plus);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
    }


    private Cursor queryData() {
        // Create and/or open a database to read from it
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        // Perform this raw SQL query "SELECT * FROM books"
        // to get a Cursor that contains all rows from the books table.
        String[] projection = {
                Entry._ID,
                Entry.COLUMN_PRODUCT_NAME,
                Entry.COLUMN_PRODUCT_PRICE,
                Entry.COLUMN_PRODUCT_QUANTITY,
                Entry.COLUMN_PRODUCT_SUPPLIER_NAME,
                Entry.COLUMN_PRODUCT_SUPPLIER_PHONE_NUMBER,
        };


        Cursor cursor;
        cursor = sqLiteDatabase.query(Entry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null,
                null);

        return cursor;
    }

    private void displayDatabaseInfo() {

        Cursor cursor = queryData();

        TextView displayView = findViewById(R.id.text_view_inventory);
        displayView.setText(getString(R.string.the_table_contains));
        displayView.append(" " + cursor.getCount() + " ");
        displayView.append(getString(R.string.items) + "\n\n");

        int idColumnIndex = cursor.getColumnIndex(Entry._ID);
        int productNameColumnIndex = cursor.getColumnIndex(Entry.COLUMN_PRODUCT_NAME);
        int productPriceColumnIndex = cursor.getColumnIndex(Entry.COLUMN_PRODUCT_PRICE);
        int productQuantityColumnIndex = cursor.getColumnIndex(Entry.COLUMN_PRODUCT_QUANTITY);
        int supplierNameColumnIndex = cursor.getColumnIndex(Entry.COLUMN_PRODUCT_SUPPLIER_NAME);
        int supplierContactColumnIndex = cursor.getColumnIndex(Entry.COLUMN_PRODUCT_SUPPLIER_PHONE_NUMBER);

        try {
            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentProductName = cursor.getString(productNameColumnIndex);
                int currentProductPrice = cursor.getInt(productPriceColumnIndex);
                int currentProductQuantity = cursor.getInt(productQuantityColumnIndex);
                String currentSupplierName = cursor.getString(supplierNameColumnIndex);
                String currentSupplierContact = cursor.getString(supplierContactColumnIndex);

                displayView.append("\n" + Entry._ID + "  : " + currentID + "\n" +
                        Entry.COLUMN_PRODUCT_NAME + "  : " + currentProductName + "\n" +
                        Entry.COLUMN_PRODUCT_PRICE + "  : " + currentProductPrice + "\n" +
                        Entry.COLUMN_PRODUCT_QUANTITY + "  : " + currentProductQuantity + "\n" +
                        Entry.COLUMN_PRODUCT_SUPPLIER_NAME + "  : " + currentSupplierName + "\n" +
                        Entry.COLUMN_PRODUCT_SUPPLIER_PHONE_NUMBER + "  : " + currentSupplierContact + "\n");
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }
}
