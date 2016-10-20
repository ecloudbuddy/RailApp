package t90.railapp;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.dragankrstic.autotypetextview.AutoTypeTextView;
import com.gigamole.navigationtabstrip.NavigationTabStrip;

import java.util.ArrayList;

import t90.railapp.swipePages.Page1;
import t90.railapp.swipePages.Page2;
import t90.railapp.swipePages.Page3;
import t90.railapp.swipePages.SwipePageAdapter;

public class HomeScreen extends AppCompatActivity {

    ViewPager swipePager;
    SwipePageAdapter swipePageAdapter;
    ArrayList<Fragment> swipePages;
    AutoTypeTextView title, ticker;
    NavigationTabStrip navigationTabStrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        commonUtils.setStatusBarTheme(HomeScreen.this);
        initUi();
    }

    private void initUi() {
        swipePager = (ViewPager) findViewById(R.id.swipePager);
        navigationTabStrip = (NavigationTabStrip) findViewById(R.id.home_swipe_page_tabs);

        swipePageAdapter = new SwipePageAdapter(getSupportFragmentManager(), getPages());
        swipePager.setAdapter(swipePageAdapter);

        swipePager.setCurrentItem(1);
        navigationTabStrip.setViewPager(swipePager);
        navigationTabStrip.setStripColor(Color.RED);
        navigationTabStrip.setStripType(NavigationTabStrip.StripType.LINE);
        navigationTabStrip.setTitles("Settings", "Home", "Upcoming");

        Toolbar toolbar = (Toolbar) findViewById(R.id.home_toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        title = (AutoTypeTextView) findViewById(R.id.home_title);
        ticker = (AutoTypeTextView) findViewById(R.id.home_count_ticker);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                title.setTextAutoTypingWithMistakes("RailApp", 7);
//                ticker.setTextAutoTyping("100");
            }
        }, 200);

    }


    public ArrayList<Fragment> getPages() {
        swipePages = new ArrayList<>();

        swipePages.add(new Page1());
        swipePages.add(new Page2());
        swipePages.add(new Page3());

        return swipePages;
    }

}
