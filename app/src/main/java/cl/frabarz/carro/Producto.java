package cl.frabarz.carro;

public class Producto {
    private Integer id;
    private String nombre;
    private String precio;
    private String descripcion;
    private String imagen;

    public void setId(Integer id)
    {
        this.id = id;
    }
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }
    public void setPrecio(String precio)
    {
        this.precio = precio;
    }
    public void setDescripcion(String descripcion)
    {
        this.descripcion = descripcion;
    }
    public void setImagen(String imagen)
    {
        this.imagen = imagen;
    }

    public Integer getId()
    {
        return this.id;
    }
    public String getNombre()
    {
        return this.nombre;
    }
    public String getPrecio()
    {
        return this.precio;
    }
    public String getDescripcion()
    {
        return this.descripcion;
    }
    public String getImagen()
    {
        return this.imagen;
    }
}
