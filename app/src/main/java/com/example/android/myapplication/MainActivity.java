package com.example.android.myapplication;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.android.myapplication.App.CHANNEL_2_ID;

public class MainActivity extends AppCompatActivity {

    public static final String CHANNEL_1_ID = "channel 1";

    Button button;
    TextView textView;

    private NotificationManagerCompat notificationManagerCompat;
    private EditText title1;
    private EditText description;

    private Notification notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManagerCompat = NotificationManagerCompat.from(this);

        title1 = findViewById(R.id.title);
        description = findViewById(R.id.description);

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });

        Button sendChannel1 = findViewById(R.id.channel1);
        Button sendChannel2 = findViewById(R.id.channel2);


        sendChannel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNotification();
            }
        });

        sendChannel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = title1.getText().toString();
                String message = description.getText().toString();

                Notification notification = new NotificationCompat.Builder(MainActivity.this, CHANNEL_2_ID)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setPriority(NotificationCompat.PRIORITY_LOW)
                        .build();

                notificationManagerCompat.notify(2, notification);
            }
        });
    }

    private void sendNotification() {
        String title = title1.getText().toString();
        String message = description.getText().toString();

        //This intent is for if the notification is clicked by the user
        //Defining where the user should go
        Intent resultIntent = new Intent(this, Main2Activity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);//TaskStackBuilder is used if we want to add other activities in back stack
        stackBuilder.addNextIntentWithParentStack(resultIntent);

        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.order);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            notification = new NotificationCompat.Builder(MainActivity.this, CHANNEL_1_ID)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setLargeIcon(bitmap)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true)
                    .setColor(Color.GREEN)
                    .setCategory(NotificationCompat.CATEGORY_ALARM)
                    .setContentIntent(resultPendingIntent)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .build();

            notificationManagerCompat.notify(1, notification);

            Toast.makeText(this, "From if " + Build.VERSION.SDK_INT, Toast.LENGTH_SHORT).show();
        } else {
            notification = new NotificationCompat.Builder(MainActivity.this)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setLargeIcon(bitmap)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true)
                    .setColor(Color.GREEN)
                    .setCategory(NotificationCompat.CATEGORY_ALARM)
                    .setContentIntent(resultPendingIntent)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .build();

            notificationManagerCompat.notify(1, notification);

            Toast.makeText(this, "From else " + Build.VERSION.SDK_INT, Toast.LENGTH_SHORT).show();
        }
    }
}
