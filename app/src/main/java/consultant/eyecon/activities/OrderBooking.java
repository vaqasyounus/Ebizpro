package consultant.eyecon.activities;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.HorizontalBarChart;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import consultant.eyecon.R;
import consultant.eyecon.adapters.ReportPartyLedgerAdapter;
import consultant.eyecon.interfaces.IDatabaseCallback;
import consultant.eyecon.models.AppConstants;
import consultant.eyecon.models.PartyLedgerModel;
import consultant.eyecon.models.PartyModel;
import consultant.eyecon.models.PartyModel2;
import consultant.eyecon.services.BackgroudTask;
import consultant.eyecon.utils.Utilities;

public class OrderBooking extends AppCompatActivity implements IDatabaseCallback {

    //    Spinner location;
    AutoCompleteTextView autoCompleteTextView;
    Spinner category;
    TextView textView211;
    TextView fromDate;
    TextView toDate;
    Calendar myCalendar;
    HorizontalBarChart barChart;
    DatePickerDialog.OnDateSetListener dateFrom;
    DatePickerDialog.OnDateSetListener dateTo;
    SimpleDateFormat toDateFormat;
    SimpleDateFormat fromDateFormat;
    List<PartyModel> locationsName;
    List<String> locationsName2;
    List<String> categoriesID;
    String PartyName = new String();
    List<PartyModel2> categoriesName;
    List<String> categoriesName2;
    ArrayList<PartyLedgerModel> data = new ArrayList<>();
    int[] a = {5, 2, 10, 4, 7, 8, 13, 10, 11, 12, 14, 15, 16, 17, 18};
    final String[] b = {"January", "February", "March", "April", "May", "June", "July", "aug", "sep", "oct", "nov", "dec", "123", "456", "678"};

    ReportPartyLedgerAdapter adapter;
    TextView one;
    TextView two;
    TextView three;
    //TextView four;
    TextView typeTV;

    BackgroudTask backgroudTask;
    RecyclerView recyclerView;
    static TextView total;
    static float totalAmount = 0;
    ImageView customerInfo;
    TextView ledgerTv;
    TextView cashTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        two = (TextView) findViewById(R.id.credit);
        three = (TextView) findViewById(R.id.debit);
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
//        location = (Spinner) findViewById(R.id.location_spinner);
        typeTV = findViewById(R.id.type);
        total = findViewById(R.id.totalAmount);
        customerInfo = findViewById(R.id.customerInfo);
        ledgerTv = findViewById(R.id.ledger);
        cashTv = findViewById(R.id.cash);

        int type = getIntent().getIntExtra("type", 0);
        if (type == 4)
            typeTV.setText("Customer");
        else if (type == 1)
            typeTV.setText("Supplier");

//        String query_loc = "SELECT    PartyName as PartyType,PartyId as PartyTypeId FROM         dbo.Tbl_Party where PartyTypeId=" + type;//  PartytypeId=4 is customer and 1 is supplier
        String query_loc = "SELECT    A.PartyName as PartyType,A.PartyId as PartyTypeId,B.PartyCode as Account FROM dbo.Tbl_Party A INNER JOIN TBL_Party_CustDet B on A.PartyId = B.PartyID where PartyTypeId=" + type;//  PartytypeId=4 is customer and 1 is supplier
        backgroudTask = new BackgroudTask(OrderBooking.this, 3);
        backgroudTask.execute(query_loc);

