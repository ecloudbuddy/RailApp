package t90.railapp.swipePages;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.viewanimator.ViewAnimator;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import t90.railapp.R;
import t90.railapp.commonUtils;


public class Page2 extends Fragment {

    View v;
    Boolean dashIsExpanded = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_page2, container, false);
        initUi();
        return v;
    }

    private void initUi() {

        v.findViewById(R.id.page2_dash_live_tracker_layout).setVisibility(View.GONE);
        handleAnimations();
        // Load data for dash Tile
        loadDashCard();
    }

    private void loadDashCard() {
        SharedPreferences pref = getActivity().getSharedPreferences("dashData", Context.MODE_PRIVATE);
        JsonObject data = new JsonParser().parse(pref.getString("data", "{}")).getAsJsonObject();
        setDataForUIElements(data);

        Ion.with(this)
                .load(commonUtils.getUrl(getActivity(), "dashCard"))
                .setHeader("Authorization", commonUtils.getCommonAuth())
                .asJsonObject()
                .withResponse()
                .setCallback(new FutureCallback<Response<JsonObject>>() {
                    @Override
                    public void onCompleted(Exception e, Response<JsonObject> jsonObjectResponse) {
                        if (jsonObjectResponse.getResult() != null) {

                            JsonObject resp = jsonObjectResponse.getResult();
                            if (resp.get("status").getAsBoolean()) {

                                JsonObject data = resp.get("data").getAsJsonArray().get(0).getAsJsonObject();

                                SharedPreferences pref = getActivity().getSharedPreferences("dashData", Context.MODE_PRIVATE);
                                pref.edit().putString("data", data.toString()).apply();

                                setDataForUIElements(data);
                                loadTrainTrackerTile();

                            } else {
                                Toast.makeText(getActivity(), "Some Error Occured", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getContext(), "null", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void setDataForUIElements(JsonObject data) {

        if(!data.has("journeyDate"))
            return;

        String date = formatDate(data.get("journeyDate").getAsString());

        ((TextView) v.findViewById(R.id.page2_dash_next_date_day)).setText(date.split(":")[0]);
        ((TextView) v.findViewById(R.id.page2_dash_next_date_month)).setText(date.split(":")[1]);
        ((TextView) v.findViewById(R.id.page2_dash_next_date_weekday)).setText(date.split(":")[2]);

        if(!data.get("fromStation").isJsonNull())
            ((TextView) v.findViewById(R.id.page2_dash_next_station_from)).setText(data.get("fromStation").getAsString());
        if(!data.get("toStation").isJsonNull())
            ((TextView) v.findViewById(R.id.page2_dash_next_station_to)).setText(data.get("toStation").getAsString());
        if(!data.get("fromStationArrival").isJsonNull())
            ((TextView) v.findViewById(R.id.page2_dash_next_station_from_time)).setText(data.get("fromStationArrival").getAsString());
        if(!data.get("toStationArrival").isJsonNull())
            ((TextView) v.findViewById(R.id.page2_dash_next_station_to_time)).setText(data.get("toStationArrival").getAsString());

        if(!data.get("trainNumber").isJsonNull())
            ((TextView) v.findViewById(R.id.page2_dash_next_train_number)).setText(data.get("trainNumber").getAsString());
        if(!data.get("trainName").isJsonNull())
            ((TextView) v.findViewById(R.id.page2_dash_next_train_name)).setText(data.get("trainName").getAsString());

        if(!data.get("pnrNumber").isJsonNull())
            ((TextView) v.findViewById(R.id.page2_dash_next_train_pnr)).setText(data.get("pnrNumber").getAsString());
        if(!data.get("boardingStationCode").isJsonNull())
            ((TextView) v.findViewById(R.id.page2_dash_next_train_boarding)).setText(data.get("boardingStationCode").getAsString());
        if(!data.get("bookingStatus").isJsonNull())
            ((TextView) v.findViewById(R.id.page2_dash_next_train_booking_status)).setText(data.get("bookingStatus").getAsString());
        if(!data.get("reservationClass").isJsonNull())
            ((TextView) v.findViewById(R.id.page2_dash_next_train_class)).setText(data.get("reservationClass").getAsString());
        if(!data.get("currentStatus").isJsonNull())
            ((TextView) v.findViewById(R.id.page2_dash_next_pnr_status)).setText(data.get("currentStatus").getAsString());

        if(!data.get("resLastDay").isJsonNull())
            ((TextView) v.findViewById(R.id.page2_dash_reservation_period)).setText(
                    "Reservation avaliable upto " + data.get("resLastDay").getAsString());

        if(!data.get("updationDate").isJsonNull())
            ((TextView) v.findViewById(R.id.page2_last_updated_text)).setText("Last Updated at : " + data.get("updationDate").getAsString());

    }


    private void loadTrainTrackerTile(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                v.findViewById(R.id.page2_dash_live_tracker_layout).setVisibility(View.VISIBLE);
                ViewAnimator
                        .animate(v.findViewById(R.id.page2_dash_live_tracker_layout))
                        .alpha(0, 1)
                        .scale(0, 1)
                        .duration(1000)
                        .start();
            }
        }, 2000);
    }

    private String formatDate(String journeyDate) {
        SimpleDateFormat initialPattern = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat finalPattern = new SimpleDateFormat("dd:MMM, yyyy:");
        String parsedDate = ":";
        try {
            Date date = initialPattern.parse(journeyDate);
            parsedDate = finalPattern.format(date);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            String dayOfWeek = c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US);
            parsedDate += dayOfWeek;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parsedDate;
    }

    private void handleAnimations() {
        ViewAnimator
                .animate(v.findViewById(R.id.page2_dash), v.findViewById(R.id.page2_dash_live_tracker_layout),
                        v.findViewById(R.id.page2_btns_train_status), v.findViewById(R.id.page2_btns_pnr_status),
                        v.findViewById(R.id.page2_btns_timetable), v.findViewById(R.id.page2_btns_station_alarm),
                        v.findViewById(R.id.page2_last_updated_text))
                .alpha(1, 0).duration(10)
                .thenAnimate(v.findViewById(R.id.page2_dash))
                .translationY(1000, 0)
                .alpha(0, 1)
                .duration(1000)
                .thenAnimate(v.findViewById(R.id.page2_dash_live_tracker_layout))
                .scale(1, 0)
                .duration(1000)
                .thenAnimate(v.findViewById(R.id.page2_btns_train_status))
                .translationX(-100, 0)
                .alpha(0, 1)
                .andAnimate(v.findViewById(R.id.page2_btns_pnr_status))
                .translationX(100, 0)
                .alpha(0, 1)
                .duration(1000)
                .thenAnimate(v.findViewById(R.id.page2_btns_timetable))
                .translationX(-100, 0)
                .alpha(0, 1)
                .andAnimate(v.findViewById(R.id.page2_btns_station_alarm))
                .translationX(100, 0)
                .alpha(0, 1)
                .duration(1000)
                .thenAnimate(v.findViewById(R.id.page2_last_updated_text))
                .translationY(1000, 0)
                .alpha(0, 1)
                .duration(700)
                .start();
    }

}
