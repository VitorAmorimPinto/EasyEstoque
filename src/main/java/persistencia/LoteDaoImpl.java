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
import org.dizitart.no2.objects.filters.ObjectFilters;
import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

/**
 *
 * @author Priscilaa
 */
public class LoteDaoImpl implements LoteDao{
    
    //Adiciona no banco
    @Override
    public void adicionar(Lote lote) throws Exception {
        BaseDados.rLotes.insert(lote);
    }
    //Busca pelo ID
    @Override
    public Lote pesquisarPorId(NitriteId loteId) throws Exception {
        return BaseDados.rLotes.getById(loteId);
    }

    //Busca por parâmetro (filtro da tabela)
    @Override
    public List<Lote> pesquisarParametro(String parametro, FindOptions ordem, String valorPesquisa) throws Exception {
        //inicializando a variável
        List<Lote> lotes = BaseDados.rLotes.find(ObjectFilters.regex("item.produto.nome", ".*(?i)(" + valorPesquisa + ").*"), ordem).toList();
        
        switch (parametro) {
            case "Nome":
                lotes = BaseDados.rLotes.find(ObjectFilters.regex("item.produto.nome", ".*(?i)(" + valorPesquisa + ").*"), ordem).toList();
                break;
            case "Marca":
                lotes = BaseDados.rLotes.find(ObjectFilters.regex("item.produto.marca.marca", ".*(?i)(" + valorPesquisa + ").*"), ordem).toList();
                break;
            case "Registro":
                lotes = BaseDados.rLotes.find(ObjectFilters.regex("registro", ".*(?i)(" + valorPesquisa + ").*"), ordem).toList();
                break;
            case "Código de Barras":
                lotes = BaseDados.rLotes.find(ObjectFilters.regex("item.codigoDeBarras", ".*(?i)(" + valorPesquisa + ").*"), ordem).toList();
                break;
        }
        
        return lotes;
    }

    @Override
    public List<Lote> visualizar() throws Exception {
        List<Lote> lotes = BaseDados.rLotes.find().toList();
        return lotes;
    }

    @Override
    public List<Lote> loteExiste(Lote lote) throws Exception {
        List<Lote> loteExiste = BaseDados.rLotes.find(eq("registro", lote.registro)).toList();
        return loteExiste;
    }

    @Override
    public void atualizar(Lote lote) throws Exception {
        BaseDados.rLotes.update(lote);
    }

    @Override
    public void excluir(Lote lote) throws Exception {
        BaseDados.rLotes.remove(lote);
    }
    
}
