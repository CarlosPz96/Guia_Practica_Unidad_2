/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.main;

import mvc.controlador.ControladorPersona;
import mvc.Modelo.ModeloPersona;
import mvc.Vista.VistaPersonas;

/**
 *
 * @author ASUS
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ModeloPersona modelo = new ModeloPersona();
        VistaPersonas vista = new VistaPersonas();
        ControladorPersona controlador = new ControladorPersona(modelo, vista);
        controlador.iniciaControl();
    }

}
