package com.example.aburom.newsapp.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.aburom.newsapp.DB.DbHelper;
import com.example.aburom.newsapp.NewsPageActivity;
import com.example.aburom.newsapp.R;
import com.example.aburom.newsapp.TabbedActivity;
import com.example.aburom.newsapp.adapters.NewsListAdapter;
import com.example.aburom.newsapp.model.News;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    static DbHelper dbHelper;
    static ArrayList<News> allNews;
    NewsListAdapter customAdapter;
    ListView newsListView;
    TextView noNewsMessage;

    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        noNewsMessage = root.findViewById(R.id.noNews);
        newsListView = root.findViewById(R.id.newsListView);
        dbHelper = new DbHelper(getActivity());
        customAdapter = new NewsListAdapter();

        fillList();
        if (allNews != null) {
            customAdapter = new NewsListAdapter(getActivity(), allNews);

        }

        if (customAdapter != null) {
            newsListView.setAdapter(customAdapter);
        }

        if (allNews.size() == 0) {
            noNewsMessage.setVisibility(View.VISIBLE);
        }



        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        fillList();
    }

    public static void fillList() {
        allNews = dbHelper.getAllNews();
    }

}
