/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocios;

import java.util.List;
import modelo.Marca;
import modelo.Mercado;
import modelo.Pedido;
import org.dizitart.no2.FindOptions;

/**
 *
 * @author vitor.pinto
 */
public interface CadMercado {
   public void adicionar(Mercado mercado) throws Exception;
   public void atualizar (Mercado mercado)throws Exception;
   public List<Mercado> visualizar(FindOptions opcao) throws Exception;
}
