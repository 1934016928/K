package com.skyrain.software.k;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KBind;
import com.skyrain.library.k.api.KFragment;
import com.skyrain.library.k.api.KListener;

@KFragment(R.layout.fragment_main)
public class MainFragment extends Fragment {

    @KBind(R.id.button3)
    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = BindClass.bind(this);
        button.setText("测试按钮");
        return view;
    }

    @KListener(R.id.button3)
    private void show() {
        Toast.makeText(getActivity(), "按钮被点击", Toast.LENGTH_SHORT).show();
    }
}
