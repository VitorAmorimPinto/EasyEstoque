/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apresentacao;

import java.awt.CardLayout;
import java.text.DateFormat;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Lote;
import modelo.Marca;
import modelo.Produto;
import modelo.ProdutoFLV;
import negocios.CadLote;
import negocios.CadLoteImpl;
import negocios.CadMarca;
import negocios.CadMarcaImpl;
import negocios.CadProduto;
import negocios.CadProdutoFLV;
import negocios.CadProdutoFLVImpl;
import negocios.CadProdutoImpl;
import org.dizitart.no2.FindOptions;
import org.dizitart.no2.NitriteId;
import org.dizitart.no2.SortOrder;
import org.dizitart.no2.objects.filters.ObjectFilters;
import static org.dizitart.no2.objects.filters.ObjectFilters.and;
import static org.dizitart.no2.objects.filters.ObjectFilters.eq;
import persistencia.BaseDados;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Baixa;
import modelo.Item;
import modelo.Mercado;
import modelo.Pedido;
import modelo.Usuario;
import negocios.CadBaixa;
import negocios.CadBaixaImpl;
import negocios.CadItem;
import negocios.CadItemImpl;
import negocios.CadMercado;
import negocios.CadMercadoImpl;
import negocios.CadPedido;
import negocios.CadPedidoImpl;
import negocios.CadUser;
import negocios.CadUserImpl;

/**
 *
 * @author Priscilaa
 */
public class JFrameCardLayout extends javax.swing.JFrame {

    /**
     * Creates new form JFrameCardLayout
     */
    public JFrameCardLayout() {
        initComponents();
    }
    
    //INSTANCIAÇÃO DOS OBJETOS QUE VÃO SER UTILIZADOS
    public static String IdP;
    public static Pedido DadosPedido = new Pedido();
    public static int EstadoPedido;
    Pedido pedido = new Pedido();
    Produto produto = new Produto();
    ProdutoFLV produtoFLV = new ProdutoFLV();
    Marca marca = new Marca();
    Lote lote = new Lote();
    Item item = new Item();
    Baixa baixa = new Baixa();
    Usuario usuario = new Usuario();
    Mercado mercadoInfo = new Mercado();
    CadItem cadItem = new CadItemImpl();
    CadProduto cadProduto = new CadProdutoImpl();
    CadProdutoFLV cadProdutoFLV = new CadProdutoFLVImpl();
    CadLote cadLote = new CadLoteImpl();
    CadMarca cadMarca = new CadMarcaImpl();
    CadBaixa cadBaixa = new CadBaixaImpl();
    CadPedido cadPedido = new CadPedidoImpl();
    CadMercado cadMercado = new CadMercadoImpl();
    
    //MÉTODOS DE ATUALIZAÇÃO DAS INFORMAÇÕES DAS TABELAS
     public void atualizarTabelaPedidos(){
    
      DefaultTableModel dtm = (DefaultTableModel) jTablePedidos.getModel(); 
      dtm.setRowCount(0);
      FindOptions ordenacao = FindOptions.sort("fornecedor", SortOrder.Ascending);
      List<Pedido> produtos = BaseDados.rPedidos.find(ordenacao).toList();
      
      for(Pedido p : produtos){
          dtm.addRow(new Object[]{p.id, p.idPedido,p.fornecedor,p.status});
      }
  }
     public void atualizarTabelaUsuarios(){
    
      DefaultTableModel dtm = (DefaultTableModel) jTableUsuarios.getModel(); 
      dtm.setRowCount(0);
      FindOptions ordenacao = FindOptions.sort("usuario", SortOrder.Ascending);
      List<Usuario> usuarios = BaseDados.rUsuarios.find(ordenacao).toList();
      
      for(Usuario u : usuarios){
          dtm.addRow(new Object[]{u.id,u.usuario,u.pessoa.nome,u.pessoa.sobrenome});
      }
  }
    public void atualizarTabelaMarcasCadProduto() throws Exception {
        DefaultTableModel dtm = (DefaultTableModel) jTableMarcasCadProduto.getModel();
        FindOptions opcoes = FindOptions.sort("marca", SortOrder.Ascending);
        List<Marca> marcas = cadMarca.visualizar(opcoes);
        dtm.setRowCount(0);
        for (Marca m : marcas) {
            dtm.addRow(new Object[]{m.id, m.marca});
        }
    }
     public void buscarInfoMercado() throws Exception {
        FindOptions opcoes = FindOptions.sort("", SortOrder.Ascending);
        List<Mercado> mercado = cadMercado.visualizar(opcoes);
        for (Mercado m : mercado) {
            mercadoInfo.endereco = m.endereco;
            mercadoInfo.nome = m.nome;
            mercadoInfo.telefone = m.telefone;
            mercadoInfo.id = m.id;
        }
    }

    public void atualizarTabelaVisualizarProdutos() throws Exception {
        DefaultTableModel dtm = (DefaultTableModel) jTableVisualizarProdutos.getModel();
        List<Produto> produtos = cadProduto.visualizarProdutos();
        dtm.setRowCount(0);
        for (Produto p : produtos) {
            dtm.addRow(new Object[]{p.nome, p.marca.marca});
        }
    }
    
