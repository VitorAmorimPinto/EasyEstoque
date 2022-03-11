/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import org.dizitart.no2.IndexType;
import org.dizitart.no2.NitriteId;
import org.dizitart.no2.objects.Id;
import org.dizitart.no2.objects.Index;
import org.dizitart.no2.objects.Indices;

/**
 *
 * @author Vitor
 */
@Indices({
 @Index(value = "marca", type = IndexType.Unique)
})
public class Marca {
    @Id
    public NitriteId id;
    public String marca;

    public Marca() {
    }

    public Marca(NitriteId id, String marca) {
        this.id = id;
        this.marca = marca;
    }
    
    @Override
    public String toString() {
        return "Marca: " + marca;
    }
    
}
