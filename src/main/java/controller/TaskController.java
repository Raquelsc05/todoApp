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
import model.Task;
import util.ConnectionFactory;

/**
 *
 * @author Raquel
 */
public class TaskController {
    
    public void save(Task task){
        
        String sql = "INSERT INTO tasks(idProject,"
                + " name, description, completed,"
                + " notes, deadline, createdAt, updatedAt)"
                + " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)";
            
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.isIsCompleted());
            statement.setString(5, task.getNotes());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
            statement.execute();
            
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao salvar a tarefa " 
                    + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
            
      
        
    }
    public void update(Task task){
        String sql = "UPDATE tasks SET idProject = ?, name = ?, description = ?,"
                + " completed = ?, notes = ?, deadline = ?,"
                + " createdAt = ?, updatedAt = ? WHERE id = ?";
               
             
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            //Estabelecendo a conexao com o banco de dados
            
            connection = ConnectionFactory.getConnection();
            
            //Preparando a Query
            statement = connection.prepareStatement(sql);
            
            //Setando os valores do statement
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.isIsCompleted());
            statement.setString(5, task.getNotes());  
            statement.setDate(6, new Date(task.getDeadline().getTime())); 
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAt().getTime())); 
            statement.setInt(9, task.getId());
            
            //Executando a Query
            statement.execute();
        } catch (Exception ex) {
             throw new RuntimeException("Erro ao atualizar a tarefa "
             + ex.getMessage(), ex);
        }
        
    }
    
    public void removeById(int taskId) {
        
        String sql = "DELETE FROM tasks WHERE id = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            
             //Criacao da conexao do banco de dados
            connection = ConnectionFactory.getConnection();
            
            //Preparando a Query
            statement = connection.prepareStatement(sql);
            
            //Setando os valores
            statement.setInt(1, taskId);
            
            //Executando a Query
            statement.execute();
            
        } catch (SQLException ex) {
             throw new RuntimeException("Erro ao deletar a tarefa "
             + ex.getMessage(), ex);
           
        } finally {
            
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
    
    public List<Task> getAll(int idProject){
        
        String sql = "SELECT * FROM tasks WHERE idProject = ?";
        
        
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        //Lista de tarefas q sera devolvida qnd a chamada do metodo acontecer
        List<Task> tasks = new ArrayList<Task>();
        
        try {
            
            //Conexao com o banco de dados
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            
            //setando o valor correspondente ao filtro de busca
            statement.setInt(1, idProject);
            
            //Valor retornado pela execucao da Query
            resultSet = statement.executeQuery();
            
            //Enquanto houverem valores a serem percorridos no meu resultSet
            while(resultSet.next()){
                
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setIdProject(resultSet.getInt("idProject"));
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("description"));
                task.setIsCompleted(resultSet.getBoolean("completed"));
                task.setNotes(resultSet.getString("notes"));
                task.setDeadline(resultSet.getDate("deadline"));
                task.setCreatedAt(resultSet.getDate("createdAt"));
                task.setUpdatedAt(resultSet.getDate("updatedAt"));
                
                tasks.add(task);
            }
            
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao inserir a tarefa"
             + ex.getMessage(), ex);
        }finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }
          
        //Lista de tarefas q foi criada ecarregada pelo banco de dados
        return tasks;
    }
    
}
