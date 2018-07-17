package com.privaliacine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class SimpleAdapterData extends ArrayAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private String[] imageUrl;
    private String[] title;
    private String[] releaseDate;
    private String[] overView;

    public SimpleAdapterData(Context context, String[] imageUrl, String[] title, String[] releaseDate, String[] overView) {
        super(context, R.layout.data_movies, imageUrl);

        this.context = context;
        this.imageUrl = imageUrl;
        this.title = title;
        this.releaseDate = releaseDate;
        this.overView = overView;

        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = layoutInflater.inflate(R.layout.data_movies, null, true);

        //defición de los campos y valores que van a recibir
        TextView textViewTitle = (TextView) convertView.findViewById(R.id.textViewTitleDataMovies);
        textViewTitle.setText(title[position]);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageViewDataMovies);
        //descarga imagenes usando la libreria Glide
        Glide.with(context).load(imageUrl[position]).into((ImageView) imageView);

        return convertView;
    }
}
