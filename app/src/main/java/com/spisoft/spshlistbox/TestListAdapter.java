package com.spisoft.spshlistbox;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class TestListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ClsTest> mList;
    private OnItemClickListener mItemClickListener;

    public TestListAdapter() {
    }

    public void updateList(List<ClsTest> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    private class CellViewHolder extends RecyclerView.ViewHolder {
//    private class CellViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private TextView textView, textDesc;
        private MaterialCardView itemCard;

        public CellViewHolder(View view) {
            super(view);

            textView = (TextView) view.findViewById(R.id.txtSV);
            textDesc = (TextView) view.findViewById(R.id.txtDesc);
            itemCard = view.findViewById(R.id.cardView);

//            itemView.setOnClickListener(this);
//            itemView.setOnLongClickListener(this);
        }

//        @Override
//        public void onClick(View view) {
//            if (mItemClickListener != null) {
//                mItemClickListener.onItemClick(view, getLayoutPosition(), mParent);
//            }
//        }
//
//        @Override
//        public boolean onLongClick(View view) {
//            if (mItemClickListener != null) {
//                mItemClickListener.onItemLongClick(view, getLayoutPosition(), mParent);
//                return true;
//            }
//            return false;
//        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int viewType) {

        switch (viewType) {
            default: {
                View v1 = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_option, viewGroup, false);
                return new CellViewHolder(v1);
            }
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final CellViewHolder holder = (CellViewHolder) viewHolder;

        ClsTest tItem = mList.get(position);

        if(tItem.isDisabled()){
            holder.itemCard.setStrokeColor(Color.LTGRAY);
        }else {
            holder.itemCard.setStrokeColor(Color.BLACK);
        }
        holder.textView.setText(tItem.getOptCode());
        holder.textDesc.setText(tItem.getOptTitle());
    }

    @Override
    public int getItemCount() {
        if (mList == null)
            return 0;
        return mList.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        return position;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, int parent);

        void onItemLongClick(View view, int position, int parent);
    }

    // for both short and long click
    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}