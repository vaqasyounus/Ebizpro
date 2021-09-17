package consultant.eyecon.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
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
import consultant.eyecon.adapters.BookingAdapter;
import consultant.eyecon.adapters.ReportAdapter;
import consultant.eyecon.adapters.ReportPartyLedgerAdapter;
import consultant.eyecon.interfaces.IDatabaseCallback;
import consultant.eyecon.models.CustomerModel;
import consultant.eyecon.models.ItemModel2;
import consultant.eyecon.models.PartyLedgerModel;
import consultant.eyecon.models.PartyModel;
import consultant.eyecon.models.PartyModel2;
import consultant.eyecon.services.BackgroudTask;

public class OrderTaking extends AppCompatActivity implements IDatabaseCallback {

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
    List<PartyModel> locationsName;
    List<String> locationsName2;
    List<String> categoriesID;
    List<PartyModel2> categoriesName;
    List<String> categoriesName2;

    BookingAdapter adapter;


    BackgroudTask backgroudTask;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        String query_loc = "SELECT     TrDate, Debit, Credit, Remarks FROM         dbo.VwMLedger";
        backgroudTask = new BackgroudTask(OrderTaking.this, 2);
        backgroudTask.execute(query_loc);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//






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

            case android.R.id.home:
                Intent intent=new Intent(OrderTaking.this,OrderBooking.class);
                startActivity(intent);
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
                    categoriesName2.add("all");
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


//                    String q1 = "SELECT   '1900/1/1' as TrDate, sum( Debit) as Debit, sum(Credit) as Credit,'Opening' as Remarks\n" +
//                            "FROM         [REC_restaurant2].[dbo].[VwMGL]\n" +
//                            "WHERE     (partyid = " + categoriesID.get(category.getSelectedItemPosition()) + ") AND (TrDate <CONVERT(DATETIME, '" + "1900/1/1" + "', 102))\n" +
//                            "Union all\n" +
//                            "SELECT    TrDate, Debit, Credit, Remarks\n" +
//                            "FROM         [REC_restaurant2].[dbo].[VwMGL]\n" +
//                            "WHERE     (partyid = " + categoriesID.get(category.getSelectedItemPosition()) + ")" +
//                            "order by trdate\n";
                    String q1 = "SELECT     TrDate, Debit, Credit, Remarks FROM         dbo.VwMLedger";

                    backgroudTask = new BackgroudTask(OrderTaking.this, 2);
                    backgroudTask.execute(q1);

                }

            } catch (Exception e) {
                Log.d("MyException", "loginActivityCallback");
            }

        } else if (requestCode == 2) {
            final ArrayList<CustomerModel> data = new ArrayList<>();
            try {
                if (resultSet != null) {
                    while (resultSet.next()) {
                        String trDate = resultSet.getString("TrDate");
                        String debit = resultSet.getString("Debit");
                        String credit = resultSet.getString("Credit");
                        String remarks = resultSet.getString("Remarks");
                        CustomerModel partyLedgerModel = new CustomerModel();
                        partyLedgerModel.setTrdate(trDate);
                        partyLedgerModel.setDebit(debit);
                        partyLedgerModel.setCredit(credit);
                        partyLedgerModel.setRemarks(remarks);

                        data.add(partyLedgerModel);

                    }


                    recyclerView.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(OrderTaking.this);
                    recyclerView.setLayoutManager(layoutManager);
                    adapter = new BookingAdapter(OrderTaking.this, data);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

            } catch (Exception e) {
                Log.d("MyException", "loginActivityCallback");
            }
        }
    }
}

