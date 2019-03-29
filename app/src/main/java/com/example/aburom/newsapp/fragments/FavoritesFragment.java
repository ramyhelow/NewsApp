package com.example.aburom.newsapp.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.aburom.newsapp.DB.DbHelper;
import com.example.aburom.newsapp.R;
import com.example.aburom.newsapp.adapters.NewsListAdapter;
import com.example.aburom.newsapp.model.News;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {


    static DbHelper dbHelper;
    static ArrayList<News> allFavoriteNews;
    NewsListAdapter customAdapter;
    ListView favoriteNewsListView;
    TextView noFavoriteNewsMessage;

    public FavoritesFragment() {
        //dbHelper = DbHelper.getDbHelper(getActivity());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favorites, container, false);

        noFavoriteNewsMessage = root.findViewById(R.id.noFavoriteNews);
        favoriteNewsListView = root.findViewById(R.id.favoriteNewsListView);
        dbHelper = new DbHelper(getActivity());
        customAdapter = new NewsListAdapter();

        fillList();
        if (allFavoriteNews != null) {
            customAdapter = new NewsListAdapter(getActivity(), allFavoriteNews);
        }

        if (customAdapter != null) {
            favoriteNewsListView.setAdapter(customAdapter);
        }

        if (allFavoriteNews.size() == 0) {
            noFavoriteNewsMessage.setVisibility(View.VISIBLE);
        }


        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        fillList();
    }

    public static void fillList() {
        //dbHelper = new DbHelper(new FragmentActivity());
        allFavoriteNews = dbHelper.getFavoriteNews();
    }

}
