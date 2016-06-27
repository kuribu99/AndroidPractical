package com.kongmy.androidprac;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.jamesooi.geometry.Point;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DisplayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DisplayFragment extends Fragment {

    public interface OnDetachListener {
        void onDetach();
    }

    private static final String EXTRA_MID = "com.kongmy.androidprac.DisplayFragment.extra.mid";

    private Point midpoint;
    private OnDetachListener onDetachListener;
    private EditText tbxMidX;
    private EditText tbxMidY;

    public DisplayFragment() {
        // Required empty public constructor
    }

    public static DisplayFragment newInstance(Point midpoint, OnDetachListener onDetachListener) {
        DisplayFragment fragment = new DisplayFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_MID, midpoint);
        fragment.setArguments(bundle);
        fragment.onDetachListener = onDetachListener;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            midpoint = (Point) getArguments().getSerializable(EXTRA_MID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_display, container, false);

        tbxMidX = (EditText) view.findViewById(R.id.tbx_mid_x);
        tbxMidY = (EditText) view.findViewById(R.id.tbx_mid_y);

        if (midpoint != null) {
            tbxMidX.setText(String.valueOf(midpoint.getX()));
            tbxMidY.setText(String.valueOf(midpoint.getY()));
        }

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onDetachListener.onDetach();
    }

}
