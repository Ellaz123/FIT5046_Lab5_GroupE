package com.example.fit5046_lab5_groupe;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;



public class BottomDialog extends Dialog implements View.OnClickListener {
    private Context mContext;
    private LayoutInflater inflater;
    BottomDialogListener listener;
    private View view;

    public void setListener(BottomDialogListener listener) {
        this.listener = listener;
    }

    public BottomDialog(@NonNull Context context) {
        super(context, R.style.Theme_dialog);
        this.mContext = context;
        initView();
    }

    public BottomDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        initView();
    }

    private void initView() {
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.layout_bottom_dialog, null);
        view.findViewById(R.id.tvPie).setOnClickListener(this);
        view.findViewById(R.id.tvColumnChart).setOnClickListener(this);
        setContentView(view);
        setCancelable(true);
        initDialogParams(dp2px(108));
    }

    void initDialogParams(int height) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = mContext.getResources().getDisplayMetrics().widthPixels;
        params.height = height;
        view.setLayoutParams(params);
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
    }

    public static int dp2px(float dpValue) {
        return (int)(0.5F + dpValue * Resources.getSystem().getDisplayMetrics().density);
    }

    @Override
    public void onClick(View v) {
        if (listener == null) {
            return;
        }
        switch (v.getId()){
            case R.id.tvPie:
                listener.onPieChart();
                break;
            case R.id.tvColumnChart:
                listener.onColumnChart();
                break;
        }
        dismiss();
    }

    public interface BottomDialogListener {
        void onPieChart();
        void onColumnChart();
    }

}