package com.example.samplecountrydetails.adaptor;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.ahmadrosid.svgloader.SvgLoader;
import com.example.samplecountrydetails.R;
import com.example.samplecountrydetails.listener.CallBack;
import com.example.samplecountrydetails.model.CountryNameFlagModel;

import java.util.ArrayList;
import java.util.List;

public class ShowCountryDetailsRecyclerViewAdapter extends RecyclerView.Adapter<ShowCountryDetailsRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<CountryNameFlagModel> countryNameFlagModelList;
    private List<CountryNameFlagModel> countryNameFlagModelListFull;
    private Activity activity;
    public CallBack callBack;

    public ShowCountryDetailsRecyclerViewAdapter(Context _context, List<CountryNameFlagModel> _countryNameFlagModelList, Activity _activity, CallBack _callBack) {
        this.context = _context;
        this.countryNameFlagModelList = _countryNameFlagModelList;
        countryNameFlagModelListFull = new ArrayList<>(countryNameFlagModelList);
        this.activity = _activity;
        this.callBack = _callBack;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem = layoutInflater.inflate(R.layout.country_name_and_flag_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        final CountryNameFlagModel countryNameFlagModel = countryNameFlagModelList.get(position);
        String countryName = countryNameFlagModel.getCountryName();
        final String countryFlagUrl = countryNameFlagModel.getCountryFlag();
        viewHolder.countryNameTV.setText(countryName);

        SvgLoader.pluck()
                .with(activity)
                .setPlaceHolder(R.mipmap.ic_launcher, R.mipmap.ic_launcher)
                .load(countryFlagUrl, viewHolder.countryFlagImage);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String countryName = countryNameFlagModelList.get(position).getCountryName();
                callBack.countryNameCallBack(countryName);
            }
        });
    }

    @Override
    public int getItemCount() {
        return countryNameFlagModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView countryNameTV;
        ImageView countryFlagImage;

        public ViewHolder(View itemView) {
            super(itemView);
            this.countryNameTV = itemView.findViewById(R.id.country_name_tv);
            this.countryFlagImage = itemView.findViewById(R.id.country_flage_img);
        }
    }


    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<CountryNameFlagModel> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(countryNameFlagModelListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (CountryNameFlagModel item : countryNameFlagModelListFull) {
                    if (item.getCountryName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            countryNameFlagModelList.clear();
            countryNameFlagModelList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

}
