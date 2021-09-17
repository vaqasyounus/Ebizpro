

        package consultant.eyecon.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import consultant.eyecon.R;

import consultant.eyecon.models.Partybalance;

        /**
 * Created by Muhammad on 30-Mar-2017.
 */

public class Reportbalanceadapter extends RecyclerView.Adapter<consultant.eyecon.adapters.Reportbalanceadapter.ViewHolder> {
    private ArrayList<Partybalance> list;
    private Context context;


    public Reportbalanceadapter(Context context, ArrayList<Partybalance> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_report_party_ledger_row_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final consultant.eyecon.adapters.Reportbalanceadapter.ViewHolder viewHolder, final int i) {
        if (i % 2 == 0) {
            viewHolder.root.setBackgroundColor(Color.parseColor("#f0f1f1"));
        } else {
            viewHolder.root.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        viewHolder.sn.setText((i + 1));
        viewHolder.trdate.setText(list.get(i).getTrdate());
        viewHolder.debit.setText(list.get(i).getDebit());
        viewHolder.credit.setText(list.get(i).getCredit());
        viewHolder.remarks.setText(list.get(i).getRemarks());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

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
            sn = (TextView) view.findViewById(R.id.sn1);
            trdate = (TextView) view.findViewById(R.id.trdate1);
            debit = (TextView) view.findViewById(R.id.debit1);
            credit = (TextView) view.findViewById(R.id.credit1);
            remarks = (TextView) view.findViewById(R.id.remarks1);


        }
    }

}