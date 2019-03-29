package com.example.aburom.newsapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.aburom.newsapp.DB.DbHelper;
import com.example.aburom.newsapp.NewsPageActivity;
import com.example.aburom.newsapp.fragments.FavoritesFragment;
import com.example.aburom.newsapp.model.News;

import com.example.aburom.newsapp.R;

import java.util.ArrayList;

public class NewsListAdapter extends BaseAdapter {
    DbHelper dbHelper;
    Activity activity;
    ArrayList<News> data;
    LayoutInflater layoutInflater;
    View root;

    public NewsListAdapter(Activity activity, ArrayList<News> data) {
        this.activity = activity;
        this.data = data;
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dbHelper = new DbHelper(activity);
    }

    public NewsListAdapter() {
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        root = convertView;
        final int pos = position;
        if (root == null) {
            root = layoutInflater.inflate(R.layout.news_list_item, null);
        }
        final ImageView newsImage = root.findViewById(R.id.newsImage);
        TextView newsTitle = root.findViewById(R.id.newsTitle);
        TextView newsTime = root.findViewById(R.id.newsTime);
        final ImageButton isFav = root.findViewById(R.id.isFavorite);
        final LinearLayout newsItem = root.findViewById(R.id.newsItem);

        if (data.get(position).getFavorite()) {
            isFav.setBackgroundResource(R.drawable.ic_favorite_red);
        } else {
            isFav.setBackgroundResource(R.drawable.ic_favorite_white);
        }

        isFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (data.get(pos).getFavorite()) {
                    data.get(pos).setFavorite(false);
                    dbHelper.toggleFavoriteNews(String.valueOf(data.get(pos).getId()), 0);
                    isFav.setBackgroundResource(R.drawable.ic_favorite_white);

                } else {
                    data.get(pos).setFavorite(true);
                    dbHelper.toggleFavoriteNews(String.valueOf(data.get(pos).getId()), 1);
                    isFav.setBackgroundResource(R.drawable.ic_favorite_red);
//                    YoYo.with(Techniques.Pulse)
//                            .duration(200)
//                            .repeat(1)
//                            .playOn(root);
                }

                YoYo.with(Techniques.Pulse)
                        .duration(200)
                        .repeat(1)
                        .playOn(newsItem);
                //FavoritesFragment.fillList();

            }
        });

        byte[] newsImageInBytes = data.get(position).getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(newsImageInBytes, 0, newsImageInBytes.length);
        newsImage.setImageBitmap(bitmap);
        newsTitle.setText(data.get(position).getTitle());
        newsTime.setText(data.get(position).getTime());



        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(root.getContext(), NewsPageActivity.class);
                intent.putExtra("newsItem", data.get(pos));


                //Toast.makeText(activity, pos+"", Toast.LENGTH_SHORT).show();
                root.getContext().startActivity(intent);
            }
        });

        return root;
    }
}