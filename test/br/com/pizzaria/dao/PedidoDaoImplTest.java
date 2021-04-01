/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pizzaria.dao;

import br.com.pizzaria.modelo.Cliente;
import br.com.pizzaria.modelo.Endereco;
import br.com.pizzaria.modelo.Pedido;
import br.com.utilitario.UtilGerador;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Titione
 */
public class PedidoDaoImplTest {
    
    private Pedido pedido;
    private Cliente cliente;
    private ClienteDao clienteDao;
    private Session sessao;
    private List<Endereco> enderecos;
    private PedidoDaoImpl pedidoDao;
    
    public PedidoDaoImplTest() {
        pedidoDao = new PedidoDaoImpl();
    }

//    @Test
    public  void testSalvar(){
        System.out.println("Salvar");
        ClienteDaoImplTest clienteDaoImplTest = new ClienteDaoImplTest();
        cliente = clienteDaoImplTest.buscaClienteBd();
        enderecos = new ArrayList<>();
        gerarEndereços();
        cliente.setEnderecos(enderecos);
        
        pedido = new Pedido();
        pedido.setValorPedido(4.3);
        pedido.setDataPedido(new Date());
        pedido.setCliente(cliente);
        
        sessao = HibernateUtil.abrirConexao();
        pedidoDao.salvarOuAlterar(pedido, sessao);
        sessao.close();
       
        assertNotNull(pedido.getId());
    }
    
    
//    @Test
    public void testPesquisarPorId() {
        System.out.println("pesquisarPorId");
        buscaPedidoBd();
       
        assertNotNull(pedido);
    }
    
//    @Test
    public void testAlterar(){
        System.out.println("Alterar");
        buscaPedidoBd();
        pedido.setValorPedido(1.1);
        sessao = HibernateUtil.abrirConexao();
        pedidoDao.salvarOuAlterar(pedido, sessao);
        sessao.close();
        
        sessao = HibernateUtil.abrirConexao();
        Pedido pedidoPesquisado = new Pedido();
        pedidoPesquisado = pedidoDao.pesquisarPorId(pedido.getId(), sessao);
        sessao.close();
        
        assertEquals(pedido.getValorPedido(), pedidoPesquisado.getValorPedido(), 0.001);
        
    }
    
    // @Test
    public void testExcluir(){
        buscaPedidoBd();
        sessao = HibernateUtil.abrirConexao();
        pedidoDao.excluir(pedido, sessao);
        sessao.close();
        
        sessao = HibernateUtil.abrirConexao();
        Pedido pedidoPesquisado = pedidoDao.pesquisarPorId(pedido.getId(), sessao);
        sessao.close();
        
        assertNull(pedidoPesquisado);
        
    }
    
    
    
 
    private void gerarEndereços(){
        for (int i = 0; i < 3; i++) {
            Endereco endereco = new Endereco(null, 
                "rua " + UtilGerador.gerarNome(), 
                "bairro" + UtilGerador.gerarNome(),
                UtilGerador.gerarCidade(), 
                UtilGerador.gerarCidade(), 
                UtilGerador.gerarNumero(2),
                "Nao há",
                "Cliente Chato" );
            endereco.setPessoa(cliente);
            enderecos.add(endereco);
        }
    }
    
    public Pedido buscaPedidoBd(){
        sessao = HibernateUtil.abrirConexao();
        Query consulta = sessao.createQuery("from Pedido");
        List<Pedido> pedidos = consulta.list();
        sessao.close();
        
        if (pedidos.isEmpty()) {
            testSalvar();
        } else {
            pedido = pedidos.get(UtilGerador.criarNumeroAleatorioEntre2Valores(1, pedidos.size()));
        }
        
        return pedido;
    }
}
