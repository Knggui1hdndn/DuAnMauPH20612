package khangndph20612.fpoly.pnlib.adpaterQuanLi;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import khangndph20612.fpoly.pnlib.DAO.LoaiSachDAO;
import khangndph20612.fpoly.pnlib.R;
import khangndph20612.fpoly.pnlib.ThucThe.LoaiSach;
import khangndph20612.fpoly.pnlib.bottomSheetDialogFragment.bottomsheetdialogLoaiSach;
import khangndph20612.fpoly.pnlib.quanli.quanliloaisach;

public class AdapterQuanLiLoaiSach extends RecyclerView.Adapter<AdapterQuanLiLoaiSach.AdapterQuanLiLoaiSachViewHolder> {
    private List<LoaiSach> loaiSaches;
    LoaiSachDAO sachDAO;
 khangndph20612.fpoly.pnlib.quanli.quanliloaisach quanliloaisach;
    public void setData(List<LoaiSach> loaiSaches) {
        this.loaiSaches = loaiSaches;
        notifyDataSetChanged();
    }

    public AdapterQuanLiLoaiSach(quanliloaisach quanliloaisach) {
        this.quanliloaisach=quanliloaisach;
    }

    @NonNull
    @Override
    public AdapterQuanLiLoaiSachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        sachDAO = new LoaiSachDAO(parent.getContext());
        return new AdapterQuanLiLoaiSachViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_qlloaisach, null));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AdapterQuanLiLoaiSachViewHolder holder, int position) {
        LoaiSach sach = loaiSaches.get(position);
        holder.txtMaLoaiSach.setText(sach.getMaLoai());
        holder.txtTenLoaiSach.setText(sach.getTenLoai() + " - ");
        holder.txtViTri.setText(spannableString("Vị trí:" + sach.getViTri()));
        holder.txtMoTa.setText(spannableString("Mô tả:" + sach.getMoTa()));
        holder.imgDelete.setOnClickListener(v -> {
            sachDAO.removeLoaiSach(sach);
            loaiSaches.remove(position);
            notifyDataSetChanged();
        });

        holder.mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomsheetdialogLoaiSach loaiSach=new bottomsheetdialogLoaiSach();
                Bundle bundle=new Bundle();
                bundle.putString("update","update");
                bundle.putSerializable("loaisach",sach);
                loaiSach.setArguments(bundle);
                loaiSach.show(quanliloaisach.getSupportFragmentManager(), "tag");
            }
        });
    }

    public static SpannableString spannableString(String s) {
        SpannableString string = new SpannableString(s);
        int i=s.indexOf(":")+1;
        if(i>0){
            string.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 0,i , 0);
            string.setSpan(new ForegroundColorSpan(Color.BLACK), 0, i, 0);
        }

        return string;
    }

    @Override
    public int getItemCount() {
        return loaiSaches.size();
    }

    public class AdapterQuanLiLoaiSachViewHolder extends RecyclerView.ViewHolder {
        TextView txtMaLoaiSach, txtTenLoaiSach, txtMoTa, txtViTri;
        ImageView imgDelete;
RelativeLayout mRelativeLayout;
        public AdapterQuanLiLoaiSachViewHolder(@NonNull View itemView) {
            super(itemView);
            mRelativeLayout = itemView.findViewById(R.id.mRelativeLayout);

            txtMaLoaiSach = itemView.findViewById(R.id.txtMaLoaiSach);
            txtTenLoaiSach = itemView.findViewById(R.id.txtTenLoaiSach);
            txtMoTa = itemView.findViewById(R.id.txtMoTa);
            txtViTri = itemView.findViewById(R.id.txtViTri);
            imgDelete = itemView.findViewById(R.id.imgDelete);

        }
    }
}
