package model.exceptions;

import java.util.HashMap;
import java.util.Map;

public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	/*
	 * carrega uma coleção contendo todos os erros possiveis 
	 * O primeiro string salva o campo e o segundo a msg de erro
	 */
	private Map<String, String> errors =  new HashMap<>();// savalvando cada erro para o seu campo, campo x com erro y 
	
	public ValidationException(String msg) {
		super(msg);
	}
	/*
	 * retorna o erro capturado
	 */
	public Map<String, String> getErrors(){
		return errors;
	}
	
	/*
	 * adiciona elementos na coleção
	 */
	public void addError(String fielName, String erroMessage) {
		errors.put(fielName, erroMessage); 
	}
}
