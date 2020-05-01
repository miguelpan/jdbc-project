package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

public class SellerService {
	/**
	 * Dependencia do SellerDao Usando a fabrica DaoFactory
	 */
	private SellerDao dao = DaoFactory.createSellerDao();

	/**
	 * Operação pra retornar uma lista de departamento do banco de dados
	 */
	public List<Seller> findAll() {
		return dao.findAll();
	}
	/*
	 * Metodo pra atualizar ou criar novo departamento
	 */
	public void saveOrUpdate(Seller obj) {
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
	public void remove(Seller obj) {
		dao.deleteById(obj.getId());
	}
}