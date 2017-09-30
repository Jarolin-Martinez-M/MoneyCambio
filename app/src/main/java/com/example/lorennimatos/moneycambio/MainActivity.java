package com.example.lorennimatos.moneycambio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import static com.example.lorennimatos.moneycambio.R.id.valorCambio;

public class MainActivity extends AppCompatActivity {

    final String[] Datos = new String[] {"Dollar","Euro","Peso Dominicano"};

    private Spinner monedaActualSP;
    private Spinner monedaCambioSP;
    private EditText valorCambioET;
    private TextView resultadoTV;

    final private double euro = 53.56;
    final private double dolar = 47.47;
    final private double  factorEuroDolar = 1.13;
    final private double  factorDolarEuro = 0.88;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter<String> adaptador  = new ArrayAdapter<String>
                (this,R.layout.support_simple_spinner_dropdown_item,Datos);

        monedaActualSP  = (Spinner) findViewById(R.id.monedaActualSP);
        monedaActualSP.setAdapter(adaptador);
    }

    public void clickConvertir(View view){
        monedaActualSP  = (Spinner) findViewById(R.id.monedaActualSP);
        monedaCambioSP  = (Spinner) findViewById(R.id.monedaCambioSP);
        valorCambioET = (EditText) findViewById(R.id.valorCambioET);
        resultadoTV = (TextView) findViewById(R.id.resultadoTV);

        String monedaActual = monedaActualSP.getSelectedItem().toString();
        String monedaCambio = monedaCambioSP.getSelectedItem().toString();
        double valorCambio = 0;
        if(!TextUtils.isEmpty(valorCambioET.getText())){
             valorCambio = Double.parseDouble(valorCambioET.getText().toString());
        }

        double resultado = procesarConversion(monedaActual,monedaCambio,valorCambio);

        if (resultado>0){
            resultadoTV.setText(String.format("Por %5.2f %s,  recibira %5.2f %s", valorCambio,monedaActual,resultado,monedaCambio));
            valorCambioET.setText("");
        }else{
            resultadoTV.setText(String.format(""));
            Toast.makeText(MainActivity.this, "Las opciones elegida no tienen un factor de converitdor", Toast.LENGTH_SHORT).show();

        }

    }

    private double procesarConversion(String monedaActual, String monedaCambio,  double valorCambio){
        double  resultadoConversion =  0;

        switch (monedaActual) {
            case "Dollar":
                if (monedaCambio.equals("Euro")){
                    resultadoConversion = valorCambio / factorEuroDolar;
                }
                if (monedaCambio.equals("Peso Dominicano")) {
                    resultadoConversion = valorCambio * dolar;
                }
                break;
            case "Euro":
                if (monedaCambio.equals("Dollar")){
                    resultadoConversion = valorCambio/ factorEuroDolar;
                }if(monedaCambio.equals("Peso Dominicano")){
                    resultadoConversion = valorCambio * dolar;
                 }
                break;
            case "Peso Dominicano":
                if (monedaCambio.equals("Dollar")){
                    resultadoConversion = valorCambio /dolar;
                }
                if(monedaCambio.equals("Euro")){
                    resultadoConversion = valorCambio /euro;
                }
                break;
        }
        return  resultadoConversion;
    }
}
