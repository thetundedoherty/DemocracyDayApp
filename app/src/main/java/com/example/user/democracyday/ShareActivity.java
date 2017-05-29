package com.example.user.democracyday;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;


public class ShareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
    }

        public void shareMessage(View view) {
            EditText nameField = (EditText) findViewById(R.id.name_field);
            String name = nameField.getText().toString();

            EditText locationField = (EditText) findViewById(R.id.location_field);
            String location = locationField.getText().toString();

            EditText messageField = (EditText) findViewById(R.id.message_field);
            String message = messageField.getText().toString();

            Intent tweetIntent = new Intent(Intent.ACTION_SEND);
            tweetIntent.putExtra(Intent.EXTRA_TEXT,  message + " from " + name + ", "+ location);
            tweetIntent.setType("text/plain");

            PackageManager packManager = getPackageManager();
            List<ResolveInfo> resolvedInfoList = packManager.queryIntentActivities(tweetIntent,  PackageManager.MATCH_DEFAULT_ONLY);

            boolean resolved = false;
            for(ResolveInfo resolveInfo: resolvedInfoList){
                if(resolveInfo.activityInfo.packageName.startsWith("com.twitter.android")){
                    tweetIntent.setClassName(
                            resolveInfo.activityInfo.packageName,
                            resolveInfo.activityInfo.name );
                    resolved = true;
                    break;
                }
            }
            if(resolved){
                startActivity(tweetIntent);
            }else{
                Toast.makeText(this, "Twitter app isn't found", Toast.LENGTH_LONG).show();
            }

        }


}


