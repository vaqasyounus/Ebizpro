
package consultant.eyecon.activities;

import android.app.DatePickerDialog;
import android.arch.persistence.room.Database;
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
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import net.sourceforge.jtds.jdbc.JtdsResultSet;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import consultant.eyecon.R;
import consultant.eyecon.adapters.ReportAdapter;
import consultant.eyecon.adapters.ReportExpendibleListAdapter;
import consultant.eyecon.interfaces.IDatabaseCallback;
import consultant.eyecon.models.ItemModel2;
import consultant.eyecon.models.SalessummaryModel;
import consultant.eyecon.services.BackgroudTask;

public class ReportSalesSummeryActivity extends AppCompatActivity implements IDatabaseCallback, View.OnClickListener {
    Spinner spinner_one;
    Spinner spinner_two;
    List<String> locationsName;
    List<String> locationsNames;

    List<String> categoriesName;
    ArrayList<SalessummaryModel> data = new ArrayList<>();
    BackgroudTask backgroudTask;
    RecyclerView recyclerView;
    ReportExpendibleListAdapter adapter;
    int cat = 1;
    TextView fromDate;
    TextView toDate;
    TextView t1;
    Calendar myCalendar;
    int month, day, year;
Button button;
    SimpleDateFormat toDateFormat;
    SimpleDateFormat fromDateFormat;
    TextView first;
    TextView second;
    TextView third;
    TextView fourth;

    String ra, rs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_summery);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
