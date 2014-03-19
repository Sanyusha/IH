package com.example.news;

import java.util.Vector;

public class Parser {
	
	private String mFile;
	private Vector<String> lines;
	private HeadArticle headArticle;
	private Vector<SubArticle> subArticles;
	private Vector<Category> categories;
	
	
	public Parser(String file){
		mFile = file;
	}
	
	//fills the vector "lines" 
	public void readFile(){
	
	}
	
	//fills the Dasts
	public void parse(){
		
	}
	
	public HeadArticle getHeadArticle(){
		return headArticle;
	}
	
	public Vector<SubArticle> getSubArticles(){
		return subArticles;
	}
	
	public Vector<Category> getCategories(){
		return categories;
	}
}
