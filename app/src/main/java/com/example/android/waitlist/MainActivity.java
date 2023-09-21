package com.example.android.waitlist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.android.waitlist.data.WaitlistContract;
import com.example.android.waitlist.data.WaitlistDbHelper;

public class MainActivity extends AppCompatActivity {

    private GuestListAdapter mAdapter;
    private SQLiteDatabase mDb;
    private EditText mRestaurantNameEditText;
    private EditText mDateEditText;
    private EditText mTimeEditText;
    private RatingBar mDecorationRatingBar;
    private RatingBar mFoodRatingBar;
    private RatingBar mServiceRatingBar;
    private EditText mCritiqueEditText;
    private final static String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView waitlistRecyclerView;

        // Set local attributes to corresponding views
        waitlistRecyclerView = findViewById(R.id.all_reviews_list_view);
        mRestaurantNameEditText = findViewById(R.id.restaurant_name_edit_text);
        mDateEditText = findViewById(R.id.date_edit_text);
        mTimeEditText = findViewById(R.id.time_edit_text);
        mDecorationRatingBar = findViewById(R.id.decoration_rating_bar);
        mFoodRatingBar = findViewById(R.id.food_rating_bar);
        mServiceRatingBar = findViewById(R.id.service_rating_bar);
        mCritiqueEditText = findViewById(R.id.critique_edit_text);

        // Set layout for the RecyclerView
        waitlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create a DB helper (this will create the DB if run for the first time)
        WaitlistDbHelper dbHelper = new WaitlistDbHelper(this);

        // Keep a reference to the mDb until paused or killed. Get a writable database
        mDb = dbHelper.getWritableDatabase();

        // Get all guest info from the database and save in a cursor
        Cursor cursor = getAllReviews();

        // Create an adapter for that cursor to display the data
        mAdapter = new GuestListAdapter(this, cursor);

        // Link the adapter to the RecyclerView
        waitlistRecyclerView.setAdapter(mAdapter);

        // Attach ItemTouchHelper to handle swiping items off the list
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                long id = (long) viewHolder.itemView.getTag();
                removeReview(id);
                mAdapter.swapCursor(getAllReviews());
            }
        }).attachToRecyclerView(waitlistRecyclerView);

        Button addReviewButton = findViewById(R.id.add_review_button);
        addReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewReview();
                mAdapter.swapCursor(getAllReviews());
                clearUITextFields();
            }
        });
    }

    private Cursor getAllReviews() {
        return mDb.query(
                WaitlistContract.WaitlistEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                WaitlistContract.WaitlistEntry.COLUMN_TIMESTAMP
        );
    }

    private long addNewReview() {
        ContentValues cv = new ContentValues();
        cv.put(WaitlistContract.WaitlistEntry.COLUMN_RESTAURANT_NAME, mRestaurantNameEditText.getText().toString());
        cv.put(WaitlistContract.WaitlistEntry.COLUMN_DATE, mDateEditText.getText().toString());
        cv.put(WaitlistContract.WaitlistEntry.COLUMN_TIME, mTimeEditText.getText().toString());
        cv.put(WaitlistContract.WaitlistEntry.COLUMN_DECORATION_RATING, mDecorationRatingBar.getRating());
        cv.put(WaitlistContract.WaitlistEntry.COLUMN_FOOD_RATING, mFoodRatingBar.getRating());
        cv.put(WaitlistContract.WaitlistEntry.COLUMN_SERVICE_RATING, mServiceRatingBar.getRating());
        cv.put(WaitlistContract.WaitlistEntry.COLUMN_CRITIQUE, mCritiqueEditText.getText().toString());
        return mDb.insert(WaitlistContract.WaitlistEntry.TABLE_NAME, null, cv);
    }

    private void removeReview(long id) {
        mDb.delete(WaitlistContract.WaitlistEntry.TABLE_NAME, WaitlistContract.WaitlistEntry._ID + "=" + id, null);
    }

    private void clearUITextFields() {
        mRestaurantNameEditText.getText().clear();
        mDateEditText.getText().clear();
        mTimeEditText.getText().clear();
        mDecorationRatingBar.setRating(0.0f);
        mFoodRatingBar.setRating(0.0f);
        mServiceRatingBar.setRating(0.0f);
        mCritiqueEditText.getText().clear();
    }
}
