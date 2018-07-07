package br.com.fabricio.cursoudemyorganizze.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.fabricio.cursoudemyorganizze.R;
import br.com.fabricio.cursoudemyorganizze.adapter.ReceitaDespesaAdapter;
import br.com.fabricio.cursoudemyorganizze.config.ConfiguracaoFirebase;
import br.com.fabricio.cursoudemyorganizze.model.Movimentacao;
import br.com.fabricio.cursoudemyorganizze.model.Usuario;
import br.com.fabricio.cursoudemyorganizze.utils.Base64Custom;

public class PrincipalActivity extends AppCompatActivity{

    private MaterialCalendarView calendarView;
    private TextView txtSaudacao, txtSaldo;
    private FirebaseAuth firebaseAuth = ConfiguracaoFirebase.autenticacaoFirebase();
    private DatabaseReference databaseReference = ConfiguracaoFirebase.getFirebaseDatabase();
    private Double despesaTotal = 0.0;
    private Double receitaTotal = 0.0;
    private Double resumoUsuario = 0.0;
    private DatabaseReference usuarioRef;
    private ValueEventListener valueEventListenerUsuario;
    private ValueEventListener valueEventListenerMovimentacao;
    private RecyclerView recyclerView;
    private List<Movimentacao> lsMovimentacao = new ArrayList<>();
    private ReceitaDespesaAdapter receitaDespesaAdapter;
    private DatabaseReference movimentacaoRef;
    private String mesAnoSelecionado;

    @Override
    protected void onStart() {
        super.onStart();
        recuperarResumo();
        recuperaMovimentacoes();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("");

        calendarView = findViewById(R.id.calendarView);
        txtSaudacao = findViewById(R.id.txtSaudacao);
        txtSaldo = findViewById(R.id.txtSaldo);
        recyclerView = findViewById(R.id.reciclerMovimentos);

        iniciaCalendario();
        swipe();

        //Configurar Adapter
        receitaDespesaAdapter = new ReceitaDespesaAdapter(lsMovimentacao, this);

        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(receitaDespesaAdapter);
    }

    public void swipe(){
        ItemTouchHelper.Callback itemTouch = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int dragsFlags = ItemTouchHelper.ACTION_STATE_IDLE;
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(dragsFlags, swipeFlags);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                Log.i("swipe", "item arrastado");
            }
        };

        new ItemTouchHelper(itemTouch).attachToRecyclerView(recyclerView);
    }

    public void recuperaMovimentacoes(){
        String idUsuario = Base64Custom.encodeToString(firebaseAuth.getCurrentUser().getEmail());;
        usuarioRef = databaseReference.child("usuarios").child(idUsuario);
        movimentacaoRef = databaseReference.child("movimentacao")
        .child(idUsuario)
        .child(mesAnoSelecionado);

        valueEventListenerMovimentacao = movimentacaoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lsMovimentacao.clear();
                for(DataSnapshot dados : dataSnapshot.getChildren()){
                    lsMovimentacao.add(dados.getValue(Movimentacao.class));
                }
                receitaDespesaAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void recuperarResumo(){
        //criar um metodo dentro de configuracaoFirebase
        String idUsuario = Base64Custom.encodeToString(firebaseAuth.getCurrentUser().getEmail());;
        usuarioRef = databaseReference.child("usuarios").child(idUsuario);

        Log.i("Evento", "Evento foi iniciado");
        valueEventListenerUsuario = usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                despesaTotal = usuario.getDespesaTotal();
                receitaTotal = usuario.getReceitaTotal();
                resumoUsuario = receitaTotal - despesaTotal;

                DecimalFormat decimalFormat = new DecimalFormat("0.##");

                txtSaudacao.setText("Olá, "+usuario.getNome());
                txtSaldo.setText("R$ "+decimalFormat.format(resumoUsuario));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_sair:
                //evento sair
                firebaseAuth.signOut();
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
        }
        return true;
    }

    private void iniciaCalendario() {
        String [] mesesPortugues = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro" };
        calendarView.setTitleMonths(mesesPortugues);

        CalendarDay dataAtual = calendarView.getCurrentDate();
        String mesSelecionado = String.format("%02d", (dataAtual.getMonth()+1));
        mesAnoSelecionado = String.valueOf( mesSelecionado+""+ dataAtual.getYear());

        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView materialCalendarView, CalendarDay calendarDay) {
                String mesSelecionado = String.format("%02d", (calendarDay.getMonth()+1));
                mesAnoSelecionado = String.valueOf(mesSelecionado +""+ calendarDay.getYear());

                movimentacaoRef.removeEventListener(valueEventListenerMovimentacao);
                recuperaMovimentacoes();
            }
        });
    }

    public void adicionarReceita(View view) {
        startActivity(new Intent(this, ReceitaActivity.class));
    }

    public void adicionarDespesa(View view) {
        startActivity(new Intent(this, DespesaActivity.class));
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Evento", "Evento foi removido");
        usuarioRef.removeEventListener(valueEventListenerUsuario);
        movimentacaoRef.removeEventListener(valueEventListenerMovimentacao);
    }
}

