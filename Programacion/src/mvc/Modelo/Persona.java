/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.Modelo;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;

/**
 *
 * @author ASUS
 */
public class Persona {
    private String id_persona;
    private String nombre;
    private String apellido;
   

    public Persona() {
    }

    public Persona(String id_persona, String nombre, String apellido) {
        this.id_persona = id_persona;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public String getId_persona() {
        return id_persona;
    }

    public void setId_persona(String id_persona) {
        this.id_persona = id_persona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombres(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

}
