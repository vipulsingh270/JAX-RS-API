package com.hr.test;


import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;

import com.hr.entity.Employee;
import com.hr.entity.Team;

public class InvokingClient {
	
	private static final String REST_URI_EMPLOYEE = "http://localhost:8080/javaee-api/api/hr/employeeTemp";

	private static final String REST_URI_TEAM = "http://localhost:8080/javaee-api/api/hr/team";
	
	Client client = ClientBuilder.newClient(); 
	
	public Response getJsonEmployee() {
		return client.target(REST_URI_EMPLOYEE).request(MediaType.APPLICATION_JSON).get();
	}
	
	public Response createJsonEmployee(Employee emp) {
		return client.target(REST_URI_EMPLOYEE).request(MediaType.APPLICATION_JSON).post(Entity.entity(emp, MediaType.APPLICATION_JSON));
	}
	
	public Response createJsonEmployeePut(Employee emp) {
		return client.target(REST_URI_EMPLOYEE).request(MediaType.APPLICATION_JSON).put(Entity.entity(emp, MediaType.APPLICATION_JSON));
	}
	
	public Response createTeam(Team team) {
		return client.target(REST_URI_TEAM).request(MediaType.APPLICATION_JSON).post(Entity.entity(team, MediaType.APPLICATION_JSON));
	}

}
