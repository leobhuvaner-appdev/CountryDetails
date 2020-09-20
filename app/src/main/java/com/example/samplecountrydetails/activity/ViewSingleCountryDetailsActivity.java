package com.example.samplecountrydetails.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmadrosid.svgloader.SvgLoader;
import com.example.samplecountrydetails.R;
import com.example.samplecountrydetails.model.CountryDetailsModel;

public class ViewSingleCountryDetailsActivity extends AppCompatActivity {
    TextView separateCountryNameTV, capitalTV, regionTV, subRegionTV, areaTV, populationTV;
    ImageView flagImage;
    CountryDetailsModel countryDetailsModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_single_country_details);
        flagImage = findViewById(R.id.separate_country_flag);
        separateCountryNameTV = findViewById(R.id.separate_country_name);
        capitalTV = findViewById(R.id.capital_value);
        regionTV = findViewById(R.id.region_value);
        subRegionTV = findViewById(R.id.sub_region_value);
        areaTV = findViewById(R.id.area_value);
        populationTV = findViewById(R.id.population_value);

        countryDetailsModel = (CountryDetailsModel) getIntent().getSerializableExtra("MY_CLASS_OBJ");
        if(countryDetailsModel != null) {
            String flagImageUrl = countryDetailsModel.getFlagImage();
            String countryName = countryDetailsModel.getCountryName();
            String capitalValue = countryDetailsModel.getCapital();
            String regionValue = countryDetailsModel.getRegion();
            String subRegionValue = countryDetailsModel.getSubRegion();
            Double areaValue = countryDetailsModel.getArea();
            int populationValue = countryDetailsModel.getPopulation();

            SvgLoader.pluck()
                    .with(this)
                    .setPlaceHolder(R.mipmap.ic_launcher, R.mipmap.ic_launcher)
                    .load(flagImageUrl, flagImage);


            separateCountryNameTV.setText(countryName);
            capitalTV.setText(capitalValue);
            regionTV.setText(regionValue);
            subRegionTV.setText(subRegionValue);
            areaTV.setText(String.valueOf(areaValue));
            populationTV.setText(String.valueOf(populationValue));
        }

    }
}
