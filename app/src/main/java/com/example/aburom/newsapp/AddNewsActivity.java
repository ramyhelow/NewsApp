package com.example.aburom.newsapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.aburom.newsapp.DB.DbHelper;
import com.example.aburom.newsapp.fragments.HomeFragment;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class AddNewsActivity extends AppCompatActivity implements IPickResult {

    DbHelper dbHelper;
    EditText addNewsTitle, addNewsTime, addNewsDescription;
    Button addNewsImage;
    Button addNewsToDB;

    boolean isImageSelected = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);

        Toolbar toolbar = findViewById(R.id.add_news_toolbar);
        toolbar.setTitle("");
        //toolbar.inflateMenu(R.menu.menu_news_page);
        setSupportActionBar(toolbar);

        dbHelper = new DbHelper(this);

        addNewsImage = findViewById(R.id.add_news_image);
        addNewsTitle = findViewById(R.id.add_news_title);
        addNewsTime = findViewById(R.id.add_news_time);
        addNewsDescription = findViewById(R.id.add_news_description);

        addNewsToDB = findViewById(R.id.add_news_button);
        //backButton = findViewById(R.id.back_news_button);

        addNewsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickImageDialog.build(new PickSetup()).show(AddNewsActivity.this);
            }
        });

        Calendar mcurrentTime = Calendar.getInstance();
        final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        final int minute = mcurrentTime.get(Calendar.MINUTE);
        addNewsTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePicker = new TimePickerDialog(AddNewsActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String minuteString;
                        if (minute < 10) {
                            minuteString = "0" + minute;
                        } else {
                            minuteString = String.valueOf(minute);
                        }
                        if (hourOfDay > 12) {
                            addNewsTime.setText(hourOfDay - 12 + ":" + minuteString + " PM");
                        } else {
                            addNewsTime.setText(hourOfDay + ":" + minuteString + " AM");
                        }
                        addNewsTime.setBackgroundResource(R.drawable.news_edittext_shape);

                    }
                }, hour, minute, false);
                //timePicker.setTitle("Select Time");
                timePicker.show();
            }
        });


        addNewsToDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String title = addNewsTitle.getText().toString();
                    String time = addNewsTime.getText().toString();
                    String description = addNewsDescription.getText().toString();
                    byte[] image = imageViewToByte(addNewsImage);

                    boolean validatedTime;
                    boolean validatedTitle;
                    boolean validatedDescription;

                    if (!isImageSelected) {
                        //Toast.makeText(AddNewsActivity.this, "awesome", Toast.LENGTH_SHORT).show();
                        addNewsImage.setTextColor(Color.parseColor("red"));
                        addNewsImage.setBackgroundResource(R.drawable.add_image_invalid);
                        YoYo.with(Techniques.Shake)
                                .duration(200)
                                .repeat(2)
                                .playOn(addNewsImage);
                    } else {
                        addNewsImage.setTextColor(Color.parseColor("black"));
                    }

                    if (addNewsTime.getText().toString().trim().isEmpty()) {
                        validatedTime = false;
                        addNewsTime.setBackgroundResource(R.drawable.news_edittext_shape_invalid);
                        YoYo.with(Techniques.Shake)
                                .duration(200)
                                .repeat(2)
                                .playOn(addNewsTime);
                    } else {
                        validatedTime = true;
                        addNewsTime.setBackgroundResource(R.drawable.news_edittext_shape);
                    }

                    if (addNewsTitle.getText().toString().trim().isEmpty()) {
                        validatedTitle = false;
                        addNewsTitle.setBackgroundResource(R.drawable.news_edittext_shape_invalid);
                        YoYo.with(Techniques.Shake)
                                .duration(200)
                                .repeat(2)
                                .playOn(addNewsTitle);
                    } else {
                        validatedTitle = true;
                        addNewsTitle.setBackgroundResource(R.drawable.news_edittext_shape);
                    }

                    if (addNewsDescription.getText().toString().trim().isEmpty()) {
                        validatedDescription = false;
                        addNewsDescription.setBackgroundResource(R.drawable.news_edittext_shape_invalid);
                        YoYo.with(Techniques.Shake)
                                .duration(200)
                                .repeat(2)
                                .playOn(addNewsDescription);
                    } else {
                        validatedDescription = true;
                        addNewsDescription.setBackgroundResource(R.drawable.news_edittext_shape);
                    }


                    if (isImageSelected && validatedTime && validatedTitle && validatedDescription) {
                        if (dbHelper.insertNews(title, time, description, image)) {
                            Intent intent = new Intent(AddNewsActivity.this, TabbedActivity.class);
                            HomeFragment.fillList();
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(AddNewsActivity.this, "News Failed To Add", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(AddNewsActivity.this, "Invalid Input", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e("NEWSAPP_INSERT_DATABASE", e.getMessage());
                }

            }
        });
//
//        backButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.back_news_button) {
            onBackPressed();
            //Toast.makeText(this, "Search Clicked", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onPickResult(PickResult pickResult) {
        BitmapDrawable ob = new BitmapDrawable(getResources(), pickResult.getBitmap());
        addNewsImage.setBackground(ob);
        isImageSelected = true;
        addNewsImage.setTextColor(Color.parseColor("black"));
    }

    private byte[] imageViewToByte(Button buttonImage) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) buttonImage.getBackground();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;

    }


}
