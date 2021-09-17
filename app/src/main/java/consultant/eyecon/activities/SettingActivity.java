package consultant.eyecon.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import consultant.eyecon.R;
import consultant.eyecon.models.AppConstants;
import consultant.eyecon.models.SettingModel;
import consultant.eyecon.utils.Utilities;

public class SettingActivity extends AppCompatActivity {
    private EditText dataBase;
    private EditText ipAddress;
    private EditText userName;
    private EditText passWord;
    EditText buid;
    EditText terminalId;
    private Button save;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Utilities.writeToSharedPrefrence(getApplicationContext(), AppConstants.KEY_LOGIN, "false");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dataBase = (EditText) findViewById(R.id.database);

        ipAddress = (EditText) findViewById(R.id.ip);
        userName = (EditText) findViewById(R.id.username);
        passWord = (EditText) findViewById(R.id.password);
        buid = (EditText) findViewById(R.id.buid);
        terminalId = (EditText) findViewById(R.id.terminal_id);

        String isSetting = Utilities.readSettingFromSharedPrefrence(getApplicationContext(), AppConstants.KEY_SETTING);
        if (!isSetting.equalsIgnoreCase("false")) {
            Gson gson = new Gson();
            SettingModel setting = gson.fromJson(isSetting, SettingModel.class);
            dataBase.setText(setting.getDatabase());
            ipAddress.setText(setting.getIpAddress());
            userName.setText(setting.getUserName());
            passWord.setText(setting.getPassword());
            buid.setText(setting.getBuid());
            terminalId.setText(setting.getTerminalid());
        }

        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSave();

            }
        });
    }

    private void attemptSave() {

        // Reset errors.
        dataBase.setError(null);
        ipAddress.setError(null);
        userName.setError(null);
        passWord.setError(null);
        buid.setError(null);
        terminalId.setError(null);

        // Store values at the time of the login attempt.

        String mDatabase = dataBase.getText().toString().trim();
        String mIpAddress = ipAddress.getText().toString().trim();
        String mUserName = userName.getText().toString().trim();
        String mPassword = passWord.getText().toString().trim();
        String mBuid = buid.getText().toString().trim();
        String mTerminalid = terminalId.getText().toString().trim();

        boolean cancel = false;
        View focusView = null;


        // Check for a valid mPassword, if the user entered one.
        if (TextUtils.isEmpty(mPassword)) {
            passWord.setError("required field");
            focusView = passWord;
            cancel = true;
        }

        if (TextUtils.isEmpty(mUserName)) {
            userName.setError("required field");
            focusView = userName;
            cancel = true;
        }

        // Check for a valid mUserName address.
        if (TextUtils.isEmpty(mDatabase)) {
            dataBase.setError("required field");
            focusView = dataBase;
            cancel = true;
        }

        if (TextUtils.isEmpty(mIpAddress)) {
            ipAddress.setError("required field");
            focusView = ipAddress;
            cancel = true;
        }

        if (TextUtils.isEmpty(mBuid)) {
            buid.setError("required field");
            focusView = buid;
            cancel = true;
        }

        if (TextUtils.isEmpty(mTerminalid)) {
            terminalId.setError("required field");
            focusView = terminalId;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            SettingModel settingModel = new SettingModel(mDatabase, mIpAddress, mUserName, mPassword, mBuid, mTerminalid);
            Gson gson = new Gson();
            String settingJson = gson.toJson(settingModel);
            Utilities.writeSettingToSharedPrefrence(getApplicationContext(), AppConstants.KEY_SETTING, settingJson);

            //Utilities.removeSharedPrefrence(getApplicationContext());

            Utilities.writeToSharedPrefrence(getApplicationContext(), AppConstants.KEY_BUID, buid.getText().toString().trim());
            Utilities.writeToSharedPrefrence(getApplicationContext(), AppConstants.KEY_TERMINAL_ID, terminalId.getText().toString().trim());
            Toast.makeText(SettingActivity.this, "Connection setting saved successfully", Toast.LENGTH_SHORT).show();
            //if (AppModel.getInstance().isSet) {
                Intent i = new Intent(this,LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            //}
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(SettingActivity.this, "You can't go back, Tap to save setting", Toast.LENGTH_SHORT).show();

    }
}
