package consultant.eyecon.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

import consultant.eyecon.R;
import consultant.eyecon.models.CustomerModel;
import consultant.eyecon.models.ItemModel2;
import consultant.eyecon.models.PartyLedgerModel;

/**
 * Created by Muhammad on 30-Mar-2017.
 */

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> implements Filterable {
    private ArrayList<CustomerModel> list;
    private Context context;
    private List<CustomerModel> exampleListFull;


    public BookingAdapter(Context context, ArrayList<CustomerModel> list) {
        this.list = list;
        this.context = context;
        exampleListFull = new ArrayList<>(list);

    }

    @Override
    public BookingAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_group, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BookingAdapter.ViewHolder viewHolder, final int i) {
        if (i % 2 == 0) {
            viewHolder.root.setBackgroundColor(Color.parseColor("#f0f1f1"));
        } else {
            viewHolder.root.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        viewHolder.sn.setText((i + 1)+".");
        viewHolder.trdate.setText(list.get(i).getTrdate());
        viewHolder.debit.setText(list.get(i).getDebit());
        viewHolder.credit.setText("" + list.get(i).getCredit());
        viewHolder.remarks.setText("" + list.get(i).getRemarks());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<CustomerModel> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (CustomerModel item : exampleListFull) {
                    if (item.getDebit().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                    else if (item.getCredit().toLowerCase().contains(filterPattern)) {
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
        LinearLayout root;
        private TextView sn;
        private TextView trdate;
        private TextView debit;
        private TextView credit;
        private TextView remarks;


        public ViewHolder(View view) {
            super(view);
            root = (LinearLayout) view.findViewById(R.id.rootlayout);
            sn = (TextView) view.findViewById(R.id.sn);
            trdate = (TextView) view.findViewById(R.id.trdate);
            debit = (TextView) view.findViewById(R.id.debit);
            credit = (TextView) view.findViewById(R.id.credit);
            remarks = (TextView) view.findViewById(R.id.remarks);


        }
    }

}