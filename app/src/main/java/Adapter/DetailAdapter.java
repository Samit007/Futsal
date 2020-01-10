package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mylocation.R;
import com.example.mylocation.UpdateFutsal;

import java.util.List;

import Model.Futsal2;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailsViewHolder> {
    Context mcontext;
    List<Futsal2> futsalList;

    public DetailAdapter(Context mcontext, List<Futsal2> futsalList) {
        this.mcontext = mcontext;
        this.futsalList = futsalList;
    }

    @NonNull
    @Override
    public DetailsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.futsal, viewGroup, false);
        return new DetailsViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull DetailsViewHolder detailsViewHolder, int i) {
        final Futsal2 futsal=futsalList.get(i);
        detailsViewHolder.prov.setText((String.valueOf(futsal.getPROVINCE())));
        detailsViewHolder.nam.setText(futsal.getNAME());
        detailsViewHolder.dist.setText(futsal.getDISTRICT());
        detailsViewHolder.nam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mcontext, UpdateFutsal.class);
                intent.putExtra("name", futsal.getNAME());
                intent.putExtra("long", (String.valueOf(futsal.getLONGITUDE())));
                intent.putExtra("lat", (String.valueOf(futsal.getLATITUDE())));
                intent.putExtra("dist", futsal.getDISTRICT());
                intent.putExtra("prov", (String.valueOf(futsal.getPROVINCE())));
                mcontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return futsalList.size();    }

    public class DetailsViewHolder extends RecyclerView.ViewHolder {
        TextView nam,dist,prov;


        public DetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            nam = itemView.findViewById(R.id.tvName);
            dist = itemView.findViewById(R.id.tvdist);
            prov = itemView.findViewById(R.id.tvprov);
        }

    }
}
