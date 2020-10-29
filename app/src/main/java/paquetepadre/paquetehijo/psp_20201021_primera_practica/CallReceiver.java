package paquetepadre.paquetehijo.psp_20201021_primera_practica;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.provider.ContactsContract;


import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import paquetepadre.paquetehijo.psp_20201021_primera_practica.io.IO;
import paquetepadre.paquetehijo.psp_20201021_primera_practica.pojo.Call;
import paquetepadre.paquetehijo.psp_20201021_primera_practica.pojo.Contact;
import java.util.Date;

public class CallReceiver extends BroadcastReceiver {

    private static final String TAG = "xyz";

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            if (state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_RINGING)){

                String phoneNumber = (String) intent.getExtras().get("incoming_number");
                Contact contact = ManageCall.lookForContact(phoneNumber, context);
                if (contact == null){
                    contact = new Contact(phoneNumber, null);
                }

                //Creamos el archivo si no existe
                new File(context.getFilesDir().getAbsolutePath() + "/historial.csv").createNewFile();
                File file = IO.searchFile(context.getFilesDir(), "historial.csv");
                IO.writeFileInternalMemory(file, new Call(LocalDateTime.now(), contact));
                IO.readFileInternalMemory(file);

                //Creamos el archivo si no existe
                new File(context.getExternalFilesDir(null).getAbsolutePath() + "/llamadas.csv").createNewFile();
                File file2 = IO.searchFile(context.getExternalFilesDir(null), "llamadas.csv");
                IO.writeFileExternalMemory(file2, new Call(LocalDateTime.now(), contact));
                IO.readFileExternalMemory(file2);

                //Sacamos la info del archivo
                ArrayList<String> record = IO.readFileExternalMemory(file2);
                ArrayList<Call> callsRecord = new ArrayList<Call>();
                if(record.size() != 0){
                    for (String line : record) {
                        callsRecord.add(Call.csvToCall(line, Call.ORDER_BY_NAME));
                    }
                }

                /* Aqui tenemos un log para ver las llamadas
                for (Call llamada : callsRecord){
                    Log.v(TAG, "El toString de la llamada" + llamada.toString());
                }
                */

                //Ordenamos el array
                ArrayList<Call> callsRecordSorted = callsRecord;
                callsRecordSorted.sort(null);
                for (Call llamada : callsRecordSorted){
                    Log.v("xyz", "El toString de la llamada ordenada" + llamada.toString());
                }

                //Volvemos a escribir el archivo pero ya ordenado
                IO.writeCallsSort(IO.searchFile(context.getExternalFilesDir(null), "llamadas.csv"), callsRecordSorted);

            }
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }

    private static class ManageCall {

        private static final String[] PROJECTION = new String[] {
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };

       private static  ArrayList<Contact> contacts = new ArrayList<Contact>();

       private static void poblateContactList(Context context){
            ContentResolver cr = context.getContentResolver();
            Cursor cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PROJECTION, null, null, null);
            if (cursor != null) {
                try {
                    final int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                    final int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

                    String name, number;
                    while (cursor.moveToNext()) {
                        name = cursor.getString(nameIndex);
                        number = cursor.getString(numberIndex);
                        contacts.add(new Contact(number, name));
                    }
                } finally {
                    cursor.close();
                }
            }
        }

       public static Contact lookForContact(String phoneNumber, Context context){
           Contact findContact = null;
           poblateContactList(context);
           for (Contact contact : contacts) {
               if ((contact.getPhoneNumber().equalsIgnoreCase(phoneNumber)) || (contact.getPhoneNumber().equalsIgnoreCase("+34" + phoneNumber))){
                    findContact = contact;
                    break;
               }
           }
           return findContact;
       }
    }

}
