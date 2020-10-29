package paquetepadre.paquetehijo.psp_20201021_primera_practica;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import paquetepadre.paquetehijo.psp_20201021_primera_practica.io.IO;
import paquetepadre.paquetehijo.psp_20201021_primera_practica.pojo.Call;

public class ViewModelMainActivity extends ViewModel {
    //Aqui estaran los datos que se muestren en la aplicacion

    private Context context;

    public ViewModelMainActivity(Context context){
        this.context = context;
    }

    public String getCallsName(){
        ArrayList<String> calls = IO.readFileExternalMemory(IO.searchFile(this.context.getExternalFilesDir(null), "llamadas.csv"));
        String texto = "";
        for (String aux:
             calls) {
            texto = texto + aux + "\n";
        }
        return texto;
    }

    public String getCallsDate(){
        ArrayList<String> calls = IO.readFileExternalMemory(IO.searchFile(this.context.getFilesDir(), "historial.csv"));
        String texto = "";
        for (String aux:
                calls) {
            texto = texto + aux + "\n";
        }
        return texto;
    }
}
