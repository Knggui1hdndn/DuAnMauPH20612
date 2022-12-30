package khangndph20612.fpoly.pnlib.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import khangndph20612.fpoly.pnlib.Sqlite.SqliteOpenHelper;
import khangndph20612.fpoly.pnlib.ThucThe.ThanhVien;

public class ThanhVienDAO {


    static private String TABLE = "ThanhVien";

    static private String maTV = "maTV";
    static private String tenTV = "tenTV";
    static private String soDT = "soDT";
    static private String img = "img";

    private SQLiteDatabase db;
    private SqliteOpenHelper openHelper;
    private Context context;

    public ThanhVienDAO (Context context) {
        openHelper = new SqliteOpenHelper(context);
        this.db = openHelper.getWritableDatabase();
    }

    public void addThanhVien(ThanhVien thanhVien) {
        ContentValues values = new ContentValues();
        values.put(maTV, thanhVien.getMaTV());
        values.put(tenTV, thanhVien.getTenTV());
        values.put(soDT, thanhVien.getSoDT());
        values.put(img, thanhVien.getImg());

        db.insert(TABLE, null, values);
    }

    public void removeThanhVien(ThanhVien thanhVien) {
        db.delete(TABLE, maTV + "=?", new String[]{thanhVien.getMaTV()});
    }

    public void updateThanhVien(ThanhVien thanhVien,String where) {
        ContentValues values = new ContentValues();
        values.put(maTV, thanhVien.getMaTV());
        values.put(tenTV, thanhVien.getTenTV());
        values.put(soDT, thanhVien.getSoDT());
        values.put(img, thanhVien.getImg());
        db.update(TABLE, values, maTV + "=?", new String[]{where});
    }

    public List<ThanhVien> getThanhVien() {
        List<ThanhVien> loaiSaches = new ArrayList<>();
        db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from   " + TABLE, null);
        while (cursor.moveToNext()) {
            loaiSaches.add(new ThanhVien(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getBlob(3)
              ));
        }
        return loaiSaches;
    }
}



