package br.com.fabricio.olxclone.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

import br.com.fabricio.olxclone.R;
import br.com.fabricio.olxclone.helper.FirebaseHelper;

public class AnunciosActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncios);

        firebaseAuth = FirebaseHelper.getFirebaseAuth();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //chamado antes do menu ser exibido
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if(firebaseAuth.getCurrentUser() == null){
            menu.setGroupVisible(R.id.group_deslogado, true);
        }else {
            menu.setGroupVisible(R.id.group_logado, true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_acessar:
                startActivity(new Intent(getApplicationContext(), CadastroActivity.class));
                break;

            case R.id.menu_sair:
                firebaseAuth.signOut();
                invalidateOptionsMenu(); //chama o onPrepareOptionsMenu e recarrega os menus corretamente
                break;

            case R.id.menu_meus_anuncios:
                startActivity(new Intent(getApplicationContext(), MeusAnunciosActivity.class));
                break;
        }
        return true;
    }
}
