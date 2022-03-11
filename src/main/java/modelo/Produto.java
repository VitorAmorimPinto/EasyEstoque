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


public class Produto {

 @Id
 public NitriteId id;
 public String nome;
 public Marca marca;

public Produto() {
}

public Produto(String nome, Marca marca, String codigoDeBarras, int quantidade) {
    this.nome = nome;
    this.marca = marca;
}

    @Override
    public String toString() {
        return "Produto{" + "id=" + id + ", nome=" + nome + ", marca=" + marca + '}';
    }

}