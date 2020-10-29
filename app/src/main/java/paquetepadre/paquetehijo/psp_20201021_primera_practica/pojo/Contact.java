package paquetepadre.paquetehijo.psp_20201021_primera_practica.pojo;

import java.util.Comparator;

public class Contact implements Comparable<Contact>{

    private String phoneNumber;
    private String name;

    public Contact() {
        this(null, null);
    }

    public Contact(String phoneNumber, String name){
        this.setPhoneNumber(phoneNumber);
        this.setName(name);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getName() {
        return name;
    }

    public Contact setPhoneNumber(String phoneNumber) {
        if (phoneNumber != null){
            this.phoneNumber = phoneNumber.replace(" ", "");
        }
        return this;
    }

    public void setName(String name) {
        if (name == null || name == "") {
            this.name = "desconocido";
        } else {
            this.name = name;
        }
    }

    @Override
    public String toString() {
        return "Contact{" +
                "phoneNumber=" + phoneNumber +
                ", name='" + name + '\'' +
                '}';
    }


    @Override
    public int compareTo(Contact c) {
        int result = 0;
        if (this.name.compareToIgnoreCase(c.name) < 0){
            result = -1;
        }else if (this.name.compareToIgnoreCase(c.name) > 0){
            result = 1;
        }
        return result;
    }
}
