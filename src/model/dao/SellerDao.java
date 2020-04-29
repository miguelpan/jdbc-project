package model.dao;

import java.util.List;

import model.entities.Department;
import model.entities.Seller;
//Interface SellerDao
public interface SellerDao {

	void insert(Seller obj);//Operação responsavel por inserir um registro no banco de dados um objeto passado como parametro
	void update(Seller obj);//Operação responsavel por atualizar um registro no banco de dados um objeto passado como parametro
	void deleteById(Integer id);//Operação responsavel por deletar um registro no banco de dados um objeto passado como parametro
	Seller findById(Integer id);//Operação responsavel por pegar um ID e consultar o banco de dados com esse ID
	List<Seller> findAll();//Operação responsavel por retornar todos os registro do banco de dados 
	List<Seller> findByDepartment(Department department);
}

