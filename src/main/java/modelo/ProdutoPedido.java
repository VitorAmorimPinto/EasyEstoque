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
 * @author vitor.pinto
 */
public class ProdutoPedido {
   @Id
   public NitriteId id;
   public String IdProduto;
   public String nome;
   public String marca;

    public ProdutoPedido() {
    }

    public ProdutoPedido(NitriteId id, String IdProduto, String nome, String marca) {
        this.id = id;
        this.IdProduto = IdProduto;
        this.nome = nome;
        this.marca = marca;
    }

    

   

    @Override
    public String toString() {
        return "ProdutoPedido{" + "id=" + id + ", IdProduto=" + IdProduto + ", nome=" + nome + '}';
    }
   
    
   
}
