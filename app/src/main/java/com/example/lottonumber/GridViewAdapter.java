package com.example.lottonumber;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class GridViewAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<LottoNumbers> lottoNumbers;

    public GridViewAdapter(Context context, ArrayList<LottoNumbers> lottoNumbers) {
        this.context = context;
        this.lottoNumbers = lottoNumbers;
    }

    @Override
    public int getCount() {
        return lottoNumbers.size();
    }

    @Override
    public Object getItem(int position) {
        return lottoNumbers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View myView = null;
        LayoutInflater inflater = LayoutInflater.from(context);
        TextView[] textViews = new TextView[6];
        int[] textViewsID = {R.id.tvNo_01, R.id.tvNo_02, R.id.tvNo_03, R.id.tvNo_04, R.id.tvNo_05, R.id.tvNo_06};

        if (convertView != null) {
            myView = convertView;
        } else {
            myView = inflater.inflate(R.layout.adapter_main, parent, false);
        }

        TextView tvCount = myView.findViewById(R.id.tvCount);
        for (int i = 0; i < textViews.length; i++) {
            textViews[i] = myView.findViewById(textViewsID[i]);
        }
        tvCount.setText((position + 1) + "회");
        for (int i = 0; i < textViews.length; i++) {
            textViews[i].setText(lottoNumbers.get(position).getNumber()[i] + "");
        }
        return myView;
    }
}
