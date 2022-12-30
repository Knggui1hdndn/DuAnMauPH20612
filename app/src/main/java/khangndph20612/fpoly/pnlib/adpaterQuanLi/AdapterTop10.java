package khangndph20612.fpoly.pnlib.adpaterQuanLi;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import khangndph20612.fpoly.pnlib.R;
import khangndph20612.fpoly.pnlib.ThucThe.Sach;

public class AdapterTop10 extends RecyclerView.Adapter<AdapterTop10.AdapterQuanLiLoaiSachViewHolder> {
    private List<Sach> loaiSaches;

    public void setData(List<Sach> loaiSaches) {
        this.loaiSaches = loaiSaches;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public AdapterQuanLiLoaiSachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterQuanLiLoaiSachViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top10, null));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AdapterQuanLiLoaiSachViewHolder holder, int position) {
        Sach sach = loaiSaches.get(position);
        holder.txtMaLoaiSach.setText((spannableString("Mã loại sách:" + sach.getMaLoai())));
        holder.txtTenLoaiSach.setText((spannableString("Tên sách:" + sach.getTieuDe() + " - ")));
        holder.txtMoTa.setText(spannableString("Giá Bán:" + sach.getGiaBan()));
        holder.imgDelete.setText(spannableString("TOP:" + (position + 1)));


    }

    public static SpannableString spannableString(String s) {
        SpannableString string = new SpannableString(s);
        int i = s.indexOf(":") + 1;
        if (i > 0) {
            string.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 0, i, 0);
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
        TextView imgDelete;
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
