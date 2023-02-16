/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Project;

import util.ConnectionFactory;


/**
 *
 * @author Raquel
 */
public class ProjectController {
    
     public void save(Project project){
        
        String sql = "INSERT INTO projects (name, "
                + "description, "
                + "createdAt, "
                + "updatedAt) "
                + "VALUES (?, ?, ?, ?)";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            //Cria uma conexao com o banco de dados
            connection = ConnectionFactory.getConnection();
            
            //cria um PreparedStatement, classe usada para executar a Query
            statement = connection.prepareStatement(sql);
           
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));
            
            //Executa a sql para a insercao de dados
            statement.execute();
            
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao salvar o projeto " 
                    + ex.getMessage(), ex);
        }finally{
            ConnectionFactory.closeConnection(connection, statement);
        }
            
}
   
    public void update(Project project){
        
        String sql = "UPDATE projects SET" 
                + " name = ?, "
                + "description = ?,"
                + "createdAt = ?, "
                + "updatedAt = ? "
                + "WHERE id = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            //Estabelecendo a conexao com o banco de dados
            
            connection = ConnectionFactory.getConnection();
            
            //Preparando a Query
            statement = connection.prepareStatement(sql);
            
            //Setando os valores do statement
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime())); 
            statement.setInt(5, project.getId());
            
            //Executando a Query
            statement.execute();
        } catch (SQLException ex) {
             throw new RuntimeException("Erro ao atualizar o projeto ", ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
        
    }
    
     public List<Project> getAll(){
        
        String sql = "SELECT * FROM projects";
        
          //Lista de tarefas q sera devolvida qnd a chamada do metodo acontecer
        List<Project> projects = new ArrayList<>();
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        //Classe q vai recuperar todos os dados do banco de dados
        ResultSet resultSet = null;
        
        
        try {
            
            //Conexao com o banco de dados
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            
            //Valor retornado pela execucao da Query
            resultSet = statement.executeQuery();
            
            //Enquanto houverem valores a serem percorridos no meu resultSet
            while(resultSet.next()){
                
                Project project = new Project();
                
                project.setId(resultSet.getInt("id"));
                project.setName(resultSet.getString("name"));
                project.setDescription(resultSet.getString("description"));
                project.setCreatedAt(resultSet.getDate("createdAt"));
                project.setUpdatedAt(resultSet.getDate("updatedAt"));
                
                
                //Adiciono o contato recuperado, a lista de contatos
                projects.add(project);
            }
            
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao buscar os projetos ", ex);
        }finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }
          
        //Lista de tarefas q foi criada ecarregada pelo banco de dados
        return projects;
    }
     
       public void removeById(int idProject){
        
        String sql = "DELETE FROM projects WHERE id = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            
             //Criacao da conexao do banco de dados
            connection = ConnectionFactory.getConnection();
            
            //Preparando a Query
            statement = connection.prepareStatement(sql);
            
            //Setando os valores
            statement.setInt(1, idProject);
            
            //Executando a Query
            statement.execute();
            
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao deletar o projeto ", ex);
            
        }finally{
            
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
    
    
}
