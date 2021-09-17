

        package consultant.eyecon.activities;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import consultant.eyecon.R;
import consultant.eyecon.adapters.ReportAdapter;
import consultant.eyecon.adapters.ReportPartyLedgerAdapter;
import consultant.eyecon.adapters.Reportbalanceadapter;
import consultant.eyecon.interfaces.IDatabaseCallback;
import consultant.eyecon.models.ItemModel2;
import consultant.eyecon.models.PartyLedgerModel;
import consultant.eyecon.models.PartyModel;
import consultant.eyecon.models.Partybalance;
import consultant.eyecon.services.BackgroudTask;

public class ReportBalance extends AppCompatActivity implements IDatabaseCallback {

    Spinner location;
    Spinner category;
    TextView textView211;
    TextView fromDate;
    TextView toDate;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener dateFrom;
    DatePickerDialog.OnDateSetListener dateTo;
    SimpleDateFormat toDateFormat;
    SimpleDateFormat fromDateFormat;
    List<String> locationsName;
    List<String> categoriesID;
    List<String> categoriesName;

    BackgroudTask backgroudTask;
    RecyclerView recyclerView1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reportbalnce);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        recyclerView1 = (RecyclerView) findViewById(R.id.recyclerView);


//        locationsName.add("SELECT     PartyType, PartyTypeId FROM         dbo.Tbl_Party_Type");
        //above spinner fill only type, in next spinner we will load according to the above spinner selection
//        locationsName.add("Supplier");

//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spiner_row_item, locationsName);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        location.setAdapter(dataAdapter);


        fromDate = (TextView) findViewById(R.id.from_date);
        toDate = (TextView) findViewById(R.id.to_date);
        myCalendar = Calendar.getInstance();

//               dateFrom = new DatePickerDialog.OnDateSetListener() {
//
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear,
//                                  int dayOfMonth) {
//                // TODO Auto-generated method stub
//                myCalendar.set(Calendar.YEAR, year);
//                myCalendar.set(Calendar.MONTH, monthOfYear);
//                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                String myFormat = "MM-dd-yyyy"; //In which you need put here
//                fromDateFormat = new SimpleDateFormat(myFormat, Locale.US);
//                fromDate.setText(fromDateFormat.format(myCalendar.getTime()));
//            }
//
//        };
//
//        dateTo = new DatePickerDialog.OnDateSetListener() {
//
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear,
//                                  int dayOfMonth) {
//                // TODO Auto-generated method stub
//                myCalendar.set(Calendar.YEAR, year);
//                myCalendar.set(Calendar.MONTH, monthOfYear);
//                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                String myFormat = "MM-dd-yyyy"; //In which you need put here
//                toDateFormat = new SimpleDateFormat(myFormat, Locale.US);
//                toDate.setText(toDateFormat.format(myCalendar.getTime()));
//            }
//
//        };
//
//        fromDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new DatePickerDialog(ReportPartyLedgerActivity.this, dateFrom, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });
//
//        toDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new DatePickerDialog(ReportPartyLedgerActivity.this, dateTo, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String query_loc = "SELECT     TrDate, Debit, Credit, Remarks FROM         dbo.VwMLedger where PartyName ='emp 1' ";
        backgroudTask = new BackgroudTask(ReportBalance.this, 2);
        backgroudTask.execute(query_loc);

//        location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String query_cate = "SELECT     PartyTypeID, PartyName \n " + location.getSelectedItem().toString() + " FROM        dbo.VwMLedger where PartyTypeID = 3";
//                //   + parent.getSelectedItem().toString() +"'" ;
//                //  String query_cate = "SELECT     PartyType, PartyTypeId FROM         dbo.Tbl_Party_Type" + parent.getSelectedItem().toString() ;
//                backgroudTask = new BackgroudTask(ReportBalance.this, 1);
//                backgroudTask.execute(query_cate);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

