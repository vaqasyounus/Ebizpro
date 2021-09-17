package consultant.eyecon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import consultant.eyecon.R;
import consultant.eyecon.adapters.Globaladapter;
import consultant.eyecon.adapters.Reportbalanceadapter;
import consultant.eyecon.interfaces.IDatabaseCallback;
import consultant.eyecon.models.Globalmodel;
import consultant.eyecon.models.Partybalance;
import consultant.eyecon.services.BackgroudTask;

public class ReportActivity extends AppCompatActivity implements IDatabaseCallback {

    protected Toolbar toolbar;

    List<String> locationsNames;

    TextView saleSummary;
    TextView currentStock;
    TextView partyLedger;
    TextView profitLoss;
    TextView globalname;
    BackgroudTask backgroudTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        currentStock = (TextView) findViewById(R.id.current_stock);
        globalname = (TextView) findViewById(R.id.globalname);
        String query_loc = "SELECT     firstname + ' ' + lastname AS Name FROM         TBL_Users WHERE     (User_ID = 14)";
        backgroudTask = new BackgroudTask(consultant.eyecon.activities.ReportActivity.this, 1);
        backgroudTask.execute(query_loc);
        currentStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReportActivity.this, ReportCurrentStockActivity.class));
            }
        });
        saleSummary = (TextView) findViewById(R.id.sales_summary);
        saleSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReportActivity.this, ReportSalesSummeryActivity.class));
            }
        });

        partyLedger = (TextView) findViewById(R.id.party_ledger);
        partyLedger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReportActivity.this, ReportPartyLedgerActivity.class));
            }
        });
        profitLoss = (TextView) findViewById(R.id.profitandloss);
        profitLoss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReportActivity.this, ReportProfitLossActivity.class));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.report_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_setting:
                startActivity(new Intent(this, SettingActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRequestSuccess(final ResultSet resultSet, int requestCode) {
        if (requestCode == 1) {
            try {
                if (resultSet != null) {
                    locationsNames = new ArrayList<String>();


                    while (resultSet.next()) {
                        String loc = resultSet.getString(1);
                        locationsNames.add("" + loc);
                    }
                    globalname.setText("" + locationsNames.get(0));


                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spiner_row_item, locationsNames);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                 //   globalname.setAdapter(dataAdapter);
                }
            } catch (Exception e) {
                Log.d("MyException", "loginActivityCallback");
            }

        }
    }
}
