package br.com.fabricio.cursoudemyorganizze.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import br.com.fabricio.cursoudemyorganizze.R;
import br.com.fabricio.cursoudemyorganizze.enums.TipoMovimentacao;
import br.com.fabricio.cursoudemyorganizze.model.Movimentacao;

/**
 * Created by Fabricio on 06/07/2018.
 */

public class ReceitaDespesaAdapter extends RecyclerView.Adapter<ReceitaDespesaAdapter.MyViewHolder> {
    private List<Movimentacao> lsMovimentacao;
    Context context;

    public ReceitaDespesaAdapter(List<Movimentacao> lsMovimentacao, Context context) {
        this.lsMovimentacao = lsMovimentacao;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Movimentacao movimentacao = lsMovimentacao.get(position);
        holder.txtCategoria.setText(movimentacao.getCategoria());
        holder.txtDescricao.setText(movimentacao.getDescricao());

        DecimalFormat decimalFormat = new DecimalFormat("0.##");
        holder.txtValor.setText(decimalFormat.format(movimentacao.getValor()));
        holder.txtValor.setTextColor(context.getResources().getColor(R.color.colorPrimaryDarkReceita));

        if(movimentacao.getTipo().equals(TipoMovimentacao.DESPESA.getDescricao())){
            holder.txtValor.setTextColor(context.getResources().getColor(R.color.colorPrimaryDarkDespesa));
            holder.txtValor.setText("-"+decimalFormat.format(movimentacao.getValor()));
        }
    }

    @Override
    public int getItemCount() {
        return lsMovimentacao.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtCategoria;
        private TextView txtDescricao;
        private TextView txtValor;

        public MyViewHolder(View itemView) {
            super(itemView);

            txtCategoria = itemView.findViewById(R.id.listItemTxtCategoria);
            txtDescricao = itemView.findViewById(R.id.listItemTxtDescricao);
            txtValor = itemView.findViewById(R.id.listItemTxtValor);
        }
    }
}
