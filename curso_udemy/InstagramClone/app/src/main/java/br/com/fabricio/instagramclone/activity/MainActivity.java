package br.com.fabricio.instagramclone.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import br.com.fabricio.instagramclone.R;
import br.com.fabricio.instagramclone.fragment.FeedFragment;
import br.com.fabricio.instagramclone.fragment.PerfilFragment;
import br.com.fabricio.instagramclone.fragment.PesquisaFragment;
import br.com.fabricio.instagramclone.fragment.PostagemFragment;
import br.com.fabricio.instagramclone.helper.FirebaseHelper;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbarPrincipal);
        toolbar.setTitle("Instagram");
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseHelper.getFirebaseAuth();

        //configurar bottom navigation view
        configuraBottomNavigation();

        FragmentManager fragment = getSupportFragmentManager();
        FragmentTransaction transaction = fragment.beginTransaction();
        transaction.replace(R.id.viewPager, new FeedFragment()).commit();
    }

    private void configuraBottomNavigation() {
        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.bottomNavigation);

        //configuracoes iniciais
        bottomNavigationViewEx.enableAnimation(true);
        bottomNavigationViewEx.enableItemShiftingMode(true);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(true);

        //habilitar navegacao
        //metodo para tratar eventos de click no bottom navigation
        habilitarNavegacao(bottomNavigationViewEx);

        //configura menu selecionado inicialmente
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem item = menu.getItem(0);
        item.setChecked(true);
    }

    private void habilitarNavegacao(BottomNavigationViewEx bottomViewEx) {
        bottomViewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                FragmentManager fragment = getSupportFragmentManager();
                FragmentTransaction transaction = fragment.beginTransaction();

                switch (item.getItemId()){
                    case R.id.ic_home:
                        transaction.replace(R.id.viewPager, new FeedFragment()).commit();
                        return true;
                    case R.id.ic_pesquisa:
                        transaction.replace(R.id.viewPager, new PesquisaFragment()).commit();
                        return true;
                    case R.id.ic_postagem:
                        transaction.replace(R.id.viewPager, new PostagemFragment()).commit();
                        return true;
                    case R.id.ic_perfil:
                        transaction.replace(R.id.viewPager, new PerfilFragment()).commit();
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_sair:
                deslogarUsuario();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
                break;
        }
        return true;
    }

    private void deslogarUsuario() {
        try{
            firebaseAuth.signOut();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
