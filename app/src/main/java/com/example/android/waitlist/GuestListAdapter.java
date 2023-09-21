package com.example.android.waitlist;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.android.waitlist.data.WaitlistContract;

public class GuestListAdapter extends RecyclerView.Adapter<GuestListAdapter.GuestViewHolder> {

    private Cursor mCursor;
    private Context mContext;

    public GuestListAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
    }

    @Override
    public GuestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.guest_list_item, parent, false);
        return new GuestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GuestViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position))
            return;


        long id = mCursor.getLong(mCursor.getColumnIndex(WaitlistContract.WaitlistEntry._ID));

        // New columns
        String restaurantName = mCursor.getString(mCursor.getColumnIndex(WaitlistContract.WaitlistEntry.COLUMN_RESTAURANT_NAME));
        String date = mCursor.getString(mCursor.getColumnIndex(WaitlistContract.WaitlistEntry.COLUMN_DATE));
        String time = mCursor.getString(mCursor.getColumnIndex(WaitlistContract.WaitlistEntry.COLUMN_TIME));
        float decorationRating = mCursor.getFloat(mCursor.getColumnIndex(WaitlistContract.WaitlistEntry.COLUMN_DECORATION_RATING));
        float foodRating = mCursor.getFloat(mCursor.getColumnIndex(WaitlistContract.WaitlistEntry.COLUMN_FOOD_RATING));
        float serviceRating = mCursor.getFloat(mCursor.getColumnIndex(WaitlistContract.WaitlistEntry.COLUMN_SERVICE_RATING));
        String critique = mCursor.getString(mCursor.getColumnIndex(WaitlistContract.WaitlistEntry.COLUMN_CRITIQUE));


        holder.restaurantNameTextView.setText(restaurantName); // Set restaurant name
        holder.dateTextView.setText(date); // Set date
        holder.timeTextView.setText(time); // Set time
        holder.decorationRatingBar.setRating(decorationRating); // Set decoration rating
        holder.foodRatingBar.setRating(foodRating); // Set food rating
        holder.serviceRatingBar.setRating(serviceRating); // Set service rating
        holder.critiqueTextView.setText(critique); // Set critique
        holder.itemView.setTag(id);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }

    class GuestViewHolder extends RecyclerView.ViewHolder {

        TextView restaurantNameTextView;
        TextView dateTextView;
        TextView timeTextView;
        RatingBar decorationRatingBar;
        RatingBar foodRatingBar;
        RatingBar serviceRatingBar;
        TextView critiqueTextView;

        public GuestViewHolder(View itemView) {
            super(itemView);
            restaurantNameTextView = itemView.findViewById(R.id.restaurant_name_text_view);
            dateTextView = itemView.findViewById(R.id.date_edit_text);
            timeTextView = itemView.findViewById(R.id.time_edit_text);
            decorationRatingBar = itemView.findViewById(R.id.decoration_rating_bar);
            foodRatingBar = itemView.findViewById(R.id.food_rating_bar);
            serviceRatingBar = itemView.findViewById(R.id.service_rating_bar);
            critiqueTextView = itemView.findViewById(R.id.critique_text_view);
        }
    }
}
