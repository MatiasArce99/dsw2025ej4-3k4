package views;

import data.Persistencia;
import domain.Mamifero;
import domain.TipoAlimentacion;

import javax.swing.*;
import java.util.InvalidPropertiesFormatException;

public class Program {

    public static void main(String[] args) throws IllegalArgumentException, InvalidPropertiesFormatException {
        Persistencia.inicializar();        
        Controlador.mostrarMenuPrincipal();        
        Persistencia.inicializar();
        MenuPrincipal mp = new MenuPrincipal();
        mp.setVisible(true);
    }
}
