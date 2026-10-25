
package com.example.sqlitetutorial;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Modifier le titre de l'enregistrement ID 1
        ContentValues values = new ContentValues();
        values.put(
                FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE,
                "Titre modifie"
        );

        String selection = FeedReaderContract.FeedEntry._ID + " = ?";
        String[] selectionArgs = {"1"};

        int updatedRows = db.update(
                FeedReaderContract.FeedEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );

        Log.d("SQLiteTutorial",
                "Nombre de lignes modifiees : " + updatedRows);

        // Supprimer l'enregistrement ID 2
        String[] deleteArgs = {"2"};

        int deletedRows = db.delete(
                FeedReaderContract.FeedEntry.TABLE_NAME,
                selection,
                deleteArgs
        );

        Log.d("SQLiteTutorial",
                "Nombre de lignes supprimees : " + deletedRows);

        // Lire les enregistrements restants
        String[] projection = {
                FeedReaderContract.FeedEntry._ID,
                FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE,
                FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE
        };

        try (Cursor cursor = db.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                FeedReaderContract.FeedEntry._ID + " ASC"
        )) {

            while (cursor.moveToNext()) {
                long id = cursor.getLong(
                        cursor.getColumnIndexOrThrow(
                                FeedReaderContract.FeedEntry._ID
                        )
                );

                String title = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE
                        )
                );

                String subtitle = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE
                        )
                );

                Log.d("SQLiteTutorial",
                        "ID: " + id +
                                " | Title: " + title +
                                " | Subtitle: " + subtitle);
            }
        }

        dbHelper.close();
    }
}
