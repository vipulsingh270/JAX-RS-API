package com.hr.resource;

import java.net.URI;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.hr.entity.Employee;
import com.hr.entity.Team;
import com.hr.service.PersistenceService;

@Path("employeeTemp")
@Produces("application/json")
@Consumes("application/json")
public class EmployeeResourceTemp {
	@Inject
	PersistenceService ps;
	
	@Context
	private UriInfo uriInfo;
	
	@GET
	public Response getEmployeebyId() {
		return Response.ok(ps.getAllEmployees()).status(Response.Status.OK).build();
	}
	@POST
	public Response createEmployee(@Valid Employee employee) {
		URI uri;
		if(employee.getTeam() != null) {
			if(!ps.isTeamExists(employee.getTeam())) {
				employee.setTeam(null);
				ps.saveEmployee(employee);
				uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(employee.getEmployeeId())).build();
				String res = "Employee Created but team doesn't exist, Kindly assign the correct team name";
				return Response.created(uri).status(Response.Status.CREATED).entity(res).build();
			}
			else {
				Team teamd = ps.getTeambyName(employee.getTeam().getName());
				employee.setTeam(teamd);
			}
		}
		ps.saveEmployee(employee);
		uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(employee.getEmployeeId())).build();
		return Response.created(uri).status(Response.Status.CREATED).build();
	}
	
	@PUT
	public Response assignTeamtoEmployee(Employee emp) {
		if (emp.getTeam() != null && ps.isTeamExists(emp.getTeam())) {
		Team teamd = ps.getTeambyName(emp.getTeam().getName());
		Employee empdb = ps.getEmployeebyId(Long.valueOf(emp.getEmployeeId()));
		empdb.setTeam(teamd);
		ps.assignTeam(empdb);
		URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(empdb.getEmployeeId())).build();
		return Response.created(uri).status(Response.Status.OK).build();
		}
		else
			return Response.status(Response.Status.BAD_REQUEST).entity("Employee not Found").build();
	}
	
	@DELETE
	public Response removeEmployee(@Valid Employee employee) {		
	ps.removeEmployee(employee.getEmployeeId());
	return Response.ok().build();
		
	}
	

}
