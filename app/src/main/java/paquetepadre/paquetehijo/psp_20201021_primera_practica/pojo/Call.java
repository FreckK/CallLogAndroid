package paquetepadre.paquetehijo.psp_20201021_primera_practica.pojo;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Date;

public class Call implements Comparable<Call> {

    private LocalDateTime datetime;
    private Contact contact;
    public static final String ORDER_BY_DATE = "date";
    public static final String ORDER_BY_NAME = "name";


    public Call() {
        this(null, null);
    }

    public Call(LocalDateTime date, Contact contact){
        this.datetime = date;
        this.contact = contact;
    }

    public Call setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
        return this;
    }

    public Call setContact(Contact contact) {
        this.contact = contact;
        return this;
    }

    @Override
    public String toString() {
        return "Call{" +
                "date=" + datetime +
                ", contact=" + contact +
                '}';
    }

    public String toCsvDate(){
        return datetime.getYear() +
                ";" + datetime.getMonthValue() +
                ";" + datetime.getDayOfMonth() +
                ";" + datetime.getHour() +
                ";" + datetime.getMinute() +
                ";" + datetime.getSecond() +
                ";" + contact.getPhoneNumber() +
                ";" + contact.getName();
    }

    public String toCsvName(){
        return contact.getName() +
                ";" + datetime.getYear() +
                ";" + datetime.getMonthValue() +
                ";" + datetime.getDayOfMonth() +
                ";" + datetime.getHour() +
                ";" + datetime.getMinute() +
                ";" + datetime.getSecond() +
                ";" + contact.getPhoneNumber();
    }

    @Override
    public int compareTo(Call call) {
        int result = 0;
        if (this.contact.compareTo(call.contact) < 0){
            result = -1;
        }else if(this.contact.compareTo(call.contact) > 0){
            result = 1;
        }
        return result;
    }

    /**
     *
     * @param csv
     * @param mode es el modo en el que esta registrado primero el nombre o la fecha y se usan
     *             las variables estáticas ORDER_BY_DATE o ORDER_BY_NAME
     * @return
     */
    public static Call csvToCall(String csv, String mode){
        Call call = null;
        if (mode.equalsIgnoreCase(ORDER_BY_NAME)){
            call = new Call();
            Contact callContact = new Contact();
            String[] callElements = csv.split(";");
            callContact.setName(callElements[0]);
            callContact.setPhoneNumber(callElements[7]);
            call.setContact(callContact);
            String fecha = callElements[1] + "/" + callElements[2] + "/" + callElements[3] + " " + callElements[4] + ":" + callElements[5] + ":" + callElements[6];
            call.setDatetime(LocalDateTime.parse(fecha, DateTimeFormatter.ofPattern("yyyy/MM/dd H:m:s")));
        }else if(mode.equalsIgnoreCase(ORDER_BY_DATE)){
            call = new Call();
            Contact callContact = new Contact();
            String[] callElements = csv.split(";");
            callContact.setName(callElements[7]);
            callContact.setPhoneNumber(callElements[6]);
            call.setContact(callContact);
            String fecha = callElements[2] + "/" + callElements[1] + "/" + callElements[0] + " " + callElements[3] + ":" + callElements[4] + ":" + callElements[5];
            call.setDatetime(LocalDateTime.parse(fecha, DateTimeFormatter.ofPattern("yyyy/MM/dd H:m:s")));
        }
        return call;
    }
}
