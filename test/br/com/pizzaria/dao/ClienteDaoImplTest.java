/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pizzaria.dao;

import br.com.pizzaria.modelo.Cliente;
import br.com.pizzaria.modelo.Endereco;
import br.com.pizzaria.modelo.Pedido;
import br.com.utilitario.*;
import java.time.Instant;
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
public class ClienteDaoImplTest {

    private Cliente cliente;
    private ClienteDao clienteDao;
    private Session sessao;
    private List<Endereco> enderecos;
    private Cliente clientePesquisado;

    public ClienteDaoImplTest() {
        clienteDao = new ClienteDaoImpl();
    }

   @Test
    public void testSalvar() {
        System.out.println("Salvar");
        cliente = new Cliente(null,
                UtilGerador.gerarNome(),
                UtilGerador.gerarEmail(),
                UtilGerador.gerarTelefoneFixo(),
                true);
        enderecos = new ArrayList<>();
        gerarEndereços();
        cliente.setEnderecos(enderecos);
        List<Pedido> pedidos = new ArrayList<>();
        Pedido pedido = new Pedido();
        pedido.setValorPedido(4.3);
        pedido.setDataPedido(new Date());
        pedido.setCliente(cliente);
        pedidos.add(pedido);
        cliente.setPedidos(pedidos);
        
        
        sessao = HibernateUtil.abrirConexao();
        clienteDao.salvarOuAlterar(cliente, sessao);
        sessao.close();
        
        assertNotNull(cliente.getId());
        assertNotNull(cliente.getEnderecos().isEmpty());
        
     
    }
    
//   @Test
    public void testAlterar() {
        System.out.println("Alterar");
        buscaClienteBd();
        cliente.setNome("Alterado 2 " + UtilGerador.gerarNome());
        cliente.getEnderecos().get(0).setComplemento("Alterado OU nao?");
        sessao = HibernateUtil.abrirConexao();
        clienteDao.salvarOuAlterar(cliente, sessao);
        sessao.close();
        
        sessao = HibernateUtil.abrirConexao();
        Cliente clienteAlterado = new Cliente();
        
        clienteAlterado = clienteDao.pesquisaPorIdComEndereco(cliente.getId(), sessao);
        sessao.close();
        
        assertNotNull(cliente.getNome(), clienteAlterado.getNome());
        assertNotNull(cliente.getEnderecos().get(0).getComplemento(), clienteAlterado.getEnderecos().get(0).getComplemento());
        
    }
    


   // @Test
    public void testPesquisaPorNome() {
        System.out.println("pesquisaPorNome");
        buscaClienteBd();
        sessao = HibernateUtil.abrirConexao();
        clientePesquisado = clienteDao.pesquisaPorNome(cliente.getNome(), sessao).get(0);
        sessao.close();
        
        
        assertNotNull(cliente.getNome(), clientePesquisado.getNome());
        assertEquals(   cliente.getEnderecos().get(0).getId(), 
                clientePesquisado.getEnderecos().get(0).getId());
    }

    // @Test
    public void testPesquisaPorIdComEndereco() {
        System.out.println("pesquisaPorIdComEndereco");
        buscaClienteBd();
        sessao = HibernateUtil.abrirConexao();
        clientePesquisado = clienteDao.pesquisaPorIdComEndereco(cliente.getId(), sessao);
        sessao.close();
        
        assertEquals(cliente.getEnderecos().get(0).getId(), clientePesquisado.getEnderecos().get(0).getId());
    }

    
 // @Test
    public void testExcluir() {
        System.out.println("Excluir");
        buscaClienteBd();
        sessao = HibernateUtil.abrirConexao();
        clienteDao.excluir(cliente, sessao);
        
        Cliente clienteExcluido = clienteDao.pesquisaPorIdComEndereco(cliente.getId(), sessao);
        sessao.close();
        
        assertNull(clienteExcluido);
        
    }
    
   // @Test
    public void testPesquisaPorTelefone() {
        System.out.println("pesquisaPorNome");
        buscaClienteBd();
        sessao = HibernateUtil.abrirConexao();
        clientePesquisado = clienteDao.pesquisaPorTelefone(cliente.getTelefone(), sessao);
        sessao.close();
        
        assertNotNull(cliente.getTelefone(), clientePesquisado.getTelefone());
        
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
 
    
   public Cliente buscaClienteBd(){
        sessao = HibernateUtil.abrirConexao();
        Query consulta = sessao.createQuery("from Cliente c join fetch c.enderecos");
        List<Cliente> clientes = consulta.list();
        sessao.close();
        
        if (clientes.isEmpty()) {
            testSalvar();
        } else {
            cliente = clientes.get(UtilGerador.criarNumeroAleatorioEntre2Valores(1, 5));
        }
        
        return cliente;
    }
    
}
