package khangndph20612.fpoly.pnlib.Sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SqliteOpenHelper extends SQLiteOpenHelper {
    public SqliteOpenHelper(@Nullable Context context) {
        super(context, "DuAnMau", null, 1);
    }

    String phieumuon = "create table PhieuMuon(maPM TEXT not null PRIMARY key ," +
            " maTT TEXT  REFERENCES ThuThu(maTT)," +
            "maTV Text REFERENCES ThanhVien(maTV)," +
            "maSach Text REFERENCES Sach(maSach)," +
            "tienThue integer not null," +
            "ngayMuon Text not null,traSach integer not null)";

    String thuthu = "create table ThuThu(" +
            "maTT TEXT not null PRIMARY key ," +
            " tenTT TEXT   not null," +

            "soDT Text not null," +
            "matKhau Text not null," +
            "img blog  )";


    String sach = "create table Sach(maSach TEXT not null PRIMARY key ," +
            " maLoai TEXT  REFERENCES LoaiSach(maLoai)," +
            "tieuDe Text not null," +
            "tacGia Text not null," +
            "nhaXB Text not null," +
            "giaBan Text not null," +
            "soLuong Text not null ," +
            "img blog)";

    String loaisach = "create table LoaiSach(maLoai TEXT not null PRIMARY key ," +
            "tenLoai Text not null," +
            "moTa Text not null," +
            "viTri Text not null )";


    String thanhvien = "create table ThanhVien(maTV TEXT not null PRIMARY key ," +
            "tenTV Text not null," +
            "soDT Text not null," +
            "img blog  )";

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(thanhvien);
        db.execSQL(thuthu);
        db.execSQL(loaisach);
        db.execSQL(sach);
        db.execSQL(phieumuon);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
