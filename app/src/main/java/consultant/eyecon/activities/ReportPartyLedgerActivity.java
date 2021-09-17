package consultant.eyecon.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import consultant.eyecon.R;
import consultant.eyecon.adapters.ReportPartyLedgerAdapter;
import consultant.eyecon.interfaces.IDatabaseCallback;
import consultant.eyecon.models.PartyLedgerModel;
import consultant.eyecon.models.PartyModel;
import consultant.eyecon.models.PartyModel2;
import consultant.eyecon.services.BackgroudTask;

import static consultant.eyecon.database.RealEstateDao.a;

public class ReportPartyLedgerActivity extends AppCompatActivity implements IDatabaseCallback  {

    Spinner location;
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
    String PartyName= new String();
    List<PartyModel2> categoriesName;
    List<String> categoriesName2;
    ArrayList<PartyLedgerModel> data = new ArrayList<>();
     int[] a = {5,2,10,4,7,8,13,10,11,12,14,15,16,17,18};
    final String[] b= {"January","February","March","April","May","June","July","aug","sep","oct","nov","dec","123","456","678"};

   ReportPartyLedgerAdapter adapter;
    TextView one;
    TextView two;
    TextView three;
    //TextView four;


    BackgroudTask backgroudTask;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_ledger);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        two = (TextView) findViewById(R.id.credit);
        three = (TextView) findViewById(R.id.debit);
        //   four = (TextView) findViewById(R.id.remarks);

//        barChart = (HorizontalBarChart) findViewById(R.id.barchart);
//
//        ArrayList<BarEntry> entries;
//        entries = new ArrayList<>();
//        ArrayList<String> labels = new ArrayList<String>();
//
//        for(int i=0 ; i<14;i++){
//            entries.add(new BarEntry(a[i],i));
//            labels.add(b[i]);
//        }
////        entries.add(new BarEntry(19f, 0));
////
////         entries.add(new BarEntry(9f,1));
////
////        entries.add(new BarEntry(8f,2));
////        entries.add(new BarEntry(5f, 3));
////        entries.add(new BarEntry(20f, 4));
////        entries.add(new BarEntry(15f, 5));
////        entries.add(new BarEntry(15f, 6));
////        entries.add(new BarEntry(15f, 7));
////        entries.add(new BarEntry(10f,8));
////        entries.add(new BarEntry(19f,9));
////        entries.add(new BarEntry(1f,10));
////        entries.add(new BarEntry(2f,11));
//
//        BarDataSet bardataset = new BarDataSet(entries, "Cells");
//
//
//
//
////        labels.add("saqib ameen");
////        labels.add("adil shaikh");
////        labels.add("uzair bhai");
////        labels.add("2012");
////        labels.add("2011");
////        labels.add("2010");
////        labels.add(" bhai");
////        labels.add(" bhai");
////        labels.add("adi");l
////        labels.add("1234");
////        labels.add("5678");
////        labels.add("wrer");
//        for(int i=14; i<a.length;i++){
//            entries.add(new BarEntry(a[i],i));
//            labels.add(b[i]);
//        }
//
//
//
//
//
//        BarData data = new BarData(labels, bardataset);
//        barChart.setData(data); // set the data and list of labels into chart
//        barChart.setHorizontalScrollBarEnabled(true);
//
//        barChart.setDescription("Set Bar Chart Description Here");  // set the description
//        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
//        barChart.setVerticalScrollBarEnabled(true);
//        barChart.animateY(5000);

        location = (Spinner) findViewById(R.id.location_spinner);

        String query_loc = "SELECT     PartyType, PartyTypeId FROM         dbo.Tbl_Party_Type";
        backgroudTask = new BackgroudTask(ReportPartyLedgerActivity.this, 3);
        backgroudTask.execute(query_loc);

        location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int typeId  = locationsName.get(position).getPartyTypeId();

