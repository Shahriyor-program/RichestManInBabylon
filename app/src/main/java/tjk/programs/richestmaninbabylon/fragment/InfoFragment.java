package tjk.programs.richestmaninbabylon.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ms.square.android.expandabletextview.ExpandableTextView;

import tjk.programs.richestmaninbabylon.R;

public class InfoFragment extends Fragment {

    private ExpandableTextView expandableTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        expandableTextView = view.findViewById(R.id.expand_text_view);
        expandableTextView.setText(getString(R.string.txt_info));
        return view;
    }
}