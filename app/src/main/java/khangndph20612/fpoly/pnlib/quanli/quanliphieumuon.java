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


import java.util.ArrayList;
import java.util.List;

import khangndph20612.fpoly.pnlib.DAO.PhieuMuonDAO;
import khangndph20612.fpoly.pnlib.R;
import khangndph20612.fpoly.pnlib.ThucThe.PhieuMuon;
import khangndph20612.fpoly.pnlib.adpaterQuanLi.AdapterQuanLiPhieuMuon;
import khangndph20612.fpoly.pnlib.bottomSheetDialogFragment.bottomsheetdialogthemphiuemuon;
import khangndph20612.fpoly.pnlib.loadRcy;

public class quanliphieumuon extends AppCompatActivity implements loadRcy {
RecyclerView recyclerView;
PhieuMuonDAO phieuMuonDAO;
    AdapterQuanLiPhieuMuon adapterQlSach;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanliphieumuon);
        setToolBar();
        recyclerView = findViewById(R.id.rcy);
         phieuMuonDAO=new PhieuMuonDAO(this);
          adapterQlSach = new AdapterQuanLiPhieuMuon();
         phieuMuonDAO.addColum();
         adapterQlSach.setData(setData());
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapterQlSach);
        findViewById(R.id.action).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomsheetdialogthemphiuemuon bottomsheetdialog=new bottomsheetdialogthemphiuemuon();
                bottomsheetdialog.show(getSupportFragmentManager(),"tag");
            }
        });
    }

    private List<PhieuMuon> setData() {
        List<PhieuMuon>list=new ArrayList<>();
        list.addAll(phieuMuonDAO.getPhieuMuon());
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