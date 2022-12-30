package khangndph20612.fpoly.pnlib.bottomSheetDialogFragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

 import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.IOException;

import khangndph20612.fpoly.pnlib.GiaoDienChinh;
import khangndph20612.fpoly.pnlib.R;

public class bottomsheetdialogselectedimg extends BottomSheetDialogFragment {
    GiaoDienChinh context;

    public bottomsheetdialogselectedimg(GiaoDienChinh context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.selecimg, null);

        Button button = view.findViewById(R.id.btnCam);
        Button button1 = view.findViewById(R.id.btnGalary);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 2);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(i, 1);

            }
        });
        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            Log.d("kkkkkkkk", "onActivityResult: " + data.getData());
            try {
                if (requestCode == 2) {
                    if (data.getExtras().get("data") != null) {
                        context.editInfo((Bitmap) data.getExtras().get("data"));
                    }
                } else {
                    context.editInfo(MediaStore.Images.Media.getBitmap(context.getContentResolver(), data.getData()));

                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                dismiss();
            }

        }
    }
}
