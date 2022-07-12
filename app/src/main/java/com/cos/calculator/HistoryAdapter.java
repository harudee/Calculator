package com.cos.calculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cos.calculator.model.History;

import org.w3c.dom.Text;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>{


    private List<History> historyList;

    public HistoryAdapter(List<History> list){  historyList = list; }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.history_recode, parent, false);
        HistoryAdapter.ViewHolder viewHolder = new HistoryAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.expression.setText(historyList.get(position).expression);
        holder.result.setText(historyList.get(position).result);

    }

    @Override
    public int getItemCount() {
        return this.historyList.size();
    }

    public void setHistoryList(List<History> list){
        this.historyList = list;
    }



    // 1.
    class ViewHolder extends RecyclerView.ViewHolder{


        TextView expression, result;

        public ViewHolder( View itemView) {
            super(itemView);

            expression = itemView.findViewById(R.id.expression);
            result = itemView.findViewById(R.id.result);


        }


    }

}
