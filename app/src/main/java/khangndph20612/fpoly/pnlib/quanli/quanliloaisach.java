package khangndph20612.fpoly.pnlib.quanli;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

 import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;
import java.util.List;

import khangndph20612.fpoly.pnlib.DAO.LoaiSachDAO;
import khangndph20612.fpoly.pnlib.R;
import khangndph20612.fpoly.pnlib.ThucThe.LoaiSach;
import khangndph20612.fpoly.pnlib.adpaterQuanLi.AdapterQuanLiLoaiSach;
import khangndph20612.fpoly.pnlib.bottomSheetDialogFragment.bottomsheetdialogLoaiSach;
import khangndph20612.fpoly.pnlib.loadRcy;

public class quanliloaisach extends AppCompatActivity implements loadRcy {
    FloatingActionButton button;
    RecyclerView recyclerView;
    LoaiSachDAO sachDAO;
    AdapterQuanLiLoaiSach adapterQlSach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanliloaisach);
        button = findViewById(R.id.action);
        recyclerView = findViewById(R.id.rcy);
        sachDAO = new LoaiSachDAO(this);
        adapterQlSach = new AdapterQuanLiLoaiSach(this);
        adapterQlSach.setData(setData());
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapterQlSach);
        button.setOnClickListener(v -> {
            bottomsheetdialogLoaiSach bottomsheetdialog = new bottomsheetdialogLoaiSach();
            bottomsheetdialog.show(getSupportFragmentManager(), "tag");
        });
        setToolBar();
    }

    private List<LoaiSach> setData() {
        List<LoaiSach> list = new ArrayList<>();
        list.addAll(sachDAO.getLoaiSach());
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