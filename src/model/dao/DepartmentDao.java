package model.dao;
//Departamento é uma entidade do model

import java.util.List;

import model.entities.Department;
// Interface DepartmentDao
public interface DepartmentDao {

	void insert(Department obj);//Operação responsavel por inserir um registro no banco de dados um objeto passado como parametro
	void update(Department obj);//Operação responsavel por atualizar um registro no banco de dados um objeto passado como parametro
	void deleteById(Integer id);//Operação responsavel por deletar um registro no banco de dados um objeto passado como parametro
	Department findById(Integer id);//Operação responsavel por pegar um ID e consultar o banco de dados com esse ID
	List<Department> findAll();//Operação responsavel por retornar todos os registro do banco de dados 
}
