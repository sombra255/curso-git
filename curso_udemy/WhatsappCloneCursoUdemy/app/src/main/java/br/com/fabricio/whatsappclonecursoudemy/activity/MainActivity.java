package br.com.fabricio.whatsappclonecursoudemy.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import br.com.fabricio.whatsappclonecursoudemy.R;
import br.com.fabricio.whatsappclonecursoudemy.fragment.ContatosFragment;
import br.com.fabricio.whatsappclonecursoudemy.fragment.ConversasFragment;
import br.com.fabricio.whatsappclonecursoudemy.helper.FirebaseHelper;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth = FirebaseHelper.autenticacaoFirebase();
    private MaterialSearchView materialSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbarPrincipal);
        toolbar.setTitle("WhatsApp");
        setSupportActionBar(toolbar);

        final FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("Conversas", ConversasFragment.class)
                .add("Contatos", ContatosFragment.class)
                .create());

        final ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = findViewById(R.id.smartTabLayout);
        viewPagerTab.setViewPager(viewPager);

        //configuracao search view
        materialSearchView = findViewById(R.id.materialSearchView);

        materialSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                ConversasFragment fragment = (ConversasFragment) adapter.getPage(0);
                fragment.recarregarConversas();
            }
        });

        materialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                switch (viewPager.getCurrentItem()){
                    case 0:
                        ConversasFragment fragmentConversas = (ConversasFragment) adapter.getPage(0);
                        if(newText != null && !newText.isEmpty()){
                            fragmentConversas.pesquisarConversas(newText);
                        }else {
                            fragmentConversas.recarregarConversas();
                        }
                        break;
                    case 1:
                        ContatosFragment fragmentContatos = (ContatosFragment) adapter.getPage(1);
                        if(newText != null && !newText.isEmpty()){
                            fragmentContatos.pesquisarContatos(newText);
                        }else {
                            fragmentContatos.recarregarUsuarios();
                        }
                        break;

                }
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        //search view
        MenuItem item = menu.findItem(R.id.menuPesquisa);
        materialSearchView.setMenuItem(item);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuSair:
                deslogarUsuario();
                finish();
                break;
            case R.id.menuConfiguracoes:
                abrirTelaConfiguracoes();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void abrirTelaConfiguracoes() {
        startActivity(new Intent(MainActivity.this, ConfiguracoesActivity.class));
    }

    private void deslogarUsuario() {
        try{
            firebaseAuth.signOut();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
