package paquetepadre.paquetehijo.psp_20201021_primera_practica.io;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import paquetepadre.paquetehijo.psp_20201021_primera_practica.pojo.Call;

public class IO {

    private File file;
    private static final String ERROR_TAG = "error";
    private static final String TAG = "xyz";

    public static void writeFileInternalMemory(File file, Call call){
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
            bw.write(call.toCsvDate());
            bw.newLine();
            bw.close();
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    public static ArrayList<String> readFileInternalMemory(File file){
        ArrayList<String> strLines = new ArrayList<String>();
        try {
            String strCurrentLine;
            BufferedReader objReader = new BufferedReader(new FileReader(file));
            while ((strCurrentLine = objReader.readLine()) != null) {
                strLines.add(strCurrentLine);
            }
        } catch (FileNotFoundException e) {
            Log.d(ERROR_TAG, e.getMessage());
        } catch (IOException ioe) {
            Log.d(ERROR_TAG, ioe.getMessage());
        }
        return strLines;
    }

    public static void writeFileExternalMemory(File file, Call call){
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
            bw.write(call.toCsvName());
            bw.newLine();
            bw.close();
        } catch (IOException ioe){
            Log.d(ERROR_TAG, ioe.getMessage());
        }
    }

    public static ArrayList<String> readFileExternalMemory(File file){
        ArrayList<String> strLines = new ArrayList<String>();
        try {
            String strCurrentLine;
            BufferedReader objReader = new BufferedReader(new FileReader(file));
            while ((strCurrentLine = objReader.readLine()) != null) {
                strLines.add(strCurrentLine);
            }
        } catch (FileNotFoundException e) {
            Log.d(ERROR_TAG, e.getMessage());
        } catch (IOException ioe) {
            Log.d(ERROR_TAG, ioe.getMessage());
        }
        return strLines;
    }

    public static void writeCallsSort(File file, ArrayList<Call> calls){
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, false));
            for (Call call : calls) {
                bw.write(call.toCsvName());
                bw.newLine();
            }
            bw.close();
        } catch (IOException ioe){
            Log.d(ERROR_TAG, ioe.getMessage());
        }
    }

    public static File searchFile(File folder, String fileName){
        File fileOut = null;
        File[] listOfFiles = folder.listFiles();
        Log.d(TAG, "Dentro de la carpeta " + folder.getAbsolutePath() + " hay " + listOfFiles.length + " archivos");
        if(listOfFiles != null){
            for(File file: listOfFiles){
                if (file.isFile()){
                    if (file.getName().equalsIgnoreCase(fileName)){
                        fileOut = file;
                        Log.d(TAG, "archivo encontrado: " + file.getAbsolutePath());
                    }
                }else if(file.isDirectory()){
                    Log.d(TAG, "no esta");
                    searchFile(file, fileName);
                }
            }
        }
        return fileOut;
    }

}