//        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
////                String q1 = "SELECT   '1900/1/1' as TrDate, sum( Debit) as Debit, sum(Credit) as Credit,'Opening' as Remarks\n" +
////                        "FROM         [REC_restaurant2].[dbo].[VwMGL]\n" +
////                        "WHERE     (partyid = " + categoriesID.get(category.getSelectedItemPosition()) + ") AND (TrDate <CONVERT(DATETIME, '" + "1900/1/1" + "', 102))\n" +
////                        "Union all\n" +
////                        "SELECT    TrDate, Debit, Credit, Remarks\n" +
////                        "FROM         [REC_restaurant2].[dbo].[VwMGL]\n" +
////                        "WHERE     (partyid = " + categoriesID.get(category.getSelectedItemPosition()) + ")" +
////                        "order by trdate\n";
//                String q1="SELECT     TrDate, Debit, Credit, Remarks FROM         dbo.VwMLedger  ";
//
//                backgroudTask = new BackgroudTask(ReportBalance.this, 2);
//                backgroudTask.execute(q1);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.report, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
                try {
                    Date from = sdf.parse(fromDate.getText().toString());
                    Date to = sdf.parse(toDate.getText().toString());
                    if (from.compareTo(to) > 0) {
                        Toast.makeText(consultant.eyecon.activities.ReportBalance.this, "invalid date", Toast.LENGTH_SHORT).show();
                    } else {
//                        String q1 = "SELECT   '1900/1/1' as TrDate, sum( Debit) as Debit, sum(Credit) as Credit,'Opening' as Remarks\n" +
//                                "FROM         [REC_restaurant2].[dbo].[VwMGL]\n" +
//                                "WHERE     (partyid = " + categoriesID.get(category.getSelectedItemPosition()) + ") AND (TrDate <CONVERT(DATETIME, '" + fromDate.getText().toString() + "', 102))\n" +
//                                "Union all\n" +
//                                "SELECT    TrDate, Debit, Credit, Remarks\n" +
//                                "FROM         [REC_restaurant2].[dbo].[VwMGL]\n" +
//                                "WHERE     (partyid = " + categoriesID.get(category.getSelectedItemPosition()) + ") AND (TrDate >= CONVERT(DATETIME, '" + fromDate.getText().toString() + "', 102)) AND (TrDate <= CONVERT(DATETIME, '" + toDate.getText().toString() + "', 102))\n" +
//                                "order by trdate\n";
                        String q1="SELECT     TrDate, Debit, Credit, Remarks FROM dbo.VwMLedger  ";

                        backgroudTask = new BackgroudTask(consultant.eyecon.activities.ReportBalance.this, 2);
                        backgroudTask.execute(q1);
                    }
                } catch (Exception e) {
                }
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onRequestSuccess(final ResultSet resultSet, int requestCode) {
         if (requestCode == 1) {
            final ArrayList<Partybalance> data = new ArrayList<>();
            try {
                if (resultSet != null) {
                    while (resultSet.next()) {
                        String trDate = resultSet.getString("TrDate");
                        String debit = resultSet.getString("Debit");
                        String credit = resultSet.getString("Credit");
                        String remarks = resultSet.getString("Remarks");
                        Partybalance partyLedgerModel = new Partybalance();
                        partyLedgerModel.setTrdate(trDate);
                        partyLedgerModel.setDebit(debit);
                        partyLedgerModel.setCredit(credit);
                        partyLedgerModel.setRemarks(remarks);

                        data.add(partyLedgerModel);

                    }


                    recyclerView1.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(consultant.eyecon.activities.ReportBalance.this);
                    recyclerView1.setLayoutManager(layoutManager);
                    Reportbalanceadapter adapter = new Reportbalanceadapter(consultant.eyecon.activities.ReportBalance.this, data);
                    recyclerView1.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

            } catch (Exception e) {
                Log.d("MyException", "loginActivityCallback");
            }
        }


    }
}

