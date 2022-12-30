package khangndph20612.fpoly.pnlib.taiKhoan;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

 import com.google.android.material.textfield.TextInputLayout;

import java.util.Locale;

import khangndph20612.fpoly.pnlib.R;
import khangndph20612.fpoly.pnlib.Sqlite.SqliteOpenHelper;

public class doimatkhau extends AppCompatActivity {
    TextInputLayout edtmkCu, edtmk, edtNhapLai;
    Button btnChange, btnHuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doimatkhau);
        edtmkCu = findViewById(R.id.edtmkCu);
        edtmk = findViewById(R.id.edtmk);
        edtNhapLai = findViewById(R.id.edtNhapLai);
        btnHuy = findViewById(R.id.btnHuy);
        btnChange = findViewById(R.id.btnChange);
        setToolBar();

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = check(edtmk);
                int x1 = check(edtmkCu);
                int x2 = check(edtNhapLai);
                SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
                String s = preferences.getString("login", null);
                if (x == 0 && x1 == 0 && x2 == 0) {
                    if (!edtmk.getEditText().getText().toString().equals(edtNhapLai.getEditText().getText().toString())) {
                        edtNhapLai.setErrorEnabled(true);
                        edtNhapLai.setError("Nhập lại không khớp");
                    } else {
                        SQLiteDatabase db = new SqliteOpenHelper(doimatkhau.this).getWritableDatabase();
                        if (s.equals("admin")) {
                            SharedPreferences preferences1 = getSharedPreferences("admin", MODE_PRIVATE);
                            preferences1.edit().putString("mk", edtmk.getEditText().getText().toString()).apply();
                            preferences.edit().putString("login", "admin").apply();
                        } else {
                            SharedPreferences preferences1 = getSharedPreferences("thuthu", MODE_PRIVATE);
                            preferences.edit().putString("login", "thuthu").apply();

                            db.rawQuery(" UPDATE ThuThu" +
                                    "SET matKhau = '" + edtmk.getEditText().getText().toString()+"' WHERE maTT = '" + s + "'", null);
                            preferences1.edit().putString("mk", edtmk.getEditText().getText().toString()).apply();
                        }
finish();

                    }
                }
            }
        });
    }

    private int check(TextInputLayout inputLayout) {
        final int[] x = {0};
        inputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    inputLayout.setErrorEnabled(false);
                    x[0] = 0;

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        if (inputLayout.getEditText().getText().toString().length() == 0) {
            inputLayout.setErrorEnabled(true);
            inputLayout.setError("Không để trống " + inputLayout.getHint().toString().toLowerCase(Locale.ROOT));
            x[0] = 1;
        }

        return x[0];
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
}