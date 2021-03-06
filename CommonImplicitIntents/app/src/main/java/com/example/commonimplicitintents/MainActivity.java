package com.example.commonimplicitintents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView videoTxt, emailTxt, searchTxt;

    TextView messagingBtn, phonecallBtn, settingsBtn;

    TextView cameraBtn, calendarBtn, contactBtn;

    TextView thangWeb, thangAlarm, thangStorage;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static  Uri locationForPhotos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Init();
    }

    void Init(){
        //HUYNH
        messagingBtn = findViewById(R.id.message_btn);
        phonecallBtn = findViewById(R.id.phonecall_btn);
        settingsBtn = findViewById(R.id.settings_btn);

        messagingBtn.setOnClickListener(this);
        phonecallBtn.setOnClickListener(this);
        settingsBtn.setOnClickListener(this);
        //HUYNH

        //THUC
        videoTxt = findViewById(R.id.videoTxt);
        searchTxt = findViewById(R.id.searchTxt);
        emailTxt = findViewById(R.id.emailTxt);

        videoTxt.setOnClickListener(this);
        emailTxt.setOnClickListener(this);
        searchTxt.setOnClickListener(this);
        //THUC

        //THIEN
        cameraBtn = findViewById(R.id.thien1);
        calendarBtn = findViewById(R.id.thien2);
        contactBtn = findViewById(R.id.thien3);

        cameraBtn.setOnClickListener(this);
        calendarBtn.setOnClickListener(this);
        contactBtn.setOnClickListener(this);
        // Thang
        thangAlarm = findViewById(R.id.thangAlarm);
        thangStorage = findViewById(R.id.thangStorage);
        thangWeb = findViewById(R.id.thangWeb);

        thangWeb.setOnClickListener(this);
        thangStorage.setOnClickListener(this);
        thangAlarm.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            //---------------HUYNH---------------//
            //Text Messaging
            case R.id.message_btn:
                intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "Hello this is sending messaging an already built text for messaging");
                intent.setType("text/plain");
                break;
            //Phone call
            case R.id.phonecall_btn:
                intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + "035807274")); //Goi cho 1 so dien thoai dc set san LAM DON GIAN NHAT ROI DO
                break;
            //settings
            case R.id.settings_btn:
                intent = new Intent();
                intent.setAction(Settings.ACTION_SETTINGS);
                break;
            //--------------------HUYNH-------------//

            //-------------------THUC---------------//
            case  R.id.emailTxt:
                intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
                break;
            case R.id.videoTxt:

                intent = new Intent(MediaStore.INTENT_ACTION_MEDIA_PLAY_FROM_SEARCH);
                intent.putExtra(MediaStore.EXTRA_MEDIA_FOCUS,
                        MediaStore.Audio.Artists.ENTRY_CONTENT_TYPE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
                break;
            case R.id.searchTxt:
                intent = new Intent(Intent.ACTION_WEB_SEARCH);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
                break;
            //-------------------THUC---------------//
            //-------------------THIEN--------------//
            case  R.id.thien1:
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,true);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
                break;
            case R.id.thien3:
                intent = new Intent(Intent.ACTION_PICK);
                intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, 1);
                }

                break;
            case R.id.thien2:
                addEvent("Birthday","Restaurant",1,5);
                Toast.makeText(MainActivity.this,"Add Event Successfully",Toast.LENGTH_SHORT);

                break;
                //------------THIEN---------------//
            //------------THANG---------------//
            case R.id.thangWeb:
                String url = "en.uit.edu.vn";
                if (!url.startsWith("http://") && !url.startsWith("https://"))
                    url = "http://" + url;
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
                break;
            case R.id.thangAlarm:
                    createAlarm("b??o th???c",1,20);
                    break;
            case R.id.thangStorage:
                selectImage();
            //------------THANG---------------//
        }

//        startActivity(intent);
    }
    public void addEvent(String title, String location, long begin, long end) {
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.Events.TITLE, title)
                .putExtra(CalendarContract.Events.EVENT_LOCATION, location)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, begin)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, end);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    public void createAlarm(String message, int hour, int minutes) {
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
                .putExtra(AlarmClock.EXTRA_MESSAGE, message)
                .putExtra(AlarmClock.EXTRA_HOUR, hour)
                .putExtra(AlarmClock.EXTRA_MINUTES, minutes);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    static final int REQUEST_IMAGE_GET = 1;

    public void selectImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_GET);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_GET && resultCode == RESULT_OK) {
            Bitmap thumbnail = data.getParcelableExtra("data");
            Uri fullPhotoUri = data.getData();
            // Do work with photo saved at fullPhotoUri
        }
    }

}