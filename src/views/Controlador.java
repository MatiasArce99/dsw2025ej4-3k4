package views;

import data.Persistencia;
import domain.*;
import views.*;

import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class Controlador {
    static AgregarAnimal ventana = new AgregarAnimal();
    static MenuPrincipal ventanaPrincipal = new MenuPrincipal();
    static ListarAnimalesView ventanaTabla = new ListarAnimalesView();
    
    public static TipoAlimentacion[] getTiposAlimentacion(){
        return  TipoAlimentacion.values();
    }
    public static ArrayList<Especie> getEspecies(){
        return Persistencia.getEspecies();
    }
    public static ArrayList<Sector> getSectores(){
        return Persistencia.getSectores();
    }
    public static ArrayList<Pais> getPaises() {
        return Persistencia.getPaises();
    }
    public static void guardarAnimal(Mamifero mamifero) {
        Persistencia.agregarAnimal(mamifero);
    }
    
    public static ArrayList<AnimalViewModel> getAnimales(){
        ArrayList<AnimalViewModel> animales = new ArrayList<>();
        for(Mamifero animal : Persistencia.getAnimales()){
            animales.add(new AnimalViewModel(animal));
        }
        return animales;
    }
    
    public static ComidaViewModel  calcularComida(){
        double totalCarnivoros = Persistencia.getTotalComida(TipoAlimentacion.CARNIVORO);
        double totalHerbivoros = Persistencia.getTotalComida(TipoAlimentacion.HERBIVORO);
        return new ComidaViewModel(totalCarnivoros, totalHerbivoros);
    }
    
    public static void mostrarMenuPrincipal(){
        ventanaPrincipal.setVisible(true);
    }
    
    public static void mostrarAgregarAnimales(){
        ventanaPrincipal.setVisible(false);
        ventana.setVisible(true);
        CargarComboBox();
    }
    
    public static void CargarComboBox(){
        ArrayList<Especie> especies = Controlador.getEspecies();        
        for(Especie especie : especies) {
            ventana.getComboBoxEspecie().addItem(especie.getNombre());
        }
        
        ArrayList<Sector> sectores = Controlador.getSectores();        
        for (Sector sector : sectores) {
            ventana.getComboBoxSector().addItem(sector.toString());
        }
        
        ArrayList<Pais> paises = Controlador.getPaises();
        for (Pais pais : paises) {
            ventana.getComboBoxPais().addItem(pais.getNombre());
        }
    }
    
    public static void agregarAnimal(){        
       
        Double peso = Double.parseDouble(ventana.getTxtPeso().getText());
        int edad = Integer.parseInt(ventana.getTxtEdad().getText());
        String nombreEspecie = String.valueOf(ventana.getComboBoxEspecie().getSelectedItem());
        String pais = String.valueOf(ventana.getComboBoxPais().getSelectedItem());
        String nombreSector = String.valueOf(ventana.getComboBoxSector().getSelectedItem());
        Sector sector = new Sector();
        sector = buscarSector(nombreSector);
        
        System.out.println(sector);
        if(sector.getTipoAlimentacion().equals(TipoAlimentacion.HERBIVORO)){
            Double valorFijo = Double.parseDouble(ventana.getTxtValorFijo().getText());
            try {
                Persistencia.agregarAnimal(new Herbivoro(edad,peso,buscarEspecie(nombreEspecie),sector,valorFijo,buscarPais(pais)));
            } catch (InvalidPropertiesFormatException ex) {
                //Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if (sector.getTipoAlimentacion().equals(TipoAlimentacion.CARNIVORO)){
            try {
                Persistencia.agregarAnimal(new Carnivoro(edad,peso,buscarEspecie(nombreEspecie),sector,buscarPais(pais)));
            } catch (InvalidPropertiesFormatException ex) {
                //Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static Sector buscarSector(String nombre){
        for(Sector sector : Persistencia.getSectores()){
            if(sector.toString().equals(nombre)){
                return sector;
            }
        }
        return null;
    }
    
    public static Pais buscarPais(String nombre){
        for(Pais pais : Persistencia.getPaises()){
            if(pais.getNombre().equals(nombre)){
                return pais;
            }
        }
        return null;
    }
    
    public static Especie buscarEspecie(String nombre){
        for(Especie especie : Persistencia.getEspecies()){
            if(especie.getNombre().equals(nombre)){
                return especie;
            }
        }
        return null;
    }
    
    public static void mostrarVentanaAnimales(){
        ventanaPrincipal.setVisible(false);
        ventanaTabla.setVisible(true);
        
        ArrayList<AnimalViewModel> animales = Controlador.getAnimales();
        ventanaTabla.animalesGrid.setModel(new DefaultTableModel(new Object[][] {},
            new String[] {"Especie", "Edad", "Peso", "Sector", "Comida Fija", "Por. Peso"}));
        
        for(AnimalViewModel animal : animales){
            ((DefaultTableModel)ventanaTabla.animalesGrid.getModel()).addRow(new Object[] {
                animal.getEspecie(),
                animal.getEdad(),
                animal.getPeso(),
                animal.getSector(),
                animal.getValorFijo() > 0 ? String.format("%.2f%n Kgs", animal.getValorFijo()) : "-",
                animal.getPorcentaje() > 0 ? String.format("%.2f %%", animal.getPorcentaje()*100) : "-"
            });
        }
    }
 }