button=(Button)findViewById(R.id.serachbutton);
        spinner_one = (Spinner) findViewById(R.id.location_spinner);

        first = (TextView) findViewById(R.id.first);
        second = findViewById(R.id.second);
        third = findViewById(R.id.third);
        fourth = findViewById(R.id.fourth);

        first.setSelected(true);
        second.setSelected(true);
        third.setSelected(true);
        fourth.setSelected(true);

        first.setOnClickListener(this);
        second.setOnClickListener(this);
        third.setOnClickListener(this);
        fourth.setOnClickListener(this);
        t1 = (TextView) findViewById(R.id.te);

        fromDate = (TextView) findViewById(R.id.from_date);
        toDate = (TextView) findViewById(R.id.t_date);
        myCalendar = Calendar.getInstance();

        day=myCalendar.get(Calendar.DAY_OF_MONTH);
        month=myCalendar.get(Calendar.MONTH);
        year=myCalendar.get(Calendar.YEAR);
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        fromDateFormat = new SimpleDateFormat(myFormat, Locale.US);
      //  t1.setText(fromDateFormat.format(myCalendar.getTime()));

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ReportSalesSummeryActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthofyear, int dayOfMonth) {

                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthofyear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String myFormat = "yyyy-MM-dd"; //In which you need put here
                        fromDateFormat = new SimpleDateFormat(myFormat, Locale.US);
                        t1.setText(fromDateFormat.format(myCalendar.getTime()));

                        //  t1.setText(myFormat);
                        String date = t1.getText().toString();
                        String toda = toDate.getText().toString();
                        String query_cate = "";
                        if (spinner_two.getSelectedItem().toString().trim().equalsIgnoreCase("All")) {
                            query_cate = "select * from VwMSalesSummary where Sales_date >='" + date + "' AND Sales_date <='" + toda + "' AND Location ='" +spinner_one.getSelectedItem().toString()+ "'";
                            // String query_cat = "select * from VWMProfitLoss where
                            // query_cate=query_cate+"Sales_date <'" +toda+ "'";;


                        } else {
                            query_cate = "select * from VwMSalesSummary where Category = '" + spinner_two.getSelectedItem().toString() + "' AND Location = '" + spinner_two.getSelectedItem() + "'";
                            //query_cate = "select * VWMProfitLoss where Category " + spinner_one.getSelectedItem().toString() + " = '" + parent.getSelectedItem().toString() + "'";

                            query_cate = query_cate + "And Sales_date >='" + date + "' AND Sales_date <='" + toda + "'";

                        }

                        backgroudTask = new BackgroudTask(consultant.eyecon.activities.ReportSalesSummeryActivity.this, 3);
                        backgroudTask.execute(query_cate);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ReportSalesSummeryActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthofyear, int dayOfMonth) {

                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthofyear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String myFormat = "yyyy-MM-dd"; //In which you need put here
                        fromDateFormat = new SimpleDateFormat(myFormat, Locale.US);
                        toDate.setText(fromDateFormat.format(myCalendar.getTime()));


                        //  t1.setText(myFormat);
                        String date = t1.getText().toString();
                        String toda = toDate.getText().toString();
                        String query_cate = "";
                        if (spinner_two.getSelectedItem().toString().trim().equalsIgnoreCase("All")) {
                            query_cate = "select * from VwMSalesSummary where Sales_date >='" + date + "' AND Sales_date <='" + toda + "' AND Location ='" +spinner_one.getSelectedItem().toString()+ "'";
                            // String query_cat = "select * from VWMProfitLoss where
                            // query_cate=query_cate+"Sales_date <'" +toda+ "'";;


                        } else {
                            query_cate = "select * from VwMSalesSummary where Category = '" + spinner_one.getSelectedItem().toString() + "' AND Location = '" + spinner_two.getSelectedItem() + "'";
                            //query_cate = "select * VWMProfitLoss where Category " + spinner_one.getSelectedItem().toString() + " = '" + parent.getSelectedItem().toString() + "'";

                            query_cate = query_cate + "And Sales_date >='" + date + "' AND Sales_date <='" + toda + "'";

                        }

                        backgroudTask = new BackgroudTask(consultant.eyecon.activities.ReportSalesSummeryActivity.this, 3);
                        backgroudTask.execute(query_cate);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
//
//select 'All' as BuName from VwMSalesSummary union
        String quer_loc = "SELECT top 4 c.name AS column_name from sys.views  AS t INNER JOIN sys.columns c ON t.OBJECT_ID = c.OBJECT_ID where t.name='VwMsalessummary'";
        backgroudTask = new BackgroudTask(consultant.eyecon.activities.ReportSalesSummeryActivity.this, 4);
        backgroudTask.execute(quer_loc);

        String query_loc = " select 'All' as BuName from VwMSalesSummary union SELECT  dbo.Tbl_UtilityDetail.UtilityDetValue AS BuName FROM         dbo.Tbl_Utility INNER JOIN dbo.Tbl_UtilityDetail ON dbo.Tbl_Utility.UtilityID = dbo.Tbl_UtilityDetail.UtilityID where dbo.Tbl_UtilityDetail.UtilityID=1";
        backgroudTask = new BackgroudTask(consultant.eyecon.activities.ReportSalesSummeryActivity.this, 1);
        backgroudTask.execute(query_loc);

        String query_cate = "SELECT     TOP (1000)  Item_Name AS catName FROM         dbo.TBL_Category_Item_File WHERE     (Item_BarCode = '' or Item_BarCode IS NULL)";
        backgroudTask = new BackgroudTask(consultant.eyecon.activities.ReportSalesSummeryActivity.this, 2);
        backgroudTask.execute(query_cate);

        spinner_two = (Spinner) findViewById(R.id.cat_spinner);
        spinner_one.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String date = t1.getText().toString();
                String toda = toDate.getText().toString();

                String query_cate = "";

                if (date.isEmpty() && toda.isEmpty()) {
                    if((spinner_one.getSelectedItem().toString().trim().equalsIgnoreCase("All"))){
                        query_cate = "select * from VwMSalesSummary ";
                    }


                    else if((spinner_one.getSelectedItem().toString().trim().equalsIgnoreCase("All"))==(spinner_two.getSelectedItem().toString().trim().equalsIgnoreCase("All"))){
                        query_cate = "select * from VwMSalesSummary";

                    }

                    else if (spinner_two.getSelectedItem().toString().trim().equalsIgnoreCase("All")) {
                        query_cate = "select * from VwMSalesSummary where Location = '"+parent.getSelectedItem().toString()+"'";
                        // String query_cat = "select * from VWMProfitLoss where
                        // query_cate=query_cate+"Sales_date <'" +toda+ "'";;


                    } else {

                        query_cate = "select * from VwMSalesSummary where Location = '" + spinner_one.getSelectedItem().toString() + "' AND  Category= '" + spinner_two.getSelectedItem() + "'";
                    }
                }
                else {
                    if((spinner_one.getSelectedItem().toString().trim().equalsIgnoreCase("All"))){
                        query_cate = "select * from VwMSalesSummary where Sales_date >='" + date + "' AND Sales_date <='" + toda + "' AND Category = '" + spinner_two.getSelectedItem().toString() + "' AND Location = '" + spinner_one.getSelectedItem() + "'";

                    }


                    else if((spinner_one.getSelectedItem().toString().trim().equalsIgnoreCase("All"))==(spinner_two.getSelectedItem().toString().trim().equalsIgnoreCase("All"))){
                        query_cate = "select * from VwMSalesSummary where Category = '" + spinner_two.getSelectedItem().toString() + "' AND Location = '" + spinner_one.getSelectedItem() + "'";

                    }
                    else if (spinner_two.getSelectedItem().toString().trim().equalsIgnoreCase("All")) {
                        query_cate = "select * from VwMSalesSummary where Sales_date >='" + date + "' AND Sales_date <='" + toda + "' AND Location ='" +spinner_one.getSelectedItem().toString()+ "'";
                        // String query_cat = "select * from VWMProfitLoss where
                        // query_cate=query_cate+"Sales_date <'" +toda+ "'";;


                    } else {
                        query_cate = "select * from VwMSalesSummary where Category = '" + parent.getSelectedItem().toString() + "' AND Location = '" + spinner_one.getSelectedItem() + "'";
                        //query_cate = "select * VWMProfitLoss where Category " + spinner_one.getSelectedItem().toString() + " = '" + parent.getSelectedItem().toString() + "'";

                        query_cate = query_cate + "And Sales_date >='" + date + "' AND Sales_date <='" + toda + "'";

                    }
                }

//query_cate=query_cate+"And Sales_date='2020-06-03'";
                backgroudTask = new BackgroudTask(consultant.eyecon.activities.ReportSalesSummeryActivity.this, 3);
                backgroudTask.execute(query_cate);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_two.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String date = t1.getText().toString();
                String toda = toDate.getText().toString();
                String query_cate = "";
                if (date.isEmpty() && toda.isEmpty()) {
                    if((spinner_one.getSelectedItem().toString().trim().equalsIgnoreCase("All"))){
                        query_cate = "select * from VwMSalesSummary ";

                    }

                    else if((spinner_two.getSelectedItem().toString().trim().equalsIgnoreCase("All"))==(spinner_one.getSelectedItem().toString().trim().equalsIgnoreCase("All"))){
                        query_cate = "select * from VwMSalesSummary where Category = '" + spinner_two.getSelectedItem().toString() + "' AND Location = '" + spinner_one.getSelectedItem() + "'";

                    }
                    else if (spinner_one.getSelectedItem().toString().trim().equalsIgnoreCase("All")) {
                        query_cate = "select * from VwMSalesSummary where Category = '"+spinner_two.getSelectedItem().toString()+"'";
                        // String query_cat = "select * from VWMProfitLoss where
                        // query_cate=query_cate+"Sales_date <'" +toda+ "'";;


                    }
                    else if (spinner_two.getSelectedItem().toString().trim().equalsIgnoreCase("All")) {
                        query_cate = "select * from VwMSalesSummary where Category = '"+parent.getSelectedItem().toString()+"'";
                        // String query_cat = "select * from VWMProfitLoss where
                        // query_cate=query_cate+"Sales_date <'" +toda+ "'";;


                    }
                    else if (spinner_two.getSelectedItem().toString().trim().equalsIgnoreCase("All")) {
                        query_cate = "select * from VwMSalesSummary where Location = '"+spinner_one.getSelectedItem().toString()+"'";

                        // String query_cat = "select * from VWMProfitLoss where
                        // query_cate=query_cate+"Sales_date <'" +toda+ "'";;


                    } else {
                        query_cate = "select * from VwMSalesSummary where Category = '" + parent.getSelectedItem().toString() + "' AND Location = '" + spinner_one.getSelectedItem() + "'";
                    }
                }
                else {
                    if((spinner_one.getSelectedItem().toString().trim().equalsIgnoreCase("All"))){
                        query_cate = "select * from VwMSalesSummary where Sales_date >='" + date + "' AND Sales_date <='" + toda + "'";

                    }

                    else if((spinner_one.getSelectedItem().toString().trim().equalsIgnoreCase("All"))==(spinner_two.getSelectedItem().toString().trim().equalsIgnoreCase("All"))){
                        query_cate = "select * from VwMSalesSummary where  Category = '" + spinner_two.getSelectedItem().toString() + "' AND Location = '" + spinner_one.getSelectedItem() + "'";
                        query_cate = query_cate + "And Sales_date >='" + date + "' AND Sales_date <='" + toda + "'";


                    }

                    else if (spinner_two.getSelectedItem().toString().trim().equalsIgnoreCase("All")) {
                        query_cate = "select * from VwMSalesSummary where Sales_date >='" + date + "' AND Sales_date <='" + toda + "' AND Location ='" +spinner_one.getSelectedItem().toString()+ "'";
                        // String query_cat = "select * from VWMProfitLoss where
                        // query_cate=query_cate+"Sales_date <'" +toda+ "'";;


                    } else {
                        query_cate = "select * from VwMSalesSummary where Category = '" + spinner_two.getSelectedItem().toString() + "' AND Location = '" + spinner_one.getSelectedItem() + "'";
                        //query_cate = "select * VWMProfitLoss where Category " + spinner_one.getSelectedItem().toString() + " = '" + parent.getSelectedItem().toString() + "'";

                        query_cate = query_cate + "And Sales_date >='" + date + "' AND Sales_date <='" + toda + "'";

                    }
                }

//query_cate=query_cate+"And Sales_date='2020-06-03'";
                backgroudTask = new BackgroudTask(consultant.eyecon.activities.ReportSalesSummeryActivity.this, 3);
                backgroudTask.execute(query_cate);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
            case R.id.search:

//                String date = t1.getText().toString();
//                String toda = toDate.getText().toString();
//                String query_cate = "";
//                if (spinner_two.getSelectedItem().toString().trim().equalsIgnoreCase("All")) {
//                    query_cate = "select * from VWMProfitLoss where Sales_date >='" + date + "' AND Sales_date <='" + toda + "' AND Location ='" +spinner_one.getSelectedItem().toString()+ "'";
//                    // String query_cat = "select * from VWMProfitLoss where
//                    // query_cate=query_cate+"Sales_date <'" +toda+ "'";;
//
//
//                } else {
//                    query_cate = "select * from VWMProfitLoss where Category = '" + spinner_two.getSelectedItem().toString() + "' AND Location = '" + spinner_one.getSelectedItem() + "'";
//                    //query_cate = "select * VWMProfitLoss where Category " + spinner_one.getSelectedItem().toString() + " = '" + parent.getSelectedItem().toString() + "'";
//
//                    query_cate = query_cate + "And Sales_date >='" + date + "' AND Sales_date <='" + toda + "'";
//
//                }
//
//                backgroudTask = new BackgroudTask(consultant.eyecon.activities.ReportSalesSummeryActivity.this, 3);
//                backgroudTask.execute(query_cate);
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
            try {
                if (resultSet != null) {
                    locationsName = new ArrayList<String>();

                    while (resultSet.next()) {
                        String loc = resultSet.getString(1);
                        locationsName.add("" + loc);
                    }
//                    first.setText("" + locationsNames.get(0));
//                    second.setText("" + locationsNames.get(1));
//                    third.setText("" + locationsNames.get(2));
//                    fourth.setText("" + locationsNames.get(3));

                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spiner_row_item, locationsName);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_one.setAdapter(dataAdapter);
                }
            } catch (Exception e) {
                Log.d("MyException", "loginActivityCallback");
            }

        } else if (requestCode == 2) {
            try {
                if (resultSet != null) {
                    categoriesName = new ArrayList<String>();
                    categoriesName.add("All");
                    while (resultSet.next()) {
                        String cate = resultSet.getString(1);
                        categoriesName.add("" + cate);
                    }
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spiner_row_item, categoriesName);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_two.setAdapter(dataAdapter);
                }
            } catch (Exception e) {
                Log.d("MyException", "loginActivityCallback");
            }
        } else if (requestCode == 3) {
            try {
                if (resultSet != null) {

                    data = new ArrayList<>();
                    while (resultSet.next()) {
                        String one = "" + resultSet.getString(1);
                        String two = "" + resultSet.getString(2);
                        String three = "" + resultSet.getString(3);
                        String four = "" + resultSet.getString(4);
                        SalessummaryModel itemModel2 = new SalessummaryModel();
                        itemModel2.setItemName(one);
                        itemModel2.setQuantity(two);
                        itemModel2.setSalePrice(three);
                        itemModel2.setRemarks(four);
                        data.add(itemModel2);
                    }
                    recyclerView.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(consultant.eyecon.activities.ReportSalesSummeryActivity.this);
                    recyclerView.setLayoutManager(layoutManager);
                    adapter = new ReportExpendibleListAdapter(consultant.eyecon.activities.ReportSalesSummeryActivity.this, data);
                    recyclerView.setAdapter(adapter);
                    isFirst = true;
                    Collections.sort(data,
                            (o1, o2) -> o1.getItemName().compareTo(o2.getItemName()));
                    first.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.arrow_up_float, 0);
                    adapter.notifyDataSetChanged();
                }
            } catch (Exception e) {
                Log.d("MyException", "loginActivityCallback");
            }
        }
        if (requestCode == 4) {
            try {
                if (resultSet != null) {
                    locationsNames = new ArrayList<String>();


                    while (resultSet.next()) {
                        String loc = resultSet.getString(1);
                        locationsNames.add("" + loc);
                    }
                    first.setText("" + locationsNames.get(0));
                    second.setText("" + locationsNames.get(1));
                    third.setText("" + locationsNames.get(2));
                    fourth.setText("" + locationsNames.get(3));

                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spiner_row_item, locationsNames);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_one.setAdapter(dataAdapter);
                }
            } catch (Exception e) {
                Log.d("MyException", "loginActivityCallback");
            }

        }
    }

    boolean isFirst = false;
    boolean isSecond = false;
    boolean isThird = false;
    boolean isFourth = false;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.first:
                if (isFirst) {
                    isFirst = false;
                    Collections.sort(data,
                            (o1, o2) -> o2.getItemName().compareTo(o1.getItemName()));
                    first.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.arrow_down_float, 0);

                } else {
                    isFirst = true;
                    Collections.sort(data,
                            (o1, o2) -> o1.getItemName().compareTo(o2.getItemName()));
                    first.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.arrow_up_float, 0);


                }
                second.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                third.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                fourth.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                adapter.notifyDataSetChanged();
                break;
            case R.id.second:
                if (isSecond) {
                    isSecond = false;
                    Collections.sort(data,
                            (o1, o2) -> o2.getQuantity().compareTo(o1.getQuantity()));
                    second.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.arrow_down_float, 0);

                } else {
                    isSecond = true;
                    Collections.sort(data,
                            (o1, o2) -> o1.getQuantity().compareTo(o2.getQuantity()));
                    second.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.arrow_up_float, 0);


                }
                first.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                third.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                fourth.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                adapter.notifyDataSetChanged();
                break;
            case R.id.third:
                if (isThird) {
                    isThird = false;
                    Collections.sort(data,
                            (o1, o2) -> o2.getSalePrice().compareTo(o1.getSalePrice()));
                    third.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.arrow_down_float, 0);

                } else {
                    isThird = true;
                    Collections.sort(data,
                            (o1, o2) -> o1.getSalePrice().compareTo(o2.getSalePrice()));
                    third.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.arrow_up_float, 0);


                }
                first.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                second.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                fourth.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                adapter.notifyDataSetChanged();
                break;
            case R.id.fourth:
                if (isFourth) {
                    isFourth = false;
                    Collections.sort(data,
                            (o1, o2) -> o2.getRemarks().compareTo(o1.getRemarks()));
                    fourth.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.arrow_down_float, 0);

                } else {
                    isFourth = true;
                    Collections.sort(data,
                            (o1, o2) -> o1.getRemarks().compareTo(o2.getRemarks()));
                    fourth.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.arrow_up_float, 0);


                }
                first.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                second.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                third.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                adapter.notifyDataSetChanged();
                break;
        }
    }
}