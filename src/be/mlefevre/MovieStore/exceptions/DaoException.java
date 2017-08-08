package be.mlefevre.MovieStore.exceptions;

import java.io.IOException;

public class DaoException extends IOException {
	
	public DaoException() {
		super();
	}
	
	public DaoException(String messsage){
		super(messsage);
	}

}
