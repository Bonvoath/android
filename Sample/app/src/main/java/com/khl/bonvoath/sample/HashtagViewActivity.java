package com.khl.bonvoath.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.SuperscriptSpan;

import com.greenfrvr.hashtagview.HashtagView;

import java.util.ArrayList;
import java.util.Map;

public class HashtagViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hashtag_view);
        hashtagView = findViewById(R.id.hash_view);
        ArrayList<Person> persons = new ArrayList<>();
        Person p1 = new Person();
        p1.id = 1;
        p1.firstName = "Tanson";
        persons.add(p1);
        Person p2= new Person();
        p2.id = 2;
        p2.firstName = "Heng";
        persons.add(p1);
        hashtagView.setData(persons, new HashtagView.DataTransform<Person>() {
            @Override
            public CharSequence prepare(Person item) {
                String label = "@" + item.firstName;
                SpannableString spannableString = new SpannableString(label);
                spannableString.setSpan(new SuperscriptSpan(), 0, 1, Spanned.SPAN_COMPOSING);
                spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)), 1, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                return spannableString;
            }
        });
    }

    public class Person{
        int id;
        String firstName;
        String midName;
        String lastName;
    }
    HashtagView hashtagView;
}
