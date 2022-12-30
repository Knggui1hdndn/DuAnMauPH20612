package khangndph20612.fpoly.pnlib;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import khangndph20612.fpoly.pnlib.ThucThe.sach1;

public class adapterQlSach extends RecyclerView.Adapter<adapterQlSach.adapterQlSachViewholder> {
    List<sach1> sach1s;
    Context context;

    public void setData(List<sach1> sach1s) {
        this.sach1s = sach1s;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public adapterQlSachViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new adapterQlSachViewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_sach1, null));
    }

    @Override
    public void onBindViewHolder(@NonNull adapterQlSachViewholder holder, int position) {
        sach1 sach1 = sach1s.get(position);
        holder.txt.setText(sach1.getSach());
        adapterQlSach1 adapterQlSach1 = new  adapterQlSach1();
        adapterQlSach1.setData(sach1.getSachList());
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.recyclerView.setAdapter(adapterQlSach1);
    }

    @Override
    public int getItemCount() {
        return sach1s.size();
    }

    public class adapterQlSachViewholder extends RecyclerView.ViewHolder {
        TextView txt;
        RecyclerView recyclerView;

        public adapterQlSachViewholder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.rcy);
            txt = itemView.findViewById(R.id.txtLoaiSach);
        }
    }
}
