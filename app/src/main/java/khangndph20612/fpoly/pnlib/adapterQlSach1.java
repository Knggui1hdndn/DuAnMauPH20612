package khangndph20612.fpoly.pnlib;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import khangndph20612.fpoly.pnlib.DAO.SachDAO;
import khangndph20612.fpoly.pnlib.ThucThe.Sach;
import khangndph20612.fpoly.pnlib.bottomSheetDialogFragment.bottomsheetdialogSach;
import khangndph20612.fpoly.pnlib.quanli.quanlisach;

public class adapterQlSach1 extends RecyclerView.Adapter<adapterQlSach1.adapterQlSachViewholder> {
    private List<Sach> sachList;
    SachDAO sachDAO;
 quanlisach quanlisach;

    public void setData(List<Sach> sachList) {
        this.sachList = sachList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public adapterQlSachViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        sachDAO = new SachDAO(parent.getContext());
        quanlisach = (khangndph20612.fpoly.pnlib.quanli.quanlisach) parent.getContext();
        return new adapterQlSachViewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_sach, null));
    }

    @Override
    public void onBindViewHolder(@NonNull adapterQlSachViewholder holder, @SuppressLint("RecyclerView") int position) {
        Sach sach = sachList.get(position);
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sachDAO.removeSach(sach);
                sachList.remove(position);
                notifyDataSetChanged();
            }
        });
        holder.gia.setText("$"+sach.getGiaBan());
        holder.soLuong.setText("Số lượng sách"+sach.getSoLuong());
        holder.txtMaSach.setText(sach.getMaSach() + "");
        holder.txtTieuDe.setText(sach.getTieuDe());
        holder.soLuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Dialog dialog=new Dialog(v.getContext());
                dialog.setTitle("Chỉnh sửa số lượng sách");
                dialog.getWindow().setGravity(Gravity.CENTER);
                dialog.setContentView(R.layout.itemdialogsoluong);
                dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);

                TextView ten=dialog.findViewById(R.id.tenSach);
                Button btnClose=dialog.findViewById(R.id.btnClose);
                Button btnSave=dialog.findViewById(R.id.btnSave);
                ten.setText(sach.getTieuDe());
                EditText edtSoLuong=dialog.findViewById(R.id.edtSoLuong);
                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sach.setSoLuong(edtSoLuong.getText().toString());
                        sachDAO.updateSach(sach,sach.getMaSach());
                        notifyDataSetChanged();
                        dialog.cancel();
                        Toast.makeText(v.getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
             }
        });
        holder.mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomsheetdialogSach bottomsheetdialogSach = new bottomsheetdialogSach(quanlisach);
                Bundle bundle = new Bundle();
                bundle.putSerializable("sach", sach);
                bottomsheetdialogSach.setArguments(bundle);
                bottomsheetdialogSach.show(quanlisach.getSupportFragmentManager(), "tag");
            }
        });
        if (sach.getImg() != null) {
            holder.ìmg.setImageBitmap(BitmapFactory.decodeByteArray(sach.getImg(), 0, sach.getImg().length));
        }
    }

    @Override
    public int getItemCount() {
        return sachList.size();
    }

    public class adapterQlSachViewholder extends RecyclerView.ViewHolder {
        ImageView ìmg, imgDelete;
        TextView txtMaSach, txtTieuDe,gia,soLuong;
        CardView mRelativeLayout;

        public adapterQlSachViewholder(@NonNull View itemView) {
            super(itemView);
            soLuong = itemView.findViewById(R.id.soLuong);
            gia = itemView.findViewById(R.id.gia);
            mRelativeLayout = itemView.findViewById(R.id.mRelativeLayout);
            txtMaSach = itemView.findViewById(R.id.txtMaSach);
            ìmg = itemView.findViewById(R.id.ìmg);
            txtTieuDe = itemView.findViewById(R.id.txtTieuDe);
            imgDelete = itemView.findViewById(R.id.imgDelete);
        }
    }
}
