package consultant.eyecon.activities;

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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import consultant.eyecon.R;
import consultant.eyecon.adapters.ReportAdapter;
import consultant.eyecon.interfaces.IDatabaseCallback;
import consultant.eyecon.models.ItemModel2;
import consultant.eyecon.services.BackgroudTask;

public class ReportCurrentStockActivity extends AppCompatActivity implements IDatabaseCallback, View.OnClickListener {
    Spinner spinner_one;
    Spinner spinner_two;
    List<String> locationsName;
    List<String> locationsNames;

    List<String> categoriesName;
    ArrayList<ItemModel2> data = new ArrayList<>();
    BackgroudTask backgroudTask;
    RecyclerView recyclerView;
    ReportAdapter adapter;
    int cat = 1;

    TextView first;
    TextView second;
    TextView third;
    TextView fourth;

    String ra,rs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_stock);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        spinner_one = (Spinner) findViewById(R.id.location_spinner);

        first =(TextView) findViewById(R.id.first);
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
//
//

        String quer_loc = "SELECT top 4 c.name AS column_name from sys.views  AS t INNER JOIN sys.columns c ON t.OBJECT_ID = c.OBJECT_ID where t.name='VwMCurrentStock'";
        backgroudTask = new BackgroudTask(consultant.eyecon.activities.ReportCurrentStockActivity.this, 4);
        backgroudTask.execute(quer_loc);

        String query_loc = "SELECT c.name AS column_name from sys.views  AS t INNER JOIN sys.columns c ON t.OBJECT_ID = c.OBJECT_ID where t.name='VwMCurrentStock' AND (SUBSTRING(c.name, 1, 1) = '_')";
        backgroudTask = new BackgroudTask(ReportCurrentStockActivity.this, 1);
        backgroudTask.execute(query_loc);


        spinner_two = (Spinner) findViewById(R.id.cat_spinner);
        spinner_one.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String query_cate = "select distinct " + parent.getSelectedItem().toString() + " from VwMCurrentStock";
                backgroudTask = new BackgroudTask(ReportCurrentStockActivity.this, 2);
                backgroudTask.execute(query_cate);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_two.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String query_cate = "";
                if (parent.getSelectedItem().toString().trim().equalsIgnoreCase("All")) {
                    query_cate = "select * from VwMCurrentStock";// where " + spinner_one.getSelectedItem().toString() + " = '" + parent.getSelectedItem().toString() + "'";
                } else {
                    query_cate = "select * from VwMCurrentStock where " + spinner_one.getSelectedItem().toString() + " = '" + parent.getSelectedItem().toString() + "'";
                }
                backgroudTask = new BackgroudTask(ReportCurrentStockActivity.this, 3);
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
                        locationsName.add(loc.toUpperCase());
                    }
//                    first.setText("" + locationsName.get(0));
//                    second.setText("" + locationsName.get(1));
//                    third.setText("" + locationsName.get(2));
//                    fourth.setText("" + locationsName.get(3));

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
                        ItemModel2 itemModel2 = new ItemModel2();
                        itemModel2.setItemName(one);
                        itemModel2.setQuantity(two);
                        itemModel2.setSalePrice(three);
                        itemModel2.setRemarks(four);
                        data.add(itemModel2);
//                        first.setText("" + itemModel2.getItemName());
//                        second.setText("" + locationsName.get(1));
//                        third.setText("" + locationsName.get(2));
//                        fourth.setText("" + locationsName.get(3));
                    }


                    recyclerView.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ReportCurrentStockActivity.this);
                    recyclerView.setLayoutManager(layoutManager);
                    adapter = new ReportAdapter(ReportCurrentStockActivity.this, data);
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