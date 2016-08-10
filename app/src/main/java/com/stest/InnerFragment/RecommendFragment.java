package com.stest.InnerFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.stest.neteasycloud.R;

/**
 * Created by Limuyang on 2016/7/7.
 */
public class RecommendFragment extends Fragment {

    @ViewInject(R.id.txt)
    private TextView txt;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.recomment_fragment, container, false);
        return v;
    }
}
