package com.hanbit.hp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class SuckDAO {
   
   //상수
   private static final String URL = "jdbc:mysql://localhost:3306/hanbit?characterEncoding=utf8;";
   private static final String USERNAME = "hanbit";
   private static final String PASSWORD = "hanbit";
   
   public List selectMainImags() {
      List imgs = new ArrayList();
      Connection connection = null;
      PreparedStatement statement = null;
      ResultSet resultSet = null;
      
      
      try {
         Class.forName("com.mysql.jdbc.Driver");
         connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);      
         
         String sql = "SELECT img_url FROM tbl_main_img";
         
         statement = connection.prepareStatement(sql);
         resultSet = statement.executeQuery();
         
         while( resultSet.next() ) {   //데이터있으면 true, 없으면 false
            String imgUrl = resultSet.getString(1);
            imgs.add(imgUrl);
         }
         
      }
      catch(Exception e) {   //예외발생시
         
      }
      finally {   //예외발생하든 안 하든 무조건 실행
         
         if(resultSet != null) {
            try {
               resultSet .close();
            }
            catch (Exception e) {
               
            }
         }
         
         if(statement != null) {
            try {
               statement .close();
            }
            catch (Exception e) {
               
            }
         }
         
         if(connection != null) {
            try {
               connection .close();
            }
            catch (Exception e) {
               
            }
         }
      }
      
      return imgs;
   }
}