package consultant.eyecon.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import consultant.eyecon.models.AppConstants;

/**
 * Created by Muhammad on 1/1/2017.
 */

public class Utilities {

    public static boolean isConnnected(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (!(networkInfo != null && networkInfo.isConnected())) {

            Toast.makeText(context, "No Internet connection, Make sure that WI-FI or mobile data is turned on,then try again.", Toast.LENGTH_LONG).show();
        }
        return (networkInfo != null && networkInfo.isConnected());
    }

    public static void writeToSharedPrefrence(Context context, String key, String value) {
        SharedPreferences sharedPref = context.getSharedPreferences(AppConstants.EYECON_SHAREDPREF, Context.MODE_WORLD_WRITEABLE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String readFromSharedPrefrence(Context context, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(AppConstants.EYECON_SHAREDPREF, Context.MODE_WORLD_READABLE);
        return sharedPref.getString(key, "false");
    }

    public static void writeSettingToSharedPrefrence(Context context, String key, String value) {
        SharedPreferences sharedPref = context.getSharedPreferences(AppConstants.EYECON_SETTING_SHAREDPREF, Context.MODE_WORLD_WRITEABLE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String readSettingFromSharedPrefrence(Context context, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(AppConstants.EYECON_SETTING_SHAREDPREF, Context.MODE_WORLD_READABLE);
        return sharedPref.getString(key, "false");
    }

    public static void removeSharedPrefrence(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(AppConstants.EYECON_SHAREDPREF, Context.MODE_WORLD_READABLE);
        sharedPref.edit().clear().commit();

    }

    public static boolean isValid(ViewGroup viewGroup) {
        for (View view : viewGroup.getTouchables()) {
            if (view instanceof EditText) {
                EditText editText = (EditText) view;
                String text = editText.getText().toString().trim();
                if (text.isEmpty()) {
                    editText.setError("Required");
                    editText.requestFocus();
                    return false;
                } else {
                    if (editText.getInputType() == InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS + InputType.TYPE_CLASS_TEXT) {
                        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
                            editText.setError("Invalid Email");
                            editText.requestFocus();
                            return false;
                        } else {
                            editText.setError(null);
                        }
                    } else {
                        editText.setError(null);
                    }
                }
            }
        }

        return true;
    }

    public static String  currentDate(){
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date dateobj = new Date();
        return df.format(dateobj);
    }


}
