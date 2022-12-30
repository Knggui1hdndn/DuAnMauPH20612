package khangndph20612.fpoly.pnlib.quanli;

import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import khangndph20612.fpoly.pnlib.DAO.LoaiSachDAO;
import khangndph20612.fpoly.pnlib.R;
import khangndph20612.fpoly.pnlib.Sqlite.SqliteOpenHelper;
import khangndph20612.fpoly.pnlib.ThucThe.LoaiSach;
import khangndph20612.fpoly.pnlib.ThucThe.Sach;
import khangndph20612.fpoly.pnlib.ThucThe.sach1;
import khangndph20612.fpoly.pnlib.adapterQlSach;
import khangndph20612.fpoly.pnlib.bottomSheetDialogFragment.bottomsheetdialogSach;
import khangndph20612.fpoly.pnlib.loadRcy;

public class quanlisach extends AppCompatActivity implements loadRcy, Serializable {
    RecyclerView recyclerView;
    khangndph20612.fpoly.pnlib.adapterQlSach adapterQlSach;
    FloatingActionButton button;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanlisach);
        button = findViewById(R.id.action);
        searchView = findViewById(R.id.Search);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomsheetdialogSach bottomsheetdialog = new bottomsheetdialogSach(quanlisach.this);
                bottomsheetdialog.show(getSupportFragmentManager(), "tag");
            }
        });
        setToolBar();
        recyclerView = findViewById(R.id.rcy);
        adapterQlSach = new adapterQlSach();
        adapterQlSach.setData(setData());
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapterQlSach);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapterQlSach.setData(setData1(Integer.parseInt(query)));

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.equals("")){
                    adapterQlSach.setData(setData());

                }
                return true;
            }
        });
    }

    private List<sach1> setData1(int soLuong) {
        List<Sach> sach1;
        List<sach1> sach1List = new ArrayList<>();
        String loaiSach = null;

        for (LoaiSach sach : new LoaiSachDAO(quanlisach.this).getLoaiSach()) {
            sach1 = new ArrayList<Sach>();
            Cursor cursor = new SqliteOpenHelper(quanlisach.this).getReadableDatabase().rawQuery(
                    "select Sach.maSach,Sach.maLoai,Sach.tieuDe,Sach.tacGia,Sach.nhaXB," +
                            "Sach.giaBan,Sach.soLuong,Sach.img,LoaiSach.tenLoai " +
                            "from LoaiSach inner join Sach on LoaiSach.maLoai=Sach.maLoai" +
                            " where LoaiSach.maLoai='" + sach.getMaLoai() + "'", null);

            while (cursor.moveToNext()) {
                loaiSach = cursor.getString(8);
                if(Integer.parseInt(cursor.getString(6))<=soLuong){
                    sach1.add(new Sach(
                            cursor.getString(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getString(6),
                            cursor.getBlob(7)));
                }

            }
            cursor.close();
            if (sach1.size() > 0) {
                sach1List.add(new sach1(loaiSach, sach1));
            }
        }
        return sach1List;
    }

    private List<sach1> setData() {
        List<Sach> sach1;
        List<sach1> sach1List = new ArrayList<>();
        String loaiSach = null;

        for (LoaiSach sach : new LoaiSachDAO(quanlisach.this).getLoaiSach()) {
            sach1 = new ArrayList<Sach>();
            Cursor cursor = new SqliteOpenHelper(quanlisach.this).getReadableDatabase().rawQuery(
                    "select Sach.maSach,Sach.maLoai,Sach.tieuDe,Sach.tacGia,Sach.nhaXB," +
                            "Sach.giaBan,Sach.soLuong,Sach.img,LoaiSach.tenLoai " +
                            "from LoaiSach inner join Sach on LoaiSach.maLoai=Sach.maLoai" +
                            " where LoaiSach.maLoai='" + sach.getMaLoai() + "'", null);

            while (cursor.moveToNext()) {
                loaiSach = cursor.getString(8);
                sach1.add(new Sach(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getBlob(7)));
            }
            cursor.close();
            if (sach1.size() > 0) {
                sach1List.add(new sach1(loaiSach, sach1));
            }
        }
        return sach1List;
    }


    public void setToolBar() {
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setDisplayHomeAsUpEnabled(true);//set icon tren toolbar
        actionbar.setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_backspace_245);//set icon menu
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.batdau1, R.anim.ketthucfrag);
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.batdau1, R.anim.ketthucfrag);
        }
        return true;
    }

    @Override
    public void loadRcy() {
        adapterQlSach.setData(setData());
    }
}