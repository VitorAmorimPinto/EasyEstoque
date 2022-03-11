/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import org.dizitart.no2.NitriteId;
import org.dizitart.no2.objects.Id;

/**
 *
 * @author Vitor
 */
public class Item {
   @Id
   public NitriteId id;
   public String codigoDeBarras,situacao;
   public double preco, peso;
   public Produto produto;

    public Item() {
    }

    public Item(String codigoDeBarras, String situacao, double preco, double peso, Produto produto) {
        this.codigoDeBarras = codigoDeBarras;
        this.situacao = situacao;
        this.preco = preco;
        this.peso = peso;
        this.produto = produto;
    }

    @Override
    public String toString() {
        return "Item{" + "codigoDeBarras=" + codigoDeBarras + ", situacao=" + situacao + ", preco=" + preco + ", peso=" + peso + ", produto=" + produto + '}';
    }

}
