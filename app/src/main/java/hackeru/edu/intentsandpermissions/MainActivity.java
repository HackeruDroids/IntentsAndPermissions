package hackeru.edu.intentsandpermissions;

import android.Manifest;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.AlarmClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private static final int REQUEST_CODE_CALL = 1;
    private static final int REQUEST_CODE_CAMERA = 2;
    private static final int REQUEST_CODE_LOCATION = 3;

    private EditText etPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etPhone = (EditText) findViewById(R.id.etPhoneNumber);

    }


    public void call(@Nullable View view) {
        Uri phoneUri = Uri.parse("tel:" + etPhone.getText());
        Intent callIntent = new Intent(Intent.ACTION_CALL, phoneUri);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE_CALL);
            return;
        }
        startActivity(callIntent);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case REQUEST_CODE_CALL:
                if (grantResults[0]  == PackageManager.PERMISSION_GRANTED){
                    call(null);
                }else {
                    Toast.makeText(this, "No Call For You...", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void next(View view) {
        Uri dataTel = Uri.parse("http://www.google.com"); //tel:0507123012
        //http:www.google.com
        //file://
        //geo:


        Intent nextIntent = new Intent(Intent.ACTION_VIEW, dataTel);

        startActivity(nextIntent);
        etPhone = (EditText) findViewById(R.id.etPhoneNumber);
    }

    public void setTimer(View view) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, this , 12, 20, true);
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Intent intent = new Intent(AlarmClock.ACTION_SET_TIMER);

        intent.putExtra(AlarmClock.EXTRA_LENGTH, 100);
        intent.putExtra(AlarmClock.EXTRA_MESSAGE, "Turn off the oven");
        intent.putExtra(AlarmClock.EXTRA_SKIP_UI, true);

        startActivity(intent);

        Toast.makeText(this, "Set", Toast.LENGTH_SHORT).show();
    }

    public void sendSMS(View view) {
        //1) Intent
        SmsManager smsManager = SmsManager.getDefault();

        smsManager.sendTextMessage("05000", null, "The Message", null, null);
    }
}
