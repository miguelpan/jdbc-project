package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentService {
	/**
	 * Dependencia do DepartmentDao Usando a fabrica DaoFactory
	 */
	private DepartmentDao dao = DaoFactory.createDepartmentDao();

	/**
	 * Operação pra retornar uma lista de departamento do banco de dados
	 */
	public List<Department> findAll() {
		return dao.findAll();
	}
	/*
	 * Metodo pra atualizar ou criar novo departamento
	 */
	public void saveOrUpdate(Department obj) {
		if (obj .getId() == null) {// se obj == null, quer dizer que ele vai criar um novo obj
			dao.insert(obj);
		}
		else {// se não ele vai atualizar um ja existente 
			dao.update(obj);
		}
	}
	/*
	 * Remover um departamento do banco de dados
	 */
	public void remove(Department obj) {
		dao.deleteById(obj.getId());
	}
}