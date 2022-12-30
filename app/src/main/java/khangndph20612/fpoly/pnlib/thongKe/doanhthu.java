package khangndph20612.fpoly.pnlib.thongKe;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.google.android.material.textfield.TextInputLayout;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import khangndph20612.fpoly.pnlib.DAO.PhieuMuonDAO;
import khangndph20612.fpoly.pnlib.R;

public class doanhthu extends AppCompatActivity {
ImageView imgbaDau,imgKetThuc;
TextInputLayout edtBatDau,edtKetThuc;
Button btnDoanhThu;
TextView txtDoanhThu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doanhthu);
        setToolBar();
        edtBatDau=findViewById(R.id.edtBatDau);
        edtKetThuc=findViewById(R.id.edtKetThuc);
        imgKetThuc=findViewById(R.id.imgKetThuc);
        imgbaDau=findViewById(R.id.imgBatDau);
        txtDoanhThu=findViewById(R.id.txtDoanhThu);
        btnDoanhThu=findViewById(R.id.btnDoanhThu);
        edtBatDau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               setDay(edtBatDau);
            }

        });
        edtKetThuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDay(edtKetThuc);
            }
        });
        btnDoanhThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat dateFormatSymbols=new SimpleDateFormat("yyyy-MM-dd");
                try {
                    dateFormatSymbols.parse(edtBatDau.getEditText().getText().toString());
                    dateFormatSymbols.parse(edtKetThuc.getEditText().getText().toString());
txtDoanhThu.setText(new PhieuMuonDAO(doanhthu.this).getDoanhThu(edtBatDau.getEditText().getText().toString(),edtKetThuc.getEditText().getText().toString())+"");
                } catch (ParseException e) {
                    Toast.makeText(doanhthu.this, "Vui lòng nhập đúng định dạng", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setDay(TextInputLayout edtBatDau) {
        Calendar calendar=Calendar.getInstance();
        DatePickerDialog datePickerDialog=new DatePickerDialog(doanhthu.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                edtBatDau.getEditText().setText(year+"-"+month+"-"+dayOfMonth);
            }
        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
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
}