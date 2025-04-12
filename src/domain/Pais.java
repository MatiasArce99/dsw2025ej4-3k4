
package domain;

public class Pais {
    private String nombre;
    private String ISO;

    public Pais() {
        
    }
    
    public Pais(String nombre, String ISO) {
        this.nombre = nombre;
        this.ISO = ISO;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getISO() {
        return ISO;
    }

    public void setISO(String ISO) {
        this.ISO = ISO;
    }
}
