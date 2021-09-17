package consultant.eyecon.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import consultant.eyecon.R;
import consultant.eyecon.interfaces.IDatabaseCallback;
import consultant.eyecon.models.PartyModel;
import consultant.eyecon.services.BackgroudTask;
import consultant.eyecon.utils.Utilities;

public class CashActivity extends AppCompatActivity implements IDatabaseCallback {


    BackgroudTask backgroudTask;
    RadioGroup radioGroup;
    AutoCompleteTextView autoCompleteTextView;
    EditText moneyValue;
    EditText note;
    RadioGroup radioGroupCashType;
    TextView proceed;
    TextView type;
    int partyTypeId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        radioGroup = findViewById(R.id.rb);
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        moneyValue = findViewById(R.id.amount);
        note = findViewById(R.id.note);
        radioGroupCashType = findViewById(R.id.rb_two);
        proceed = findViewById(R.id.proceed);
        type = findViewById(R.id.type);
        String query_loc = "SELECT    PartyName as PartyType,PartyId as PartyTypeId FROM         dbo.Tbl_Party where PartyTypeId=4";//  PartytypeId=4 is customer and 1 is supplier
//        String query_loc = "SELECT    A.PartyName as PartyType,A.PartyId as PartyTypeId,B.PartyCode as AccountCode FROM dbo.Tbl_Party A INNER JOIN TBL_Party_CustDet B on A.PartyId = B.PartyID where PartyTypeId=4";//  PartytypeId=4 is customer and 1 is supplier
        backgroudTask = new BackgroudTask(CashActivity.this, 3);
        backgroudTask.execute(query_loc);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                autoCompleteTextView.setText("");
                RadioButton checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                boolean isChecked = checkedRadioButton.isChecked();
                if (checkedRadioButton.getTag().equals("4")) {
                    type.setText("Customer");
                } else {
                    type.setText("Supplier");
                }
                if (isChecked) {
                    String query_loc = "SELECT    PartyName as PartyType,PartyId as PartyTypeId FROM         dbo.Tbl_Party where PartyTypeId=" + checkedRadioButton.getTag();//  PartytypeId=4 is customer and 1 is supplier
                    backgroudTask = new BackgroudTask(CashActivity.this, 3);
                    backgroudTask.execute(query_loc);
                }
            }
        });
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (autoCompleteTextView.getText().toString().isEmpty()) {
                    autoCompleteTextView.setError("Field is mandatory");
                    return;
                }
                if (moneyValue.getText().toString().isEmpty()) {
                    moneyValue.setError("Field is mandatory");
                    return;
                }
                if (note.getText().toString().isEmpty()) {
                    note.setError("Field is mandatory");
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(CashActivity.this);
                builder.setMessage("Are you sure you want to proceed?").setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RadioButton checkedRadioButton = (RadioButton) radioGroupCashType.findViewById(radioGroupCashType.getCheckedRadioButtonId());
                        String q1 = "INSERT INTO Tbl_PortalCashRecord (TrDate,PartyId,Amount,PaymentType) Values('" + Utilities.currentDate() + "'," + partyTypeId + "," + moneyValue.getText().toString() + ",'" + checkedRadioButton.getTag() + "')";
                        backgroudTask = new BackgroudTask(CashActivity.this, 1);
                        backgroudTask.execute(q1);
                        dialog.dismiss();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                partyTypeId = locationsName.get(position).getPartyTypeId();
            }
        });

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

    List<PartyModel> locationsName;
    List<String> locationsName2;

    @Override
    public void onRequestSuccess(ResultSet resultSet, int requestCode) {
        if (requestCode == 3) {
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

        } else {
            Toast.makeText(this,"Thank you for using the App :)",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, IntroActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            this.finish(); // if the activity running has it's own context

        }
    }
}
