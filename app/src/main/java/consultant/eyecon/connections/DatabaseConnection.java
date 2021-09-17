package consultant.eyecon.connections;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.SQLException;
import android.os.StrictMode;
import android.util.Log;

import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.DriverManager;

import consultant.eyecon.models.AppConstants;
import consultant.eyecon.models.SettingModel;
import consultant.eyecon.utils.Utilities;

/**
 * Created by Muhammad on 1/3/2017.
 */

public class DatabaseConnection {
    String ip;
    String classs;
    String db;
    String un;
    String password;
    Context context;

    public DatabaseConnection(Context context) {
//        ip = "113.203.249.17";
//        ip = ".";
////        db = "REC";
//        db = "REC1";
//        un = "sa";
//        password = "meb123+++";
        String isSetting = Utilities.readSettingFromSharedPrefrence(context, AppConstants.KEY_SETTING);
        if (!isSetting.equalsIgnoreCase("false")) {
            Gson gson = new Gson();
            SettingModel setting = gson.fromJson(isSetting, SettingModel.class);
            db = setting.getDatabase();
            ip = setting.getIpAddress();
            un = setting.getUserName();
            password = setting.getPassword();
        }


        classs = "net.sourceforge.jtds.jdbc.Driver";
        this.context = context;
    }

    @SuppressLint("NewApi")
    public Connection getInstance() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection con = null;
        String ConUrl = null;
        try {
            Class.forName(classs);
            ConUrl = "jdbc:jtds:sqlserver://" + ip + ";"
                    + "databaseName=" + db + ";user=" + un + ";password="
                    + password + ";";
            con = DriverManager.getConnection(ConUrl);
        } catch (SQLException se) {
            Log.d("ERROR", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.d("ERRO", e.getMessage());
        } catch (Exception e) {
            Log.d("ERRO", e.getMessage());
        }
        return con;
    }
}
