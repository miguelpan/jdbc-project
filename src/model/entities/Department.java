package model.entities;
//Departamento é uma entidade do model

import java.io.Serializable;

public class Department implements Serializable {//Implementar o implements Serializable para poder salvar em arquivo ou tranferir em rede

	// Isso serialVersion do Serializable
	private static final long serialVersionUID = 1L;
	
	//Atributos
	private Integer id;
	private String name;
	
	//Construtor padrão
	public Department() {
	}
	//Construtor com argumentos
	public Department(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/*
	 * HashCode and equals
	 * Para poder comparar por conteudo
	 * 
	 * pode ser gerado automaticamente no source(potão direito) > HashCode() and equals()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	/*
	 * HashCode() and equals()
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Department other = (Department) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	/*
	 * TO String
	 */
	@Override
	public String toString() {
		return "Department [id=" + id + ", name=" + name + "]";
	}
}
