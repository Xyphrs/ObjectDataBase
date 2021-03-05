package com.company;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.company.Entities.*;
import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Predicate;

public class DB4OMagazineManagerV02 {
	private static ArrayList<Revista> revistes;
	private static ObjectContainer db;
	
	public static void main(String[] args) throws IOException, ParseException {
		DB4OMagazineManagerV02 MM = new DB4OMagazineManagerV02();
		// load ArrayList<Revista> data from files
		MM.loadMagazines();
		try {
			//open database represented by ObjectContainer
			MM.connect();
			// store magazines
			MM.storeMagazines();
			// Querying magazines
			MM.listMagazines();
			// Querying articles
			MM.listArticles();
			// Querying autors
			MM.listAutors();
			// Querying autors by autor name
			MM.llistaAutorsByName("F. Perrier");
			// QUerying articles by autor name
			MM.llistaArticlesByAutorName("R. Manito");
			MM.deletingArticlesByAutorName("R. Manito");
			MM.llistaArticlesByAutorName("R. Manito");
			MM.llistaAutorsAlemanys();
			// Deleting autor by id
			MM.deleteAutor(15);
			// retrieveMagazine by id
			MM.retrieveMagazineContentById_Revista(2);
			// deleting Magazine CASCADE by id
			MM.deleteMagazineContentById_Revista(2);
			MM.retrieveMagazineContentById_Revista(2);
			MM.listAutors();
			// Deleting all objects
			MM.clearDatabase();
			MM.listMagazines();
			
			
		} finally {
			db.close();
		}
	}
	
	// Method to connect and open database file
	public void connect() throws IOException {
		
		File file = new File("revistes.db");
		String fullPath = file.getAbsolutePath();
		db = Db4o.openFile(fullPath);
		
	}
	
	// Method to LOAD Magazines in memory from files using FileAccesor
	public void loadMagazines() throws IOException, ParseException {
		FileAccessor fa = new FileAccessor();
		fa.readAutorsFile("autors.csv");
		fa.readMagazinesFile("revistes.csv");
		revistes = fa.readArticlesFile("articles.csv");
	}
	
	// Method to STORE all in memory magazines to the database
	public void storeMagazines() {
		System.out.println("\n\nRevistes llegides des del fitxer\n\nARA enmagatzemades a la BBDD");
		for (int i = 0; i < revistes.size(); i++) {
			System.out.println(revistes.get(i).toString());
			db.store(revistes.get(i));
		}
	}
	
	// Method to LIST all magazines from the database using QBE example
	public void listMagazines() {
		System.out.println("\n\nRevistes llegides des de la base de dades");
		ObjectSet<Revista> result = db.queryByExample(Revista.class);
		System.out.println(result.size());
		while (result.hasNext()) {
			System.out.println(result.next());
		}
	}
	
	// Method to LIST all articles from the database using QBE example
	public void listArticles() {
		System.out.println("\n\nArticles llegits des de la base de datos");
		ObjectSet<Article> result = db.queryByExample(Article.class);
		System.out.println(result.size());
		while (result.hasNext()){
			System.out.println(result.next());
		}
	}
	
	// Method to LIST all autors from the database using QBE example
	public void listAutors() {
		System.out.println("\n\nAutors llegits des de la base de datos");
		ObjectSet<Autor> result = db.queryByExample(Autor.class);
		System.out.println(result.size());
		while (result.hasNext()){
			System.out.println(result.next());
		}
	}
	
	// Method to QUERY articles by id_revista using QBE example
	public void retrieveMagazineContentById_Revista(int _id) {
		System.out.println("\n\nDevuelve la revista por id");
		ObjectSet<Revista> result = db.queryByExample(new Revista(_id, null, null));
		while(result.hasNext()) {
			System.out.println(result.next());
		}
	}
	
	// Method to DELETE all objects from the database using QBE example
	public void clearDatabase() {
		System.out.println("\n\nLimpiando base de datos...");
		ObjectSet<Article> result = db.queryByExample(new Article(0,null,null,true,null));
		while(result.hasNext()) {
			db.delete(result.next());
		}

		ObjectSet<Autor> result2 = db.queryByExample(new Autor(0,null,null,null,true));
		while(result2.hasNext()) {
			db.delete(result2.next());
		}

		ObjectSet<Revista> result3 = db.queryByExample(new Revista(0,null,null));
		while(result3.hasNext()) {
			db.delete(result3.next());
		}
	}
	
	// Method to DELETE revistes by id_revista using QBE example
	public void deleteMagazineContentById_Revista(int _id) {
		ObjectSet<Revista> result = db.queryByExample(new Revista(_id, null, null));
		while(result.hasNext()) {
			db.delete(result.next());
		}
	}
	
	// Method to DELETE an autor from the database using QBE example
	public void deleteAutor(int id_autor) {
		ObjectSet<Autor> result = db.queryByExample(new Autor(id_autor, null, null, null, true));
		while(result.hasNext()) {
			db.delete(result.next());
		}
	}
	
	// Method to QUERY autors by nacionalitat using Native Queries
	public void llistaAutorsAlemanys() {
		System.out.println("\n\nAutors llegits des de la base de datos que son alemanes");
		List<Autor> autors = db.query(new Predicate<>() {
			public boolean match(Autor autor) {
				return autor.isActiu() && autor.getNacionalitat().compareTo("alemany") == 0;
			}
		});
		autors.forEach(System.out::println);
	}
	
	// Method to QUERY autors by name using Native Queries
	public void llistaAutorsByName(String _nom) {
		System.out.println("\n\nAutors llegits des de la base de datos el cual sus nombres son F. Perrier");
		List<Autor> autors = db.query(new Predicate<>() {
			public boolean match(Autor autor) {
				return autor.getNom().equals(_nom);
			}
		});
		autors.forEach(System.out::println);
	}
	
	// Method to QUERY articles by autor's name using Native Queries
	public void llistaArticlesByAutorName(String _nom) {
		System.out.println("\n\nArticles llegits des de la base de datos el su creador es R. Manito");
		List<Article> articles = db.query(new Predicate<>() {
			public boolean match(Article article) {
				return article.getAutor().getNom().equals(_nom);
			}
		});
		articles.forEach(System.out::println);
	}

	/* Este metodo borra el CAMPO autor del articulo el cual coincide, no se como hacer para que borre el articulo entero */
	public void deletingArticlesByAutorName(String _nom) {
		List<Article> articles = db.query(new Predicate<>() {
			public boolean match(Article article) {
				return article.getAutor().getNom().equals(_nom);
			}
		});
		db.delete(articles);
	}
}	