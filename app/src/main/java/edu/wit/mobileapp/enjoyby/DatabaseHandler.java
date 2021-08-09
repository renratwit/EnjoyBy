package edu.wit.mobileapp.enjoyby;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "enjoy_by_db";
    private static final int DB_VERSION = 1;

    public DatabaseHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + ListItem.SQL.Table + " ("
                + ListItem.SQL.Id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ListItem.SQL.Name + " TEXT,"
                + ListItem.SQL.PurchaseDate + " INT,"
                + ListItem.SQL.ExpireDate + " INT)";

        db.execSQL(query);
    }

    public void createFoodItem(String name, Date purchaseDate, Date expireDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        expireDate = setEndOfDay(expireDate);

        values.put(ListItem.SQL.Name, name);
        values.put(ListItem.SQL.PurchaseDate, purchaseDate.getTime());
        values.put(ListItem.SQL.ExpireDate, expireDate.getTime());

        db.insert(ListItem.SQL.Table, null, createFoodItemContentValues(name, purchaseDate, expireDate));

        db.close();
    }

    private Date setEndOfDay(Date date) {
        final Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.HOUR, 11);
        calendar.set(Calendar.AM_PM, Calendar.PM);

        return calendar.getTime();
    }

    private ContentValues createFoodItemContentValues(String name, Date purchaseDate, Date expireDate) {
        ContentValues values = new ContentValues();

        values.put(ListItem.SQL.Name, name);
        values.put(ListItem.SQL.PurchaseDate, purchaseDate.getTime());
        values.put(ListItem.SQL.ExpireDate, expireDate.getTime());

        return values;
    }

    public List<ListItem> getFoodItems() {
        List items = getFreshFoodItems();
        items.addAll(getExpiredFoodItems());
        return items;
    }

    public List<ListItem> getExpiredFoodItems() {
        String selection = ListItem.SQL.ExpireDate + " < ?";
        String[] selectionArgs = { Long.toString(new Date().getTime()) };
        return getFoodItems(selection, selectionArgs,true);
    }

    public List<ListItem> getFreshFoodItems() {
        String selection = ListItem.SQL.ExpireDate + " > ?";
        String[] selectionArgs = { Long.toString(new Date().getTime()) };
        return getFoodItems(selection, selectionArgs, true);
    }

    public List<ListItem> getFoodItems(String selection, String[] selectionArgs, Boolean asc) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                ListItem.SQL.Id,
                ListItem.SQL.Name,
                ListItem.SQL.PurchaseDate,
                ListItem.SQL.ExpireDate
        };

        String sortOrder = ListItem.SQL.ExpireDate + " " + (asc ? "ASC" : "DESC");

        Cursor cursor = db.query(
                ListItem.SQL.Table,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        List<ListItem> items = new ArrayList<>();

        while(cursor.moveToNext()) {
            ListItem item = new ListItem();
            item.Id = cursor.getInt(cursor.getColumnIndexOrThrow(ListItem.SQL.Id));
            item.name = cursor.getString(cursor.getColumnIndexOrThrow(ListItem.SQL.Name));
            item.purchaseDate = castLongToDate(cursor.getLong(cursor.getColumnIndexOrThrow(ListItem.SQL.PurchaseDate)));
            item.expireDate = castLongToDate(cursor.getLong(cursor.getColumnIndexOrThrow(ListItem.SQL.ExpireDate)));

            items.add(item);
        }
        cursor.close();

        db.close();

        return items;
    }

    private Date castLongToDate(long dateLong) {
        return new Date(dateLong);
    }

    public void updateFoodItem(int id, String name, Date purchaseDate, Date expireDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(ListItem.SQL.Table, createFoodItemContentValues(name, purchaseDate, expireDate), ListItem.SQL.Id + " = ?", new String[]{ Integer.toString(id) });
        db.close();
    }

    public void deleteFoodItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ListItem.SQL.Table, ListItem.SQL.Id + " = ?", new String[]{ Integer.toString(id) });
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + ListItem.SQL.Table);
        onCreate(db);
    }
}
