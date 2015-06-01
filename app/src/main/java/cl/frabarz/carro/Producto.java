package cl.frabarz.carro;

import java.text.Normalizer;

public class Producto {
    private Integer
        id;
    private String
        nombre,
        precio,
        categoria,
        descripcion,
        imagen;

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
    public void setCategoria(String categoria)
    {
        this.categoria = categoria;
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
    public String getCategoria()
    {
        return this.categoria;
    }
    public String getDescripcion()
    {
        return this.descripcion;
    }
    public String getImagen()
    {
        return this.imagen;
    }

    public String getNombreNormalizado()
    {
        return Normalizer.normalize(this.nombre.toLowerCase(), Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }
}
