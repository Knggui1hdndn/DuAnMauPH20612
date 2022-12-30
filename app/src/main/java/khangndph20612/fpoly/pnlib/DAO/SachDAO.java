package khangndph20612.fpoly.pnlib.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;



import java.util.ArrayList;
import java.util.List;

import khangndph20612.fpoly.pnlib.Sqlite.SqliteOpenHelper;
import khangndph20612.fpoly.pnlib.ThucThe.Sach;

public class SachDAO {

    static private String TABLE = "Sach";

    static private String maSach = "maSach";
    static private String maLoai = "maLoai";
    static private String tieuDe = "tieuDe";
    static private String tacGia = "tacGia";
    static private String nhaXB = "nhaXB";
    static private String giaBan = "giaBan";
    static private String soLuong = "soLuong";
    private SQLiteDatabase db;
    private SqliteOpenHelper openHelper;
    private Context context;

    public SachDAO(Context context) {
        openHelper = new SqliteOpenHelper(context);
        this.db = openHelper.getWritableDatabase();
    }

    public void addSach(Sach sach) {
        ContentValues values = new ContentValues();
        values.put(maSach, sach.getMaSach());
        values.put(maLoai, sach.getMaLoai());
        values.put(tieuDe, sach.getTieuDe());
        values.put(tacGia, sach.getGiaBan());
        values.put(nhaXB, sach.getNhaXB());
        values.put(giaBan, sach.getGiaBan());
        values.put(soLuong, sach.getSoLuong());
        values.put("img", sach.getImg());
        int i = (int) db.insert(TABLE, null, values);
        Log.d("addLoaiSach", "addLoaiSach: 1" + i);

    }

    public void removeSach(Sach sach) {
        db.delete(TABLE, maSach + "=?", new String[]{sach.getMaSach()});
    }

    public void updateSach(Sach sach, String where) {
        ContentValues values = new ContentValues();
        values.put(maSach, sach.getMaSach());
        values.put(maLoai, sach.getMaLoai());
        values.put(tieuDe, sach.getTieuDe());
        values.put(tacGia, sach.getGiaBan());
        values.put(nhaXB, sach.getNhaXB());
        values.put(giaBan, sach.getGiaBan());
        values.put(soLuong, sach.getSoLuong());
        values.put("img", sach.getImg());

        db.update(TABLE, values, maSach + "=?", new String[]{where});
    }

    public List<Sach> getSach() {
        List<Sach> loaiSaches = new ArrayList<>();
        db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from   " + TABLE, null);
        while (cursor.moveToNext()) {
            loaiSaches.add(new Sach(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getBlob(7)));
        }
        return loaiSaches;
    }

    public List<Sach> top10SachMuonNhieu() {
        List<Sach> sachList=new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT Sach.maSach, COUNT(PhieuMuon.maPM),Sach.maSach,Sach.maLoai,Sach.tieuDe,Sach.giaBan   FROM Sach " +
                "INNER JOIN PhieuMuon ON Sach.maSach = PhieuMuon.maSach GROUP BY Sach.maSach " +
                "order by COUNT(PhieuMuon.maPM) DESC;", null);
        while (cursor.moveToNext()) {
            sachList.add(new Sach(cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5)));
        }
        cursor.close();
    return sachList;
    }
}


