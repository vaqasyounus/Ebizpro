package consultant.eyecon.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;

import java.sql.ResultSet;

import consultant.eyecon.R;
import consultant.eyecon.interfaces.IDatabaseCallback;
import consultant.eyecon.models.AppConstants;
import consultant.eyecon.services.BackgroudTask;
import consultant.eyecon.utils.Utilities;

/**
 * Created by Muhammad on 1/5/2017.
 */

public class LoginActivity extends AppCompatActivity implements IDatabaseCallback {

    private EditText userName;
    private EditText passWord;
    private Button login;
    private ImageView setting1;
    private BackgroudTask backgroudTask;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userName = (EditText) findViewById(R.id.username);
        passWord = (EditText) findViewById(R.id.password);
        setting1 = (ImageView) findViewById(R.id.imgSetting);

        userName.setText(Prefs.getString("user_name", ""));
        passWord.setText(Prefs.getString("user_password", ""));
        setting1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n = new Intent(LoginActivity.this, SettingActivity.class);
                startActivity(n);
            }
        });

        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         String isSetting = Utilities.readSettingFromSharedPrefrence(getApplicationContext(), AppConstants.KEY_SETTING);
                                         if (!isSetting.equalsIgnoreCase("false")) {
                                             if (Utilities.isConnnected((Context) LoginActivity.this)) {
                                                 attemptLogin();
                                                 Utilities.writeToSharedPrefrence(getApplicationContext(), AppConstants.KEY_FIRST_RUN, "1");
                                             }
                                         } else {
                                             Toast.makeText(LoginActivity.this, "connection setting is missing", Toast.LENGTH_SHORT).show();
                                             startActivity(new Intent(LoginActivity.this, SettingActivity.class));
                                         }
                                     }
                                 }

        );
//        login.performClick();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_setting) {
            startActivity(new Intent(LoginActivity.this, SettingActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void attemptLogin() {

        // Reset errors.
        userName.setError(null);
        passWord.setError(null);

        // Store values at the time of the login attempt.
        String mUserName = userName.getText().toString().trim();
        String mPassword = passWord.getText().toString().trim();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid mPassword, if the user entered one.
        if (TextUtils.isEmpty(mPassword)) {
            passWord.setError("required field");
            focusView = passWord;
            cancel = true;
        }

        // Check for a valid mUserName address.
        if (TextUtils.isEmpty(mUserName)) {
            userName.setError("required field");
            focusView = userName;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            String query = "select * from TBL_Users where Username='" + mUserName + "' and UserPass='" + mPassword + "' and IsAllowReportingApp = 'True'";
            backgroudTask = new BackgroudTask(LoginActivity.this, 1);
            backgroudTask.execute(query);
        }
    }

    @Override
    public void onRequestSuccess(ResultSet resultSet, int requestCode) {
        try {
            if (resultSet != null) {
                if (resultSet.next()) {
                    Utilities.writeToSharedPrefrence(getApplicationContext(), AppConstants.KEY_LOGIN, "true");
                    Utilities.writeToSharedPrefrence(getApplicationContext(), AppConstants.KEY_CUST_ID, resultSet.getString("User_ID"));
                    AppConstants.userid = resultSet.getString("User_ID");
                    Utilities.writeToSharedPrefrence(getApplicationContext(), AppConstants.KEY_USER_GROUP_ID, resultSet.getString("UserGroupID"));
//                    Utilities.writeToSharedPrefrence(getApplicationContext(), AppConstants.KEY_AGENT_SLECTED, resultSet.getString("LinkAgent"));
                    Utilities.writeToSharedPrefrence(getApplicationContext(), AppConstants.KEY_USER, userName.getText().toString());
                    Prefs.putString("user_name", userName.getText().toString());
                    Prefs.putString("user_password", passWord.getText().toString());
                    startActivity(new Intent(LoginActivity.this, IntroActivity.class));
                    finish();
                } else {
                    Toast.makeText(this, "Invalid Credentials.", Toast.LENGTH_SHORT).show();
                }
            }

        } catch (Exception e) {
            Log.d("MyException", "loginActivityCallback");
        }
    }
}
