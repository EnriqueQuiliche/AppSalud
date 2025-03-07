package Models;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Persona implements Serializable {
    private String nombre;
    private String apellido;
    private int edad;
    private String dni;
    private double peso;
    private double altura;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public Persona( String nombre, String apellido, int edad, String dni, double peso, double altura) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.dni = dni;
        this.peso = peso;
        this.altura = altura;
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
        return this.apellido+", "+this.nombre+" tiene peso "+ tipoPeso[calcularIMC()+1]+" y es "+(esMayorDeEdad()?"mayor de edad":"menor de edad");
    }
}
