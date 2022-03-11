/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocios;

import java.util.List;
import modelo.Lote;
import org.dizitart.no2.FindOptions;
import org.dizitart.no2.NitriteId;

/**
 *
 * @author Priscilaa
 */
public interface CadLote {
    //Cadastrar
    public void adicionar (Lote lote) throws Exception;
    //Selecionar um lote
    public Lote pesquisarPorId (NitriteId loteId) throws Exception;
    //Filtrar a tabela na pesquisa
    public List<Lote> pesquisarParametro(String parametro, FindOptions ordem, String valorPesquisa) throws Exception;
    //Visualizar todos os lotes
    public List<Lote> visualizar() throws Exception;
    //Verifica se o lote existe
    public List<Lote> loteExiste(Lote lote) throws Exception;
    //Atualiza as informações do lote
    public void atualizar(Lote lote) throws Exception;
    //Apaga o lote do banco
    public void excluir(Lote lote) throws Exception;
}
