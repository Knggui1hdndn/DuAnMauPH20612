package khangndph20612.fpoly.pnlib.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import khangndph20612.fpoly.pnlib.Sqlite.SqliteOpenHelper;
import khangndph20612.fpoly.pnlib.ThucThe.PhieuMuon;

public class PhieuMuonDAO {

    static private String TABLE = "PhieuMuon";

    static private String maPM = "maPM";
    static private String maTT = "maTT";
    static private String maTV = "maTV";
    static private String maSach = "maSach";
    static private String tienThue = "tienThue";
    static private String ngayMuon = "ngayMuon";
    static private String traSach = "traSach";
    private SQLiteDatabase db;
    private SqliteOpenHelper openHelper;
    private Context context;

    public PhieuMuonDAO(Context context) {
        openHelper = new SqliteOpenHelper(context);
        this.db = openHelper.getWritableDatabase();
    }

    public int getDoanhThu(String start, String end) {
        Cursor cursor = db.rawQuery("select sum(tienThue) from PhieuMuon where PhieuMuon.ngayMuon >='" + start + "' AND   PhieuMuon.ngayMuon <='" + end + "'", null);
        cursor.moveToFirst();
        int tien = cursor.getInt(0);
        cursor.close();
        return tien;
    }

    public void addPhieuMuon(PhieuMuon phieuMuon) {
        Calendar calendar=Calendar.getInstance();
        ContentValues values = new ContentValues();
        values.put(maPM, phieuMuon.getMaPM());
        values.put(maTT, phieuMuon.getMaTT());
        values.put(maTV, phieuMuon.getMaTV());
        values.put(maSach, phieuMuon.getMaSach());
        values.put(tienThue, phieuMuon.getTienThue());
        values.put(ngayMuon, phieuMuon.getNgayMuon());
        values.put(traSach, phieuMuon.getTraSach());
        values.put("gio",calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE) );
        long i = db.insert(TABLE, null, values);
        Log.d("addPhieuMuon", "addPhieuMuon: " + i);
    }

    public void addColum() {
try {
    db.execSQL("ALTER TABLE PhieuMuon ADD COLUMN gio Text");

}catch (Exception e){

}
    }

    public void removePhieuMuon(PhieuMuon phieuMuon) {
      int x=  db.delete(TABLE, maPM + "=?", new String[]{phieuMuon.getMaPM()});
     }

    public void updatePhieuMuon(PhieuMuon phieuMuon, String where) {
        ContentValues values = new ContentValues();
        values.put(maPM, phieuMuon.getMaPM());
        values.put(maTT, phieuMuon.getMaTT());
        values.put(maTV, phieuMuon.getMaTV());
        values.put(maSach, phieuMuon.getMaSach());
        values.put(tienThue, phieuMuon.getTienThue());
        values.put(ngayMuon, phieuMuon.getNgayMuon());
        values.put(traSach, phieuMuon.getTraSach());
        db.update(TABLE, values, maPM + "=?", new String[]{where});
    }

    public List<PhieuMuon> getPhieuMuon() {
        List<PhieuMuon> loaiSaches = new ArrayList<>();
        db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from   " + TABLE, null);
        while (cursor.moveToNext()) {
            Log.d("ahxh", "getPhieuMuon: " + cursor.getString(2));
            loaiSaches.add(new PhieuMuon(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4),
                    cursor.getString(5),
                    cursor.getInt(6), cursor.getString(7)));
        }
        return loaiSaches;
    }

    public String getTenThanhVienMuon(String maTV) {
        db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select *  from ThanhVien where maTV='" + maTV + "'", null);
        cursor.moveToFirst();
        String s = cursor.getString(0);
        cursor.close();
        return s;
    }

    public String getTenSachMuon(String maSach) {
        db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select Sach.tieuDe from Sach where maSach='" + maSach + "'", null);
        cursor.moveToFirst();
        String s = cursor.getString(0);
        cursor.close();
        return s;
    }
}

