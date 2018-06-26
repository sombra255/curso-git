package br.com.fabricio.sliderprojeto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;

public class MainActivity extends IntroActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        setButtonBackVisible(false);
        setButtonNextVisible(false);

        addSlide(new FragmentSlide.Builder()
                    .background(android.R.color.white)
                    .fragment(R.layout.intro_1)
                    .build()
        );

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_2)
                .build()
        );

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_3)
                .build()
        );

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_4)
                .canGoForward(false)
                .build()
        );



//        setButtonBackVisible(false);
//        setButtonNextVisible(false);
//
//        addSlide(new SimpleSlide.Builder()
//                .title("Titulo")
//                .description("Descrição")
//                .background(android.R.color.white)
//                .image(R.drawable.um)
//                .build()
//        );
//
//        addSlide(new SimpleSlide.Builder()
//                .title("Titulo2")
//                .description("Descrição2")
//                .background(android.R.color.white)
//                .image(R.drawable.dois)
//                .build()
//        );
//
//        addSlide(new SimpleSlide.Builder()
//                .title("Titulo3")
//                .description("Descrição3")
//                .background(android.R.color.white)
//                .image(R.drawable.tres)
//                .build()
//        );
//
//        addSlide(new SimpleSlide.Builder()
//                .title("Titulo4")
//                .description("Descrição4")
//                .background(android.R.color.white)
//                .image(R.drawable.quatro)
//                .canGoForward(false)
//                .build()
//        );
    }
}
