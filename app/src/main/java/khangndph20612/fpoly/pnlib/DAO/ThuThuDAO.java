package khangndph20612.fpoly.pnlib.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;



import java.util.ArrayList;
import java.util.List;

import khangndph20612.fpoly.pnlib.Sqlite.SqliteOpenHelper;
import khangndph20612.fpoly.pnlib.ThucThe.ThuThu;

public class ThuThuDAO {
    static private String TABLE = "ThuThu";


    static private String maTT = "maTT";
    static private String tenTT = "tenTT";
    static private String soDT = "soDT";
        static private String matKhau = "matKhau";
        static private String img = "img";

    private SQLiteDatabase db;
    private SqliteOpenHelper openHelper;
    private Context context;

    public ThuThuDAO (Context context) {
        openHelper = new SqliteOpenHelper(context);
        this.db = openHelper.getWritableDatabase();
    }

    public void addThuThu(ThuThu ThuThu) {
        ContentValues values = new ContentValues();
        values.put(maTT, ThuThu.getMaTT());
        values.put(tenTT, ThuThu.getTenTT());
        values.put(soDT, ThuThu.getSoDT());
        values.put(matKhau, ThuThu.getMatKhau());
        values.put(img, ThuThu.getImg());

       long i= db.insert(TABLE, null, values);
        Log.d("addThuThu", "addThuThu: "+i);
    }

    public void removeThuThu(ThuThu ThuThu) {
        db.delete(TABLE, maTT + "=?", new String[]{ThuThu.getMaTT()});
    }

    public void updateThuThu(ThuThu ThuThu) {
        ContentValues values = new ContentValues();
         values.put(tenTT, ThuThu.getTenTT());
        values.put(soDT, ThuThu.getSoDT());
        values.put(matKhau, ThuThu.getMatKhau());
        values.put(img, ThuThu.getImg());

      int x=  db.update(TABLE, values, maTT + "=?", new String[]{ThuThu.getMaTT()});
        Log.d("ttrrrrr", "updateThuThu: "+x+ThuThu.toString());
    }
public ThuThu getThuThu(String ma){
    Cursor cursor = db.rawQuery("select * from  ThuThu where maTT ='"+ma+"'", null);
    cursor.moveToFirst();
    return new ThuThu(
            cursor.getString(0),
            cursor.getString(1),
            cursor.getString(2),
            cursor.getString(3),
            cursor.getBlob(4));
}
    public List<ThuThu> getThuThu() {
        List<ThuThu> loaiSaches = new ArrayList<>();
        db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from   " + TABLE, null);
        while (cursor.moveToNext()) {
            loaiSaches.add(new ThuThu(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getBlob(4)

                    ));
        }
        return loaiSaches;
    }
}




