/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pizzaria.dao;


import br.com.pizzaria.modelo.Endereco;
import br.com.pizzaria.modelo.Fornecedor;
import br.com.utilitario.*;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Titione
 */
public class FornecedorDaoImplTest {
    
    private Fornecedor fornecedor;
    private FornecedorDao fornecedorDao;
    private Session sessao;
    private List<Endereco> enderecos;
    
    public FornecedorDaoImplTest() {
        fornecedorDao = new FornecedorDaoImpl();
    }   
    
    
    
//    @Test 
    public void testSalvar(){
        System.out.println("Salvar");
                fornecedor = new Fornecedor(null, 
                UtilGerador.gerarNome(),
                UtilGerador.gerarEmail(),
                UtilGerador.gerarTelefoneFixo(), 
                UtilGerador.gerarNumero(11),
                UtilGerador.gerarNumero(8)        
        );
        enderecos = new ArrayList<>();
        gerarEndereços();
        fornecedor.setEnderecos(enderecos);
        
        sessao = HibernateUtil.abrirConexao();
        fornecedorDao.salvarOuAlterar(fornecedor, sessao);
        sessao.close();
        
        assertNotNull(fornecedor.getId());
        assertNotNull(fornecedor.getEnderecos().isEmpty());
    }
    
//    @Test
    public void testAlterar(){
        System.out.println("Alterar");
        buscaFornecedorBd();
        fornecedor.setNome("Alterado " + UtilGerador.gerarNome());
        fornecedor.getEnderecos().get(0).setCidade("Alterado " + UtilGerador.gerarCidade());
        sessao = HibernateUtil.abrirConexao();
        fornecedorDao.salvarOuAlterar(fornecedor, sessao);
        sessao.close();
        
        sessao = HibernateUtil.abrirConexao();
        Fornecedor fornecedorALterado = fornecedorDao.pesquisarPorIdComEndereco(fornecedor.getId(), sessao);
        fornecedorALterado.getEnderecos().get(0);
        sessao.close();
        
        assertEquals(fornecedor.getNome(),fornecedorALterado.getNome());
        assertEquals(fornecedor.getEnderecos().get(0).getCidade(), fornecedorALterado.getEnderecos().get(0).getCidade());
        
    }

//                @Test
    public void testExcluir() {
        System.out.println("Excluir");
        buscaFornecedorBd();
        sessao = HibernateUtil.abrirConexao();
        fornecedorDao.excluir(fornecedor, sessao);
        
        Fornecedor fornecedorExcluido = fornecedorDao.pesquisarPorIdComEndereco(fornecedor.getId(), sessao);
        sessao.close();
        
        assertNull(fornecedorExcluido);
        
    }
    
    

   @Test
    public void testPesquisarPorNome() {
        System.out.println("pesquisarPorNome");
        buscaFornecedorBd();
        String nome = fornecedor.getNome();
        int letra = nome.indexOf(" ");
        nome = nome.substring(0, letra);
        
        sessao = HibernateUtil.abrirConexao();
        List<Fornecedor> fornecedores = fornecedorDao.pesquisarPorNome(nome, sessao);
        sessao.close();
        
        assertTrue(!fornecedores.isEmpty());
        
    }
        
    
    private void gerarEndereços(){
        for (int i = 0; i < 3; i++) {
            Endereco endereco = new Endereco(null, 
                "rua " + UtilGerador.gerarNome(), 
                "bairro" + UtilGerador.gerarNome(),
                UtilGerador.gerarCidade(), 
                UtilGerador.gerarCidade(), 
                UtilGerador.gerarNumero(2),
                "Nao há" ,
                "Nao há");
            endereco.setPessoa(fornecedor);
            enderecos.add(endereco);
        }
    }
    
    private Fornecedor buscaFornecedorBd (){
        sessao = HibernateUtil.abrirConexao();
        Query consulta = sessao.createQuery("from Fornecedor f join fetch f.enderecos");
        List<Fornecedor> fornecedores = consulta.list();
        sessao.close();
        if (fornecedores.isEmpty()) {
            testSalvar();
        } else {
        fornecedor = fornecedores.get(UtilGerador.criarNumeroAleatorioEntre2Valores(1, fornecedores.size()));
     }
        
        return  fornecedor;
    }
    
}
