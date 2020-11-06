package paquetepadre.paquetehijo.psp_20201021_primera_practica;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import java.io.File;
import java.io.IOException;
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
        try {
            new File(context.getExternalFilesDir(null).getAbsolutePath() + "/llamadas.csv").createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<String> calls = IO.readFileExternalMemory(IO.searchFile(this.context.getExternalFilesDir(null), "llamadas.csv"));
        String texto = "";
        for (String aux:
             calls) {
            texto = texto + aux + "\n";
        }
        if (texto.equalsIgnoreCase("")){
            texto = "No hay llamadas registradas";
        }
        return texto;
    }

    public String getCallsDate(){
        try {
            new File(context.getFilesDir().getAbsolutePath() + "/historial.csv").createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<String> calls = IO.readFileExternalMemory(IO.searchFile(this.context.getFilesDir(), "historial.csv"));
        String texto = "";
        for (String aux:
                calls) {
            texto = texto + aux + "\n";
        }
        if (texto.equalsIgnoreCase("")){
            texto = "No hay llamadas registradas";
        }
        return texto;
    }
}
