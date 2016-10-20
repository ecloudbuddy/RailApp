package t90.railapp.swipePages;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import t90.railapp.R;


public class Page1 extends Fragment {

    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_page1, container, false);

        initUi();

        return v;
    }

    private void initUi() {
    }

}
