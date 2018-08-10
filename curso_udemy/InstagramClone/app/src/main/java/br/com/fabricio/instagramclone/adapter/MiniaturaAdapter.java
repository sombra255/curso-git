package br.com.fabricio.instagramclone.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zomato.photofilters.utils.ThumbnailItem;

import java.util.List;

import br.com.fabricio.instagramclone.R;

public class MiniaturaAdapter extends RecyclerView.Adapter<MiniaturaAdapter.MyViewHolder> {

    private List<ThumbnailItem> lsMiniaturas;
    private Context context;

    public MiniaturaAdapter(List<ThumbnailItem> lsMiniaturas, Context context) {
        this.lsMiniaturas = lsMiniaturas;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_filtro, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ThumbnailItem item = lsMiniaturas.get(position);
        holder.txtFiltro.setText(item.filterName);
        holder.imagem.setImageBitmap(item.image);
    }

    @Override
    public int getItemCount() {
        return lsMiniaturas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView imagem;
        private TextView txtFiltro;

        public MyViewHolder(View itemView) {
            super(itemView);

            imagem = itemView.findViewById(R.id.list_item_filtro_imagem);
            txtFiltro = itemView.findViewById(R.id.list_item_filtro_nome_filtro);
        }
    }
}