    public void atualizarTabelaVisualizarProdutosFLV() throws Exception {
        DefaultTableModel dtm = (DefaultTableModel) jTableVisualizarProdutosFLV.getModel();
        FindOptions opcoes = FindOptions.sort("nome", SortOrder.Ascending);
        List<ProdutoFLV> produtosFLV = cadProdutoFLV.visualizar(opcoes);
        dtm.setRowCount(0);
        for (ProdutoFLV p : produtosFLV) {
            dtm.addRow(new Object[]{p.nome, p.codigo});
        }
    }
    
    
     public void atualizarTabelaVisualizarLotes() throws Exception {
        DefaultTableModel dtm = (DefaultTableModel) jTableVisualizarLotes.getModel();
        List<Lote> lotes = cadLote.visualizar();
        dtm.setRowCount(0);
        for (Lote l : lotes) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String validade = sdf.format(l.dataValidade);
            dtm.addRow(new Object[]{l.registro, l.item.produto.nome, l.item.produto.marca.marca, l.quantidade, validade, l.item.codigoDeBarras, l.situacao});
        }
    }
    
        public void atualizarTabelaVisualizarLotesBaixaItem() throws Exception {
        DefaultTableModel dtm = (DefaultTableModel) jTableVisualizarLotesBaixaItem.getModel();
        List<Lote> lotes = cadLote.visualizar();
        dtm.setRowCount(0);
        for (Lote l : lotes) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String validade = sdf.format(l.dataValidade);
            dtm.addRow(new Object[]{l.id, l.registro, l.item.produto.nome, l.item.produto.marca.marca, l.quantidade, validade, l.item.codigoDeBarras, l.situacao});
        }
    }
    
     public void atualizarTabelaVisualizarLotesBaixaLote() throws Exception {
        DefaultTableModel dtm = (DefaultTableModel) jTableVisualizarLotesBaixaLote.getModel();
        List<Lote> lotes = cadLote.visualizar();
        dtm.setRowCount(0);
        for (Lote l : lotes) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String validade = sdf.format(l.dataValidade);
            dtm.addRow(new Object[]{l.id, l.registro, l.item.produto.nome, l.item.produto.marca.marca, l.quantidade, validade, l.item.codigoDeBarras, l.situacao});
        }
    }
    
    public void atualizarTabelaVisualizarProdutosItem() throws Exception {
        DefaultTableModel dtm = (DefaultTableModel) jTableProdutosItem.getModel();
        FindOptions opcoes = FindOptions.sort("produto.nome", SortOrder.Ascending);
        List<Produto> produtos = cadProduto.visualizarProdutos();
        dtm.setRowCount(0);
        for (Produto p : produtos) {
            dtm.addRow(new Object[]{p.id, p.nome, p.marca.marca});
        }
    }
    
    public void atualizarTabelaVisualizarItens() throws Exception {
        DefaultTableModel dtm = (DefaultTableModel) jTableVisualizarItens.getModel();
        List<Item> itens = cadItem.visualizarItens();
        dtm.setRowCount(0);
        for (Item i : itens) {
            dtm.addRow(new Object[]{i.codigoDeBarras, i.produto.nome, i.produto.marca.marca, i.peso, i.preco});
        }
    }
    
    public void atualizarTabelaVisualizarItensLote() throws Exception {
        DefaultTableModel dtm = (DefaultTableModel) jTableItensLote.getModel();
        List<Item> itens = cadItem.visualizarItens();
        dtm.setRowCount(0);
        for (Item i : itens) {
            dtm.addRow(new Object[]{i.id, i.codigoDeBarras, i.produto.nome, i.produto.marca.marca, i.peso, i.preco});
        }
    }
    
    public void atualizarTabelaVisualizarProdutosAtualizar() throws Exception {
        DefaultTableModel dtm = (DefaultTableModel) jTableProdutosAtualizar.getModel();
        List<Produto> produtos = cadProduto.visualizarProdutos();
        dtm.setRowCount(0);
        for (Produto p : produtos) {
            dtm.addRow(new Object[]{p.id, p.nome, p.marca.marca});
        }
    }
    
    public void atualizarTabelaVisualizarProdutosFLVAtualizar() throws Exception {
        DefaultTableModel dtm = (DefaultTableModel) jTableProdutosFLVAtualizar.getModel();
        FindOptions opcoes = FindOptions.sort("nome", SortOrder.Ascending);
        List<ProdutoFLV> produtosFLV = cadProdutoFLV.visualizar(opcoes);
        dtm.setRowCount(0);
        for (ProdutoFLV p : produtosFLV) {
            dtm.addRow(new Object[]{p.id, p.nome, p.codigo});
        }
    }
    
    public void atualizarTabelaVisualizarItensAtualizar() throws Exception {
        DefaultTableModel dtm = (DefaultTableModel) jTableItensAtualizar.getModel();
        List<Item> itens = cadItem.visualizarItens();
        dtm.setRowCount(0);
        for (Item i : itens) {
            dtm.addRow(new Object[]{i.id, i.produto.nome, i.produto.marca.marca, i.peso, i.preco, i.codigoDeBarras});
        }
    }
    
    public void atualizarTabelaVisualizarLotesAtualizar() throws Exception {
        DefaultTableModel dtm = (DefaultTableModel) jTableLotesAtualizar.getModel();
        List<Lote> lotes = cadLote.visualizar();
        dtm.setRowCount(0);
        for (Lote l : lotes) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String validade = sdf.format(l.dataValidade);
            String chegada = sdf.format(l.dataChegada);
            dtm.addRow(new Object[]{l.id, l.registro, l.item.produto.nome, l.item.produto.marca.marca, l.quantidade, validade, chegada, l.situacao});
        }
    }

    //MÉTODOS DE PESQUISA 
    public void pesquisarMarcas(String nomeMarca) throws Exception {
        DefaultTableModel dtm = (DefaultTableModel) jTableMarcasCadProduto.getModel();
        FindOptions opcoes = FindOptions.sort("marca", SortOrder.Ascending);
        List<Marca> marcas = cadMarca.pesquisar(nomeMarca, opcoes);
        dtm.setRowCount(0);
        for (Marca m : marcas) {
            dtm.addRow(new Object[]{m.id, m.marca});
        }
    }

    public void pesquisarProdutos(String valorPesquisa, String tipoPesquisa, String ordemPesquisa) throws Exception {
        DefaultTableModel dtm = (DefaultTableModel) jTableVisualizarProdutos.getModel();
        FindOptions opcoes = FindOptions.sort("nome", SortOrder.Ascending);
        switch (ordemPesquisa) {
            case "Nome A-Z":
                opcoes = FindOptions.sort("nome", SortOrder.Ascending);
                break;
            case "Nome Z-A":
                opcoes = FindOptions.sort("nome", SortOrder.Descending);
                break;
            case "Marca A-Z":
                opcoes = FindOptions.sort("marca.marca", SortOrder.Ascending);
                break;
            case "Marca Z-A":
                opcoes = FindOptions.sort("marca.marca", SortOrder.Descending);
                break;
        }

        List<Produto> produtos = cadProduto.pesquisarParametro(tipoPesquisa, opcoes, valorPesquisa);
        dtm.setRowCount(0);
        
        for (Produto p : produtos) {
            dtm.addRow(new Object[]{ p.nome, p.marca.marca});
        }

    }
    public void pesquisarPedidos(String valorPesquisa, String tipoPesquisa, String ordemPesquisa) throws Exception {
        DefaultTableModel dtm = (DefaultTableModel) jTablePedidos.getModel();
        FindOptions opcoes = FindOptions.sort("nome", SortOrder.Ascending);
        switch (ordemPesquisa) {
            case "Fornecedor A-Z":
                opcoes = FindOptions.sort("fornecedor", SortOrder.Ascending);
                break;
            case "Fornecedor Z-A":
                opcoes = FindOptions.sort("fornecedor", SortOrder.Descending);
                break;
           
        }

        List<Pedido> pedidos = cadPedido.pesquisarParametro(tipoPesquisa, opcoes, valorPesquisa);
        dtm.setRowCount(0);
        
        for (Pedido p : pedidos) {
            dtm.addRow(new Object[]{ p.id, p.idPedido,p.fornecedor,p.status});
        }

    }
    
    
    public void pesquisarLotes(String valorPesquisa, String tipoPesquisa, String ordemPesquisa) throws Exception {
        DefaultTableModel dtm = (DefaultTableModel) jTableVisualizarLotes.getModel();
        FindOptions opcoes = FindOptions.sort("nome", SortOrder.Ascending);
        switch (ordemPesquisa) {
            case "Nome A-Z":
                opcoes = FindOptions.sort("item.produto.nome", SortOrder.Ascending);
                break;
            case "Nome Z-A":
                opcoes = FindOptions.sort("item.produto.nome", SortOrder.Descending);
                break;
            case "Marca A-Z":
                opcoes = FindOptions.sort("item.produto.marca.marca", SortOrder.Ascending);
                break;
            case "Marca Z-A":
                opcoes = FindOptions.sort("item.produto.marca.marca", SortOrder.Descending);
                break;
        }

        List<Lote> lotes = cadLote.pesquisarParametro(tipoPesquisa, opcoes, valorPesquisa);
        dtm.setRowCount(0);
        
        for (Lote l : lotes) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String validade = sdf.format(l.dataValidade);
            dtm.addRow(new Object[]{l.registro, l.item.produto.nome, l.item.produto.marca.marca, l.quantidade, validade, l.item.codigoDeBarras, l.situacao});
        }

    }
    
    public void pesquisarLotesBaixa(String tela, String valorPesquisa, String tipoPesquisa) throws Exception {
        DefaultTableModel dtm = new DefaultTableModel();
        if(tela.equals("Baixa Item")){
            dtm = (DefaultTableModel) jTableVisualizarLotesBaixaItem.getModel();
        }else if(tela.equals("Baixa Lote")){
            dtm = (DefaultTableModel) jTableVisualizarLotesBaixaLote.getModel();
        }
        
        FindOptions opcoes = FindOptions.sort("item.produto.nome", SortOrder.Ascending);
        
        List<Lote> lotes = cadLote.pesquisarParametro(tipoPesquisa, opcoes, valorPesquisa);
        dtm.setRowCount(0);
        
        for (Lote l : lotes) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String validade = sdf.format(l.dataValidade);
            dtm.addRow(new Object[]{l.id,l.registro, l.item.produto.nome, l.item.produto.marca.marca, l.quantidade, validade, l.item.codigoDeBarras, l.situacao});
        }

    }

    
    public void pesquisarItens(String valorPesquisa, String tipoPesquisa, String ordemPesquisa) throws Exception {
        DefaultTableModel dtm = (DefaultTableModel) jTableVisualizarItens.getModel();
        FindOptions opcoes = FindOptions.sort("nome", SortOrder.Ascending);
        switch (ordemPesquisa) {
            case "Nome A-Z":
                opcoes = FindOptions.sort("produto.nome", SortOrder.Ascending);
                break;
            case "Nome Z-A":
                opcoes = FindOptions.sort("produto.nome", SortOrder.Descending);
                break;
            case "Marca A-Z":
                opcoes = FindOptions.sort("produto.marca.marca", SortOrder.Ascending);
                break;
            case "Marca Z-A":
                opcoes = FindOptions.sort("produto.marca.marca", SortOrder.Descending);
                break;
        }

        List<Item> itens = cadItem.pesquisarParametro(tipoPesquisa, opcoes, valorPesquisa);
        dtm.setRowCount(0);
        
        for (Item i : itens) {
            dtm.addRow(new Object[]{i.codigoDeBarras, i.produto.nome, i.produto.marca.marca, i.peso, i.preco});
        }

    }
    
        public void pesquisarItensLote(String valorPesquisa, String tipoPesquisa, String ordemPesquisa) throws Exception {
        DefaultTableModel dtm = (DefaultTableModel) jTableItensLote.getModel();
        FindOptions opcoes = FindOptions.sort("nome", SortOrder.Ascending);
        switch (ordemPesquisa) {
            case "Nome A-Z":
                opcoes = FindOptions.sort("produto.nome", SortOrder.Ascending);
                break;
            case "Nome Z-A":
                opcoes = FindOptions.sort("produto.nome", SortOrder.Descending);
                break;
            case "Marca A-Z":
                opcoes = FindOptions.sort("produto.marca.marca", SortOrder.Ascending);
                break;
            case "Marca Z-A":
                opcoes = FindOptions.sort("produto.marca.marca", SortOrder.Descending);
                break;
        }

        List<Item> itens = cadItem.pesquisarParametro(tipoPesquisa, opcoes, valorPesquisa);
        dtm.setRowCount(0);
        
        for (Item i : itens) {
            dtm.addRow(new Object[]{i.id, i.codigoDeBarras, i.produto.nome, i.produto.marca.marca, i.peso, i.preco});
        }

    }
    
    public void pesquisarProdutosItem(String valorPesquisa, String tipoPesquisa, String ordemPesquisa) throws Exception {
        DefaultTableModel dtm = (DefaultTableModel) jTableProdutosItem.getModel();
        FindOptions opcoes = FindOptions.sort("nome", SortOrder.Ascending);
        switch (ordemPesquisa) {
            case "Nome A-Z":
                opcoes = FindOptions.sort("nome", SortOrder.Ascending);
                break;
            case "Nome Z-A":
                opcoes = FindOptions.sort("nome", SortOrder.Descending);
                break;
            case "Marca A-Z":
                opcoes = FindOptions.sort("marca.marca", SortOrder.Ascending);
                break;
            case "Marca Z-A":
                opcoes = FindOptions.sort("marca.marca", SortOrder.Descending);
                break;
        }

        List<Produto> produtos = cadProduto.pesquisarParametro(tipoPesquisa, opcoes, valorPesquisa);
        dtm.setRowCount(0);
        
        for (Produto p : produtos) {
            dtm.addRow(new Object[]{p.id, p.nome, p.marca.marca});
        }

    }
    
    public void pesquisarBaixas(String tipoPesquisa) throws Exception {
        
        DefaultTableModel dtm = (DefaultTableModel) jTableVisualizarHistoricoBaixas.getModel();
        dtm.setRowCount(0);
        
        List<Baixa> baixas = cadBaixa.visualizar(tipoPesquisa);

        for (Baixa b : baixas) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String dataBaixa = sdf.format(b.dataBaixa);
            dtm.addRow(new Object[]{b.produto.nome, b.produto.marca.marca, b.quantidade, dataBaixa, b.motivo});
        }

    }

    
    public void pesquisarProdutosFLV(String valorPesquisa,String tipoPesquisa) throws Exception
    {  DefaultTableModel dtm = (DefaultTableModel) jTableVisualizarProdutosFLV.getModel();
       FindOptions opcoes = FindOptions.sort("nome", SortOrder.Ascending);
       
       List<ProdutoFLV> produtosFLV = cadProdutoFLV.pesquisar(valorPesquisa, tipoPesquisa, opcoes);
       
       dtm.setRowCount(0);
       for (ProdutoFLV p : produtosFLV)
            {
            dtm.addRow(new Object[]{p.nome, p.codigo});
            }
    }

    
    //MÉTODOS PARA VERIFICAR EXISTÊNCIA
    public boolean verificarProdutoVazio(Produto produto) throws Exception{
        //Lista recebe uma consulta ao repositório verificando se existe um produto cujos atributos são iguais aos do objeto passado de parâmetro
        List<Produto> produtoExiste = cadProduto.produtoExiste(produto);
        return produtoExiste.isEmpty();
    }
    
    public boolean verificarProdutoFLVVazio(ProdutoFLV produtoFLV) throws Exception{
        List<ProdutoFLV> produtoFLVExiste = cadProdutoFLV.produtoFLVExiste(produtoFLV);
        return produtoFLVExiste.isEmpty();
    }
    
    public boolean verificarMarcaVazia(Marca marca) throws Exception{
        List<Marca> marcaExiste = cadMarca.marcaExiste(marca);
        return marcaExiste.isEmpty();
    }
    
    public boolean verificarLoteVazio(Lote lote) throws Exception{
        List<Lote> loteExiste = cadLote.loteExiste(lote);
        return loteExiste.isEmpty();
    }
    
    public boolean verificarItemVazio(Item item) throws Exception{
        List<Item> itemExiste = cadItem.pesquisar(item);
        return itemExiste.isEmpty();
    }
    
    //MÉTODOS CASE INSENSITIVE
    public boolean verificarProdutoFLVCaseInsensitive(ProdutoFLV produtoFLV) throws Exception{
        List<ProdutoFLV> produtoFLVExiste = cadProdutoFLV.produtoFLVExisteNome(produtoFLV);
        return produtoFLVExiste.isEmpty();
    }
    
    //VERIFICA CAMPOS
    public boolean verificaCamposLote(){
        int linhaSelecionada = jTableItensLote.getSelectedRow();
        if(jTextFieldRegistroLote.getText().trim().equals("") ||
                jFormattedTextFieldValidadeLote.getText().trim().equals("")||
                jFormattedTextFieldChegadaLote.getText().trim().equals("")||
                linhaSelecionada < 0){
            return true;
        }else{
            return false;
        }   
    }
    
    public boolean verificaCamposItem(){
        int linhaSelecionada = jTableProdutosItem.getSelectedRow();
        if(jTextFieldCodigoDeBarrasItem.getText().trim().equals("") ||
                jTextFieldPrecoItem.getText().trim().equals("")||
                jTextFieldPesoItem.getText().trim().equals("")||
                linhaSelecionada < 0){
            return true;
        }else{
            return false;
        }   
    }
    public boolean verificaCamposPedido(){
        if (pedido.fornecedor.trim().equals("") || pedido.endereco.trim().equals("")|| pedido.idPedido.trim().equals("")) {
            return true;
        }else{
        return false;
        }
    }
    //MÉTODO PARA A CONFERÊNCIA DE DATAS COM BASE NA DATA ATUAL
    public boolean dataValidadeMenorDataAtual(Date dataValidade){
        Date dataAtual = new Date();
        return dataValidade.before(dataAtual);
    }
    
    //MÉTODO PARA A VALIDAÇÃO DE DATAS
   public static boolean isDateValid(String strDate) {
    String dateFormat = "dd/MM/uuuu";

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter
    .ofPattern(dateFormat)
    .withResolverStyle(ResolverStyle.STRICT);
    try {
        LocalDate date = LocalDate.parse(strDate, dateTimeFormatter);
        return true;
    } catch (DateTimeParseException e) {
       return false;
    } 
}
    
        
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanelVisualizarProdutosFLV = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTableVisualizarProdutosFLV = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jComboBoxTipoPesquisaProdutoFLV = new javax.swing.JComboBox<>();
        jTextFieldPesquisarProdutosFLV = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jPanelVisualizarPedidos = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        jScrollPane14 = new javax.swing.JScrollPane();
        jTablePedidos = new javax.swing.JTable();
        jButton7 = new javax.swing.JButton();
        jPanel20 = new javax.swing.JPanel();
        jTextFieldPesquisarPedidos = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        jComboBoxTipoPesquisaPedido = new javax.swing.JComboBox<>();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jComboBoxOrdemPesquisaPedido = new javax.swing.JComboBox<>();
        jPanelVisualizarItens = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jComboBoxTipoPesquisaItem = new javax.swing.JComboBox<>();
        jTextFieldPesquisarItens = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jComboBoxOrdemPesquisaItem = new javax.swing.JComboBox<>();
        jLabel36 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTableVisualizarItens = new javax.swing.JTable();
        jPanelVisualizarLote = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTableVisualizarLotes = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jComboBoxTipoPesquisaLote = new javax.swing.JComboBox<>();
        jTextFieldPesquisarLotes = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jComboBoxOrdemPesquisaLote = new javax.swing.JComboBox<>();
        jPanelVisualizarMercado = new javax.swing.JPanel();
        jLabel54 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jPanel21 = new javax.swing.JPanel();
        jLabel56 = new javax.swing.JLabel();
        jTextFieldNomeMercado = new javax.swing.JTextField();
        jTextFieldEnderecoMercado = new javax.swing.JTextField();
        jLabel57 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jTextFieldTelefoneMercado = new javax.swing.JTextField();
        jPanelCadItem = new javax.swing.JPanel();
        jButtonCadastrarItem = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldCodigoDeBarrasItem = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldPesoItem = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldPrecoItem = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jComboBoxTipoPesquisaProdutosItem = new javax.swing.JComboBox<>();
        jScrollPane11 = new javax.swing.JScrollPane();
        jTableProdutosItem = new javax.swing.JTable();
        jTextFieldPesquisarProdutosItem = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jComboBoxOrdemPesquisaProdutosItem = new javax.swing.JComboBox<>();
        jPanelCadProduto = new javax.swing.JPanel();
        jButtonCadastrarProduto = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldPesquisarMarca = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableMarcasCadProduto = new javax.swing.JTable();
        jButtonCadastrarMarca = new javax.swing.JButton();
        jLabelMarcaSelecionada = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldNomeProduto = new javax.swing.JTextField();
        jPanelCadProdutoFLV = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jTextFieldNomeProdutoFLV = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jTextFieldCodigoProdutoFLV = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jButtonCadastrarProdutoFLV = new javax.swing.JButton();
        jPanelCadLote = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jTextFieldRegistroLote = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jSpinnerQuantidadeLote = new javax.swing.JSpinner();
        jLabel27 = new javax.swing.JLabel();
        jFormattedTextFieldValidadeLote = new javax.swing.JFormattedTextField();
        jLabel28 = new javax.swing.JLabel();
        jComboBoxSituacaoLote = new javax.swing.JComboBox<>();
        jLabel31 = new javax.swing.JLabel();
        jFormattedTextFieldChegadaLote = new javax.swing.JFormattedTextField();
        jButtonCadastrarLote = new javax.swing.JButton();
        jLabel30 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jTextFieldPesquisarItensLote = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jComboBoxTipoPesquisaItensLote = new javax.swing.JComboBox<>();
        jLabel38 = new javax.swing.JLabel();
        jComboBoxOrdemPesquisaItensLote = new javax.swing.JComboBox<>();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTableItensLote = new javax.swing.JTable();
        jPanelCadPedido = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jPanel22 = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        jTextFieldFornecerdor = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        jTextFieldEndereco = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        jTextFieldIdPedido = new javax.swing.JTextField();
        jPanelBaixaItem = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jTextFieldValorPesquisaLoteBaixaItem = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jComboBoxTipoPesquisaLoteBaixaItem = new javax.swing.JComboBox<>();
        jScrollPane12 = new javax.swing.JScrollPane();
        jTableVisualizarLotesBaixaItem = new javax.swing.JTable();
        jButtonRegistrarBaixaItem = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jSpinnerQtdBaixaItem = new javax.swing.JSpinner();
        jLabel14 = new javax.swing.JLabel();
        jComboBoxMotivoBaixaItem = new javax.swing.JComboBox<>();
        jPanelBaixaLote = new javax.swing.JPanel();
        jComboBoxMotivoBaixaLote = new javax.swing.JComboBox<>();
        jLabel41 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jTextFieldValorPesquisaLoteBaixaLote = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        jComboBoxTipoPesquisaLoteBaixaLote = new javax.swing.JComboBox<>();
        jScrollPane13 = new javax.swing.JScrollPane();
        jTableVisualizarLotesBaixaLote = new javax.swing.JTable();
        jButtonRegistrarBaixaLote = new javax.swing.JButton();
        jLabel43 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jPanelVisualizarHistoricoBaixas = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableVisualizarHistoricoBaixas = new javax.swing.JTable();
        jPanel14 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        jComboBoxTipoPesquisaBaixa = new javax.swing.JComboBox<>();
        jPanelAtualizarProduto = new javax.swing.JPanel();
        jLabel58 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane15 = new javax.swing.JScrollPane();
        jTableProdutosAtualizar = new javax.swing.JTable();
        jButtonSalvarAtualizacoesProduto = new javax.swing.JButton();
        jPanel24 = new javax.swing.JPanel();
        jLabel59 = new javax.swing.JLabel();
        jTextFieldNomeAtualizarProduto = new javax.swing.JTextField();
        jButtonExcluirProduto = new javax.swing.JButton();
        jPanelAtualizarProdutoFLV = new javax.swing.JPanel();
        jButtonSalvarAtualizacoesProdutoFLV = new javax.swing.JButton();
        jLabel61 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jScrollPane16 = new javax.swing.JScrollPane();
        jTableProdutosFLVAtualizar = new javax.swing.JTable();
        jPanel25 = new javax.swing.JPanel();
        jLabel60 = new javax.swing.JLabel();
        jTextFieldNomeAtualizarProdutoFLV = new javax.swing.JTextField();
        jButtonExcluirProdutoFLV = new javax.swing.JButton();
        jPanelAtualizarItem = new javax.swing.JPanel();
        jButtonSalvarAtualizacoesItem = new javax.swing.JButton();
        jLabel62 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane17 = new javax.swing.JScrollPane();
        jTableItensAtualizar = new javax.swing.JTable();
        jPanel26 = new javax.swing.JPanel();
        jLabel63 = new javax.swing.JLabel();
        jTextFieldPesoAtualizarItem = new javax.swing.JTextField();
        jLabel64 = new javax.swing.JLabel();
        jTextFieldPrecoAtualizarItem = new javax.swing.JTextField();
        jButtonExcluirItem = new javax.swing.JButton();
        jPanelAtualizarLote = new javax.swing.JPanel();
        jButtonSalvarAtualizacoesLote = new javax.swing.JButton();
        jLabel65 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jScrollPane18 = new javax.swing.JScrollPane();
        jTableLotesAtualizar = new javax.swing.JTable();
        jPanel27 = new javax.swing.JPanel();
        jLabel66 = new javax.swing.JLabel();
        jComboBoxSituacaoAtualizarLote = new javax.swing.JComboBox<>();
        jButtonExcluirLote = new javax.swing.JButton();
        jPanelUsuarios = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        jPanel28 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableUsuarios = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jPanel29 = new javax.swing.JPanel();
        jLabel70 = new javax.swing.JLabel();
        jTextFieldCpf = new javax.swing.JTextField();
        jLabel68 = new javax.swing.JLabel();
        jTextFieldNomeUser = new javax.swing.JTextField();
        jLabel69 = new javax.swing.JLabel();
        jTextFieldSobrenome = new javax.swing.JTextField();
        jLabel67 = new javax.swing.JLabel();
        jTextFieldUsuario = new javax.swing.JTextField();
        jLabel71 = new javax.swing.JLabel();
        jPasswordFieldSenhaUser = new javax.swing.JPasswordField();
        jButton3 = new javax.swing.JButton();
        jPanelVisualizarProdutos = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableVisualizarProdutos = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jComboBoxTipoPesquisaProduto = new javax.swing.JComboBox<>();
        jTextFieldPesquisarProdutos = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jComboBoxOrdemPesquisaProduto = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenu8 = new javax.swing.JMenu();
        jMenuItemVisualizarProdutos = new javax.swing.JMenuItem();
        jMenuItemVisualizarProdutosFLV = new javax.swing.JMenuItem();
        jMenuItemVisualizarLote = new javax.swing.JMenuItem();
        jMenuVisualizarItens = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jMenuItemCadProduto = new javax.swing.JMenuItem();
        jMenuItemCadProdutoFLV = new javax.swing.JMenuItem();
        jMenuItemCadLote = new javax.swing.JMenuItem();
        jMenuItemCadItem = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        jMenu9 = new javax.swing.JMenu();
        jMenuItemAtualizarPedido = new javax.swing.JMenuItem();
        jMenuItemAtualizarProdutoFLV = new javax.swing.JMenuItem();
        jMenuItemAtualizarItem = new javax.swing.JMenuItem();
        jMenuItemAtualizarLote = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItemBaixaItem = new javax.swing.JMenuItem();
        jMenuItemBaixaLote = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItemVisualizarHistoricoBaixas = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        jMenuItem14 = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuItem15 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Lager - Slogan bacana");
        setPreferredSize(new java.awt.Dimension(833, 603));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setLayout(new java.awt.CardLayout());

        jPanelVisualizarProdutosFLV.setBackground(new java.awt.Color(255, 255, 255));

        jTableVisualizarProdutosFLV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nome", "Código"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane7.setViewportView(jTableVisualizarProdutosFLV);

        jPanel9.setBackground(new java.awt.Color(73, 73, 73));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Filtro de Pesquisa", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel9.setForeground(new java.awt.Color(255, 255, 255));
        jPanel9.setName(""); // NOI18N

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Pesquisar por:");

        jComboBoxTipoPesquisaProdutoFLV.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nome", "Código" }));
        jComboBoxTipoPesquisaProdutoFLV.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxTipoPesquisaProdutoFLVItemStateChanged(evt);
            }
        });

        jTextFieldPesquisarProdutosFLV.setToolTipText("Digite aqui etc");
        jTextFieldPesquisarProdutosFLV.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldPesquisarProdutosFLVKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextFieldPesquisarProdutosFLV, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxTipoPesquisaProdutoFLV, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(jComboBoxTipoPesquisaProdutoFLV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldPesquisarProdutosFLV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel24.setText("Visualizar Produtos FLV (Frutas, Verduras e Legumes)");

        javax.swing.GroupLayout jPanelVisualizarProdutosFLVLayout = new javax.swing.GroupLayout(jPanelVisualizarProdutosFLV);
        jPanelVisualizarProdutosFLV.setLayout(jPanelVisualizarProdutosFLVLayout);
        jPanelVisualizarProdutosFLVLayout.setHorizontalGroup(
            jPanelVisualizarProdutosFLVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelVisualizarProdutosFLVLayout.createSequentialGroup()
                .addGap(151, 151, 151)
                .addComponent(jLabel24)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanelVisualizarProdutosFLVLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelVisualizarProdutosFLVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 813, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelVisualizarProdutosFLVLayout.setVerticalGroup(
            jPanelVisualizarProdutosFLVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelVisualizarProdutosFLVLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24)
                .addGap(18, 18, 18)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(521, Short.MAX_VALUE))
        );

        jPanel1.add(jPanelVisualizarProdutosFLV, "visualizarProdutosFLV");

        jPanelVisualizarPedidos.setBackground(new java.awt.Color(255, 255, 255));

        jLabel49.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel49.setText("Pedidos");

        jTablePedidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Indentificador", "Fonecedor", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablePedidos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablePedidosMouseClicked(evt);
            }
        });
        jScrollPane14.setViewportView(jTablePedidos);

        jButton7.setBackground(new java.awt.Color(51, 51, 51));
        jButton7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("Visualizar");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jPanel20.setBackground(new java.awt.Color(73, 73, 73));
        jPanel20.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Filtro de pesquisa", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel20.setForeground(new java.awt.Color(255, 255, 255));

        jTextFieldPesquisarPedidos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldPesquisarPedidosKeyReleased(evt);
            }
        });

        jLabel51.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(255, 255, 255));
        jLabel51.setText("Pesquisar");

        jComboBoxTipoPesquisaPedido.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Fornecedor", "Id Pedido", "Status" }));

        jLabel52.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(255, 255, 255));
        jLabel52.setText("Pesquisar por");

        jLabel53.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(255, 255, 255));
        jLabel53.setText("Ordenar");

        jComboBoxOrdemPesquisaPedido.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Fornecedor A-Z", "Fornecedor Z-A" }));
        jComboBoxOrdemPesquisaPedido.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxOrdemPesquisaPedidoItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel51)
                .addGap(18, 18, 18)
                .addComponent(jTextFieldPesquisarPedidos, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(jLabel52)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBoxTipoPesquisaPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel53)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBoxOrdemPesquisaPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(226, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldPesquisarPedidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel51)
                    .addComponent(jComboBoxTipoPesquisaPedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel52)
                    .addComponent(jLabel53)
                    .addComponent(jComboBoxOrdemPesquisaPedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelVisualizarPedidosLayout = new javax.swing.GroupLayout(jPanelVisualizarPedidos);
        jPanelVisualizarPedidos.setLayout(jPanelVisualizarPedidosLayout);
        jPanelVisualizarPedidosLayout.setHorizontalGroup(
            jPanelVisualizarPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelVisualizarPedidosLayout.createSequentialGroup()
                .addGap(345, 345, 345)
                .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanelVisualizarPedidosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelVisualizarPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane14, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelVisualizarPedidosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(278, 278, 278))
        );
        jPanelVisualizarPedidosLayout.setVerticalGroup(
            jPanelVisualizarPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelVisualizarPedidosLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel49)
                .addGap(18, 18, 18)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(657, Short.MAX_VALUE))
        );

        jPanel1.add(jPanelVisualizarPedidos, "VisualizarPedidos");

        jPanelVisualizarItens.setBackground(new java.awt.Color(255, 255, 255));

        jPanel13.setBackground(new java.awt.Color(73, 73, 73));
        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Filtro de Pesquisa", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel13.setForeground(new java.awt.Color(255, 255, 255));
        jPanel13.setName(""); // NOI18N

        jLabel34.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 255, 255));
        jLabel34.setText("Pesquisar por:");

        jComboBoxTipoPesquisaItem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nome", "Marca", "Código de Barras" }));
        jComboBoxTipoPesquisaItem.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxTipoPesquisaItemItemStateChanged(evt);
            }
        });

        jTextFieldPesquisarItens.setToolTipText("Digite aqui etc");
        jTextFieldPesquisarItens.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldPesquisarItensKeyReleased(evt);
            }
        });

        jLabel35.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 255, 255));
        jLabel35.setText("Ordenar por:");

        jComboBoxOrdemPesquisaItem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nome A-Z", "Nome Z-A", "Marca A-Z", "Marca Z-A" }));
        jComboBoxOrdemPesquisaItem.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxOrdemPesquisaItemItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextFieldPesquisarItens, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel34)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxTipoPesquisaItem, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel35)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxOrdemPesquisaItem, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(149, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(jComboBoxTipoPesquisaItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldPesquisarItens, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35)
                    .addComponent(jComboBoxOrdemPesquisaItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel36.setText("Visualizar Itens");

        jTableVisualizarItens.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código de barras", "Produto", "Marca", "Peso", "Preço"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane10.setViewportView(jTableVisualizarItens);

        javax.swing.GroupLayout jPanelVisualizarItensLayout = new javax.swing.GroupLayout(jPanelVisualizarItens);
        jPanelVisualizarItens.setLayout(jPanelVisualizarItensLayout);
        jPanelVisualizarItensLayout.setHorizontalGroup(
            jPanelVisualizarItensLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelVisualizarItensLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelVisualizarItensLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 813, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelVisualizarItensLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel36)
                .addGap(312, 312, 312))
        );
        jPanelVisualizarItensLayout.setVerticalGroup(
            jPanelVisualizarItensLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelVisualizarItensLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel36)
                .addGap(18, 18, 18)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanelVisualizarItens, "visualizarItens");

        jPanelVisualizarLote.setBackground(new java.awt.Color(255, 255, 255));

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel32.setText("Visualizar Lotes");

        jTableVisualizarLotes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Registro", "Produto", "Marca", "Quantidade", "Data de validade", "Código de Barras", "Situação"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane9.setViewportView(jTableVisualizarLotes);

        jPanel11.setBackground(new java.awt.Color(73, 73, 73));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Filtro de Pesquisa", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel11.setName(""); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Pesquisar por:");

        jComboBoxTipoPesquisaLote.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nome", "Marca", "Registro", "Código de Barras" }));
        jComboBoxTipoPesquisaLote.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxTipoPesquisaLoteItemStateChanged(evt);
            }
        });

        jTextFieldPesquisarLotes.setToolTipText("Digite aqui etc");
        jTextFieldPesquisarLotes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldPesquisarLotesKeyReleased(evt);
            }
        });

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setText("Ordenar por:");

        jComboBoxOrdemPesquisaLote.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nome A-Z", "Nome Z-A", "Marca A-Z", "Marca Z-A" }));
        jComboBoxOrdemPesquisaLote.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxOrdemPesquisaLoteItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextFieldPesquisarLotes, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxTipoPesquisaLote, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel33)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxOrdemPesquisaLote, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(149, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jComboBoxTipoPesquisaLote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldPesquisarLotes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33)
                    .addComponent(jComboBoxOrdemPesquisaLote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelVisualizarLoteLayout = new javax.swing.GroupLayout(jPanelVisualizarLote);
        jPanelVisualizarLote.setLayout(jPanelVisualizarLoteLayout);
        jPanelVisualizarLoteLayout.setHorizontalGroup(
            jPanelVisualizarLoteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelVisualizarLoteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelVisualizarLoteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane9))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelVisualizarLoteLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel32)
                .addGap(308, 308, 308))
        );
        jPanelVisualizarLoteLayout.setVerticalGroup(
            jPanelVisualizarLoteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelVisualizarLoteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel32)
                .addGap(18, 18, 18)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanelVisualizarLote, "visualizarLote");

        jPanelVisualizarMercado.setBackground(new java.awt.Color(255, 255, 255));

        jLabel54.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel54.setText("Informações do Mercado");

        jButton2.setBackground(new java.awt.Color(51, 51, 51));
        jButton2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Atualizar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPanel21.setBackground(new java.awt.Color(73, 73, 73));
        jPanel21.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel56.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(255, 255, 255));
        jLabel56.setText("Nome");

        jLabel57.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(255, 255, 255));
        jLabel57.setText("Endereço");

        jLabel55.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(255, 255, 255));
        jLabel55.setText("Telefone");

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel56)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldNomeMercado, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel57)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldEnderecoMercado, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel55)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldTelefoneMercado, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(148, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel56)
                    .addComponent(jTextFieldNomeMercado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldEnderecoMercado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel57)
                    .addComponent(jLabel55)
                    .addComponent(jTextFieldTelefoneMercado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout jPanelVisualizarMercadoLayout = new javax.swing.GroupLayout(jPanelVisualizarMercado);
        jPanelVisualizarMercado.setLayout(jPanelVisualizarMercadoLayout);
        jPanelVisualizarMercadoLayout.setHorizontalGroup(
            jPanelVisualizarMercadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelVisualizarMercadoLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel54)
                .addGap(271, 271, 271))
            .addGroup(jPanelVisualizarMercadoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelVisualizarMercadoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(271, 271, 271))
        );
        jPanelVisualizarMercadoLayout.setVerticalGroup(
            jPanelVisualizarMercadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelVisualizarMercadoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel54)
                .addGap(18, 18, 18)
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(944, Short.MAX_VALUE))
        );

        jPanel1.add(jPanelVisualizarMercado, "VisualizarMercado");

        jPanelCadItem.setBackground(new java.awt.Color(255, 255, 255));

        jButtonCadastrarItem.setBackground(new java.awt.Color(51, 51, 51));
        jButtonCadastrarItem.setForeground(new java.awt.Color(255, 255, 255));
        jButtonCadastrarItem.setText("Finalizar cadastro");
        jButtonCadastrarItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCadastrarItemActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel18.setText("Cadastro de Itens");

        jPanel4.setBackground(new java.awt.Color(73, 73, 73));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dados do item", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(255, 255, 255))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Código de barras:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Peso:");

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Preço:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldCodigoDeBarrasItem, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldPesoItem, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldPrecoItem, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(182, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextFieldCodigoDeBarrasItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldPesoItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldPrecoItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel12.setBackground(new java.awt.Color(73, 73, 73));
        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Escolher item", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel12.setForeground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Pesquisar por:");

        jComboBoxTipoPesquisaProdutosItem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nome", "Marca" }));
        jComboBoxTipoPesquisaProdutosItem.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxTipoPesquisaProdutosItemItemStateChanged(evt);
            }
        });

        jTableProdutosItem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome", "Marca"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane11.setViewportView(jTableProdutosItem);

        jTextFieldPesquisarProdutosItem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldPesquisarProdutosItemKeyReleased(evt);
            }
        });

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Ordenar por:");

        jComboBoxOrdemPesquisaProdutosItem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nome A-Z", "Nome Z-A", "Marca A-Z", "Marca Z-A" }));
        jComboBoxOrdemPesquisaProdutosItem.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxOrdemPesquisaProdutosItemItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jTextFieldPesquisarProdutosItem, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxTipoPesquisaProdutosItem, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxOrdemPesquisaProdutosItem, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 781, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jComboBoxTipoPesquisaProdutosItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldPesquisarProdutosItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jComboBoxOrdemPesquisaProdutosItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelCadItemLayout = new javax.swing.GroupLayout(jPanelCadItem);
        jPanelCadItem.setLayout(jPanelCadItemLayout);
        jPanelCadItemLayout.setHorizontalGroup(
            jPanelCadItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCadItemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelCadItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelCadItemLayout.createSequentialGroup()
                        .addGroup(jPanelCadItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCadItemLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel18)
                        .addGap(297, 297, 297))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCadItemLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonCadastrarItem, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(278, 278, 278))
        );
        jPanelCadItemLayout.setVerticalGroup(
            jPanelCadItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCadItemLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonCadastrarItem, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanelCadItem, "cadItem");

        jPanelCadProduto.setBackground(new java.awt.Color(255, 255, 255));

        jButtonCadastrarProduto.setBackground(new java.awt.Color(51, 51, 51));
        jButtonCadastrarProduto.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButtonCadastrarProduto.setText("Finalizar cadastro");
        jButtonCadastrarProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCadastrarProdutoActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel16.setText("Cadastro de Produtos");

        jPanel6.setBackground(new java.awt.Color(73, 73, 73));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Selecionar marca", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(255, 255, 255))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Pesquisar");

        jTextFieldPesquisarMarca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldPesquisarMarcaKeyReleased(evt);
            }
        });

        jTableMarcasCadProduto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableMarcasCadProduto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableMarcasCadProdutoMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTableMarcasCadProduto);

        jButtonCadastrarMarca.setBackground(new java.awt.Color(255, 102, 102));
        jButtonCadastrarMarca.setForeground(new java.awt.Color(255, 255, 255));
        jButtonCadastrarMarca.setText("Cadastrar nova marca");
        jButtonCadastrarMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCadastrarMarcaActionPerformed(evt);
            }
        });

        jLabelMarcaSelecionada.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldPesquisarMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonCadastrarMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(130, 130, 130)
                        .addComponent(jLabelMarcaSelecionada)
                        .addGap(39, 39, 39)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 170, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldPesquisarMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonCadastrarMarca))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelMarcaSelecionada)
                        .addGap(44, 44, 44))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(26, Short.MAX_VALUE))))
        );

        jPanel3.setBackground(new java.awt.Color(73, 73, 73));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dados do produto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Nome:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldNomeProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldNomeProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelCadProdutoLayout = new javax.swing.GroupLayout(jPanelCadProduto);
        jPanelCadProduto.setLayout(jPanelCadProdutoLayout);
        jPanelCadProdutoLayout.setHorizontalGroup(
            jPanelCadProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCadProdutoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelCadProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCadProdutoLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanelCadProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCadProdutoLayout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(289, 289, 289))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCadProdutoLayout.createSequentialGroup()
                        .addComponent(jButtonCadastrarProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(280, 280, 280))))
        );
        jPanelCadProdutoLayout.setVerticalGroup(
            jPanelCadProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCadProdutoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonCadastrarProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(666, Short.MAX_VALUE))
        );

        jPanel1.add(jPanelCadProduto, "cadProduto");

        jPanelCadProdutoFLV.setBackground(new java.awt.Color(255, 255, 255));

        jPanel7.setBackground(new java.awt.Color(73, 73, 73));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informações do produto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(255, 255, 255))); // NOI18N

        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Nome:");

        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Código:");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldNomeProdutoFLV, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(87, 87, 87)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldCodigoProdutoFLV, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jTextFieldNomeProdutoFLV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21)
                    .addComponent(jTextFieldCodigoProdutoFLV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel11.setText("Cadastro de Produtos FLV (Frutas, Verduras e Legumes)");

        jButtonCadastrarProdutoFLV.setBackground(new java.awt.Color(51, 51, 51));
        jButtonCadastrarProdutoFLV.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButtonCadastrarProdutoFLV.setForeground(new java.awt.Color(255, 255, 255));
        jButtonCadastrarProdutoFLV.setText("Finalizar cadastro");
        jButtonCadastrarProdutoFLV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCadastrarProdutoFLVActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelCadProdutoFLVLayout = new javax.swing.GroupLayout(jPanelCadProdutoFLV);
        jPanelCadProdutoFLV.setLayout(jPanelCadProdutoFLVLayout);
        jPanelCadProdutoFLVLayout.setHorizontalGroup(
            jPanelCadProdutoFLVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCadProdutoFLVLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelCadProdutoFLVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCadProdutoFLVLayout.createSequentialGroup()
                        .addGap(0, 202, Short.MAX_VALUE)
                        .addComponent(jLabel11)
                        .addGap(118, 118, 118))
                    .addGroup(jPanelCadProdutoFLVLayout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
            .addGroup(jPanelCadProdutoFLVLayout.createSequentialGroup()
                .addGap(282, 282, 282)
                .addComponent(jButtonCadastrarProdutoFLV, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanelCadProdutoFLVLayout.setVerticalGroup(
            jPanelCadProdutoFLVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCadProdutoFLVLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addGap(29, 29, 29)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(jButtonCadastrarProdutoFLV, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(942, Short.MAX_VALUE))
        );

        jPanel1.add(jPanelCadProdutoFLV, "cadProdutoFLV");

        jPanelCadLote.setBackground(new java.awt.Color(255, 255, 255));

        jPanel10.setBackground(new java.awt.Color(73, 73, 73));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dados do Lote", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Registro:");

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("Quantidade de itens:");

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Data de validade:");

        try {
            jFormattedTextFieldValidadeLote.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFormattedTextFieldValidadeLote.setToolTipText("");

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Situação:");

        jComboBoxSituacaoLote.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Totalmente estocado", "Parcialmente estocado", "Em período de vencimento", "Inválido", "Descartado" }));

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("Data de chegada:");

        try {
            jFormattedTextFieldChegadaLote.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFormattedTextFieldChegadaLote.setText("    ");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(246, 246, 246)
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxSituacaoLote, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel31)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jFormattedTextFieldChegadaLote))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel25)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldRegistroLote, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSpinnerQuantidadeLote, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFormattedTextFieldValidadeLote, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 192, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldRegistroLote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25)
                    .addComponent(jLabel26)
                    .addComponent(jSpinnerQuantidadeLote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27)
                    .addComponent(jFormattedTextFieldValidadeLote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(jComboBoxSituacaoLote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31)
                    .addComponent(jFormattedTextFieldChegadaLote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButtonCadastrarLote.setBackground(new java.awt.Color(51, 51, 51));
        jButtonCadastrarLote.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButtonCadastrarLote.setForeground(new java.awt.Color(255, 255, 255));
        jButtonCadastrarLote.setText("Finalizar cadastro");
        jButtonCadastrarLote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCadastrarLoteActionPerformed(evt);
            }
        });

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel30.setText("Cadastro de Lote");

        jPanel8.setBackground(new java.awt.Color(73, 73, 73));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Selecionar Item", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(255, 255, 255))); // NOI18N

        jTextFieldPesquisarItensLote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldPesquisarItensLoteActionPerformed(evt);
            }
        });
        jTextFieldPesquisarItensLote.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldPesquisarItensLoteKeyReleased(evt);
            }
        });

        jLabel37.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setText("Pesquisar por:");

        jComboBoxTipoPesquisaItensLote.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nome", "Marca", "Código de Barras" }));

        jLabel38.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setText("Ordenar por:");

        jComboBoxOrdemPesquisaItensLote.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nome A-Z", "Nome Z-A", "Marca A-Z", "Marca Z-A" }));
        jComboBoxOrdemPesquisaItensLote.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxOrdemPesquisaItensLoteItemStateChanged(evt);
            }
        });

        jTableItensLote.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Código de barras", "Produto", "Marca", "Peso (Kg)", "Preço (R$)"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane8.setViewportView(jTableItensLote);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jTextFieldPesquisarItensLote)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel37)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxTipoPesquisaItensLote, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel38)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxOrdemPesquisaItensLote, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(143, 143, 143))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 781, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldPesquisarItensLote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37)
                    .addComponent(jComboBoxTipoPesquisaItensLote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel38)
                    .addComponent(jComboBoxOrdemPesquisaItensLote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelCadLoteLayout = new javax.swing.GroupLayout(jPanelCadLote);
        jPanelCadLote.setLayout(jPanelCadLoteLayout);
        jPanelCadLoteLayout.setHorizontalGroup(
            jPanelCadLoteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCadLoteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelCadLoteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCadLoteLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButtonCadastrarLote, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(276, 276, 276))
            .addGroup(jPanelCadLoteLayout.createSequentialGroup()
                .addGap(320, 320, 320)
                .addComponent(jLabel30)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanelCadLoteLayout.setVerticalGroup(
            jPanelCadLoteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCadLoteLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel30)
                .addGap(18, 18, 18)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonCadastrarLote, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(493, Short.MAX_VALUE))
        );

        jPanel1.add(jPanelCadLote, "cadLote");

        jPanelCadPedido.setBackground(new java.awt.Color(255, 255, 255));
        jPanelCadPedido.setPreferredSize(new java.awt.Dimension(833, 603));

        jLabel44.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel44.setText("NOVO PEDIDO");

        jButton5.setBackground(new java.awt.Color(51, 51, 51));
        jButton5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Adicionar Produtos");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jPanel22.setBackground(new java.awt.Color(73, 73, 73));
        jPanel22.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel46.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(255, 255, 255));
        jLabel46.setText("Fornercedor:");

        jLabel47.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(255, 255, 255));
        jLabel47.setText("Endereço:");

        jLabel48.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(255, 255, 255));
        jLabel48.setText("ID Do Pedido");

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addComponent(jLabel46)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldFornecerdor, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(jLabel47)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel48)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldIdPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(jTextFieldFornecerdor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel47)
                    .addComponent(jTextFieldEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel48)
                    .addComponent(jTextFieldIdPedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelCadPedidoLayout = new javax.swing.GroupLayout(jPanelCadPedido);
        jPanelCadPedido.setLayout(jPanelCadPedidoLayout);
        jPanelCadPedidoLayout.setHorizontalGroup(
            jPanelCadPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCadPedidoLayout.createSequentialGroup()
                .addGroup(jPanelCadPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelCadPedidoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanelCadPedidoLayout.createSequentialGroup()
                        .addGroup(jPanelCadPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelCadPedidoLayout.createSequentialGroup()
                                .addGap(342, 342, 342)
                                .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelCadPedidoLayout.createSequentialGroup()
                                .addGap(300, 300, 300)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelCadPedidoLayout.setVerticalGroup(
            jPanelCadPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCadPedidoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel44)
                .addGap(18, 18, 18)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(935, Short.MAX_VALUE))
        );

        jPanel1.add(jPanelCadPedido, "CadPedido");

        jPanelBaixaItem.setBackground(new java.awt.Color(255, 255, 255));

        jPanel5.setBackground(new java.awt.Color(73, 73, 73));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Selecionar Item", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12), new java.awt.Color(255, 255, 255))); // NOI18N

        jTextFieldValorPesquisaLoteBaixaItem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldValorPesquisaLoteBaixaItemKeyReleased(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Pesquisar por:");

        jComboBoxTipoPesquisaLoteBaixaItem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nome", "Marca", "Código de Barras", "Registro" }));

        jTableVisualizarLotesBaixaItem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Registro do Lote", "Produto", "Marca", "Quantidade", "Data de validade", "Código de Barras", "Situação"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableVisualizarLotesBaixaItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableVisualizarLotesBaixaItemMouseClicked(evt);
            }
        });
        jScrollPane12.setViewportView(jTableVisualizarLotesBaixaItem);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jTextFieldValorPesquisaLoteBaixaItem, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxTipoPesquisaLoteBaixaItem, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(368, Short.MAX_VALUE))
            .addComponent(jScrollPane12)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldValorPesquisaLoteBaixaItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jComboBoxTipoPesquisaLoteBaixaItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jButtonRegistrarBaixaItem.setBackground(new java.awt.Color(51, 51, 51));
        jButtonRegistrarBaixaItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButtonRegistrarBaixaItem.setForeground(new java.awt.Color(255, 255, 255));
        jButtonRegistrarBaixaItem.setText("Registrar baixa");
        jButtonRegistrarBaixaItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRegistrarBaixaItemActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel17.setText("Registro de Baixa em Item");

        jPanel23.setBackground(new java.awt.Color(73, 73, 73));
        jPanel23.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Quantidade da baixa:");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Motivo da baixa:");

        jComboBoxMotivoBaixaItem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Vencimento", "Avaria", "Uso", "Outro" }));

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSpinnerQtdBaixaItem, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxMotivoBaixaItem, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jSpinnerQtdBaixaItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(jComboBoxMotivoBaixaItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelBaixaItemLayout = new javax.swing.GroupLayout(jPanelBaixaItem);
        jPanelBaixaItem.setLayout(jPanelBaixaItemLayout);
        jPanelBaixaItemLayout.setHorizontalGroup(
            jPanelBaixaItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBaixaItemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelBaixaItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBaixaItemLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanelBaixaItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBaixaItemLayout.createSequentialGroup()
                                .addComponent(jButtonRegistrarBaixaItem, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(240, 240, 240))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBaixaItemLayout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addGap(260, 260, 260))))
                    .addGroup(jPanelBaixaItemLayout.createSequentialGroup()
                        .addGroup(jPanelBaixaItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        jPanelBaixaItemLayout.setVerticalGroup(
            jPanelBaixaItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBaixaItemLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonRegistrarBaixaItem, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(568, Short.MAX_VALUE))
        );

        jPanel1.add(jPanelBaixaItem, "baixaItem");

        jComboBoxMotivoBaixaLote.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Vencimento", "Avaria", "Uso", "Outro" }));

        jLabel41.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(204, 0, 0));
        jLabel41.setText("Registrar novo motivo");

        jPanel15.setBackground(new java.awt.Color(204, 234, 242));
        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Selecionar Item", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jTextFieldValorPesquisaLoteBaixaLote.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldValorPesquisaLoteBaixaLoteKeyReleased(evt);
            }
        });

        jLabel42.setText("Pesquisar por:");

        jComboBoxTipoPesquisaLoteBaixaLote.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nome", "Marca", "Código de Barras", "Registro" }));

        jTableVisualizarLotesBaixaLote.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Registro do Lote", "Produto", "Marca", "Quantidade", "Data de validade", "Código de Barras", "Situação"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableVisualizarLotesBaixaLote.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableVisualizarLotesBaixaLoteMouseClicked(evt);
            }
        });
        jScrollPane13.setViewportView(jTableVisualizarLotesBaixaLote);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(jTextFieldValorPesquisaLoteBaixaLote, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel42)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxTipoPesquisaLoteBaixaLote, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(375, Short.MAX_VALUE))
            .addComponent(jScrollPane13)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldValorPesquisaLoteBaixaLote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42)
                    .addComponent(jComboBoxTipoPesquisaLoteBaixaLote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jButtonRegistrarBaixaLote.setText("Registrar baixa");
        jButtonRegistrarBaixaLote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRegistrarBaixaLoteActionPerformed(evt);
            }
        });

        jLabel43.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel43.setText("Registro de Baixa em Lote Inteiro");

        jLabel45.setText("Motivo da baixa:");

        javax.swing.GroupLayout jPanelBaixaLoteLayout = new javax.swing.GroupLayout(jPanelBaixaLote);
        jPanelBaixaLote.setLayout(jPanelBaixaLoteLayout);
        jPanelBaixaLoteLayout.setHorizontalGroup(
            jPanelBaixaLoteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBaixaLoteLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel43)
                .addGap(295, 295, 295))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBaixaLoteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBaixaLoteLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonRegistrarBaixaLote, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(259, 259, 259))
            .addGroup(jPanelBaixaLoteLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel45)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxMotivoBaixaLote, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel41)
                .addContainerGap())
        );
        jPanelBaixaLoteLayout.setVerticalGroup(
            jPanelBaixaLoteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBaixaLoteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel43)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanelBaixaLoteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45)
                    .addComponent(jComboBoxMotivoBaixaLote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41))
                .addGap(28, 28, 28)
                .addComponent(jButtonRegistrarBaixaLote, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(602, Short.MAX_VALUE))
        );

        jPanel15.getAccessibleContext().setAccessibleName("Selecionar Lote");

        jPanel1.add(jPanelBaixaLote, "baixaLote");

        jPanelVisualizarHistoricoBaixas.setBackground(new java.awt.Color(255, 255, 255));
        jPanelVisualizarHistoricoBaixas.setPreferredSize(new java.awt.Dimension(790, 603));

        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel39.setText("Histórico de Baixas");

        jTableVisualizarHistoricoBaixas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Produto", "Marca", "Quantidade", "Data", "Motivo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTableVisualizarHistoricoBaixas);

        jPanel14.setBackground(new java.awt.Color(73, 73, 73));
        jPanel14.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel40.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setText("Pesquisar por motivo:");

        jComboBoxTipoPesquisaBaixa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos os motivos", "Vencimento", "Avaria", "Uso", "Outros" }));
        jComboBoxTipoPesquisaBaixa.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxTipoPesquisaBaixaItemStateChanged(evt);
            }
        });
        jComboBoxTipoPesquisaBaixa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxTipoPesquisaBaixaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel40)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxTipoPesquisaBaixa, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(jComboBoxTipoPesquisaBaixa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelVisualizarHistoricoBaixasLayout = new javax.swing.GroupLayout(jPanelVisualizarHistoricoBaixas);
        jPanelVisualizarHistoricoBaixas.setLayout(jPanelVisualizarHistoricoBaixasLayout);
        jPanelVisualizarHistoricoBaixasLayout.setHorizontalGroup(
            jPanelVisualizarHistoricoBaixasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelVisualizarHistoricoBaixasLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel39)
                .addGap(296, 296, 296))
            .addGroup(jPanelVisualizarHistoricoBaixasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelVisualizarHistoricoBaixasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 813, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelVisualizarHistoricoBaixasLayout.setVerticalGroup(
            jPanelVisualizarHistoricoBaixasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelVisualizarHistoricoBaixasLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel39)
                .addGap(20, 20, 20)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 487, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(511, 511, 511))
        );

        jPanel1.add(jPanelVisualizarHistoricoBaixas, "historicoBaixas");

        jPanelAtualizarProduto.setBackground(new java.awt.Color(255, 255, 255));

        jLabel58.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel58.setText("Atualizar Dados do Produto");

        jPanel16.setBackground(new java.awt.Color(73, 73, 73));
        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Escolher Produto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(255, 255, 255))); // NOI18N

        jTableProdutosAtualizar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome", "Marca"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableProdutosAtualizar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableProdutosAtualizarMouseClicked(evt);
            }
        });
        jScrollPane15.setViewportView(jTableProdutosAtualizar);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 781, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jButtonSalvarAtualizacoesProduto.setBackground(new java.awt.Color(51, 51, 51));
        jButtonSalvarAtualizacoesProduto.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButtonSalvarAtualizacoesProduto.setForeground(new java.awt.Color(255, 255, 255));
        jButtonSalvarAtualizacoesProduto.setText("Salvar Atualizações");
        jButtonSalvarAtualizacoesProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSalvarAtualizacoesProdutoActionPerformed(evt);
            }
        });

        jPanel24.setBackground(new java.awt.Color(73, 73, 73));
        jPanel24.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel59.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(255, 255, 255));
        jLabel59.setText("Nome:");

        jButtonExcluirProduto.setBackground(new java.awt.Color(255, 0, 0));
        jButtonExcluirProduto.setForeground(new java.awt.Color(255, 255, 255));
        jButtonExcluirProduto.setText("Excluir Produto");
        jButtonExcluirProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExcluirProdutoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel59)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldNomeAtualizarProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonExcluirProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldNomeAtualizarProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel59)
                    .addComponent(jButtonExcluirProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelAtualizarProdutoLayout = new javax.swing.GroupLayout(jPanelAtualizarProduto);
        jPanelAtualizarProduto.setLayout(jPanelAtualizarProdutoLayout);
        jPanelAtualizarProdutoLayout.setHorizontalGroup(
            jPanelAtualizarProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAtualizarProdutoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelAtualizarProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAtualizarProdutoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelAtualizarProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAtualizarProdutoLayout.createSequentialGroup()
                        .addComponent(jLabel58)
                        .addGap(241, 241, 241))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAtualizarProdutoLayout.createSequentialGroup()
                        .addComponent(jButtonSalvarAtualizacoesProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(278, 278, 278))))
        );
        jPanelAtualizarProdutoLayout.setVerticalGroup(
            jPanelAtualizarProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAtualizarProdutoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel58)
                .addGap(18, 18, 18)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonSalvarAtualizacoesProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(584, Short.MAX_VALUE))
        );

        jPanel1.add(jPanelAtualizarProduto, "atualizarProduto");

        jPanelAtualizarProdutoFLV.setBackground(new java.awt.Color(255, 255, 255));

        jButtonSalvarAtualizacoesProdutoFLV.setBackground(new java.awt.Color(73, 73, 73));
        jButtonSalvarAtualizacoesProdutoFLV.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButtonSalvarAtualizacoesProdutoFLV.setForeground(new java.awt.Color(255, 255, 255));
        jButtonSalvarAtualizacoesProdutoFLV.setText("Salvar Atualizações");
        jButtonSalvarAtualizacoesProdutoFLV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSalvarAtualizacoesProdutoFLVActionPerformed(evt);
            }
        });

        jLabel61.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel61.setText("Atualizar Dados do Produto FLV");

        jPanel17.setBackground(new java.awt.Color(73, 73, 73));
        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Escolher Produto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel17.setForeground(new java.awt.Color(73, 73, 73));

        jTableProdutosFLVAtualizar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome", "Código"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableProdutosFLVAtualizar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableProdutosFLVAtualizarMouseClicked(evt);
            }
        });
        jScrollPane16.setViewportView(jTableProdutosFLVAtualizar);

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane16, javax.swing.GroupLayout.DEFAULT_SIZE, 781, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        jPanel25.setBackground(new java.awt.Color(73, 73, 73));
        jPanel25.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel60.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(255, 255, 255));
        jLabel60.setText("Nome:");

        jButtonExcluirProdutoFLV.setBackground(new java.awt.Color(255, 0, 0));
        jButtonExcluirProdutoFLV.setForeground(new java.awt.Color(255, 255, 255));
        jButtonExcluirProdutoFLV.setText("Excluir Produto FLV");
        jButtonExcluirProdutoFLV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExcluirProdutoFLVActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel60)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldNomeAtualizarProdutoFLV, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonExcluirProdutoFLV, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldNomeAtualizarProdutoFLV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel60)
                    .addComponent(jButtonExcluirProdutoFLV, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelAtualizarProdutoFLVLayout = new javax.swing.GroupLayout(jPanelAtualizarProdutoFLV);
        jPanelAtualizarProdutoFLV.setLayout(jPanelAtualizarProdutoFLVLayout);
        jPanelAtualizarProdutoFLVLayout.setHorizontalGroup(
            jPanelAtualizarProdutoFLVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAtualizarProdutoFLVLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelAtualizarProdutoFLVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAtualizarProdutoFLVLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanelAtualizarProdutoFLVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAtualizarProdutoFLVLayout.createSequentialGroup()
                                .addComponent(jButtonSalvarAtualizacoesProdutoFLV, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(281, 281, 281))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAtualizarProdutoFLVLayout.createSequentialGroup()
                                .addComponent(jLabel61)
                                .addGap(236, 236, 236))))
                    .addGroup(jPanelAtualizarProdutoFLVLayout.createSequentialGroup()
                        .addGroup(jPanelAtualizarProdutoFLVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        jPanelAtualizarProdutoFLVLayout.setVerticalGroup(
            jPanelAtualizarProdutoFLVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAtualizarProdutoFLVLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonSalvarAtualizacoesProdutoFLV, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(584, Short.MAX_VALUE))
        );

        jPanel1.add(jPanelAtualizarProdutoFLV, "atualizarProdutoFLV");

        jPanelAtualizarItem.setBackground(new java.awt.Color(255, 255, 255));

        jButtonSalvarAtualizacoesItem.setBackground(new java.awt.Color(51, 51, 51));
        jButtonSalvarAtualizacoesItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButtonSalvarAtualizacoesItem.setForeground(new java.awt.Color(255, 255, 255));
        jButtonSalvarAtualizacoesItem.setText("Salvar Atualizações");
        jButtonSalvarAtualizacoesItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSalvarAtualizacoesItemActionPerformed(evt);
            }
        });

        jLabel62.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel62.setText("Atualizar Dados do Item");

        jPanel18.setBackground(new java.awt.Color(73, 73, 73));
        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Escolher Produto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(255, 255, 255))); // NOI18N

        jTableItensAtualizar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome", "Marca", "Peso (Kg)", "Preço", "Código de Barras"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableItensAtualizar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableItensAtualizarMouseClicked(evt);
            }
        });
        jScrollPane17.setViewportView(jTableItensAtualizar);

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane17, javax.swing.GroupLayout.DEFAULT_SIZE, 781, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel26.setBackground(new java.awt.Color(73, 73, 73));
        jPanel26.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel63.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(255, 255, 255));
        jLabel63.setText("Peso (Kg):");

        jLabel64.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel64.setForeground(new java.awt.Color(255, 255, 255));
        jLabel64.setText("Preço:");

        jButtonExcluirItem.setBackground(new java.awt.Color(255, 0, 0));
        jButtonExcluirItem.setForeground(new java.awt.Color(255, 255, 255));
        jButtonExcluirItem.setText("Excluir Item");
        jButtonExcluirItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExcluirItemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel63)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldPesoAtualizarItem, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel64)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldPrecoAtualizarItem, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonExcluirItem, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel63)
                    .addComponent(jTextFieldPesoAtualizarItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel64)
                    .addComponent(jTextFieldPrecoAtualizarItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonExcluirItem, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelAtualizarItemLayout = new javax.swing.GroupLayout(jPanelAtualizarItem);
        jPanelAtualizarItem.setLayout(jPanelAtualizarItemLayout);
        jPanelAtualizarItemLayout.setHorizontalGroup(
            jPanelAtualizarItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAtualizarItemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelAtualizarItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAtualizarItemLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelAtualizarItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAtualizarItemLayout.createSequentialGroup()
                        .addComponent(jButtonSalvarAtualizacoesItem, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(265, 265, 265))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAtualizarItemLayout.createSequentialGroup()
                        .addComponent(jLabel62)
                        .addGap(276, 276, 276))))
        );
        jPanelAtualizarItemLayout.setVerticalGroup(
            jPanelAtualizarItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAtualizarItemLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel62)
                .addGap(18, 18, 18)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonSalvarAtualizacoesItem, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(584, Short.MAX_VALUE))
        );

        jPanel1.add(jPanelAtualizarItem, "atualizarItem");

        jPanelAtualizarLote.setBackground(new java.awt.Color(255, 255, 255));

        jButtonSalvarAtualizacoesLote.setBackground(new java.awt.Color(51, 51, 51));
        jButtonSalvarAtualizacoesLote.setForeground(new java.awt.Color(255, 255, 255));
        jButtonSalvarAtualizacoesLote.setText("Salvar Atualizações");
        jButtonSalvarAtualizacoesLote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSalvarAtualizacoesLoteActionPerformed(evt);
            }
        });

        jLabel65.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel65.setText("Atualizar Dados do Lote");

        jPanel19.setBackground(new java.awt.Color(73, 73, 73));
        jPanel19.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Escolher Produto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(255, 255, 255))); // NOI18N

        jTableLotesAtualizar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Registro", "Nome", "Marca", "Quantidade", "Validade", "Chegada", "Situação"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableLotesAtualizar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableLotesAtualizarMouseClicked(evt);
            }
        });
        jScrollPane18.setViewportView(jTableLotesAtualizar);

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane18)
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel27.setBackground(new java.awt.Color(73, 73, 73));
        jPanel27.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel66.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel66.setForeground(new java.awt.Color(255, 255, 255));
        jLabel66.setText("Situação:");

        jComboBoxSituacaoAtualizarLote.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Totalmente estocado", "Parcialmente estocado", "Em período de vencimento", "Inválido", "Descartado" }));

        jButtonExcluirLote.setBackground(new java.awt.Color(255, 0, 0));
        jButtonExcluirLote.setForeground(new java.awt.Color(255, 255, 255));
        jButtonExcluirLote.setText("Excluir Lote");
        jButtonExcluirLote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExcluirLoteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel66)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxSituacaoAtualizarLote, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonExcluirLote, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel66)
                    .addComponent(jComboBoxSituacaoAtualizarLote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonExcluirLote, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelAtualizarLoteLayout = new javax.swing.GroupLayout(jPanelAtualizarLote);
        jPanelAtualizarLote.setLayout(jPanelAtualizarLoteLayout);
        jPanelAtualizarLoteLayout.setHorizontalGroup(
            jPanelAtualizarLoteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAtualizarLoteLayout.createSequentialGroup()
                .addGap(312, 312, 312)
                .addComponent(jLabel65)
                .addContainerGap(309, Short.MAX_VALUE))
            .addGroup(jPanelAtualizarLoteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAtualizarLoteLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonSalvarAtualizacoesLote, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(270, 270, 270))
            .addGroup(jPanelAtualizarLoteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelAtualizarLoteLayout.setVerticalGroup(
            jPanelAtualizarLoteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAtualizarLoteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel65)
                .addGap(18, 18, 18)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonSalvarAtualizacoesLote, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(584, Short.MAX_VALUE))
        );

        jPanel1.add(jPanelAtualizarLote, "atualizarLote");

        jPanelUsuarios.setBackground(new java.awt.Color(255, 255, 255));

        jLabel50.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel50.setText("Usuarios");

        jPanel28.setBackground(new java.awt.Color(73, 73, 73));
        jPanel28.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Todos os Usuários", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(255, 255, 255))); // NOI18N

        jTableUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Usuario", "Nome", "Sobrenome"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableUsuariosMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTableUsuarios);

        jButton1.setBackground(new java.awt.Color(255, 0, 0));
        jButton1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Cadastrar novo usuário");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel28Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel29.setBackground(new java.awt.Color(73, 73, 73));
        jPanel29.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel70.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel70.setForeground(new java.awt.Color(255, 255, 255));
        jLabel70.setText("CPF");

        jTextFieldCpf.setEditable(false);

        jLabel68.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel68.setForeground(new java.awt.Color(255, 255, 255));
        jLabel68.setText("Nome");

        jTextFieldNomeUser.setEditable(false);

        jLabel69.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel69.setForeground(new java.awt.Color(255, 255, 255));
        jLabel69.setText("Sobrenome");

        jTextFieldSobrenome.setEditable(false);

        jLabel67.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel67.setForeground(new java.awt.Color(255, 255, 255));
        jLabel67.setText("Usuário");

        jTextFieldUsuario.setEditable(false);

        jLabel71.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel71.setForeground(new java.awt.Color(255, 255, 255));
        jLabel71.setText("Nova Senha");

        jButton3.setBackground(new java.awt.Color(255, 0, 0));
        jButton3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Atualizar Senha");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addComponent(jLabel68)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldNomeUser, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel69)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldSobrenome, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel29Layout.createSequentialGroup()
                        .addComponent(jLabel71)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPasswordFieldSenhaUser, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jLabel70)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldCpf, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jLabel67)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel70)
                    .addComponent(jTextFieldCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel68)
                    .addComponent(jTextFieldNomeUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel69)
                    .addComponent(jTextFieldSobrenome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel67)
                    .addComponent(jTextFieldUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel71)
                    .addComponent(jPasswordFieldSenhaUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelUsuariosLayout = new javax.swing.GroupLayout(jPanelUsuarios);
        jPanelUsuarios.setLayout(jPanelUsuariosLayout);
        jPanelUsuariosLayout.setHorizontalGroup(
            jPanelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelUsuariosLayout.createSequentialGroup()
                .addGap(336, 336, 336)
                .addComponent(jLabel50)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanelUsuariosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelUsuariosLayout.setVerticalGroup(
            jPanelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelUsuariosLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel50)
                .addGap(18, 18, 18)
                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(683, Short.MAX_VALUE))
        );

        jPanel1.add(jPanelUsuarios, "JPainelUser");

        jPanelVisualizarProdutos.setBackground(new java.awt.Color(255, 255, 255));

        jTableVisualizarProdutos.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTableVisualizarProdutos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nome", "Marca"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTableVisualizarProdutos);

        jPanel2.setBackground(new java.awt.Color(73, 73, 73));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Filtro de Pesquisa", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.setName(""); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Pesquisar por:");

        jComboBoxTipoPesquisaProduto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nome", "Marca" }));
        jComboBoxTipoPesquisaProduto.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxTipoPesquisaProdutoItemStateChanged(evt);
            }
        });

        jTextFieldPesquisarProdutos.setToolTipText("Digite aqui etc");
        jTextFieldPesquisarProdutos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldPesquisarProdutosKeyReleased(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Ordenar por:");

        jComboBoxOrdemPesquisaProduto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nome A-Z", "Nome Z-A", "Marca A-Z", "Marca Z-A" }));
        jComboBoxOrdemPesquisaProduto.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxOrdemPesquisaProdutoItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextFieldPesquisarProdutos, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxTipoPesquisaProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxOrdemPesquisaProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(149, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jComboBoxTipoPesquisaProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldPesquisarProdutos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jComboBoxOrdemPesquisaProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel19.setText("Visualizar Produtos");

        javax.swing.GroupLayout jPanelVisualizarProdutosLayout = new javax.swing.GroupLayout(jPanelVisualizarProdutos);
        jPanelVisualizarProdutos.setLayout(jPanelVisualizarProdutosLayout);
        jPanelVisualizarProdutosLayout.setHorizontalGroup(
            jPanelVisualizarProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelVisualizarProdutosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelVisualizarProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelVisualizarProdutosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel19)
                .addGap(302, 302, 302))
        );
        jPanelVisualizarProdutosLayout.setVerticalGroup(
            jPanelVisualizarProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelVisualizarProdutosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(632, Short.MAX_VALUE))
        );

        jPanel1.add(jPanelVisualizarProdutos, "visualizarProdutos");

        jMenu3.setText("Visualizar");

        jMenu8.setText("Visualizar Produtos");

        jMenuItemVisualizarProdutos.setText("Visualizar Produtos Comuns");
        jMenuItemVisualizarProdutos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemVisualizarProdutosActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItemVisualizarProdutos);

        jMenuItemVisualizarProdutosFLV.setText("Visualizar Produtos FLV");
        jMenuItemVisualizarProdutosFLV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemVisualizarProdutosFLVActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItemVisualizarProdutosFLV);

        jMenu3.add(jMenu8);

        jMenuItemVisualizarLote.setText("Visualizar Lotes");
        jMenuItemVisualizarLote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemVisualizarLoteActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItemVisualizarLote);

        jMenuVisualizarItens.setText("Visualizar Itens");
        jMenuVisualizarItens.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuVisualizarItensActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuVisualizarItens);

        jMenuItem1.setText("Visualizar Informações do Mercado");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem1);

        jMenuBar1.add(jMenu3);

        jMenu1.setText("Cadastrar");

        jMenu4.setText("Cadastrar Produto");

        jMenuItemCadProduto.setText("Cadastrar Produto Comum");
        jMenuItemCadProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCadProdutoActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItemCadProduto);

        jMenuItemCadProdutoFLV.setText("Cadastrar Produto FLV");
        jMenuItemCadProdutoFLV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCadProdutoFLVActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItemCadProdutoFLV);

        jMenu1.add(jMenu4);

        jMenuItemCadLote.setText("Cadastrar Lote");
        jMenuItemCadLote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCadLoteActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemCadLote);

        jMenuItemCadItem.setText("Cadastrar Item");
        jMenuItemCadItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCadItemActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemCadItem);

        jMenuBar1.add(jMenu1);

        jMenu6.setText("Atualizar");

        jMenu9.setText("Atualizar Produto");

        jMenuItemAtualizarPedido.setText("Atualizar Produto Comum");
        jMenuItemAtualizarPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAtualizarPedidoActionPerformed(evt);
            }
        });
        jMenu9.add(jMenuItemAtualizarPedido);

        jMenuItemAtualizarProdutoFLV.setText("Atualizar Produto FLV");
        jMenuItemAtualizarProdutoFLV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAtualizarProdutoFLVActionPerformed(evt);
            }
        });
        jMenu9.add(jMenuItemAtualizarProdutoFLV);

        jMenu6.add(jMenu9);

        jMenuItemAtualizarItem.setText("Atualizar Item");
        jMenuItemAtualizarItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAtualizarItemActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItemAtualizarItem);

        jMenuItemAtualizarLote.setText("Atualizar Lote");
        jMenuItemAtualizarLote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAtualizarLoteActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItemAtualizarLote);
        jMenu6.add(jSeparator2);

        jMenuItem16.setText("Atualizar Informações do Mercado");
        jMenu6.add(jMenuItem16);

        jMenuBar1.add(jMenu6);

        jMenu2.setText("Dar baixa");

        jMenuItemBaixaItem.setText("Dar Baixa em Item");
        jMenuItemBaixaItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemBaixaItemActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItemBaixaItem);

        jMenuItemBaixaLote.setText("Dar Baixa em Lote");
        jMenuItemBaixaLote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemBaixaLoteActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItemBaixaLote);
        jMenu2.add(jSeparator1);

        jMenuItemVisualizarHistoricoBaixas.setText("Visualizar Histórico de Baixas");
        jMenuItemVisualizarHistoricoBaixas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemVisualizarHistoricoBaixasActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItemVisualizarHistoricoBaixas);

        jMenuBar1.add(jMenu2);

        jMenu5.setText("Pedidos");

        jMenuItem7.setText("Cadastrar");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem7);

        jMenuItem8.setText("Visualizar");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem8);

        jMenuBar1.add(jMenu5);

        jMenu7.setText("Usuário");

        jMenuItem14.setText("Gerenciar Usuários");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem14);
        jMenu7.add(jSeparator3);

        jMenuItem15.setText("Sair");
        jMenu7.add(jMenuItem15);

        jMenuBar1.add(jMenu7);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getAccessibleContext().setAccessibleName("EasyEstoque");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItemCadItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemCadItemActionPerformed
        try {
            // TODO add your handling code here:
            atualizarTabelaVisualizarProdutosItem();
            CardLayout cl = (CardLayout) jPanel1.getLayout();
            cl.show(jPanel1, "cadItem");
        } catch (Exception ex) {
            Logger.getLogger(JFrameCardLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItemCadItemActionPerformed

    private void jMenuItemCadProdutoFLVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemCadProdutoFLVActionPerformed
        // TODO add your handling code here:
        CardLayout cl = (CardLayout) jPanel1.getLayout();
        cl.show(jPanel1, "cadProdutoFLV");
    }//GEN-LAST:event_jMenuItemCadProdutoFLVActionPerformed

    private void jMenuItemCadProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemCadProdutoActionPerformed
        try {
            // TODO add your handling code here:
            atualizarTabelaMarcasCadProduto();
            CardLayout cl = (CardLayout) jPanel1.getLayout();
            cl.show(jPanel1, "cadProduto");
        } catch (Exception ex) {
            Logger.getLogger(JFrameCardLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItemCadProdutoActionPerformed

    private void jButtonCadastrarProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCadastrarProdutoActionPerformed
        // TODO add your handling code here:
        try {
            if (jTextFieldNomeProduto.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
            } else {
                produto.nome = jTextFieldNomeProduto.getText().trim();

                int linhaSelecionada = jTableMarcasCadProduto.getSelectedRow();

                if (linhaSelecionada >= 0) {
                    DefaultTableModel dtm = (DefaultTableModel) jTableMarcasCadProduto.getModel();

                    marca.id = (NitriteId) dtm.getValueAt(linhaSelecionada, 0);

                    marca = BaseDados.rMarcas.getById(marca.id);

                    produto.marca = marca;
                    
                    
                        if (verificarProdutoVazio(produto) == true) {
                            cadProduto.adicionar(produto);

                            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
                            jTextFieldNomeProduto.setText("");
                            jTableMarcasCadProduto.clearSelection();
                            jLabelMarcaSelecionada.setText("");
                            produto = new Produto();
                            marca = new Marca();
                        }else{
                            JOptionPane.showMessageDialog(null, "Esse produto já existe!");
                            jTextFieldNomeProduto.setText("");
                            jTableMarcasCadProduto.clearSelection();
                            jLabelMarcaSelecionada.setText("");
                            produto = new Produto();
                            marca = new Marca();
                        }
                    

                } else {
                    JOptionPane.showMessageDialog(null, "Selecione uma marca para cadastrar o produto!");
                }
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "ERRO AO CADASTRAR PRODUTO \n" + ex.getMessage());
        }
    }//GEN-LAST:event_jButtonCadastrarProdutoActionPerformed

    private void jMenuItemBaixaItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemBaixaItemActionPerformed
        try {
            atualizarTabelaVisualizarLotesBaixaItem();
            CardLayout cl = (CardLayout) jPanel1.getLayout();
            cl.show(jPanel1, "baixaItem");
        } catch (Exception ex) {
            Logger.getLogger(JFrameCardLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItemBaixaItemActionPerformed

    private void jButtonCadastrarMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCadastrarMarcaActionPerformed
        // TODO add your handling code here:
        try {
            String nomeMarca = JOptionPane.showInputDialog("Digite o nome da nova marca:").trim();
            
            marca.marca = nomeMarca;
            
            boolean marcaVazia = verificarMarcaVazia(marca);
            
            if (marcaVazia == true) {
                //Verifica se existe uma marca parecida
                List<Marca> marcasCadastradas = BaseDados.rMarcas.find(ObjectFilters.regex("marca", ".*(?i)(" + marca.marca + ").*")).toList();

                if (!(marcasCadastradas.isEmpty())) {
                    int resposta = JOptionPane.showConfirmDialog(null, "Já existe uma marca com nome parecido!\n\n" + marcasCadastradas + "\n\nDeseja cadastrar mesmo assim ?");
                    if (resposta == 0) {
                        CadMarca cadMarca = new CadMarcaImpl();
                        cadMarca.adicionar(marca);
                        JOptionPane.showMessageDialog(null, "Marca cadastrada com sucesso!");
                        atualizarTabelaMarcasCadProduto();
                        marca = new Marca();
                    }
                } else {
                    CadMarca cadMarca = new CadMarcaImpl();
                    cadMarca.adicionar(marca);
                    JOptionPane.showMessageDialog(null, "Marca cadastrada com sucesso!");
                    atualizarTabelaMarcasCadProduto();
                    marca = new Marca();
                }

            } else {
                JOptionPane.showMessageDialog(null, "Essa marca já existe!");
                marca = new Marca();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "ERRO AO CADASTRAR MARCA \n" + ex.getMessage());
            marca = new Marca();
        }
    }//GEN-LAST:event_jButtonCadastrarMarcaActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
//        BaseDados.iniciaDados();
        atualizarTabelaPedidos();
        
    }//GEN-LAST:event_formWindowOpened

    private void jTextFieldPesquisarMarcaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPesquisarMarcaKeyReleased
        try {
            // TODO add your handling code here:
            pesquisarMarcas(jTextFieldPesquisarMarca.getText());
            jLabelMarcaSelecionada.setText("");
        } catch (Exception ex) {
            Logger.getLogger(JFrameCardLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTextFieldPesquisarMarcaKeyReleased

    private void jTextFieldPesquisarProdutosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPesquisarProdutosKeyReleased
        try {
            // TODO add your handling code here:
            pesquisarProdutos(jTextFieldPesquisarProdutos.getText(),
                    jComboBoxTipoPesquisaProduto.getSelectedItem().toString(),
                    jComboBoxOrdemPesquisaProduto.getSelectedItem().toString());
        } catch (Exception ex) {
            Logger.getLogger(JFrameCardLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTextFieldPesquisarProdutosKeyReleased

    private void jComboBoxOrdemPesquisaProdutoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxOrdemPesquisaProdutoItemStateChanged
        try {
            // TODO add your handling code here:
            pesquisarProdutos(jTextFieldPesquisarProdutos.getText(),
                    jComboBoxTipoPesquisaProduto.getSelectedItem().toString(),
                    jComboBoxOrdemPesquisaProduto.getSelectedItem().toString());
        } catch (Exception ex) {
            Logger.getLogger(JFrameCardLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jComboBoxOrdemPesquisaProdutoItemStateChanged

    private void jComboBoxTipoPesquisaProdutoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxTipoPesquisaProdutoItemStateChanged
        try {
            // TODO add your handling code here:
            pesquisarProdutos(jTextFieldPesquisarProdutos.getText(),
                    jComboBoxTipoPesquisaProduto.getSelectedItem().toString(),
                    jComboBoxOrdemPesquisaProduto.getSelectedItem().toString());
        } catch (Exception ex) {
            Logger.getLogger(JFrameCardLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jComboBoxTipoPesquisaProdutoItemStateChanged

    private void jButtonCadastrarProdutoFLVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCadastrarProdutoFLVActionPerformed
        // TODO add your handling code here:
        try {
            if (jTextFieldNomeProdutoFLV.getText().trim().equals("") || jTextFieldCodigoProdutoFLV.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
            } else {
                produtoFLV.nome = jTextFieldNomeProdutoFLV.getText().trim();
                produtoFLV.codigo = jTextFieldCodigoProdutoFLV.getText().trim();

                if (verificarProdutoFLVVazio(produtoFLV) == true) {
                    if (verificarProdutoFLVCaseInsensitive(produtoFLV) == true) {
                        CadProdutoFLV cadProdutoFLV = new CadProdutoFLVImpl();
                        cadProdutoFLV.adicionar(produtoFLV);

                        JOptionPane.showMessageDialog(null, "Produto FLV cadastrado com sucesso!");
                        jTextFieldNomeProdutoFLV.setText("");
                        jTextFieldCodigoProdutoFLV.setText("");
                        produtoFLV = new ProdutoFLV();
                    } else {
                        JOptionPane.showMessageDialog(null, "Já existe um produto com esse mesmo nome!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Já existe um produto com esse mesmo código!");
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "ERRO AO CADASTRAR PRODUTO FLV\n" + ex.getMessage());
        }
    }//GEN-LAST:event_jButtonCadastrarProdutoFLVActionPerformed

    private void jMenuItemVisualizarProdutosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemVisualizarProdutosActionPerformed
        try {
            // TODO add your handling code here:
            atualizarTabelaVisualizarProdutos();
            CardLayout cl = (CardLayout) jPanel1.getLayout();
            cl.show(jPanel1, "visualizarProdutos");
        } catch (Exception ex) {
            Logger.getLogger(JFrameCardLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItemVisualizarProdutosActionPerformed

    private void jComboBoxTipoPesquisaProdutoFLVItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxTipoPesquisaProdutoFLVItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxTipoPesquisaProdutoFLVItemStateChanged

    private void jTextFieldPesquisarProdutosFLVKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPesquisarProdutosFLVKeyReleased
        try {
            // TODO add your handling code here:
            pesquisarProdutosFLV(jTextFieldPesquisarProdutosFLV.getText(),
                    jComboBoxTipoPesquisaProdutoFLV.getSelectedItem().toString());
        } catch (Exception ex) {
            Logger.getLogger(JFrameCardLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTextFieldPesquisarProdutosFLVKeyReleased

    private void jMenuItemVisualizarProdutosFLVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemVisualizarProdutosFLVActionPerformed
        try {
            // TODO add your handling code here:
            atualizarTabelaVisualizarProdutosFLV();
            CardLayout cl = (CardLayout) jPanel1.getLayout();
            cl.show(jPanel1, "visualizarProdutosFLV");
        } catch (Exception ex) {
            Logger.getLogger(JFrameCardLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItemVisualizarProdutosFLVActionPerformed

    private void jTableMarcasCadProdutoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableMarcasCadProdutoMouseClicked
        // TODO add your handling code here:
        int linhaSelecionada = jTableMarcasCadProduto.getSelectedRow();
        DefaultTableModel dtm = (DefaultTableModel) jTableMarcasCadProduto.getModel();
        jLabelMarcaSelecionada.setText(dtm.getValueAt(linhaSelecionada, 1).toString());
    }//GEN-LAST:event_jTableMarcasCadProdutoMouseClicked

    private void jComboBoxTipoPesquisaLoteItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxTipoPesquisaLoteItemStateChanged
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            pesquisarLotes(jTextFieldPesquisarLotes.getText(),
                    jComboBoxTipoPesquisaLote.getSelectedItem().toString(),
                    jComboBoxOrdemPesquisaLote.getSelectedItem().toString());
        } catch (Exception ex) {
            Logger.getLogger(JFrameCardLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jComboBoxTipoPesquisaLoteItemStateChanged

    private void jTextFieldPesquisarLotesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPesquisarLotesKeyReleased
        try {
            // TODO add your handling code here:
            pesquisarLotes(jTextFieldPesquisarLotes.getText(),
                    jComboBoxTipoPesquisaLote.getSelectedItem().toString(),
                    jComboBoxOrdemPesquisaLote.getSelectedItem().toString());
        } catch (Exception ex) {
            Logger.getLogger(JFrameCardLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTextFieldPesquisarLotesKeyReleased

    private void jComboBoxOrdemPesquisaLoteItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxOrdemPesquisaLoteItemStateChanged
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            pesquisarLotes(jTextFieldPesquisarLotes.getText(),
                    jComboBoxTipoPesquisaLote.getSelectedItem().toString(),
                    jComboBoxOrdemPesquisaLote.getSelectedItem().toString());
        } catch (Exception ex) {
            Logger.getLogger(JFrameCardLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jComboBoxOrdemPesquisaLoteItemStateChanged

    private void jButtonCadastrarLoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCadastrarLoteActionPerformed
        // TODO add your handling code here:
        try {
            if (verificaCamposLote() == true) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
            } else {
                lote.registro = jTextFieldRegistroLote.getText().trim();
                lote.quantidade = Integer.parseInt(jSpinnerQuantidadeLote.getValue().toString());
                lote.dataValidade = new SimpleDateFormat("dd/MM/yyyy").parse(jFormattedTextFieldValidadeLote.getValue().toString());
                lote.dataChegada = new SimpleDateFormat("dd/MM/yyyy").parse(jFormattedTextFieldChegadaLote.getValue().toString());
                lote.situacao = jComboBoxSituacaoLote.getSelectedItem().toString();

                DefaultTableModel dtm = (DefaultTableModel) jTableItensLote.getModel();
                item.id = (NitriteId) dtm.getValueAt(jTableItensLote.getSelectedRow(), 0);
                item = cadItem.pesquisarPorId(item.id);

                lote.item = item;
                if (dataValidadeMenorDataAtual(lote.dataValidade) == true) {
                    JOptionPane.showMessageDialog(null, "Data de validade anterior a data atual!");
                } else {
                    if (isDateValid(jFormattedTextFieldValidadeLote.getValue().toString()) == false || isDateValid(jFormattedTextFieldChegadaLote.getValue().toString()) == false) {
                        JOptionPane.showMessageDialog(null, "Data inválida!");
                    } else {
                        if (verificarLoteVazio(lote) == true) {
                            cadLote.adicionar(lote);
                            JOptionPane.showMessageDialog(null, "Lote cadastrado com sucesso!");
                            jTextFieldRegistroLote.setText("");
                            jSpinnerQuantidadeLote.setValue(0);
                            jFormattedTextFieldValidadeLote.setValue("");
                            jFormattedTextFieldChegadaLote.setValue("");
                            jComboBoxSituacaoLote.setSelectedItem(0);
                            jTableItensLote.clearSelection();
                            lote = new Lote();
                        } else {
                            JOptionPane.showMessageDialog(null, "Já existe um lote com esse registro!");
                        }
                    }

                }

            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "ERRO AO CADASTRAR LOTE\n" + ex.getMessage());
        }
    }//GEN-LAST:event_jButtonCadastrarLoteActionPerformed

    private void jMenuItemVisualizarLoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemVisualizarLoteActionPerformed
        try {
            // TODO add your handling code here:
            atualizarTabelaVisualizarLotes();
            CardLayout cl = (CardLayout) jPanel1.getLayout();
            cl.show(jPanel1, "visualizarLote");
        } catch (Exception ex) {
            Logger.getLogger(JFrameCardLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItemVisualizarLoteActionPerformed

    private void jMenuItemCadLoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemCadLoteActionPerformed
        try {
            // TODO add your handling code here:
            atualizarTabelaVisualizarItensLote();
            CardLayout cl = (CardLayout) jPanel1.getLayout();
            cl.show(jPanel1, "cadLote");
        } catch (Exception ex) {
            Logger.getLogger(JFrameCardLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItemCadLoteActionPerformed

    private void jButtonCadastrarItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCadastrarItemActionPerformed
        // TODO add your handling code here:
        try {
            if (verificaCamposItem() == false) {
                item.codigoDeBarras = jTextFieldCodigoDeBarrasItem.getText();
                item.peso = Double.parseDouble(jTextFieldPesoItem.getText());
                item.preco = Double.parseDouble(jTextFieldPrecoItem.getText());

                DefaultTableModel dtm = (DefaultTableModel) jTableProdutosItem.getModel();
                produto.id = (NitriteId) dtm.getValueAt(jTableProdutosItem.getSelectedRow(), 0);
                produto = cadProduto.pesquisarPorId(produto.id);

                item.produto = produto;
                item.situacao = "Estocado";
                if (verificarItemVazio(item) == true) {

                    cadItem.adicionar(item);
                    JOptionPane.showMessageDialog(null, "Item cadastrado com sucesso!");
                    jTextFieldCodigoDeBarrasItem.setText("");
                    jTextFieldPrecoItem.setText("");
                    jTextFieldPesoItem.setText("");
                    jTableProdutosItem.clearSelection();
                    item = new Item();
                } else {
                    JOptionPane.showMessageDialog(null, "Já existe um item com esse código de barras!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "ERRO AO CADASTRAR ITEM\n" + ex.getMessage());
        }
    }//GEN-LAST:event_jButtonCadastrarItemActionPerformed

    private void jComboBoxTipoPesquisaItemItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxTipoPesquisaItemItemStateChanged
        // TODO add your handling code here:
        try {
            pesquisarItens(jTextFieldPesquisarItens.getText(),
                    jComboBoxTipoPesquisaItem.getSelectedItem().toString(),
                    jComboBoxOrdemPesquisaItem.getSelectedItem().toString());
        } catch (Exception ex) {
            Logger.getLogger(JFrameCardLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jComboBoxTipoPesquisaItemItemStateChanged

    private void jTextFieldPesquisarItensKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPesquisarItensKeyReleased
        // TODO add your handling code here:
        try {
            pesquisarItens(jTextFieldPesquisarItens.getText(),
                    jComboBoxTipoPesquisaItem.getSelectedItem().toString(),
                    jComboBoxOrdemPesquisaItem.getSelectedItem().toString());
        } catch (Exception ex) {
            Logger.getLogger(JFrameCardLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTextFieldPesquisarItensKeyReleased

    private void jComboBoxOrdemPesquisaItemItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxOrdemPesquisaItemItemStateChanged
        // TODO add your handling code here:
         try {
            pesquisarItens(jTextFieldPesquisarItens.getText(),
                    jComboBoxTipoPesquisaItem.getSelectedItem().toString(),
                    jComboBoxOrdemPesquisaItem.getSelectedItem().toString());
        } catch (Exception ex) {
            Logger.getLogger(JFrameCardLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jComboBoxOrdemPesquisaItemItemStateChanged

    private void jMenuVisualizarItensActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuVisualizarItensActionPerformed
        try {
            // TODO add your handling code here:
            atualizarTabelaVisualizarItens();
            CardLayout cl = (CardLayout) jPanel1.getLayout();
            cl.show(jPanel1, "visualizarItens");
        } catch (Exception ex) {
            Logger.getLogger(JFrameCardLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuVisualizarItensActionPerformed

    private void jTextFieldPesquisarProdutosItemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPesquisarProdutosItemKeyReleased
        try {
            // TODO add your handling code here:
            pesquisarProdutosItem(jTextFieldPesquisarProdutosItem.getText(),
                    jComboBoxTipoPesquisaProdutosItem.getSelectedItem().toString(),
                    jComboBoxOrdemPesquisaProdutosItem.getSelectedItem().toString());
        } catch (Exception ex) {
            Logger.getLogger(JFrameCardLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTextFieldPesquisarProdutosItemKeyReleased

    private void jComboBoxOrdemPesquisaProdutosItemItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxOrdemPesquisaProdutosItemItemStateChanged
        // TODO add your handling code here:
         try {
            // TODO add your handling code here:
            pesquisarProdutosItem(jTextFieldPesquisarProdutosItem.getText(),
                    jComboBoxTipoPesquisaProdutosItem.getSelectedItem().toString(),
                    jComboBoxOrdemPesquisaProdutosItem.getSelectedItem().toString());
        } catch (Exception ex) {
            Logger.getLogger(JFrameCardLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jComboBoxOrdemPesquisaProdutosItemItemStateChanged

    private void jComboBoxTipoPesquisaProdutosItemItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxTipoPesquisaProdutosItemItemStateChanged
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            pesquisarProdutosItem(jTextFieldPesquisarProdutosItem.getText(),
                    jComboBoxTipoPesquisaProdutosItem.getSelectedItem().toString(),
                    jComboBoxOrdemPesquisaProdutosItem.getSelectedItem().toString());
        } catch (Exception ex) {
            Logger.getLogger(JFrameCardLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jComboBoxTipoPesquisaProdutosItemItemStateChanged

    private void jTextFieldPesquisarItensLoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPesquisarItensLoteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPesquisarItensLoteActionPerformed

    private void jTextFieldPesquisarItensLoteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPesquisarItensLoteKeyReleased
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            pesquisarItensLote(jTextFieldPesquisarItensLote.getText(),
                    jComboBoxTipoPesquisaItensLote.getSelectedItem().toString(),
                    jComboBoxOrdemPesquisaItensLote.getSelectedItem().toString());
        } catch (Exception ex) {
            Logger.getLogger(JFrameCardLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTextFieldPesquisarItensLoteKeyReleased

    private void jComboBoxOrdemPesquisaItensLoteItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxOrdemPesquisaItensLoteItemStateChanged
        // TODO add your handling code here:
        try {
            pesquisarItensLote(jTextFieldPesquisarItensLote.getText(),
                    jComboBoxTipoPesquisaItensLote.getSelectedItem().toString(),
                    jComboBoxOrdemPesquisaItensLote.getSelectedItem().toString());
        } catch (Exception ex) {
            Logger.getLogger(JFrameCardLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jComboBoxOrdemPesquisaItensLoteItemStateChanged

    private void jTableVisualizarLotesBaixaItemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableVisualizarLotesBaixaItemMouseClicked
        int linhaSelecionada = jTableVisualizarLotesBaixaItem.getSelectedRow();
        if (linhaSelecionada >= 0) {
            try {
                DefaultTableModel dtm = (DefaultTableModel) jTableVisualizarLotesBaixaItem.getModel();
                //preenche os dados da pessoa
                lote.id = (NitriteId) dtm.getValueAt(linhaSelecionada, 0);
                
                lote = cadLote.pesquisarPorId(lote.id);

            } catch (Exception ex) {
                Logger.getLogger(JFrameCardLayout.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_jTableVisualizarLotesBaixaItemMouseClicked

    private void jButtonRegistrarBaixaItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRegistrarBaixaItemActionPerformed
        try {
            // TODO add your handling code here:
            int qtdBaixa = (int) jSpinnerQtdBaixaItem.getValue();
            Date dataAtual = new Date();

            baixa.produto = lote.item.produto;
            baixa.motivo = jComboBoxMotivoBaixaItem.getSelectedItem().toString();
            baixa.dataBaixa = dataAtual;
            baixa.quantidade = qtdBaixa;

            if (qtdBaixa <= 0) {
                JOptionPane.showMessageDialog(null, "Digite uma quantidade de baixa maior do que 0!");
            } else {
                if (qtdBaixa > lote.quantidade) {
                    JOptionPane.showMessageDialog(null, "A quantidade da baixa excede a quantidade de itens no lote escolhido!");
                } else {
                    lote.quantidade -= qtdBaixa;

                    cadBaixa.adicionar(baixa);
                    cadLote.atualizar(lote);

                    JOptionPane.showMessageDialog(null, "Baixa efetuada com sucesso!");
                    
                    jTableVisualizarLotesBaixaItem.clearSelection();
                    jSpinnerQtdBaixaItem.setValue(0);
                    jComboBoxMotivoBaixaItem.setSelectedIndex(0);
                    baixa = new Baixa();
                    lote = new Lote();
                    atualizarTabelaVisualizarLotesBaixaItem();
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(JFrameCardLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonRegistrarBaixaItemActionPerformed

    private void jComboBoxTipoPesquisaBaixaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxTipoPesquisaBaixaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxTipoPesquisaBaixaActionPerformed

    private void jMenuItemVisualizarHistoricoBaixasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemVisualizarHistoricoBaixasActionPerformed
        try {
            CardLayout cl = (CardLayout) jPanel1.getLayout();
            cl.show(jPanel1, "historicoBaixas");
            pesquisarBaixas(jComboBoxTipoPesquisaBaixa.getSelectedItem().toString());
        } catch (Exception ex) {
            Logger.getLogger(JFrameCardLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItemVisualizarHistoricoBaixasActionPerformed

    private void jComboBoxTipoPesquisaBaixaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxTipoPesquisaBaixaItemStateChanged
        try {
            pesquisarBaixas(jComboBoxTipoPesquisaBaixa.getSelectedItem().toString());
        } catch (Exception ex) {
            Logger.getLogger(JFrameCardLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jComboBoxTipoPesquisaBaixaItemStateChanged

    private void jTableVisualizarLotesBaixaLoteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableVisualizarLotesBaixaLoteMouseClicked
        // TODO add your handling code here:
        int linhaSelecionada = jTableVisualizarLotesBaixaLote.getSelectedRow();
        if (linhaSelecionada >= 0) {
            try {
                DefaultTableModel dtm = (DefaultTableModel) jTableVisualizarLotesBaixaLote.getModel();
                //preenche os dados da pessoa
                lote.id = (NitriteId) dtm.getValueAt(linhaSelecionada, 0);
                
                lote = cadLote.pesquisarPorId(lote.id);

            } catch (Exception ex) {
                Logger.getLogger(JFrameCardLayout.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_jTableVisualizarLotesBaixaLoteMouseClicked

    private void jButtonRegistrarBaixaLoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRegistrarBaixaLoteActionPerformed
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            Date dataAtual = new Date();

            baixa.produto = lote.item.produto;
            baixa.motivo = jComboBoxMotivoBaixaItem.getSelectedItem().toString();
            baixa.dataBaixa = dataAtual;
            baixa.quantidade = lote.quantidade;

            cadBaixa.adicionar(baixa);
            cadLote.excluir(lote);

            JOptionPane.showMessageDialog(null, "Baixa efetuada com sucesso!");

            jTableVisualizarLotesBaixaLote.clearSelection();
            jComboBoxMotivoBaixaLote.setSelectedIndex(0);
            baixa = new Baixa();
            lote = new Lote();
            atualizarTabelaVisualizarLotesBaixaLote();

        } catch (Exception ex) {
            Logger.getLogger(JFrameCardLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonRegistrarBaixaLoteActionPerformed

    private void jMenuItemBaixaLoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemBaixaLoteActionPerformed
        // TODO add your handling code here:
         try {
            CardLayout cl = (CardLayout) jPanel1.getLayout();
            cl.show(jPanel1, "baixaLote");
            atualizarTabelaVisualizarLotesBaixaLote();
        } catch (Exception ex) {
            Logger.getLogger(JFrameCardLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItemBaixaLoteActionPerformed

    private void jTextFieldValorPesquisaLoteBaixaItemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldValorPesquisaLoteBaixaItemKeyReleased
        try {
            pesquisarLotesBaixa("Baixa Item", jTextFieldValorPesquisaLoteBaixaItem.getText(), jComboBoxTipoPesquisaLoteBaixaItem.getSelectedItem().toString());
        } catch (Exception ex) {
            Logger.getLogger(JFrameCardLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTextFieldValorPesquisaLoteBaixaItemKeyReleased

    private void jTextFieldValorPesquisaLoteBaixaLoteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldValorPesquisaLoteBaixaLoteKeyReleased
       try {
            pesquisarLotesBaixa("Baixa Lote", jTextFieldValorPesquisaLoteBaixaLote.getText(), jComboBoxTipoPesquisaLoteBaixaLote.getSelectedItem().toString());
        } catch (Exception ex) {
            Logger.getLogger(JFrameCardLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTextFieldValorPesquisaLoteBaixaLoteKeyReleased

    private void jMenuItemAtualizarPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAtualizarPedidoActionPerformed
         // TODO add your handling code here:
        try {
            CardLayout cl = (CardLayout) jPanel1.getLayout();
            cl.show(jPanel1, "atualizarProduto");
            atualizarTabelaVisualizarProdutosAtualizar();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "ERRO!\n" + ex.getMessage());
        }
    }//GEN-LAST:event_jMenuItemAtualizarPedidoActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        try {
            
            pedido = new Pedido();
           
            pedido.fornecedor = jTextFieldFornecerdor.getText();
            pedido.endereco = jTextFieldEndereco.getText();
            pedido.idPedido = jTextFieldIdPedido.getText();
            boolean teste =  verificaCamposPedido();
            if ( teste == true) {
             JOptionPane.showMessageDialog(null, "Dados Inválidos");
            }else{
                java.util.Date d = new Date();
                String dStr = java.text.DateFormat.getDateInstance(DateFormat.MEDIUM).format(d);
                pedido.dataDeSolicitacao = dStr;
                pedido.status = "Solicitado";

                if (pedido.id == null) {
                     CadPedido cadP = new CadPedidoImpl();
                     cadP.adicionar(pedido);
                    IdP = pedido.idPedido; 
                    EstadoPedido = 0;
                     new JFrameAddProdutoPedido(null, true).setVisible(true);
                     while (EstadoPedido == 0  ) {                        
                        JOptionPane.showMessageDialog(null, "Pedido sem itens!");
                        new JFrameAddProdutoPedido(null, true).setVisible(true);

                    }
                     JOptionPane.showMessageDialog(null, "Pedido cadastrado com sucesso!");
                    jTextFieldEndereco.setText("");
                    jTextFieldFornecerdor.setText("");
                    jTextFieldIdPedido.setText("");
                    atualizarTabelaPedidos();
                     pedido = new Pedido();
                }
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Digite apenas valores válidos!"+e);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jTablePedidosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablePedidosMouseClicked
        int linhaSelecionada = jTablePedidos.getSelectedRow();

        if(linhaSelecionada>=0){
            DefaultTableModel dtm = (DefaultTableModel) jTablePedidos.getModel();

            pedido.id = (NitriteId) dtm.getValueAt(linhaSelecionada, 0);

            pedido = BaseDados.rPedidos.getById(pedido.id);
            DadosPedido.id = pedido.id;
            DadosPedido.fornecedor = pedido.fornecedor;
            DadosPedido.endereco = pedido.endereco;
            DadosPedido.idPedido = pedido.idPedido;
            DadosPedido.dataDeSolicitacao = pedido.dataDeSolicitacao;
            DadosPedido.status = pedido.status;
            /*jTextFieldEnde.setText(pedido.endereco);
            jTextFieldFornecedorVisu.setText(pedido.fornecedor);
            jTextFieldIndentificador.setText(pedido.idPedido);
            jTextFieldData.setText(pedido.dataDeSolicitacao);
            jTextFieldStatus.setText(pedido.status);
            */

        }
    }//GEN-LAST:event_jTablePedidosMouseClicked

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        new DadosPedido(null, true).setVisible(true);
        atualizarTabelaPedidos();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
         CardLayout cl = (CardLayout) jPanel1.getLayout();
        cl.show(jPanel1, "CadPedido");
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        CardLayout cl = (CardLayout) jPanel1.getLayout();
        cl.show(jPanel1, "VisualizarPedidos");
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jTextFieldPesquisarPedidosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPesquisarPedidosKeyReleased
        try {
            // TODO add your handling code here:
            pesquisarPedidos(jTextFieldPesquisarPedidos.getText(),
                    jComboBoxTipoPesquisaPedido.getSelectedItem().toString(),
                    jComboBoxOrdemPesquisaPedido.getSelectedItem().toString());
        } catch (Exception ex) {
            Logger.getLogger(JFrameCardLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTextFieldPesquisarPedidosKeyReleased

    private void jComboBoxOrdemPesquisaPedidoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxOrdemPesquisaPedidoItemStateChanged
         try {
            // TODO add your handling code here:
            pesquisarPedidos(jTextFieldPesquisarPedidos.getText(),
                    jComboBoxTipoPesquisaPedido.getSelectedItem().toString(),
                    jComboBoxOrdemPesquisaPedido.getSelectedItem().toString());
        } catch (Exception ex) {
            Logger.getLogger(JFrameCardLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jComboBoxOrdemPesquisaPedidoItemStateChanged

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        try {
            buscarInfoMercado();
            jTextFieldNomeMercado.setText(mercadoInfo.nome);
            jTextFieldEnderecoMercado.setText(mercadoInfo.endereco);
            jTextFieldTelefoneMercado.setText(mercadoInfo.telefone);
        } catch (Exception ex) {
            Logger.getLogger(JFrameCardLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
        CardLayout cl = (CardLayout) jPanel1.getLayout();
        cl.show(jPanel1, "VisualizarMercado");
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        CadMercado cadM = new CadMercadoImpl();
        mercadoInfo.endereco = jTextFieldEnderecoMercado.getText();
        mercadoInfo.nome = jTextFieldNomeMercado.getText();
        mercadoInfo.telefone = jTextFieldTelefoneMercado.getText();
        try {
            
          cadM.atualizar(mercadoInfo);
          JOptionPane.showMessageDialog(null,"Atualizado com sucesso");
          buscarInfoMercado();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTableProdutosAtualizarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableProdutosAtualizarMouseClicked

        int linhaSelecionada = jTableProdutosAtualizar.getSelectedRow();
        DefaultTableModel dtm = (DefaultTableModel) jTableProdutosAtualizar.getModel();
        jTextFieldNomeAtualizarProduto.setText(dtm.getValueAt(linhaSelecionada, 1).toString());
    }//GEN-LAST:event_jTableProdutosAtualizarMouseClicked

    private void jButtonSalvarAtualizacoesProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalvarAtualizacoesProdutoActionPerformed
        try {
            //Verifica se um produto foi selecionado, para evitar erros
            if (jTableProdutosAtualizar.getSelectedRow() >= 0) {

                DefaultTableModel dtm = (DefaultTableModel) jTableProdutosAtualizar.getModel();
                produto.id = (NitriteId) dtm.getValueAt(jTableProdutosAtualizar.getSelectedRow(), 0);
                produto = cadProduto.pesquisarPorId(produto.id);

                //Faz pesquisa os itens com aquele produto para fazer a atualização deles também
                FindOptions opcoes = FindOptions.sort("nome", SortOrder.Ascending);
                List<Item> itensAtualizar = cadItem.pesquisarParametro("Nome", opcoes, produto.nome);
                List<Lote> lotesAtualizar = cadLote.pesquisarParametro("Nome", opcoes, produto.nome);

                if (jTextFieldNomeAtualizarProduto.getText().trim().equals("")) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
                } else {

                    produto.nome = jTextFieldNomeAtualizarProduto.getText().trim();

                    if (verificarProdutoVazio(produto) == true) {
                        cadProduto.atualizar(produto, itensAtualizar, lotesAtualizar);
                        JOptionPane.showMessageDialog(null, "Produto atualizado com sucesso!");
                        jTextFieldNomeAtualizarProduto.setText("");
                        atualizarTabelaVisualizarProdutosAtualizar();
                        produto = new Produto();
                    } else {
                        JOptionPane.showMessageDialog(null, "Esse produto já existe!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Selecione um produto na tabela para poder continuar!");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "ERRO!\n" + ex.getMessage());
        }
    }//GEN-LAST:event_jButtonSalvarAtualizacoesProdutoActionPerformed

    private void jButtonExcluirProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExcluirProdutoActionPerformed
        try {
            // TODO add your handling code here:
            DefaultTableModel dtm = (DefaultTableModel) jTableProdutosAtualizar.getModel();
            produto.id = (NitriteId) dtm.getValueAt(jTableProdutosAtualizar.getSelectedRow(), 0);
            produto = cadProduto.pesquisarPorId(produto.id);

            int confirmacao = JOptionPane.showConfirmDialog(rootPane, "Tem certeza que deseja excluir esse produto ?");

            if (confirmacao == 0) {
                cadProduto.excluir(produto);
                JOptionPane.showMessageDialog(null, "Produto excluído com sucesso");
                jTextFieldNomeAtualizarProduto.setText("");
                atualizarTabelaVisualizarProdutosAtualizar();
                produto = new Produto();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "ERRO!\n" + ex.getMessage());
        }
    }//GEN-LAST:event_jButtonExcluirProdutoActionPerformed

    private void jButtonSalvarAtualizacoesProdutoFLVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalvarAtualizacoesProdutoFLVActionPerformed
        try {
            //Verifica se um produto foi selecionado, para evitar erros
            if (jTableProdutosFLVAtualizar.getSelectedRow() >= 0) {

                DefaultTableModel dtm = (DefaultTableModel) jTableProdutosFLVAtualizar.getModel();
                produtoFLV.id = (NitriteId) dtm.getValueAt(jTableProdutosFLVAtualizar.getSelectedRow(), 0);
                produtoFLV = cadProdutoFLV.pesquisarPorId(produtoFLV.id);

                if (jTextFieldNomeAtualizarProdutoFLV.getText().trim().equals("")) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
                } else {
                    produtoFLV.nome = jTextFieldNomeAtualizarProdutoFLV.getText().trim();

                    if (verificarProdutoFLVCaseInsensitive(produtoFLV) == true) {
                        cadProdutoFLV.atualizar(produtoFLV);

                        JOptionPane.showMessageDialog(null, "Produto FLV atualizado com sucesso!");
                        jTextFieldNomeAtualizarProdutoFLV.setText("");
                        atualizarTabelaVisualizarProdutosFLVAtualizar();
                        produtoFLV = new ProdutoFLV();
                    } else {
                        JOptionPane.showMessageDialog(null, "Já existe um produto com esse mesmo nome!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Selecione um produto na tabela para poder continuar!");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "ERRO!\n" + ex.getMessage());
        }
    }//GEN-LAST:event_jButtonSalvarAtualizacoesProdutoFLVActionPerformed

    private void jTableProdutosFLVAtualizarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableProdutosFLVAtualizarMouseClicked
        int linhaSelecionada = jTableProdutosFLVAtualizar.getSelectedRow();
        DefaultTableModel dtm = (DefaultTableModel) jTableProdutosFLVAtualizar.getModel();
        jTextFieldNomeAtualizarProdutoFLV.setText(dtm.getValueAt(linhaSelecionada, 1).toString());

    }//GEN-LAST:event_jTableProdutosFLVAtualizarMouseClicked

    private void jButtonExcluirProdutoFLVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExcluirProdutoFLVActionPerformed
        try {
            // TODO add your handling code here:
            DefaultTableModel dtm = (DefaultTableModel) jTableProdutosFLVAtualizar.getModel();
            produtoFLV.id = (NitriteId) dtm.getValueAt(jTableProdutosFLVAtualizar.getSelectedRow(), 0);
            produtoFLV = cadProdutoFLV.pesquisarPorId(produtoFLV.id);

            int confirmacao = JOptionPane.showConfirmDialog(rootPane, "Tem certeza que deseja excluir esse produto ?");

            if (confirmacao == 0) {
                cadProdutoFLV.excluir(produtoFLV);
                JOptionPane.showMessageDialog(null, "Produto excluído com sucesso");
                jTextFieldNomeAtualizarProdutoFLV.setText("");
                atualizarTabelaVisualizarProdutosFLVAtualizar();
                produtoFLV = new ProdutoFLV();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "ERRO!\n" + ex.getMessage());
        }
    }//GEN-LAST:event_jButtonExcluirProdutoFLVActionPerformed

    private void jButtonSalvarAtualizacoesItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalvarAtualizacoesItemActionPerformed
        try {

            //Verifica se um item foi selecionado, para evitar erros
            if (jTableItensAtualizar.getSelectedRow() >= 0) {

                DefaultTableModel dtm = (DefaultTableModel) jTableItensAtualizar.getModel();
                item.id = (NitriteId) dtm.getValueAt(jTableItensAtualizar.getSelectedRow(), 0);
                item = cadItem.pesquisarPorId(item.id);

                //Faz pesquisa os lotes com aquele item para fazer a atualização deles também
                FindOptions opcoes = FindOptions.sort("item.produto.nome", SortOrder.Ascending);
                List<Lote> lotesAtualizar = cadLote.pesquisarParametro("Nome", opcoes, item.produto.nome);

                if (jTextFieldPesoAtualizarItem.getText().trim().equals("") || jTextFieldPrecoAtualizarItem.getText().trim().equals("")) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
                } else {
                    item.peso = Double.parseDouble(jTextFieldPesoAtualizarItem.getText().trim());
                    item.preco = Double.parseDouble(jTextFieldPrecoAtualizarItem.getText().trim());

                    cadItem.atualizar(item, lotesAtualizar);
                    JOptionPane.showMessageDialog(null, "Item atualizado com sucesso!");
                    jTextFieldPesoAtualizarItem.setText("");
                    jTextFieldPrecoAtualizarItem.setText("");
                    atualizarTabelaVisualizarItensAtualizar();
                    item = new Item();

                }

            } else {
                JOptionPane.showMessageDialog(null, "Selecione um item na tabela para poder continuar!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "ERRO!\n" + ex.getMessage());
        }
    }//GEN-LAST:event_jButtonSalvarAtualizacoesItemActionPerformed

    private void jTableItensAtualizarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableItensAtualizarMouseClicked
        // TODO add your handling code here:
        int linhaSelecionada = jTableItensAtualizar.getSelectedRow();
        DefaultTableModel dtm = (DefaultTableModel) jTableItensAtualizar.getModel();
        jTextFieldPesoAtualizarItem.setText(dtm.getValueAt(linhaSelecionada, 3).toString());
        jTextFieldPrecoAtualizarItem.setText(dtm.getValueAt(linhaSelecionada, 4).toString());
    }//GEN-LAST:event_jTableItensAtualizarMouseClicked

    private void jButtonExcluirItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExcluirItemActionPerformed
        try {
            // TODO add your handling code here:
            DefaultTableModel dtm = (DefaultTableModel) jTableItensAtualizar.getModel();
            item.id = (NitriteId) dtm.getValueAt(jTableItensAtualizar.getSelectedRow(), 0);
            item = cadItem.pesquisarPorId(item.id);

            int confirmacao = JOptionPane.showConfirmDialog(rootPane, "Tem certeza que deseja excluir esse item ?");

            if (confirmacao == 0) {
                cadItem.excluir(item);
                JOptionPane.showMessageDialog(null, "Item excluído com sucesso");
                jTextFieldPesoAtualizarItem.setText("");
                jTextFieldPrecoAtualizarItem.setText("");
                atualizarTabelaVisualizarItensAtualizar();
                item = new Item();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "ERRO!\n" + ex.getMessage());
        }
    }//GEN-LAST:event_jButtonExcluirItemActionPerformed

    private void jButtonSalvarAtualizacoesLoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalvarAtualizacoesLoteActionPerformed
        try {
            // TODO add your handling code here:

            //Verifica se um item foi selecionado, para evitar erros
            if (jTableLotesAtualizar.getSelectedRow() >= 0) {

                DefaultTableModel dtm = (DefaultTableModel) jTableLotesAtualizar.getModel();
                lote.id = (NitriteId) dtm.getValueAt(jTableLotesAtualizar.getSelectedRow(), 0);
                lote = cadLote.pesquisarPorId(lote.id);

                lote.situacao = jComboBoxSituacaoAtualizarLote.getSelectedItem().toString();

                cadLote.atualizar(lote);
                JOptionPane.showMessageDialog(null, "Lote atualizado com sucesso!");
                jComboBoxSituacaoAtualizarLote.setSelectedIndex(0);
                atualizarTabelaVisualizarLotesAtualizar();
                lote = new Lote();

            } else {
                JOptionPane.showMessageDialog(null, "Selecione um lote na tabela para poder continuar!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "ERRO!\n" + ex.getMessage());
        }
    }//GEN-LAST:event_jButtonSalvarAtualizacoesLoteActionPerformed

    private void jTableLotesAtualizarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableLotesAtualizarMouseClicked

        int linhaSelecionada = jTableLotesAtualizar.getSelectedRow();
        DefaultTableModel dtm = (DefaultTableModel) jTableLotesAtualizar.getModel();
        jComboBoxSituacaoAtualizarLote.setSelectedItem(dtm.getValueAt(linhaSelecionada, 7));
    }//GEN-LAST:event_jTableLotesAtualizarMouseClicked

    private void jButtonExcluirLoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExcluirLoteActionPerformed
        try {
            // TODO add your handling code here:
            DefaultTableModel dtm = (DefaultTableModel) jTableLotesAtualizar.getModel();
            lote.id = (NitriteId) dtm.getValueAt(jTableLotesAtualizar.getSelectedRow(), 0);
            lote = cadLote.pesquisarPorId(lote.id);

            int confirmacao = JOptionPane.showConfirmDialog(rootPane, "Tem certeza que deseja excluir esse lote ?");

            if (confirmacao == 0) {
                cadLote.excluir(lote);
                JOptionPane.showMessageDialog(null, "Lote excluído com sucesso");
                jComboBoxSituacaoAtualizarLote.setSelectedIndex(0);
                atualizarTabelaVisualizarLotesAtualizar();
                lote = new Lote();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "ERRO!\n" + ex.getMessage());
        }
    }//GEN-LAST:event_jButtonExcluirLoteActionPerformed

    private void jMenuItemAtualizarProdutoFLVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAtualizarProdutoFLVActionPerformed
        try {
            CardLayout cl = (CardLayout) jPanel1.getLayout();
            cl.show(jPanel1, "atualizarProdutoFLV");
            atualizarTabelaVisualizarProdutosFLVAtualizar();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "ERRO!\n" + ex.getMessage());
        }
    }//GEN-LAST:event_jMenuItemAtualizarProdutoFLVActionPerformed

    private void jMenuItemAtualizarLoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAtualizarLoteActionPerformed
        try {
            CardLayout cl = (CardLayout) jPanel1.getLayout();
            cl.show(jPanel1, "atualizarLote");
            atualizarTabelaVisualizarLotesAtualizar();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "ERRO!\n" + ex.getMessage());
        }
    }//GEN-LAST:event_jMenuItemAtualizarLoteActionPerformed

    private void jMenuItemAtualizarItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAtualizarItemActionPerformed
        try {
            CardLayout cl = (CardLayout) jPanel1.getLayout();
            cl.show(jPanel1, "atualizarProdutoFLV");
            atualizarTabelaVisualizarProdutosFLVAtualizar();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "ERRO!\n" + ex.getMessage());
        }
    }//GEN-LAST:event_jMenuItemAtualizarItemActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
         new JFrameCadUser(null, true).setVisible(true);
         atualizarTabelaUsuarios();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
        CardLayout cl = (CardLayout) jPanel1.getLayout();
            cl.show(jPanel1, "JPainelUser");
            atualizarTabelaUsuarios();
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       usuario.senha = String.valueOf(jPasswordFieldSenhaUser.getPassword());
       
        try {
            CadUser cadU = new CadUserImpl();
            cadU.atualizar(usuario);
            JOptionPane.showMessageDialog(null,"Senha atualizado com sucesso");
            jPasswordFieldSenhaUser.setText("");
        } catch (Exception ex) {
            Logger.getLogger(JFrameCardLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTableUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableUsuariosMouseClicked
        int linhaSelecionada = jTableUsuarios.getSelectedRow();

        if(linhaSelecionada>=0){
            DefaultTableModel dtm = (DefaultTableModel) jTableUsuarios.getModel();

            usuario.id = (NitriteId) dtm.getValueAt(linhaSelecionada, 0);

            usuario = BaseDados.rUsuarios.getById(usuario.id);
            jTextFieldCpf.setText(usuario.pessoa.cpf);
            jTextFieldUsuario.setText(usuario.usuario);
            jTextFieldNomeUser.setText(usuario.pessoa.nome);
            jTextFieldSobrenome.setText(usuario.pessoa.sobrenome);
            

        }
    }//GEN-LAST:event_jTableUsuariosMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JFrameCardLayout.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrameCardLayout.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrameCardLayout.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrameCardLayout.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrameCardLayout().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButtonCadastrarItem;
    private javax.swing.JButton jButtonCadastrarLote;
    private javax.swing.JButton jButtonCadastrarMarca;
    private javax.swing.JButton jButtonCadastrarProduto;
    private javax.swing.JButton jButtonCadastrarProdutoFLV;
    private javax.swing.JButton jButtonExcluirItem;
    private javax.swing.JButton jButtonExcluirLote;
    private javax.swing.JButton jButtonExcluirProduto;
    private javax.swing.JButton jButtonExcluirProdutoFLV;
    private javax.swing.JButton jButtonRegistrarBaixaItem;
    private javax.swing.JButton jButtonRegistrarBaixaLote;
    private javax.swing.JButton jButtonSalvarAtualizacoesItem;
    private javax.swing.JButton jButtonSalvarAtualizacoesLote;
    private javax.swing.JButton jButtonSalvarAtualizacoesProduto;
    private javax.swing.JButton jButtonSalvarAtualizacoesProdutoFLV;
    private javax.swing.JComboBox<String> jComboBoxMotivoBaixaItem;
    private javax.swing.JComboBox<String> jComboBoxMotivoBaixaLote;
    private javax.swing.JComboBox<String> jComboBoxOrdemPesquisaItem;
    private javax.swing.JComboBox<String> jComboBoxOrdemPesquisaItensLote;
    private javax.swing.JComboBox<String> jComboBoxOrdemPesquisaLote;
    private javax.swing.JComboBox<String> jComboBoxOrdemPesquisaPedido;
    private javax.swing.JComboBox<String> jComboBoxOrdemPesquisaProduto;
    private javax.swing.JComboBox<String> jComboBoxOrdemPesquisaProdutosItem;
    private javax.swing.JComboBox<String> jComboBoxSituacaoAtualizarLote;
    private javax.swing.JComboBox<String> jComboBoxSituacaoLote;
    private javax.swing.JComboBox<String> jComboBoxTipoPesquisaBaixa;
    private javax.swing.JComboBox<String> jComboBoxTipoPesquisaItem;
    private javax.swing.JComboBox<String> jComboBoxTipoPesquisaItensLote;
    private javax.swing.JComboBox<String> jComboBoxTipoPesquisaLote;
    private javax.swing.JComboBox<String> jComboBoxTipoPesquisaLoteBaixaItem;
    private javax.swing.JComboBox<String> jComboBoxTipoPesquisaLoteBaixaLote;
    private javax.swing.JComboBox<String> jComboBoxTipoPesquisaPedido;
    private javax.swing.JComboBox<String> jComboBoxTipoPesquisaProduto;
    private javax.swing.JComboBox<String> jComboBoxTipoPesquisaProdutoFLV;
    private javax.swing.JComboBox<String> jComboBoxTipoPesquisaProdutosItem;
    private javax.swing.JFormattedTextField jFormattedTextFieldChegadaLote;
    private javax.swing.JFormattedTextField jFormattedTextFieldValidadeLote;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelMarcaSelecionada;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenu jMenu9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItemAtualizarItem;
    private javax.swing.JMenuItem jMenuItemAtualizarLote;
    private javax.swing.JMenuItem jMenuItemAtualizarPedido;
    private javax.swing.JMenuItem jMenuItemAtualizarProdutoFLV;
    private javax.swing.JMenuItem jMenuItemBaixaItem;
    private javax.swing.JMenuItem jMenuItemBaixaLote;
    private javax.swing.JMenuItem jMenuItemCadItem;
    private javax.swing.JMenuItem jMenuItemCadLote;
    private javax.swing.JMenuItem jMenuItemCadProduto;
    private javax.swing.JMenuItem jMenuItemCadProdutoFLV;
    private javax.swing.JMenuItem jMenuItemVisualizarHistoricoBaixas;
    private javax.swing.JMenuItem jMenuItemVisualizarLote;
    private javax.swing.JMenuItem jMenuItemVisualizarProdutos;
    private javax.swing.JMenuItem jMenuItemVisualizarProdutosFLV;
    private javax.swing.JMenuItem jMenuVisualizarItens;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelAtualizarItem;
    private javax.swing.JPanel jPanelAtualizarLote;
    private javax.swing.JPanel jPanelAtualizarProduto;
    private javax.swing.JPanel jPanelAtualizarProdutoFLV;
    private javax.swing.JPanel jPanelBaixaItem;
    private javax.swing.JPanel jPanelBaixaLote;
    private javax.swing.JPanel jPanelCadItem;
    private javax.swing.JPanel jPanelCadLote;
    private javax.swing.JPanel jPanelCadPedido;
    private javax.swing.JPanel jPanelCadProduto;
    private javax.swing.JPanel jPanelCadProdutoFLV;
    private javax.swing.JPanel jPanelUsuarios;
    private javax.swing.JPanel jPanelVisualizarHistoricoBaixas;
    private javax.swing.JPanel jPanelVisualizarItens;
    private javax.swing.JPanel jPanelVisualizarLote;
    private javax.swing.JPanel jPanelVisualizarMercado;
    private javax.swing.JPanel jPanelVisualizarPedidos;
    private javax.swing.JPanel jPanelVisualizarProdutos;
    private javax.swing.JPanel jPanelVisualizarProdutosFLV;
    private javax.swing.JPasswordField jPasswordFieldSenhaUser;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JSpinner jSpinnerQtdBaixaItem;
    private javax.swing.JSpinner jSpinnerQuantidadeLote;
    private javax.swing.JTable jTableItensAtualizar;
    private javax.swing.JTable jTableItensLote;
    private javax.swing.JTable jTableLotesAtualizar;
    private javax.swing.JTable jTableMarcasCadProduto;
    private javax.swing.JTable jTablePedidos;
    private javax.swing.JTable jTableProdutosAtualizar;
    private javax.swing.JTable jTableProdutosFLVAtualizar;
    private javax.swing.JTable jTableProdutosItem;
    private javax.swing.JTable jTableUsuarios;
    private javax.swing.JTable jTableVisualizarHistoricoBaixas;
    private javax.swing.JTable jTableVisualizarItens;
    private javax.swing.JTable jTableVisualizarLotes;
    private javax.swing.JTable jTableVisualizarLotesBaixaItem;
    private javax.swing.JTable jTableVisualizarLotesBaixaLote;
    private javax.swing.JTable jTableVisualizarProdutos;
    private javax.swing.JTable jTableVisualizarProdutosFLV;
    private javax.swing.JTextField jTextFieldCodigoDeBarrasItem;
    private javax.swing.JTextField jTextFieldCodigoProdutoFLV;
    private javax.swing.JTextField jTextFieldCpf;
    private javax.swing.JTextField jTextFieldEndereco;
    private javax.swing.JTextField jTextFieldEnderecoMercado;
    private javax.swing.JTextField jTextFieldFornecerdor;
    private javax.swing.JTextField jTextFieldIdPedido;
    private javax.swing.JTextField jTextFieldNomeAtualizarProduto;
    private javax.swing.JTextField jTextFieldNomeAtualizarProdutoFLV;
    private javax.swing.JTextField jTextFieldNomeMercado;
    private javax.swing.JTextField jTextFieldNomeProduto;
    private javax.swing.JTextField jTextFieldNomeProdutoFLV;
    private javax.swing.JTextField jTextFieldNomeUser;
    private javax.swing.JTextField jTextFieldPesoAtualizarItem;
    private javax.swing.JTextField jTextFieldPesoItem;
    private javax.swing.JTextField jTextFieldPesquisarItens;
    private javax.swing.JTextField jTextFieldPesquisarItensLote;
    private javax.swing.JTextField jTextFieldPesquisarLotes;
    private javax.swing.JTextField jTextFieldPesquisarMarca;
    private javax.swing.JTextField jTextFieldPesquisarPedidos;
    private javax.swing.JTextField jTextFieldPesquisarProdutos;
    private javax.swing.JTextField jTextFieldPesquisarProdutosFLV;
    private javax.swing.JTextField jTextFieldPesquisarProdutosItem;
    private javax.swing.JTextField jTextFieldPrecoAtualizarItem;
    private javax.swing.JTextField jTextFieldPrecoItem;
    private javax.swing.JTextField jTextFieldRegistroLote;
    private javax.swing.JTextField jTextFieldSobrenome;
    private javax.swing.JTextField jTextFieldTelefoneMercado;
    private javax.swing.JTextField jTextFieldUsuario;
    private javax.swing.JTextField jTextFieldValorPesquisaLoteBaixaItem;
    private javax.swing.JTextField jTextFieldValorPesquisaLoteBaixaLote;
    // End of variables declaration//GEN-END:variables
}
