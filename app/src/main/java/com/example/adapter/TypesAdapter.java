package com.example.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.item.Plots;
import com.example.realestate.R;

import java.util.ArrayList;
public class TypesAdapter extends ArrayAdapter<Plots>
{
    ArrayList<Plots> plots;
    Context context;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view=LayoutInflater.from(context).inflate(R.layout.typeslayout,null
        );
        TextView title,desc;
        title=view.findViewById(R.id.tvCategoryTitle);
        title.setText(plots.get(position).getTitle());
        if(plots.get(position).getTitle().equals("Popular")||plots.get(position).getTitle().equals("Location")||
                plots.get(position).getTitle().equals("Type"))
        {

        }
        return view;
    }

    public TypesAdapter(@NonNull Context context, ArrayList<Plots> plots) {
        super(context, R.layout.plotslayout,plots);
        this.context=context;
        this.plots=plots;
    }
}
