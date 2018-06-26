package br.com.fabricio.appguanabaracasa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import br.com.fabricio.appguanabaracasa.R;
import br.com.fabricio.appguanabaracasa.model.Aluno;

/**
 * Created by Fabricio on 16/10/2016.
 */

public class AdapterAluno extends BaseAdapter implements SectionIndexer {

    private final Context context;
    private final List<Aluno> lsAlunos;
    HashMap<String, Integer> mapIndex;
    String[] sections;

    public AdapterAluno(Context context, List<Aluno> lsAlunos) {
        this.context = context;
        this.lsAlunos = lsAlunos;
        this.mapIndex = new LinkedHashMap<String, Integer>();

        for (int x = 0; x < lsAlunos.size(); x++) {
            String nomeAluno = lsAlunos.get(x).getNome();
            String ch = nomeAluno.substring(0, 1);
            ch = ch.toUpperCase();

            // HashMap will prevent duplicates
            mapIndex.put(ch, x);
        }

        Set<String> sectionLetters = mapIndex.keySet();

        // create a list from the set to sort
        List<String> sectionList = new ArrayList<String>(sectionLetters);

//        Log.d("sectionList", sectionList.toString());
        Collections.sort(sectionList);

        sections = new String[sectionList.size()];

        sectionList.toArray(sections);
    }

    @Override
    public int getCount() {
        return lsAlunos.size();
    }

    @Override
    public Object getItem(int position) {
        return lsAlunos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return lsAlunos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.item_lista_aluno, parent, false);
        }

        TextView coluna1 = (TextView) view.findViewById(R.id.item_coluna1);
        coluna1.setText(lsAlunos.get(position).getNome());

        TextView coluna2 = (TextView) view.findViewById(R.id.item_coluna2);
        coluna2.setText(lsAlunos.get(position).getTelefone());

        return view;
    }

    @Override
    public Object[] getSections() {
        return sections;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return mapIndex.get(sections[sectionIndex]);
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }
}
