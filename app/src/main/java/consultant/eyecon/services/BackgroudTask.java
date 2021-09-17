package consultant.eyecon.services;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import consultant.eyecon.connections.DatabaseConnection;
import consultant.eyecon.interfaces.IDatabaseCallback;
import consultant.eyecon.utils.Utilities;

/**
 * Created by Muhammad on 1/4/2017.
 */
public class BackgroudTask extends AsyncTask<String, String, ResultSet> {

    private ProgressDialog progressDialog;
    private boolean isShowProgress;
    private Connection connection;
    private DatabaseConnection databaseConnection;
    private IDatabaseCallback iDatabaseCallback;
    private Context context;
    private int REQ_CODE;


    public BackgroudTask(Activity activity, int reqCode) {
        this.iDatabaseCallback = (IDatabaseCallback) activity;
        this.context = (Context) activity;
        this.REQ_CODE = reqCode;
        this.databaseConnection = new DatabaseConnection(context);
        progressDialog = new ProgressDialog(this.context);
        progressDialog.setMessage("Loading");
        progressDialog.setCanceledOnTouchOutside(false);
        isShowProgress = true;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        if (this.isShowProgress)
            progressDialog.show();
    }

    @Override
    protected ResultSet doInBackground(String... params) {

        ResultSet resultSet = null;
        try {
            connection = databaseConnection.getInstance();
            if (connection != null) {
                Statement statement = connection.createStatement();
                resultSet = statement.executeQuery(params[0]);
            }

        } catch (Exception e) {

            Log.d("MyException", "doninBackgroud");
        }

        return resultSet;

    }

    @Override
    protected void onPostExecute(ResultSet resultSet) {
        super.onPostExecute(resultSet);
        progressDialog.dismiss();
        iDatabaseCallback.onRequestSuccess(resultSet, REQ_CODE);
    }
}
