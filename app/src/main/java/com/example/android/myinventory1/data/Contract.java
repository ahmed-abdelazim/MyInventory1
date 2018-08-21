package com.example.android.myinventory1.data;

import android.provider.BaseColumns;

/**
 * Created by Person on 21/08/2018.
 */

public class Contract {

    //constructor - Not instantiable
    public Contract() {
    }

    public static final class Entry implements BaseColumns {

        //Main table of the database
        public static final String TABLE_NAME = "products";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_PRODUCT_NAME = "product_name";
        public final static String COLUMN_PRODUCT_PRICE = "price";
        public final static String COLUMN_PRODUCT_QUANTITY = "quantity";
        public final static String COLUMN_PRODUCT_SUPPLIER_NAME = "supplier_name";
        public final static String COLUMN_PRODUCT_SUPPLIER_PHONE_NUMBER = "supplier_phone_number";

        // suppliers are limited so each will be identified by integer
        public final static int SUPPLIER_UNKNOWN = 0;
        public final static int SUPPLIER_ONE = 1;
        public final static int SUPPLIER_TWO = 2;
        public final static int SUPPLIER_THREE = 3;
    }
}
