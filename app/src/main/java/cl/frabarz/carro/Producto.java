package cl.frabarz.carro;

import android.content.ContentValues;

import java.text.Normalizer;

public class Producto {
    private Long
        id;
    private int
        precio;
    private String
        nombre,
        categoria,
        descripcion,
        imagen;

    public void setId(Long id)
    {
        this.id = id;
    }
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }
    public void setPrecio(int precio)
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

    public Long getId()
    {
        return this.id;
    }
    public String getNombre()
    {
        return this.nombre;
    }
    public String getPrecio()
    {
        return "$" + this.precio;
    }
    public int getPrecioRaw()
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

    public ContentValues getAsContentValues()
    {
        ContentValues item = new ContentValues();
        item.put("nombre",    nombre);
        item.put("precio",    precio);
        item.put("descrip",   descripcion);
        item.put("categoria", categoria);
        item.put("imagen",    imagen);
        return item;
    }
}
