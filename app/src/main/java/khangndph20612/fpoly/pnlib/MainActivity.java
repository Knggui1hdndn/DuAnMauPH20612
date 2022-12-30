package khangndph20612.fpoly.pnlib;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;


import khangndph20612.fpoly.pnlib.DAO.ThuThuDAO;
import khangndph20612.fpoly.pnlib.ThucThe.ThuThu;

public class MainActivity extends AppCompatActivity {
    TextInputLayout tk, mk;
    CheckBox ck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tk = findViewById(R.id.tk);
        mk = findViewById(R.id.mk);
        ck = findViewById(R.id.ck);
        findViewById(R.id.huy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tk.getEditText().setText("");
                mk.getEditText().setText("");
                ck.setChecked(false);
            }
        });
        SharedPreferences preferences = getSharedPreferences("admin", MODE_PRIVATE);
        if (preferences.getString("tk", null) == null) {
            preferences.edit().putString("tk", "admin").apply();
            preferences.edit().putString("mk", "admin").apply();
        }
        SharedPreferences preferences1 = getSharedPreferences("thuthu", MODE_PRIVATE);
        SharedPreferences preferences2 = getSharedPreferences("login", MODE_PRIVATE);
        if (preferences2.getBoolean("ghi", false) == true &&
                preferences2.getString("login", "").equals("admin")) {
            tk.getEditText().setText(preferences.getString("tk", ""));
            mk.getEditText().setText(preferences.getString("mk", ""));
            ck.setChecked(true);
        } else if (preferences2.getBoolean("ghi", false) == true &&
                preferences2.getString("login", "").equals("thuthu")) {
            tk.getEditText().setText(preferences1.getString("tk", ""));
            mk.getEditText().setText(preferences1.getString("mk", ""));
            ck.setChecked(true);

        }
        ck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    preferences2.edit().putBoolean("ghi", true).apply();
                } else {
                    preferences2.edit().putBoolean("ghi", false).apply();

                }
            }
        });


        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = 0;
                for (ThuThu thuThu : new ThuThuDAO(MainActivity.this).getThuThu()) {
                    if (thuThu.getMaTT().equals(tk.getEditText().getText().toString()) && thuThu.getMatKhau().equals(mk.getEditText().getText().toString())) {
                        preferences1.edit().putString("tk", thuThu.getMaTT()).apply();
                        preferences1.edit().putString("mk", thuThu.getMatKhau()).apply();
                        preferences2.edit().putString("login", "thuthu").apply();
                        i = 1;
                        break;
                    }
                }
                if ((tk.getEditText().getText().toString().equals("admin") && mk.getEditText().getText().toString().equals(mk.getEditText().getText().toString())) || i == 1) {
                    if (i != 1) {
                        preferences2.edit().putString("login", "admin").apply();
                    }
                    Intent intent=new Intent(MainActivity.this,  GiaoDienChinh.class);
                    intent.putExtra("tk",tk.getEditText().getText().toString());
                    startActivity(intent);
                } else {
                    tk.setErrorEnabled(true);
                    mk.setErrorEnabled(true);

                }
            }
        });
    }
}