package com.moringaschool.movieapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.slider.Slider;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class Slider_Adapter extends SliderViewAdapter<Slider_Adapter.ViewHolde> {

    int[] images;

    public Slider_Adapter(int[] images) {
        this.images = images;
    }

    @Override
    public Slider_Adapter.ViewHolde onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.slider_layout,parent,false);
        return new ViewHolde(view);
    }

    @Override
    public void onBindViewHolder(Slider_Adapter.ViewHolde viewHolder, int position) {
        viewHolder.my_imageView.setImageResource(images[position]);

    }

    @Override
    public int getCount() {
        return images.length;
    }

    public class ViewHolde extends SliderViewAdapter.ViewHolder{
        ImageView my_imageView;


        public ViewHolde(View itemView) {
            super(itemView);


            my_imageView = itemView.findViewById(R.id.image_view_s);
        }
    }
}
