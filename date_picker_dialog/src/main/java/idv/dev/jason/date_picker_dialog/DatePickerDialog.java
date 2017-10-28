package idv.dev.jason.date_picker_dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Material 日期選擇器
 *
 * 將 DatePicker 元件整合於 DialogFragment 代替系統原生的 DatePickerDialog 元件，相容於 API 21 (含)以上，更改佈
 * 局可於 dialog_date_picker.xml 上做變更，設定元件相關屬性可在 styles_dialog_date_picker.xml 中自行定義。
 *
 * Created by Jason on 2017/10/28.
 */

public class DatePickerDialog extends DialogFragment implements View.OnClickListener {

    //************************************** 公用介面宣告 ****************************************//

    public interface OnDatePickerListener {
        void onDatePickerSelect(Calendar calendar);
    }


    //************************************** 公用變數宣告 ****************************************//

    public static final String TAG = "DatePickerDialog";


    //************************************** 私用變數宣告 ****************************************//

    private static final String KEY_CALENDAR = "KEY_CALENDAR";

    private OnDatePickerListener listener;

    private Calendar calendar;

    private DatePicker datePicker;

    private Button btnSubmit;
    private Button btnCancel;


    //*************************************** 建構子宣告 *****************************************//

    public static DatePickerDialog getInstance(Calendar calendar) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_CALENDAR, calendar);

        DatePickerDialog dialog = new DatePickerDialog();
        dialog.setArguments(bundle);

        return dialog;
    }


    //************************************** 生命週期實作 ****************************************//

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initParams();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initDialog();

        View parent = inflater.inflate(R.layout.dialog_date_picker, container, false);

        datePicker = parent.findViewById(R.id.date_picker);

        btnSubmit = parent.findViewById(R.id.btn_submit);
        btnCancel = parent.findViewById(R.id.btn_cancel);

        datePicker.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        btnSubmit.setText("確認");
        btnCancel.setText("取消");

        return parent;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        registerListener();
    }

    @Override
    public void onDestroy() {
        unregisterListener();
        releaseParams();

        super.onDestroy();
    }


    //******************************** OnClickListener 介面實作 **********************************//

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_submit)
        {
            if (null != listener)
                listener.onDatePickerSelect(getCalendarFromDatePicker(datePicker));
        }

        dismiss();
    }


    //************************************** 公用方法宣告 ****************************************//

    public void setOnDatePickerListener(OnDatePickerListener listener) {
        this.listener = listener;
    }


    //************************************** 私用方法宣告 ****************************************//

    private void initParams() {
        Bundle bundle = getArguments();
        calendar = (Calendar) bundle.getSerializable(KEY_CALENDAR);
    }

    private void releaseParams() { }


    private void registerListener() {
        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    private void unregisterListener() {
        btnSubmit.setOnClickListener(null);
        btnCancel.setOnClickListener(null);
    }


    private void initDialog() {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setCancelable(false);
    }


    private Calendar getCalendarFromDatePicker(DatePicker view) {
        int year = view.getYear();
        int month = view.getMonth();
        int dayOfMonth = view.getDayOfMonth();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        return calendar;
    }

}
