package com.demo.voicetyping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.voicetyping.R;


public class CustomAdapter extends BaseAdapter {
    Context context;
    String[] countryNames;
    int[] flags;
    LayoutInflater inflter;

    @Override 
    public Object getItem(int i) {
        return null;
    }

    @Override 
    public long getItemId(int i) {
        return 0L;
    }

    public CustomAdapter(Context context, int[] iArr, String[] strArr) {
        this.context = context;
        this.flags = iArr;
        this.countryNames = strArr;
        this.inflter = LayoutInflater.from(context);
    }

    @Override 
    public int getCount() {
        return this.flags.length;
    }

    @Override 
    public View getView(int i, View view, ViewGroup viewGroup) {
        View inflate = this.inflter.inflate(R.layout.coustom_spiner, (ViewGroup) null);
        ((ImageView) inflate.findViewById(R.id.img_spiner_coustom)).setImageResource(this.flags[i]);
        ((TextView) inflate.findViewById(R.id.txt_spiner_coustom)).setText(this.countryNames[i]);
        return inflate;
    }
}