//                String query_cate = "SELECT     PartyTypeID, PartyName,SUM(Debit - Credit) AS Balance \n " + location.getSelectedItem().toString() + " FROM         dbo.VwMGL GROUP BY PartyName, PartyTypeID   where  ";
                //   + parent.getSelectedItem().toString() +"'" ;
                //  String query_cate = "SELECT     PartyType, PartyTypeId FROM         dbo.Tbl_Party_Type" + parent.getSelectedItem().toString() ;
                String q1= "SELECT      PartyName, SUM(Debit - Credit) AS Balance\n" +
                        "FROM         dbo.VwMGL\n" +
                "GROUP BY PartyName, PartyTypeID Having PartyTypeID = "+typeId+"";
                backgroudTask = new BackgroudTask(ReportPartyLedgerActivity.this, 2);
                backgroudTask.execute(q1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // category = (Spinner) findViewById(R.id.cat_spinner);

        fromDate = (TextView) findViewById(R.id.from_date);
        toDate = (TextView) findViewById(R.id.to_date);
        myCalendar = Calendar.getInstance();




        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//


//        location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                int typeId  = locationsName.get(i).getPartyTypeId();
//                //String q1 = "SELECT     TrDate, Debit, Credit  FROM         dbo.VwMLedger where PartyTypeID = "+typeId+"";
//                String q1= "SELECT     PartyTypeID, PartyName, SUM(Debit - Credit) AS Balance\n" +
//                        "FROM         dbo.VwMGL\n" +
//                        "GROUP BY PartyName, PartyTypeID ";
//                //  ""
//
//
//                //  String   query_cate = "select * from dbo.VwMLedger where " + location.getSelectedItem().toString() + " = '" + adapterView.getSelectedItem().toString() + "'";
//
//                backgroudTask = new BackgroudTask(ReportPartyLedgerActivity.this, 2);
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
        inflater.inflate(R.menu.toolbar_menu, menu);
        MenuItem search1=menu.findItem(R.id.search);
        SearchView searchView = (SearchView) search1.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
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
                NavUtils.navigateUpFromSameTask(this);
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
                    location.setAdapter(dataAdapter);


//
                    // String q1 = "SELECT     TrDate, Debit, Credit  FROM         dbo.VwMLedger";
                    String q1= " SELECT   Top 2  PartyTypeID, PartyName, SUM(Debit - Credit) AS Balance\\n\" +\n" +
                            "        \"FROM         dbo.VwMGL\\n\" +\n" +
                            "        \"GROUP BY PartyName, PartyTypeID " ;

                    backgroudTask = new BackgroudTask(ReportPartyLedgerActivity.this, 2);
                    backgroudTask.execute(q1);

                }

            } catch (Exception e) {
                Log.d("MyException", "loginActivityCallback");
            }

        } else if (requestCode == 2) {
            try {
                if (resultSet != null) {
                    data.clear();

                    while (resultSet.next()) {
                        // String trDate = resultSet.getString("TrDate");
                         PartyName = resultSet.getString("PartyName");

                        // String credit = resultSet.getString("credit");
                        String Balance = resultSet.getString("Balance");
                        PartyLedgerModel partyLedgerModel = new PartyLedgerModel();
                        partyLedgerModel.setPartyName(PartyName);
                        partyLedgerModel.setBalance(Balance);

                        data.add(partyLedgerModel);

                    }


                    recyclerView.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ReportPartyLedgerActivity.this);
                    recyclerView.setLayoutManager(layoutManager);
                     adapter = new ReportPartyLedgerAdapter(ReportPartyLedgerActivity.this,data);
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
                        PartyModel pm  =new PartyModel();
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

                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spiner_row_item, locationsName2);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    location.setAdapter(dataAdapter);

                }
            } catch (Exception e) {
                Log.d("MyException", "loginActivityCallback");
            }

        }
    }

   /* @Override
    public void onNoteClick(int position) {
        Intent n =new Intent(this, ProductActivity.class);
        n.putExtra("PartyName",data.get(position).getPartyName());
        startActivity(n);

    }*/
}

