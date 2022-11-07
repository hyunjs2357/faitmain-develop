package com.faitmain.sample;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.faitmain.FaitmainApplication;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes= {FaitmainApplication.class})
public class DataSourceTest {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired	
	private SqlSessionFactory sqlSessionFactory;
	
	@Test
	public void testConnection() throws SQLException {
		
		try(
				Connection con = dataSource.getConnection();
				SqlSession session = sqlSessionFactory.openSession();
				
			){
			
				System.out.println("con = " + con);
				System.out.println("session = " + session);
		
			}catch(Exception e) {
				e.printStackTrace();
			}
	}

}
