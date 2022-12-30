package khangndph20612.fpoly.pnlib;


import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;

import khangndph20612.fpoly.pnlib.DAO.ThuThuDAO;
import khangndph20612.fpoly.pnlib.ThucThe.ThuThu;


public class EditName extends AppCompatActivity {
    TextView txt;
    TextInputLayout textInputLayout;
    int x = 0;
    MenuItem item;
    SpannableString s;
    String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_name);
        txt = findViewById(R.id.txt);
        textInputLayout = findViewById(R.id.a);
        setToolBar();
        textChange();
    }

    private void textChange() {

        textInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s1, int start, int before, int count) {
                if (s1.length() >= 5) {
                    textInputLayout.setErrorEnabled(false);
                    s.setSpan(new ForegroundColorSpan(Color.BLUE), 0, s.length(), 0);
                    item.setEnabled(true);
                    item.setTitle(s);
                    name = s1.toString();
                } else {
                    s.setSpan(new ForegroundColorSpan(Color.GRAY), 0, s.length(), 0);
                    item.setEnabled(false);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
        if (item.getItemId() == R.id.save) {
             ThuThuDAO thuThuDAO = new ThuThuDAO(EditName.this);
            ThuThu thuThu = thuThuDAO.getThuThu(getIntent().getStringExtra("ma"));
            thuThu.setTenTT(textInputLayout.getEditText().getText().toString());
            thuThuDAO.updateThuThu(thuThu);
            finish();
            overridePendingTransition(R.anim.batdau1, R.anim.ketthucfrag);

        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.save, menu);
        int positionOfMenuItem = 0; // or whatever...
        item = menu.getItem(positionOfMenuItem);
        item.setEnabled(false);
        s = new SpannableString("LƯU");
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 0, s.length(), 0);
        item.setTitle(s);
        return true;
    }
}