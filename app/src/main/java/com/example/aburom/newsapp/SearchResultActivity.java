package com.example.aburom.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.aburom.newsapp.DB.DbHelper;
import com.example.aburom.newsapp.adapters.NewsListAdapter;
import com.example.aburom.newsapp.fragments.FavoritesFragment;
import com.example.aburom.newsapp.fragments.HomeFragment;
import com.example.aburom.newsapp.model.News;

import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity {


    static DbHelper dbHelper;
    static ArrayList<News> allNews;
    TextView toolbarTitle, numberOfSearchResults;
    NewsListAdapter customAdapter;
    ListView newsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Intent intent = getIntent();
        String query = intent.getStringExtra("query");

        toolbarTitle = findViewById(R.id.search_toolbar_news_title);
        Toolbar toolbar = findViewById(R.id.search_toolbar);
        toolbar.setTitle("");
        toolbarTitle.setText("Search Results for \"" + query + "\"");
        //toolbar.inflateMenu(R.menu.menu_news_page);
        setSupportActionBar(toolbar);

        numberOfSearchResults = findViewById(R.id.numberOfSearchResults);


        newsListView = findViewById(R.id.searchResultsNewsListView);
        dbHelper = new DbHelper(this);
        customAdapter = new NewsListAdapter();


        fillList(query);
        if (allNews != null) {
            customAdapter = new NewsListAdapter(this, allNews);
        }

        if (customAdapter != null) {
            newsListView.setAdapter(customAdapter);
        }

        numberOfSearchResults.setText(allNews.size() + " results found");
    }

    public static void fillList(String query) {
        allNews = dbHelper.getSearchedNews(query);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news_page, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    //    @Override
//    public void onBackPressed() {
//        try {
//            HomeFragment.fillList();
//            FavoritesFragment.fillList();
//        }catch (Exception e){
//            Log.e("Refreshing Lists","Error while refreshing lists");
//        }
//
//        super.onBackPressed();
//
//    }
}
