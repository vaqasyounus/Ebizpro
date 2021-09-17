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

import consultant.eyecon.R;
import consultant.eyecon.models.CustomerModel;
import consultant.eyecon.models.Globalmodel;
import consultant.eyecon.models.ItemModel2;
import consultant.eyecon.models.PartyLedgerModel;

/**
 * Created by Muhammad on 30-Mar-2017.
 */

public class Globaladapter extends RecyclerView.Adapter<Globaladapter.ViewHolder> implements Filterable {
    private String list;
    private Context context;
    private List<Globalmodel> exampleListFull;


    public Globaladapter(Context context) {
        this.context = context;

    }

    @Override
    public Globaladapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return null;
    }



    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public void onBindViewHolder(final Globaladapter.ViewHolder viewHolder, final int i) {

        Globalmodel globalmodel=new Globalmodel();
        viewHolder.Global.setText(globalmodel.getGlobalname());




    }

    @Override
    public Filter getFilter() {
        return null;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout root;
        private TextView Global;



        public ViewHolder(View view) {
            super(view);
            root = (LinearLayout) view.findViewById(R.id.layout);
            Global = (TextView) view.findViewById(R.id.globalname);



        }
    }

}