package khangndph20612.fpoly.pnlib.quanli;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

 import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;
import java.util.List;

import khangndph20612.fpoly.pnlib.DAO.ThanhVienDAO;
import khangndph20612.fpoly.pnlib.R;
import khangndph20612.fpoly.pnlib.ThucThe.ThanhVien;
import khangndph20612.fpoly.pnlib.adpaterQuanLi.AdapterQuanLiThanhVien;
import khangndph20612.fpoly.pnlib.bottomSheetDialogFragment.bottomsheetdialog;
import khangndph20612.fpoly.pnlib.loadRcy;

public class quanlithanhvien extends AppCompatActivity implements loadRcy {
FloatingActionButton button;
RecyclerView recyclerView;
ThanhVienDAO thanhVienDAO;  AdapterQuanLiThanhVien adapterQlSach;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanlinguoidung);
        button=findViewById(R.id.action);
        thanhVienDAO=new ThanhVienDAO(this);
        if(getIntent().getStringExtra("S")!=null){
            bottomsheetdialog bottomsheetdialog=new bottomsheetdialog();
            bottomsheetdialog.show(getSupportFragmentManager(),"tag");
        }
        recyclerView = findViewById(R.id.rcy);
           adapterQlSach = new AdapterQuanLiThanhVien();
        adapterQlSach.setData(setData());
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapterQlSach);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomsheetdialog bottomsheetdialog=new bottomsheetdialog();
                bottomsheetdialog.show(getSupportFragmentManager(),"tag");
            }
        });
        setToolBar();
    }
    private List<ThanhVien> setData() {
        List<ThanhVien>list=new ArrayList<>();
        list.addAll(thanhVienDAO.getThanhVien());
        return list;
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
        if(item.getItemId()==android.R.id.home){
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