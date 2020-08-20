package com.hr.service;

import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.apache.shiro.SecurityUtils;

import com.hr.entity.Employee;
import com.hr.entity.Team;

@Transactional
public class PersistenceService {
	
	@PersistenceContext
	EntityManager em;
	
//	@Inject
//	SecurityUtils securityUtil;
	
	public void saveTeam(Team team) {		
		em.persist(team);
	}
	
	public void saveEmployee(Employee employee) {
		em.persist(employee);		
	}
	
	public void assignTeam(Employee employee) {		
		em.merge(employee);		
	}
	
	public Employee getEmployeebyId(Long id) {
		return em.find(Employee.class, id);
	}
	
	public Team getTeambyId(Long id) {
		return em.find(Team.class, id);
	}
	
	public Collection<Employee> getAllEmployees(){
		
		return em.createNamedQuery("ListEmployees", Employee.class).getResultList();
		
	}
	
	public void removeEmployee(Long id) {
		Employee emp = getEmployeebyId(id);
		em.remove(emp);		
	}
	
	public boolean isTeamExists(Team team) {
		try {
		Team teamd = em.createNamedQuery("findByTeamName", Team.class).setParameter("name", team.getName().toUpperCase()).getSingleResult();
		if(teamd.equals(team)) {
			return true;
		}
		else
			return false;
		}
		catch(NoResultException e) {
			return false;
		}		
	}
	
	public boolean isEmployeeExists(Employee emp) {
		try {
			Employee e = em.find(Employee.class,emp.getEmployeeId());
			if(e != null)
				return true;
			else
				return false;
		}
		catch(NoResultException e) {
			return false;
		}
	}
	
	public Collection<Team> getAllTeams(){
		return em.createNamedQuery("getAllTeams", Team.class).getResultList();
	}
	
	public Team getTeambyName(String name) {
		return em.createNamedQuery("findByTeamName", Team.class).setParameter("name", name.toUpperCase()).getSingleResult();
	}
	
	public Employee getEmpByName(String name) {
		return em.createNamedQuery("getByName", Employee.class).setParameter("name", name.toUpperCase()).getSingleResult();
	}
}
