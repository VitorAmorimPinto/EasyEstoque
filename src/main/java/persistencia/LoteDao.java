/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.util.List;
import modelo.Lote;
import org.dizitart.no2.FindOptions;
import org.dizitart.no2.NitriteId;

/**
 *
 * @author Priscilaa
 */
public interface LoteDao {
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
    //Atualiza as informações do lote no banco de dados
    public void atualizar(Lote lote) throws Exception;
    //Remove o lote do sistema
    public void excluir(Lote lote)throws Exception;
}
