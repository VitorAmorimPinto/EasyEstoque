/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.util.List;
import modelo.Baixa;
import modelo.Item;
import modelo.Lote;
import modelo.Marca;
import modelo.Mercado;
import modelo.Pedido;
import modelo.Pessoa;
import modelo.Produto;
import modelo.ProdutoFLV;
import modelo.ProdutoPedido;
import modelo.Usuario;
import org.dizitart.no2.FindOptions;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import static org.dizitart.no2.objects.filters.ObjectFilters.and;
import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

/**
 *
 * @author Priscilaa
 */
public class BaseDados {
  
 //CRIAÇÃO DOS REPOSITÓRIOS
 public static ObjectRepository<Produto> rProdutos;
 public static ObjectRepository<ProdutoFLV> rProdutosFLV;
 public static ObjectRepository<Marca> rMarcas;
 public static ObjectRepository<Lote> rLotes;
 public static ObjectRepository<Item> rItens;
 public static ObjectRepository<Baixa> rBaixas;
 public static ObjectRepository<Pedido> rPedidos;
 public static ObjectRepository<ProdutoPedido> rPedidoProduto;
 public static ObjectRepository<Usuario> rUsuarios;
 public static ObjectRepository<Pessoa> rPessoas;
 public static ObjectRepository<Mercado> rMercado;
 


 public static void iniciaDados() {
  Nitrite db = Nitrite.builder()
          .compressed()
          .filePath("baseDados.db")
          .openOrCreate("root", "salesiano");

  rProdutos = db.getRepository(Produto.class);
  rProdutosFLV = db.getRepository(ProdutoFLV.class);
  rMarcas = db.getRepository(Marca.class);
  rLotes = db.getRepository(Lote.class);
  rItens = db.getRepository(Item.class);
  rBaixas = db.getRepository(Baixa.class);
  rPedidos = db.getRepository(Pedido.class);
  rPedidoProduto = db.getRepository(ProdutoPedido.class);
  rUsuarios = db.getRepository(Usuario.class);
  rPessoas = db.getRepository(Pessoa.class);
  rMercado = db.getRepository(Mercado.class);

 Usuario Admin = new Usuario();
 Pessoa p = new Pessoa();
  
     
  List<Usuario> usuarios;
  usuarios = BaseDados.rUsuarios.find().toList();
     
 
  if(usuarios.isEmpty() == true){
      Admin.usuario = "Admin";
      Admin.senha = "123";
      p.cpf= "";
      p.idade = 0;
      Admin.administrador = true;
      rPessoas.insert(p);
      Admin.pessoa = p;
      rUsuarios.insert(Admin);
  }

 }
}
