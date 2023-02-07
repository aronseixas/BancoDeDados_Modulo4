package org.example;

import org.example.entities.Gato;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class JDBCExemplo {

    private static final String DATA_BASE_URL = "jdbc:mysql://127.0.0.1:3308/pet";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "";
    private static final String SELECT_GATOS = "Select * FROM pet.CATS";
    private static final String INSERT_GATOS = "INSERT INTO pet.CATS (name, birth, owner) VALUES(?, ?, ?)";


    public static void main(String[] args) {

        List<Gato> gatos = new ArrayList<>();

        try{
            Connection conn = DriverManager.getConnection(DATA_BASE_URL, USER_NAME, PASSWORD);

            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_GATOS);

            ResultSet rs = preparedStatement.executeQuery();

            preparedStatement = conn.prepareStatement(INSERT_GATOS);
            preparedStatement.setString(1,"Catzinho");
            preparedStatement.setDate(2, java.sql.Date.valueOf("2020-12-01"));
            preparedStatement.setString(3,"Maria");

            int row = preparedStatement.executeUpdate();

            System.out.println(row);

            while (rs.next()){
                Gato gato = new Gato();
                //AQUI O ARGUMENTO NOS PARÊNTESES TEM QUE SER IGUAL AO NOME DADO NA TABELA NO WORKBENCH
                gato.setId(rs.getInt("id"));
                gato.setNome(rs.getString("name"));
                gato.setDono(rs.getString("owner"));
                gato.setDataNascimento(rs.getDate("birth"));
                gatos.add(gato);
            }

            gatos.forEach(System.out::println);

            //ESTA PARTE (DO FILTRO) NÃO CONSEGUI COPIAR POR COMPLETO. DEVE ESTAR FALTANDO ALGUMA COISINHA
            //var teste = gatos.stream().filter(var -> var.getNome().equalsIgnoreCase("Sandy")).findFirst().get().getId();
            //System.out.println();

        } catch (Exception throwables){
            System.out.println("Deu bom nao");
            throwables.printStackTrace();
        }
    }
}