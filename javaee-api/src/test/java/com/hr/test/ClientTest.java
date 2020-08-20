package com.hr.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ejb.embeddable.EJBContainer;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.hr.entity.Employee;
import com.hr.entity.Team;
import com.hr.service.PersistenceService;

@TestMethodOrder(OrderAnnotation.class)
public class ClientTest {
	String databaseURL = "jdbc:derby://localhost:1527/HR";
	String query = "select * from vipul.employee where name = ?";
	private static Connection conn;
	private static final int HTTP_CREATED = 201;
	private static final int HTTP_FETCHED = 200;
	private InvokingClient clientTest = new InvokingClient();
	
	public void openConnection() {
		try {
			
			DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
			conn = DriverManager.getConnection(databaseURL);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	@Order(1)
	@Test
	public void restFulPosttest() {
		Employee emp = new Employee();
		emp.setName("Vipul");
		Response rs = clientTest.createJsonEmployee(emp);
		assertEquals(rs.getStatus(), HTTP_CREATED);
	}
	
	@Order(2)
	@Test
	public void restFulGetTest() {
		Response rs = clientTest.getJsonEmployee();
		assertEquals(rs.getStatus(), HTTP_FETCHED);
	}
	
	@Order(3)
	@Test
	public void restCreateTeamTest() {
		Team team = new Team();
		team.setName("Development");
		Response rs = clientTest.createTeam(team);
		assertEquals(HTTP_CREATED, rs.getStatus());
		
	}
	
	@Order(4)
	@Test
	public void restPutTeamTest() throws SQLException {
		String name = "Vipul";
		openConnection();
		Employee emp = new Employee();
		PreparedStatement ps = conn.prepareStatement(query);
		try {
		ps.setString(1, name);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			emp.setEmployeeId(rs.getInt("EMPLOYEEID"));
			emp.setName(rs.getString("NAME"));
		}
		Team team = new Team();
		team.setName("Development");
		emp.setTeam(team);
		Response rsp = clientTest.createJsonEmployeePut(emp);
		assertEquals(HTTP_FETCHED, rsp.getStatus());
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			ps.close();
			conn.close();
		}
	}

}
