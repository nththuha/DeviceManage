package com.example.devicemanagement.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.devicemanagement.Entity.ThietBi;
import com.example.devicemanagement.R;

import java.util.List;

public class AdapterChiTietSuDung extends RecyclerView.Adapter<AdapterChiTietSuDung.ViewHolder> {
    private ItemClickCTSD _itemClick;
    private List<ThietBi> thietBiList;

    public AdapterChiTietSuDung(ItemClickCTSD itemClick, List<ThietBi> thietBiList) {
        this._itemClick = itemClick;
        this.thietBiList = thietBiList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_item_chitiet_sudung,parent,false);
        ViewHolder viewHolder = new ViewHolder(view,_itemClick);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ThietBi thietBi = thietBiList.get(position);
        holder.tvMaThietBi.setText(thietBi.getMaThietBi());
        holder.tvTenThietBi.setText(thietBi.getTenThietBi());
        holder.tvSoLuongMuon.setText(thietBi.getTenThietBi()+"/"+thietBi.getTenThietBi());
        holder.llItemClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder._itemClick.onItemClick(thietBi);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(thietBiList != null)
            return thietBiList.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemClickCTSD _itemClick;
        TextView tvMaThietBi, tvTenThietBi, tvSoLuongMuon;
        RelativeLayout llItemClick;
        public ViewHolder(@NonNull View view,ItemClickCTSD itemClick) {
            super(view);
            _itemClick = itemClick;
            tvMaThietBi = view.findViewById(R.id.tvMaThietBi);
            tvTenThietBi = view.findViewById(R.id.tvTenThietBi);
            tvSoLuongMuon = view.findViewById(R.id.tvSoLuongMuon);
            llItemClick = view.findViewById(R.id.llItemClick);
        }
    }

    public interface ItemClickCTSD{
        void onItemClick(ThietBi thietBi);
    }
}
