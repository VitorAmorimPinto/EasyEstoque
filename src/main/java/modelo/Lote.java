/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.Date;
import org.dizitart.no2.NitriteId;
import org.dizitart.no2.objects.Id;

/**
 *
 * @author Vitor
 */
public class Lote {
    @Id
    public NitriteId id;
    public int quantidade;
    public Date dataValidade,dataChegada;
    public String situacao, registro;
    public Item item;

    public Lote() {
    }

    public Lote(NitriteId id, int quantidade, Date dataValidade, Date dataChegada, String situacao, String registro, Item item) {
        this.id = id;
        this.quantidade = quantidade;
        this.dataValidade = dataValidade;
        this.dataChegada = dataChegada;
        this.situacao = situacao;
        this.registro = registro;
        this.item = item;
    }

    @Override
    public String toString() {
        return "Lote{" + "id=" + id + ", quantidade=" + quantidade + ", dataValidade=" + dataValidade + ", dataChegada=" + dataChegada + ", situacao=" + situacao + ", registro=" + registro + ", item=" + item + '}';
    }

}
