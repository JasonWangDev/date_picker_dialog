package idv.dev.jason.datepickerdialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

import idv.dev.jason.date_picker_dialog.DatePickerDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
                                                                DatePickerDialog.OnDatePickerListener {

    private Calendar calendar;

    private Button btnDate;
    private TextView tvDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendar = Calendar.getInstance();

        btnDate = findViewById(R.id.btn_date);
        btnDate.setOnClickListener(this);

        tvDate = findViewById(R.id.tv_date);
    }


    @Override
    public void onClick(View view) {
        DatePickerDialog datePicker = DatePickerDialog.getInstance(calendar);
        datePicker.setOnDatePickerListener(this);

        datePicker.show(getSupportFragmentManager(), DatePickerDialog.TAG);
    }


    @Override
    public void onDatePickerSelect(Calendar calendar) {
        this.calendar.setTime(calendar.getTime());

        tvDate.setText(String.format("%tF", calendar.getTime()));
    }

}
