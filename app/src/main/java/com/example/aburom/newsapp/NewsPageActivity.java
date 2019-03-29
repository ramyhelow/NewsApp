package com.example.aburom.newsapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.example.aburom.newsapp.DB.DbHelper;
import com.example.aburom.newsapp.model.News;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsPageActivity extends AppCompatActivity {

    DbHelper dbHelper;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news_page, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_page);

        dbHelper = new DbHelper(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.inflateMenu(R.menu.menu_news_page);
        setSupportActionBar(toolbar);

        ImageView news_image = findViewById(R.id.news_image);
        TextView news_title = findViewById(R.id.news_title);
        TextView toolbar_news_title = findViewById(R.id.toolbar_news_title);
        TextView news_description = findViewById(R.id.news_description);
        TextView news_time = findViewById(R.id.news_time);
        final Button deleteNewsButton = findViewById(R.id.delete_news_button);

        Intent intent = getIntent();
        final News news = (News) intent.getSerializableExtra("newsItem");

        byte[] newsImageByte = news.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(newsImageByte, 0, newsImageByte.length);
        news_image.setImageBitmap(bitmap);

        news_title.setText(news.getTitle());
        toolbar_news_title.setText(news.getTitle());
        news_description.setText(news.getDescription());
        news_time.setText(news.getTime());


        deleteNewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(NewsPageActivity.this);
                alertDialogBuilder.setTitle("Are you sure?");
                alertDialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.deleteNews(news.getId());
                        onBackPressed();
                    }
                });

                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //finish();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();


            }
        });

    }

}
