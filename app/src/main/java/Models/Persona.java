package Models;

import android.net.Uri;

import androidx.annotation.NonNull;

public class Persona {
    private String nombre;
    private String apellido;
    private String sexo;
    private String ciudad;
    private int edad;
    private String dni;
    private double peso;
    private double altura;
    //se cambio el tipo de dato del atributo Foto
    //private Uri foto;
    private byte[] foto;

    public Persona(String nombre, String apellido, String sexo, String ciudad, int edad, String dni, double peso, double altura, byte[] foto) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.sexo = sexo;
        this.ciudad = ciudad;
        this.edad = edad;
        this.dni = dni;
        this.peso = peso;
        this.altura = altura;
        this.foto = foto;
    }


    public String getNombreCompleto() {
        return apellido+", "+nombre;
    }

    public String getTipoPeso() {
        String[] tipoPeso = {"debajo de ideal","ideal","sobre lo ideal"};
        return tipoPeso[calcularIMC()+1];
    }

    public String getTipoPersona() {
        return esMayorDeEdad()? "Mayor de edad":"Menor de edad";
    }

    public String getApellido() {
        return apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDni() {
        return dni;
    }

    public byte[] getFoto() {
        return foto;
    }

    public String getSexo() {
        return sexo;
    }
    public String getCiudad() {
        return ciudad;
    }

    public double getPeso() {
        return peso;
    }

    public double getAltura() {
        return altura;
    }

    public int getEdad() {
        return edad;
    }

    public boolean verificarDNI(){
        if (this.dni.length() == 8)
            return true;
        else
            return false;
    }

    public boolean esMayorDeEdad() {
        if (this.edad>=18)
            return true;
        else
            return false;
    }


    public int calcularIMC() {
        double par = this.peso / Math.pow(this.altura,2);

        if (par < 20) {
            return -1;
        } else if (par >= 20 && par <= 25) {
            return 0;
        } else {
            return 1;
        }
    }

    @NonNull
    @Override
    public String toString() {
        String[] tipoPeso = {"debajo de ideal","ideal","sobre lo ideal"};
        return this.apellido+", "+this.nombre+" tiene peso "+ tipoPeso[calcularIMC()+1]+" y es "+(esMayorDeEdad()?"Mayor de edad":"Menor de edad");
    }
}
