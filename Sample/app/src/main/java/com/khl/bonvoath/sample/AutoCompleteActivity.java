package com.khl.bonvoath.sample;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.otaliastudios.autocomplete.Autocomplete;
import com.otaliastudios.autocomplete.AutocompleteCallback;
import com.otaliastudios.autocomplete.AutocompletePolicy;
import com.otaliastudios.autocomplete.AutocompletePresenter;
import com.otaliastudios.autocomplete.CharPolicy;

public class AutoCompleteActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_complete);
        setupUserAutocomplete();
        //setupMentionsAutocomplete();
        //setupMaleFemaleAutocomplete();
    }

    private void setupUserAutocomplete() {
        EditText edit = findViewById(R.id.txt_search);
        float elevation = 6f;
        AutocompletePolicy policy = new CharPolicy('#');
        Drawable backgroundDrawable = new ColorDrawable(getResources().getColor(R.color.colorGray));
        AutocompletePresenter<User> presenter = new UserPresenter(this);
        AutocompleteCallback<User> callback = new AutocompleteCallback<User>() {
            @Override
            public boolean onPopupItemClicked(Editable editable, User item) {
                editable.clear();
                editable.append(item.getFullname());

                return true;
            }

            public void onPopupVisibilityChanged(boolean shown) {}
        };

        Autocomplete.<User>on(edit)
                .with(elevation)
                .with(backgroundDrawable)
                .with(presenter)
                .with(policy)
                .with(callback)
                .build();
    }

    private void setupMentionsAutocomplete() {
        EditText edit = findViewById(R.id.txt_search);
        float elevation = 6f;
        Drawable backgroundDrawable = new ColorDrawable(Color.WHITE);
        AutocompletePolicy policy = new CharPolicy('@'); // Look for @mentions
        AutocompletePresenter<User> presenter = new UserPresenter(this);
        AutocompleteCallback<User> callback = new AutocompleteCallback<User>() {
            @Override
            public boolean onPopupItemClicked(Editable editable, User item) {
                // Replace query text with the full name.
                int[] range = CharPolicy.getQueryRange(editable);
                if (range == null) return false;
                int start = range[0];
                int end = range[1];
                String replacement = item.getUsername();
                editable.replace(start, end, replacement);
                editable.setSpan(new StyleSpan(Typeface.BOLD), start, start + replacement.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                return true;
            }

            public void onPopupVisibilityChanged(boolean shown) { }
        };
        Autocomplete.<User>on(edit)
                .with(elevation)
                .with(backgroundDrawable)
                .with(policy)
                .with(presenter)
                .with(callback)
                .build();
    }

    private void setupMaleFemaleAutocomplete() {
        EditText edit = findViewById(R.id.txt_search);
        float elevation = 6f;
        Drawable backgroundDrawable = new ColorDrawable(Color.WHITE);
        AutocompletePresenter<User> presenter = new MaleFemalePresenter(this);
        AutocompleteCallback<User> callback = new AutocompleteCallback<User>() {
            @Override
            public boolean onPopupItemClicked(Editable editable, User item) {
                editable.clear();
                editable.append(item.getFullname())
                        .append(" ")
                        .append(item.isFemale() ? "(Female)" : "(Male)");
                editable.setSpan(new StyleSpan(Typeface.BOLD), 0, item.getFullname().length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                return true;
            }

            public void onPopupVisibilityChanged(boolean shown) {}
        };

        Autocomplete.<User>on(edit)
                .with(elevation)
                .with(backgroundDrawable)
                .with(presenter)
                .with(callback)
                .build();
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_LONG).show();
    }
}
