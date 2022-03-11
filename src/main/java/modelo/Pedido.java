/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.Date;
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
 @Index(value = "idPedido", type = IndexType.Unique)
})
public class Pedido {
   @Id
   public NitriteId id;
   public String dataEntrega,dataDeSolicitacao;
   public String fornecedor,status,idPedido,endereco;

    public Pedido() {
    }

    public Pedido(NitriteId id, String dataEntrega, String dataDeSolicitacao, String fornecedor, String status, String idPedido, String endereco) {
        this.id = id;
        this.dataEntrega = dataEntrega;
        this.dataDeSolicitacao = dataDeSolicitacao;
        this.fornecedor = fornecedor;
        this.status = status;
        this.idPedido = idPedido;
        this.endereco = endereco;
    }

   

    @Override
    public String toString() {
        return "Pedido{" + "dataEntrega=" + dataEntrega + ", dataDeSolicitacao=" + dataDeSolicitacao + ", fornecedor=" + fornecedor + ", status=" + status + ", idPedido=" + idPedido + ", endereco=" + endereco + '}';
    }
    
   

    
    

    
   
}
