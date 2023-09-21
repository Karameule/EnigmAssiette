package com.example.android.waitlist.data;

import android.provider.BaseColumns;

public class WaitlistContract {

    public static final class WaitlistEntry implements BaseColumns {
        public static final String TABLE_NAME = "waitlist";
        public static final String COLUMN_GUEST_NAME = "guestName";
        public static final String COLUMN_PARTY_SIZE = "partySize";
        public static final String COLUMN_TIMESTAMP = "timestamp";
        public static final String COLUMN_RESTAURANT_NAME = "restaurantName";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_DECORATION_RATING = "decorationRating";
        public static final String COLUMN_FOOD_RATING = "foodRating";
        public static final String COLUMN_SERVICE_RATING = "serviceRating";
        public static final String COLUMN_CRITIQUE = "critique";
    }
}

