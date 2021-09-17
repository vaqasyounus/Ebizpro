package consultant.eyecon.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import consultant.eyecon.R;
import consultant.eyecon.activities.OrderBooking;
import consultant.eyecon.models.PartyLedgerModel;

/**
 * Created by Muhammad on 30-Mar-2017.
 */

public class ReportPartyLedgerAdapter extends RecyclerView.Adapter<ReportPartyLedgerAdapter.ViewHolder> implements Filterable {
    private ArrayList<PartyLedgerModel> list;
    private Context context;
    private List<PartyLedgerModel> exampleListFull;


    public ReportPartyLedgerAdapter(Context context, ArrayList<PartyLedgerModel> list) {
        this.list = list;
        this.context = context;
        exampleListFull = new ArrayList<>(list);

    }

    @Override
    public ReportPartyLedgerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//        if (context instanceof ConfirmActivity) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_report_party_ledger_row_item_new_new, viewGroup, false);
        return new ViewHolder(view);
        /*}
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_report_party_ledger_row_item_new, viewGroup, false);
        return new ViewHolder(view);*/
    }

    @Override
    public void onBindViewHolder(final ReportPartyLedgerAdapter.ViewHolder viewHolder, final int i) {
        /*if (i % 2 == 0) {
            viewHolder.root.setBackgroundColor(Color.parseColor("#B3E5FC"));
        } else {
            viewHolder.root.setBackgroundColor(Color.parseColor("#ffffff"));
        }*/
       /* if (context instanceof ConfirmActivity) {
            viewHolder.add.setText("Remove");
            viewHolder.add.setTextColor(Color.RED);
        }else{
            viewHolder.add.setText("Add");
            viewHolder.add.setTextColor(Color.WHITE);
        }*/
        if (this.list.get(i).isAdd()) {
            viewHolder.add.setText("Remove");
            viewHolder.add.setTextColor(Color.RED);
        } else {
            viewHolder.add.setText("Add");
            viewHolder.add.setTextColor(Color.WHITE);
        }

        viewHolder.PartyName.setText(this.list.get(i).getPartyName());
        viewHolder.Balance.setText(this.list.get(i).getBalance() + "  PKR");

//        if (context instanceof ConfirmActivity) {
        viewHolder.value.setText(this.list.get(i).getQuantity());
        viewHolder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int q = 0;
                q = Integer.parseInt(list.get(i).getQuantity().trim());
                q++;
                list.get(i).setQuantity(q + "");
                notifyDataSetChanged();
                OrderBooking.updateTotal();
            }
        });
        viewHolder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int q = 0;
                q = Integer.parseInt(list.get(i).getQuantity().trim());
                if (q >= 1)
                    q--;
                list.get(i).setQuantity(q + "");
                notifyDataSetChanged();
                OrderBooking.updateTotal();

            }
        });
//        }
        viewHolder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(i).isAdd()) {
                    list.get(i).setAdd(false);
                    list.get(i).setQuantity("0");

                } else {
                    list.get(i).setAdd(true);
                    list.get(i).setQuantity("1");

                }
                notifyDataSetChanged();
            }
        });

        viewHolder.value.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                OrderBooking.updateTotal();
            }
        });
/*
        viewHolder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (context instanceof ConfirmActivity) {
                        return;
                    }
                    final Dialog dialog = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.getWindow().setBackgroundDrawable(context.getDrawable(R.drawable.background_round_white_new));
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.alert_custom);
                    TextView yes = dialog.findViewById(R.id.yes);
                    TextView no = dialog.findViewById(R.id.no);
                    TextView des = dialog.findViewById(R.id.description);
                    EditText quantity = dialog.findViewById(R.id.quantity);
                    quantity.setText(list.get(i).getQuantity());
                    des.setText("Would you like to add \"" + list.get(i).getPartyName() + "\" in your cart ? ");
                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (quantity.getText().toString().length() < 1 || Integer.parseInt(quantity.getText().toString()) == 0) {
                                quantity.setError("Please enter quantity");
                                return;
                            } else {
                                list.get(i).setQuantity(quantity.getText().toString());
                                notifyDataSetChanged();

                            }
                            dialog.dismiss();
                        }
                    });

                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
*/
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<PartyLedgerModel> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (PartyLedgerModel item : exampleListFull) {
                    if (item.getPartyName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }


                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((List) results.values);

            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {
        //        LinearLayout root;
        private TextView PartyName;
        private TextView Balance;
        private TextView add;
        private EditText value;
        private ImageView plus;
        private ImageView minus;

        public ViewHolder(View view) {
            super(view);
//            root = (LinearLayout) view.findViewById(R.id.rootlayout);
            PartyName = (TextView) view.findViewById(R.id.product_name);
            Balance = (TextView) view.findViewById(R.id.price);
            add = (TextView) view.findViewById(R.id.add);
            value = (EditText) view.findViewById(R.id.value);
            plus = (ImageView) view.findViewById(R.id.plus);
            minus = (ImageView) view.findViewById(R.id.minus);
        }
    }

}