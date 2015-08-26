package br.com.ufpi.systematicmap.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelBubblePlots {
	
	@SuppressWarnings("serial")
	public static void main(String[] args) throws Exception {

		FileInputStream file = new FileInputStream(new File("/Users/Matheus/Dropbox/Easii/Teste de Usabilidade/Artigos e Outros Arquivos/Mapeamento Sistematico/Extracao/Extensão_do_Mapeamento2.xlsx"));
		XSSFWorkbook myWorkBook = new XSSFWorkbook (file);
		int rowTitles = 1, initArticles = 2, endArticles = 64;
		List<Integer> rowsignorearr = new ArrayList<Integer>() {{
		    add(26);
		    add(27);
		    add(35);
		    add(45);
		    add(47);
		    add(50);
		    add(59);
		    add(60);
		    add(63);
		    add(65);
		}};
		String path = "/Users/Matheus/Dropbox/Easii/Teste de Usabilidade/Artigos e Outros Arquivos/Mapeamento Sistematico/Extracao/Graphs/",
				file1 = path + "method-context.csv";
		
		matrix(file1, myWorkBook, rowTitles, new int[]{13,14,15,16,17}, new int[]{42,43,44}, initArticles, endArticles, rowsignorearr);
		
		myWorkBook.close();
		file.close();
	}
	
//	@SuppressWarnings("serial")
//	public static void main(String[] args) throws Exception {
//
//		FileInputStream file = new FileInputStream(new File("/Users/Matheus/Dropbox/Easii/Teste de Usabilidade/Artigos e Outros Arquivos/Mapeamento Sistematico/Extracao/Extensão_do_Mapeamento2.xlsx"));
//		XSSFWorkbook myWorkBook = new XSSFWorkbook (file);
//		int rowTitles = 1, initArticles = 2, endArticles = 64;
//		List<Integer> rowsignorearr = new ArrayList<Integer>() {{
//		    add(26);
//		    add(27);
//		    add(35);
//		    add(45);
//		    add(47);
//		    add(50);
//		    add(59);
//		    add(60);
//		    add(63);
//		    add(65);
//		}}; 
//		//int[] titles1 = new int[]{9,10,11,12};
//		//int[] titles2 = new int[]{13,14,15,16,17};
//		String path = "/Users/Matheus/Dropbox/Easii/Teste de Usabilidade/Artigos e Outros Arquivos/Mapeamento Sistematico/Extracao/Graphs/",
//				file1 = path + "method-aut.csv",
//				file2 = path + "method-effort.csv",
//				file3 = path + "method-intru.csv",
//				file4 = path + "method-valid.csv",
//				file5 = path + "method-tec.csv";
//		
//		matrix(file1, myWorkBook, rowTitles, new int[]{13,14,15,16,17}, new int[]{9,10,11,12}, initArticles, endArticles, rowsignorearr);
//		matrix(file2, myWorkBook, rowTitles, new int[]{13,14,15,16,17}, new int[]{18,19,20,21}, initArticles, endArticles, rowsignorearr);
//		matrix(file5, myWorkBook, rowTitles, new int[]{13,14,15,16,17}, new int[]{22,23,24,25,26,27,28,29,30,31,32,33}, initArticles, endArticles, rowsignorearr);
//		matrix(file3, myWorkBook, rowTitles, new int[]{13,14,15,16,17}, new int[]{36,37,38,39}, initArticles, endArticles, rowsignorearr);
//		matrix(file4, myWorkBook, rowTitles, new int[]{13,14,15,16,17}, new int[]{45,46,47,48}, initArticles, endArticles, rowsignorearr);
//		
//		myWorkBook.close();
//		file.close();
//	}
	
	
	private static void matrix(String file, XSSFWorkbook myWorkBook, int rowTitles, int[] titles1, int[] titles2, int initArticles, int endArticles, List<Integer> rowsignorearr) throws IOException{
		XSSFSheet mySheet = myWorkBook.getSheetAt(0);
		XSSFRow rowTitle = mySheet.getRow(rowTitles);
		
		System.out.print("\t");
		for(int j = 0; j < titles2.length; j++){
			String cellTitle = rowTitle.getCell(titles2[j]).getStringCellValue();
			System.out.print("|"+compactString(cellTitle) + "\t");
		}
		System.out.println();
		
		FileWriter writer = new FileWriter(file);
		writer.append("title1;title2;count\n");
		for(int i = 0; i < titles1.length; i++){
			String title1 = rowTitle.getCell(titles1[i]).getStringCellValue();
			System.out.print(compactString(title1) + "\t");
			for(int j = 0; j < titles2.length; j++){
				String title2 = rowTitle.getCell(titles2[j]).getStringCellValue();
				int count = 0;
				for(int a = initArticles; a <= endArticles; a++){
					if(!rowsignorearr.contains(a)){
						try{
							XSSFRow rowArticle = mySheet.getRow(a);
							XSSFCell cell1 = rowArticle.getCell(titles1[i]);
							XSSFCell cell2 = rowArticle.getCell(titles2[j]);
							if(cell1 != null && cell2 != null && 
									cell1.getCellType() == Cell.CELL_TYPE_STRING &&
									cell2.getCellType() == Cell.CELL_TYPE_STRING){
								String value1 = cell1.getStringCellValue();
								String value2 = cell2.getStringCellValue();
								if(value1.equals(value2) && value1.toLowerCase().equals("x")){
									count++;
								}
							}
						}catch(Exception e){
							e.printStackTrace();
						}
					}
				}
				System.out.print("|"+count + "\t");
				writer.append(title1+";"+title2+";"+count+"\n");
			}
			System.out.println();
		}
		System.out.println();
		writer.flush();
	    writer.close();
	}
	
	private static String compactString(String s){
		int maxLength = (s.length() < 5)?s.length():5;
		return s.substring(0, maxLength);
	}
}
