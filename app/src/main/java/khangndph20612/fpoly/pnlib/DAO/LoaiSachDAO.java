package khangndph20612.fpoly.pnlib.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

import khangndph20612.fpoly.pnlib.Sqlite.SqliteOpenHelper;
import khangndph20612.fpoly.pnlib.ThucThe.LoaiSach;

public class LoaiSachDAO {
    static private String TABLE = "LoaiSach";

    static private String maLoai = "maLoai";
    static private String tenLoai = "tenLoai";
    static private String moTa = "moTa";
    static private String viTri = "viTri";
    private SQLiteDatabase db;
    private SqliteOpenHelper openHelper;
    private Context context;

    public LoaiSachDAO(Context context) {
        openHelper = new SqliteOpenHelper(context);
        this.db = openHelper.getWritableDatabase();
    }

    public void addLoaiSach(LoaiSach sach) {
        ContentValues values = new ContentValues();
        values.put(maLoai, sach.getMaLoai());
        values.put(tenLoai, sach.getTenLoai());
        values.put(moTa, sach.getMoTa());
        values.put(viTri, sach.getViTri());
       int i= (int) db.insert(TABLE, null, values);
        Log.d("addLoaiSach", "addLoaiSach: "+i);
    }

    public void removeLoaiSach(LoaiSach sach) {
        db.delete(TABLE, maLoai + "=?", new String[]{sach.getMaLoai()});
    }

    public void updateLoaiSach(LoaiSach sach,String where) {
        ContentValues values = new ContentValues();
        values.put(maLoai, sach.getMaLoai());
        values.put(tenLoai, sach.getTenLoai());
        values.put(moTa, sach.getMoTa());
        values.put(viTri, sach.getViTri());
        db.update(TABLE, values, maLoai + "=?", new String[]{where});
    }

    public List<LoaiSach> getLoaiSach() {
        List<LoaiSach> loaiSaches = new ArrayList<>();
        db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from   " + TABLE, null);
        while (cursor.moveToNext()) {
            loaiSaches.add(new LoaiSach(
                    cursor.getString(0),
                    cursor.getString(1)
                    , cursor.getString(2),
                    cursor.getString(3)));
        }
        return loaiSaches;
    }
}
