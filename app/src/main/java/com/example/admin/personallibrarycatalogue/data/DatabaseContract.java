package com.example.admin.personallibrarycatalogue.data;

/**
 * Created by Mikhail Valyuskiy on 25.05.2015.
 */

import android.provider.BaseColumns;

/**
 * Describes fields of database for Personal Library Application
 */
public class DatabaseContract {

    /**
     * Inner class that defines the table contents of the Books table
     */
    public static final class BooksTable implements BaseColumns{

        public static final String TABLE_NAME = "books";
        public static final String TITLE = "title";
        public static final String AUTHOR = "author";
        public static final String COVER = "cover";
        public static final String DESCRIPTION = "description";
    }

}
