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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import consultant.eyecon.R;
import consultant.eyecon.models.ItemModel;
import consultant.eyecon.models.ItemModel2;

/**
 * Created by Muhammad on 30-Mar-2017.
 */

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> implements Filterable {
    private ArrayList<ItemModel2> list;
    private Context context;
    private List<ItemModel2> exampleListFull;




    public ReportAdapter(Context context, ArrayList<ItemModel2> list) {
        this.list = list;
        this.context = context;
        exampleListFull = new ArrayList<>(list);

    }

    @Override
    public ReportAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_report_row_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ReportAdapter.ViewHolder viewHolder, final int i) {
        if (i % 2 == 0) {
            viewHolder.root.setBackgroundColor(Color.parseColor("#B3E5FC"));
        } else {
            viewHolder.root.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        viewHolder.first.setText(list.get(i).getItemName().trim());
        viewHolder.second.setText("" + list.get(i).getQuantity().trim());
        viewHolder.third.setText("" + list.get(i).getSalePrice().trim());
        viewHolder.fourth.setText("" + list.get(i).getRemarks().trim());

        if (list.get(i).getSalePrice().equals("")) {
            viewHolder.root.setBackgroundColor(Color.parseColor("#FFA000"));
        } else {
            viewHolder.sn.setText("" + (i + 1));
        }

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
            List<ItemModel2> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ItemModel2 item : exampleListFull) {
                    if (item.getRemarks().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                   else if (item.getSalePrice().toLowerCase().contains(filterPattern)) {
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
        private TextView first;
        private TextView second;
        private TextView third;
        private TextView fourth;


        public ViewHolder(View view) {
            super(view);
            root = (LinearLayout) view.findViewById(R.id.rootlayout);
            sn = (TextView) view.findViewById(R.id.sn);
            first = (TextView) view.findViewById(R.id.first);
            second = (TextView) view.findViewById(R.id.second);
            third = (TextView) view.findViewById(R.id.third);
            fourth = (TextView) view.findViewById(R.id.fourth);

//            first.setSelected(true);
//            second.setSelected(true);
//            third.setSelected(true);
//            fourth.setSelected(true);


        }
    }
//    public void updateList(List<String> newf)
//    {
//        list=new ArrayList<>();
//        list.addAll(newf);
//        notifyDataSetChanged();
//    }
}