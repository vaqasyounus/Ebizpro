package consultant.eyecon.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.util.ArrayList;

import consultant.eyecon.R;
import consultant.eyecon.adapters.ReportPartyLedgerAdapter;
import consultant.eyecon.interfaces.IDatabaseCallback;
import consultant.eyecon.models.AppConstants;
import consultant.eyecon.models.PartyLedgerModel;
import consultant.eyecon.services.BackgroudTask;
import consultant.eyecon.utils.Utilities;

public class ConfirmActivity extends AppCompatActivity implements IDatabaseCallback {

    RecyclerView recyclerView;
    ReportPartyLedgerAdapter adapter;
    private static ArrayList<PartyLedgerModel> list;
    static TextView empty;
    static TextView total;
    TextView proceed;
    static float totalAmount = 0;
    BackgroudTask backgroudTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        empty = findViewById(R.id.empty_cart);
        total = findViewById(R.id.totalAmount);
        proceed = findViewById(R.id.proceed);
        updateTotal();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ReportPartyLedgerAdapter(this, list);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmActivity.this);
                builder.setMessage("Payment type?").setPositiveButton("Cash", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppConstants.paytypeid = "25";
                        String query_loc = "INSERT INTO Tbl_PortalRecord (Date,UserId,PartyId,Amount,Discount,NetAmount,Iscancel,DeviceId,Remarks,PayTypeId,PRTypeID,IsPick) values('" + Utilities.currentDate() + "'," + AppConstants.userid + "," + AppConstants.partyid + ",'" + totalAmount + "','0','" + totalAmount + "','False','Android-OS','','" + AppConstants.paytypeid + "','" + AppConstants.prttypeid + "','False')";
                        backgroudTask = new BackgroudTask(ConfirmActivity.this, 1);
                        backgroudTask.execute(query_loc);
                    }
                }).setNegativeButton("Ledger", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppConstants.paytypeid = "28";
                        String query_loc = "INSERT INTO Tbl_PortalRecord (Date,UserId,PartyId,Amount,Discount,NetAmount,Iscancel,DeviceId,Remarks,PayTypeId,PRTypeID,IsPick) values('" + Utilities.currentDate() + "'," + AppConstants.userid + "," + AppConstants.partyid + ",'" + totalAmount + "','0','" + totalAmount + "','False','Android-OS','','" + AppConstants.paytypeid + "','" + AppConstants.prttypeid + "','False')";
                        backgroudTask = new BackgroudTask(ConfirmActivity.this, 1);
                        backgroudTask.execute(query_loc);
                    }
                }).show();

            }
        });

    }

    public static void updateTotal() {
        list = new ArrayList<>();
        totalAmount = 0;
        for (int j = 0; j < AppConstants.list.size(); j++) {
            String quan = AppConstants.list.get(j).getQuantity();
            if (quan != null) {
                if (Integer.parseInt(quan) > 0) {
                    totalAmount = (totalAmount + (Float.parseFloat(AppConstants.list.get(j).getBalance().trim()) * Integer.parseInt(quan)));
                    list.add(AppConstants.list.get(j));
                }
            }
        }
        total.setText("Total Amount : " + totalAmount + " PKR");
        if (list.size() == 0) {
            empty.setVisibility(View.VISIBLE);
        } else {
            empty.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onRequestSuccess(ResultSet resultSet, int requestCode) {
        if (requestCode == 1) {
            String query_loc = "select TrId from Tbl_PortalRecord order by Trid desc";
            backgroudTask = new BackgroudTask(ConfirmActivity.this, 2);
            backgroudTask.execute(query_loc);
        } else if (requestCode == 2) {
            String trid = "";
            try {

                while (resultSet.next()) {
                    trid = resultSet.getString("Trid");
                    break;
                }


                for (int j = 0; j < AppConstants.list.size(); j++) {
                    String quan = AppConstants.list.get(j).getQuantity();
                    if (quan != null) {
                        if (Integer.parseInt(quan) > 0) {
                            String query_loc = "INSERT INTO Tbl_PortalRecordDet (TrId,itemId,Qty) values ('" + trid + "','" + (int) Float.parseFloat(AppConstants.list.get(j).getPartyTypeID()) + "','" + quan + "')";
                            backgroudTask = new BackgroudTask(ConfirmActivity.this, 3);
                            backgroudTask.execute(query_loc);
                        }
                    }
                }
                Toast.makeText(this,"Thank you for using the App :)",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, IntroActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                this.finish(); // if the activity running has it's own context


            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
}