        /*location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int typeId = locationsName.get(position).getPartyTypeId();
                String q1 = "select * from VwMCurrentStock";
                backgroudTask = new BackgroudTask(OrderBooking.this, 2);
                backgroudTask.execute(q1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int typeId = locationsName.get(position).getPartyTypeId();
                AppConstants.partyid = typeId + "";
                String q1 = "select * from VwMCurrentStock";
                backgroudTask = new BackgroudTask(OrderBooking.this, 2);
                backgroudTask.execute(q1);
            }
        });
        setSupportActionBar(toolbar);

        customerInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        cashTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(OrderBooking.this);
                EditText edittext = new EditText(OrderBooking.this);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(100, 0, 100, 0);
                edittext.setLayoutParams(layoutParams);
                edittext.setHint("Enter amount here . . .");
                alert.setTitle("Confirm Your Payment");

                alert.setView(edittext);

                alert.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        try {
                            float cashValue = Float.parseFloat(edittext.getText().toString());
                            if (totalAmount == cashValue) {
                                AppConstants.paytypeid = "25";
                                String query_loc = "INSERT INTO Tbl_PortalRecord (Date,UserId,PartyId,Amount,Discount,NetAmount,Iscancel,DeviceId,Remarks,PayTypeId,PRTypeID,IsPick) values('" + Utilities.currentDate() + "'," + AppConstants.userid + "," + AppConstants.partyid + ",'" + totalAmount + "','0','" + totalAmount + "','False','Android-OS','','" + AppConstants.paytypeid + "','" + AppConstants.prttypeid + "','False')";
                                backgroudTask = new BackgroudTask(OrderBooking.this, 1);
                                backgroudTask.execute(query_loc);
                            } else {
                                Toast.makeText(OrderBooking.this, "Invalid amount", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(OrderBooking.this, "Invalid amount", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                    }
                });

                alert.show();


            }
        });
        ledgerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(OrderBooking.this);
                builder.setMessage("Are you sure, you want to proceed").setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppConstants.paytypeid = "28";
                        String query_loc = "INSERT INTO Tbl_PortalRecord (Date,UserId,PartyId,Amount,Discount,NetAmount,Iscancel,DeviceId,Remarks,PayTypeId,PRTypeID,IsPick) values('" + Utilities.currentDate() + "'," + AppConstants.userid + "," + AppConstants.partyid + ",'" + totalAmount + "','0','" + totalAmount + "','False','Android-OS','','" + AppConstants.paytypeid + "','" + AppConstants.prttypeid + "','False')";
                        backgroudTask = new BackgroudTask(OrderBooking.this, 4);
                        backgroudTask.execute(query_loc);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.login, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_setting) {
            startActivity(new Intent(OrderBooking.this, OrderTaking.class));
            return true;
        }
        if (id == R.id.action_addtocart) {
            startActivity(new Intent(OrderBooking.this, ConfirmActivity.class));
            return true;
        }

        switch (item.getItemId()) {
//            case R.id.search:
//                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
//                try {
//                    Date from = sdf.parse(fromDate.getText().toString());
//                    Date to = sdf.parse(toDate.getText().toString());
//                    if (from.compareTo(to) > 0) {
//                        Toast.makeText(ReportPartyLedgerActivity.this, "invalid date", Toast.LENGTH_SHORT).show();
//                    } else {
////
//                        // String q1 = "SELECT     TrDate, Debit, Credit  FROM         dbo.VwMLedger";
//                        String q1= " SELECT     PartyTypeID, PartyName, SUM(Debit - Credit) AS Balance\\n\" +\n" +
//                                "        \"FROM         dbo.VwMGL\\n\" +\n" +
//                                "        \"GROUP BY PartyName, PartyTypeID " ;
//
//                        backgroudTask = new BackgroudTask(ReportPartyLedgerActivity.this, 2);
//                        backgroudTask.execute(q1);
//                    }
//                } catch (Exception e) {
//                }
//                return true;

            case android.R.id.home:
                onBackPressed();
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
                    categoriesName2 = new ArrayList<String>();
                    categoriesName = new ArrayList<PartyModel2>();
                    locationsName2.add("all");
                    while (resultSet.next()) {
                        String cate = resultSet.getString(1);
                        int id = resultSet.getInt(2);
                        PartyModel2 pm2 = new PartyModel2();
                        pm2.setPartyName(cate.toUpperCase());
                        pm2.setPartyTypeId(id);
                        categoriesName.add(pm2);
                        categoriesName2.add("" + cate);
                    }
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spiner_row_item, categoriesName2) {

                    };
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    location.setAdapter(dataAdapter);
                    autoCompleteTextView.setAdapter(dataAdapter);


//
                    // String q1 = "SELECT     TrDate, Debit, Credit  FROM         dbo.VwMLedger";
                    String q1 = " SELECT   Top 2  PartyTypeID, PartyName, SUM(Debit - Credit) AS Balance\\n\" +\n" +
                            "        \"FROM         dbo.VwMGL\\n\" +\n" +
                            "        \"GROUP BY PartyName, PartyTypeID ";

                    backgroudTask = new BackgroudTask(OrderBooking.this, 2);
                    backgroudTask.execute(q1);

                }

            } catch (Exception e) {
                Log.d("MyException", "loginActivityCallback");
            }

        } else if (requestCode == 2) {
            total.setText("Total amount : 0 PKR");
            try {
                if (resultSet != null) {
                    data.clear();

                    while (resultSet.next()) {
                        // String trDate = resultSet.getString("TrDate");
                        PartyName = resultSet.getString("item");

                        // String credit = resultSet.getString("credit");
                        float Balance = resultSet.getInt("item_price");
                        float id = resultSet.getInt("item_ID");
                        PartyLedgerModel partyLedgerModel = new PartyLedgerModel();
                        partyLedgerModel.setPartyName(PartyName);
                        partyLedgerModel.setBalance(Balance + "");
                        partyLedgerModel.setPartyTypeID(id + "");

                        data.add(partyLedgerModel);

                    }


                    recyclerView.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(OrderBooking.this);
                    recyclerView.setLayoutManager(layoutManager);
                    AppConstants.list = data;
                    adapter = new ReportPartyLedgerAdapter(OrderBooking.this, data);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }

            } catch (Exception e) {
                Log.d("MyException", "loginActivityCallback");
            }
        } else if (requestCode == 3) {
            try {
                if (resultSet != null) {
                    locationsName = new ArrayList<>();
                    locationsName2 = new ArrayList<>();

                    while (resultSet.next()) {
                        String loc = resultSet.getString(1);
                        int id = resultSet.getInt(2);
                        PartyModel pm = new PartyModel();
                        pm.setPartyType(loc.toUpperCase());
                        pm.setPartyTypeId(id);
                        pm.setPartyName(loc.toUpperCase());
                        locationsName.add(pm);
                        locationsName2.add(loc);
//
                    }
//                    one.setText("" + locationsName.get(0));
//                    two.setText("" + locationsName.get(1));
//                    three.setText("" + locationsName.get(2));
//                    four.setText("" + locationsName.get(3));

                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.auto_text_item, locationsName2);
//                    dataAdapter.setDropDownViewResource(R.layout.auto_text_item);
                    autoCompleteTextView.setThreshold(1);
                    autoCompleteTextView.setAdapter(dataAdapter);
//                    location.setAdapter(dataAdapter);

                }
            } catch (Exception e) {
                Log.d("MyException", "loginActivityCallback");
            }

        } else if (requestCode == 4) {
            String query_loc = "select TrId from Tbl_PortalRecord order by Trid desc";
            backgroudTask = new BackgroudTask(OrderBooking.this, 5);
            backgroudTask.execute(query_loc);
        } else if (requestCode == 5) {
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
                            backgroudTask = new BackgroudTask(OrderBooking.this, 3);
                            backgroudTask.execute(query_loc);
                        }
                    }
                }
                Toast.makeText(this, "Thank you for using the App :)", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, IntroActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                this.finish(); // if the activity running has it's own context


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static void updateTotal() {
        totalAmount = 0;
        for (int j = 0; j < AppConstants.list.size(); j++) {
            String quan = AppConstants.list.get(j).getQuantity();
            if (quan != null) {
                if (Float.parseFloat(quan) > 0) {
                    totalAmount = (totalAmount + (Float.parseFloat(AppConstants.list.get(j).getBalance().trim()) * Float.parseFloat(quan)));
                }
            }
        }
        total.setText("Total Amount : " + totalAmount + " PKR");
    }


}

