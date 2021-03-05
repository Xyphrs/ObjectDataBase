package com.company.Entities;

import java.util.Date;


public class Article  {
	private int id_article;
	private  String titol;
	private Date data_creacio;
	private boolean publicable;
	private  Autor autor;
	//private Revista  publicate;
	  
	public Article(int id_article, String titol, Date data_creacio,
			boolean publicable, Autor autor) {
		super();
		this.id_article = id_article;
		this.titol = titol;
		this.data_creacio = data_creacio;
		this.publicable = publicable;
		this.autor=autor;
	}
	public Article() {
		super();
		
	}
	
	public int getId_article() {
		return id_article;
	}
	public void setId_article(int id_article) {
		this.id_article = id_article;
	}
	public String getTitol() {
		return titol;
	}
	public void setTitol(String titol) {
		this.titol = titol;
	}
	public Date getData_creacio() {
		return data_creacio;
	}
	public void setData_creacio(Date data_creacio) {
		this.data_creacio = data_creacio;
	}
	
	public boolean isPublicable() {
		return publicable;
	}
	public void setPublicable(boolean publicable) {
		this.publicable = publicable;
	}
	public Autor getAutor() {
		return autor;
	}
	public void setAutor(Autor autor) {
		this.autor = autor;
	}
	@Override
	public String toString() {
		return "Article [id_article=" + id_article + ", titol=" + titol
				+ ", data_creacio=" + data_creacio + ", publicable="
				+ publicable + "]";
	}
	/**
	public Revista getPublicate() {
		return publicate;
	}
	public void setPublicate(Revista publicate) {
		this.publicate = publicate;
	}
	**/
	

}
