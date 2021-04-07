package com.example.imc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button envoyer = null;
    Button reset = null;
    EditText taille = null;
    EditText poids = null;
    CheckBox commentaire = null;
    RadioGroup group = null;
    RadioButton radio = null;
    TextView result = null;

    int texteInit = R.string.clique;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // On récupère toutes les vues dont on a besoin
        envoyer = (Button)findViewById(R.id.calcul);
        reset = (Button)findViewById(R.id.reset);
        taille = (EditText)findViewById(R.id.taille);
        poids = (EditText)findViewById(R.id.poids);
        commentaire = (CheckBox)findViewById(R.id.commentaire);
        group = (RadioGroup)findViewById(R.id.group);
        radio = (RadioButton)findViewById(R.id.radio_metre);
        result = (TextView)findViewById(R.id.result);

        // On attribue un listener adapté aux vues qui en ont besoin
        envoyer.setOnClickListener(envoyerListener);
        reset.setOnClickListener(resetListener);
        commentaire.setOnClickListener(checkedListener);

        taille.setOnKeyListener(modifListener);
        poids.setOnKeyListener(modifListener);

    }

    private View.OnClickListener envoyerListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // on récupère la taille
            String t = taille.getText().toString();
            // On récupère le poids
            String p = poids.getText().toString();
            float tValue = Float.valueOf(t);
            // Puis on vérifie que la taille est cohérente
            if(tValue <= 0)
                Toast.makeText(MainActivity.this, R.string.taillePositive, Toast.LENGTH_SHORT).show();
            else {
                float pValue = Float.valueOf(p);
                if(pValue <= 0)
                    Toast.makeText(MainActivity.this, R.string.poidPositif,Toast.LENGTH_SHORT).show();
                else {

                    if (group.getCheckedRadioButtonId() == R.id.radio_centimetre) tValue =
                            tValue / 100;
                    float imc = pValue / (tValue * tValue);
                    String resultat=getString(R.string.Imc) + imc+" . ";
                    if(commentaire.isChecked()) resultat += interpreteIMC(imc);
                    result.setText(resultat);
                }
            }
        }
    };

    private String interpreteIMC(Float imc)
    {
        if(imc < 16.5)
        { return getString(R.string.famine); }
        else if (imc >= 16.5 && imc < 18.5)
        { return getString(R.string.maigreur); }
        else if (imc >= 18.5 && imc < 25)
        { return getString(R.string.corpulenceNormal); }
        else if (imc >= 25 && imc < 30)
        { return getString(R.string.surpids); }
        else if (imc >= 30 && imc < 35)
        { return getString(R.string.obesiteModere); }
        else if (imc >= 35 && imc < 40)
        { return getString(R.string.obesiteSevere); }
        else
        {return getString(R.string.obesiteMorbideMassive); }


    }

    private View.OnClickListener resetListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            poids.getText().clear();
            taille.getText().clear();
            result.setText(texteInit);
        }
    };

    private View.OnClickListener checkedListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(((CheckBox)v).isChecked()) {
                result.setText(texteInit);
            }
        }
    };

    // Se lance à chaque fois qu'on appuie sur une touche en étant sur un EditText
    private View.OnKeyListener modifListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            // On remet le texte à sa valeur par défaut
            result.setText(texteInit);
            if(taille.isFocused() && keyCode == KeyEvent.KEYCODE_PERIOD)
            {
                radio.setChecked(true);
            }

            return false;
        }
    };


}