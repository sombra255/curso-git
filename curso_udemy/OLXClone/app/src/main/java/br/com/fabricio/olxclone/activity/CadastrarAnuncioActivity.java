package br.com.fabricio.olxclone.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.santalu.widget.MaskEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.com.fabricio.olxclone.R;
import br.com.fabricio.olxclone.helper.FirebaseHelper;
import br.com.fabricio.olxclone.helper.Permissoes;
import br.com.fabricio.olxclone.model.Anuncio;
import dmax.dialog.SpotsDialog;

public class CadastrarAnuncioActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtTitulo, edtDescricao;
    private CurrencyEditText edtValor;
    private Button btnCadastrar;
    private MaskEditText edtTelefone;
    private ImageView imageCadastro1, imageCadastro2, imageCadastro3;
    private Spinner spinnerEstado, spinnerCategoria;
    private String[] permissoes = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,

    };
    private List<String> lsCaminhoImagens = new ArrayList<>();
    private List<String> lsUrlFotos = new ArrayList<>();
    private Anuncio anuncio;
    private StorageReference storageReference;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_anuncio);


        Permissoes.validarPermissoes(permissoes, this, 1);
        inicializarComponentes();
        carregarDadosSpinner();

        storageReference = FirebaseHelper.getStorageReference();

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String valor = edtValor.getHintString(); //recupera o valor do hint
//                Long valor = edtValor.getRawValue(); //recupera apenas os numeros digitados no campo
//                String valor = edtValor.getText().toString(); //recupera o valor do campo incluindo a mascara R$ que estiver aplicada

                if(validarDadosAnuncio()){

                    dialog = new SpotsDialog.Builder()
                    .setContext(CadastrarAnuncioActivity.this)
                    .setMessage("Salvando Anúncio")
                    .setCancelable(false)
                    .build();

                    dialog.show();

                    //Salvar imagens do anuncio
                    for(int i = 0; i < lsCaminhoImagens.size(); i++){
                        String urlImagens = lsCaminhoImagens.get(i);
                        salvarFotoStorage(urlImagens, lsCaminhoImagens.size(), i);
                    }

                }

            }
        });
    }

    private void salvarFotoStorage(String urlImagens, final int size, int i) {
        StorageReference imagemAnuncio = storageReference.child("imagens")
                .child("anuncios")
                .child(anuncio.getIdAnuncio())
                .child("imagem"+i);

        UploadTask uploadTask = imagemAnuncio.putFile(Uri.parse(urlImagens));
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri firebaseUrl = taskSnapshot.getDownloadUrl();
                String urlConvertida = firebaseUrl.toString();
                lsUrlFotos.add(urlConvertida);

                if(size == lsUrlFotos.size()){
                    anuncio.setLsFotos(lsUrlFotos);
                    anuncio.salvar();

                    dialog.dismiss();
                    finish();
                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                exibirMensagemErro("Falha ao fazer upload da imagem!");
            }
        });
    }

    private Anuncio configurarAnuncio() {
        String estado = spinnerEstado.getSelectedItem().toString();
        String categoria = spinnerCategoria.getSelectedItem().toString();
        String titulo = edtTitulo.getText().toString();
        String valor = edtValor.getText().toString();
        String telefone = edtTelefone.getText().toString();
        String descricao = edtDescricao.getText().toString();

        Anuncio anuncio = new Anuncio();
        anuncio.setEstado(estado);
        anuncio.setCategoria(categoria);
        anuncio.setTitulo(titulo);
        anuncio.setValor(valor);
        anuncio.setTelefone(telefone);
        anuncio.setDescricao(descricao);

        return anuncio;
    }

    private boolean validarDadosAnuncio(){
        anuncio = configurarAnuncio();
        boolean retorno = false;
        String valor = String.valueOf(edtValor.getRawValue());
        String fone = edtTelefone.getRawText() != null ? edtTelefone.getRawText():"";

        if(lsCaminhoImagens.size() != 0){
                if(!anuncio.getEstado().isEmpty()){
                    if(!anuncio.getCategoria().isEmpty()){
                        if(!anuncio.getTelefone().isEmpty()){
                            if(!valor.isEmpty() && !valor.equals("0")){
                                if(!anuncio.getTelefone().isEmpty() && fone.length() >= 10){
                                    if(!anuncio.getDescricao().isEmpty()){
                                        retorno = true;
                                    }else {
                                        exibirMensagemErro("Preencha o campo descricao.");
                                    }
                                }else {
                                    exibirMensagemErro("Preencha o campo telefone, digite ao menos 10 números!");
                                }
                            }else {
                                exibirMensagemErro("Preencha o campo valor.");
                            }
                        }else {
                            exibirMensagemErro("Preencha o campo titulo.");
                        }
                    }else {
                        exibirMensagemErro("Preencha o campo categoria.");
                    }
                }else {
                    exibirMensagemErro("Preencha o campo estado.");
                }
        }else {
            exibirMensagemErro("Selecione ao menos uma foto!");
        }

        return retorno;

    }

    private void exibirMensagemErro(String mensagem){
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
    }

    private void carregarDadosSpinner(){
        String[] estados = getResources().getStringArray(R.array.estados);
        String[] categoria = getResources().getStringArray(R.array.categoria);

        ArrayAdapter<String> adapterEstado = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, estados);
        adapterEstado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEstado.setAdapter(adapterEstado);

        ArrayAdapter<String> adapterCategoria = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoria);
        adapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapterCategoria);
    }

    private void inicializarComponentes() {
        edtTitulo = findViewById(R.id.cadastrar_anuncio_edtTitulo);
        edtValor = findViewById(R.id.cadastrar_anuncio_edtValor);
        edtDescricao = findViewById(R.id.cadastrar_anuncio_edtDescricao);
        btnCadastrar = findViewById(R.id.cadastrar_anuncio_btnCadastrar);
        edtTelefone = findViewById(R.id.cadastrar_anuncio_edtTelefone);
        imageCadastro1 = findViewById(R.id.imageCadastro1);
        imageCadastro2 = findViewById(R.id.imageCadastro2);
        imageCadastro3 = findViewById(R.id.imageCadastro3);
        spinnerEstado = findViewById(R.id.spinnerEstado);
        spinnerCategoria = findViewById(R.id.spinnerCategoria);

        imageCadastro1.setOnClickListener(this);
        imageCadastro2.setOnClickListener(this);
        imageCadastro3.setOnClickListener(this);

        Locale locale = new Locale("pt", "BR");
        edtValor.setLocale(locale);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int permissaoResultado : grantResults) {
            if(permissaoResultado == PackageManager.PERMISSION_DENIED){
                alertaValidacaoPermissao();
            }
        }
    }

    private void alertaValidacaoPermissao(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setTitle("Permissões Negadas");
        builder.setMessage("Para utilizar o app é necessário aceitar as permissões.");
        builder.setCancelable(false);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageCadastro1:
                escolherImagem(1);
                break;

            case R.id.imageCadastro2:
                escolherImagem(2);
                break;

            case R.id.imageCadastro3:
                escolherImagem(3);
                break;
        }
    }

    public void escolherImagem(int requestCode){
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){

            Uri imagemSelecionada = data.getData();
            String caminhoImagem = imagemSelecionada.toString();

            if(requestCode == 1){
                imageCadastro1.setImageURI(imagemSelecionada);
            }else if (requestCode == 2){
                imageCadastro2.setImageURI(imagemSelecionada);
            }else {
                imageCadastro3.setImageURI(imagemSelecionada);
            }

            lsCaminhoImagens.add(caminhoImagem);

        }
    }
}
