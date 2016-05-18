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
	
	private enum ClassificationEnum {
		//Abordagem de Automação
		Approach("Automation Approach (Q1)", new int[]{10,11,12,13}),
		//Método
		Method("Method (Q2)", new int[]{14,15,16,17,18}),
		//Esforço Necessário
		Effort("Required Effort (Q3)", new int[]{19,20,21,22}),
		//Grau de Intrusão
		Intrusion("Intrusion Degree (Q4)", new int[]{38,39,40,41}),
		//Avaliação Remota
		Remote("Remote Evaluation (Q5)", new int[]{42,43}),
		//Contexto de Aplicações Web
		Context("Web Application Context (Q6)", new int[]{44,45,46}),
		//Validação Empírica
		Validation("Empirical Validation (Q7)", new int[]{47,48,49,50}),
		//Tipo de Pesquisa
		ResearchType("Research Type (Q8)", new int[]{51,52,53,54,55,56});
		
		private String description;
		private int[] columns;
		
		ClassificationEnum(String description, int[] columns){
			this.description = description;
			this.columns = columns;
		}

		@SuppressWarnings("unused")
		public String getDescription() {
			return description;
		}

		public int[] getColumns() {
			return columns;
		}
	}
	
	public static void main(String[] args) throws IOException {
		String inputFilePath = "/Users/Matheus/Dropbox/Easii/Teste de Usabilidade/Artigos e Outros Arquivos/Mapeamento Sistematico/Extracao/Extensão_do_Mapeamento2.xlsx",
				outputDirectoryPath = "/Users/Matheus/Dropbox/Easii/Teste de Usabilidade/Artigos e Outros Arquivos/Mapeamento Sistematico/Extracao/Graphs/Journal/";
		
		int rowTitles = 1;
		
		ClassificationEnum eixoX, eixoY; 
		
		
		//GRAPH A
		eixoY = ClassificationEnum.Approach;
		eixoX = ClassificationEnum.Method;
		generateExcel(rowTitles, inputFilePath, outputDirectoryPath, eixoX, eixoY, false);
		
		eixoX = ClassificationEnum.Intrusion;
		generateExcel(rowTitles, inputFilePath, outputDirectoryPath, eixoX, eixoY, false);
		
		//GRAPH B
//		eixoY = ClassificationEnum.Context;
//		eixoX = ClassificationEnum.Method;
//		generateExcel(rowTitles, inputFilePath, outputDirectoryPath, eixoX, eixoY, false);
//		
//		eixoX = ClassificationEnum.Approach;
//		generateExcel(rowTitles, inputFilePath, outputDirectoryPath, eixoX, eixoY, false);
//		
//		eixoY = ClassificationEnum.Effort;
//		eixoX = ClassificationEnum.Method;
//		generateExcel(rowTitles, inputFilePath, outputDirectoryPath, eixoX, eixoY, false);
//		
//		eixoX = ClassificationEnum.Approach;
//		generateExcel(rowTitles, inputFilePath, outputDirectoryPath, eixoX, eixoY, false);
		
		
		//GRAPH C
//		eixoY = ClassificationEnum.Validation;
//		eixoX = ClassificationEnum.Method;
//		generateExcel(rowTitles, inputFilePath, outputDirectoryPath, eixoX, eixoY, false);
//		
//		eixoX = ClassificationEnum.Approach;
//		generateExcel(rowTitles, inputFilePath, outputDirectoryPath, eixoX, eixoY, false);
//		
//		eixoY = ClassificationEnum.ResearchType;
//		eixoX = ClassificationEnum.Method;
//		generateExcel(rowTitles, inputFilePath, outputDirectoryPath, eixoX, eixoY, false);
//		
//		eixoX = ClassificationEnum.Approach;
//		generateExcel(rowTitles, inputFilePath, outputDirectoryPath, eixoX, eixoY, false);
	}
	
	@SuppressWarnings("serial")
	private static void generateExcel(int rowTitles, String inputFilePath, String outputDirectoryPath, ClassificationEnum eixoX, ClassificationEnum eixoY, boolean showTitle) throws IOException{
		FileInputStream file = new FileInputStream(new File(inputFilePath));
		
		//OBS: CASO ALTERAR INITARTICLES, DEVE SER ALTERADO O ROWSIGNORE E O ENDARTICLES
		
		XSSFWorkbook myWorkBook = new XSSFWorkbook (file);
		int initArticles = 3, endArticles = 67;
		List<Integer> rowsignorearr = new ArrayList<Integer>() {{
		    add(27);
		    add(28);
		    add(36);
		    add(46);
		    add(48);
		    add(51);
		    add(60);
		    add(61);
		    add(64);
		    add(66);
		}};
		
		String file1 = outputDirectoryPath + eixoX.name() + "-" + eixoY.name() + ".csv";
		
		matrix(file1, myWorkBook, rowTitles, eixoX.getColumns(), eixoY.getColumns(), initArticles, endArticles, rowsignorearr, showTitle);
		
		myWorkBook.close();
		file.close();
	}
	
	private static void matrix(String file, XSSFWorkbook myWorkBook, int rowTitles, int[] titles1, int[] titles2, int initArticles, int endArticles, List<Integer> rowsignorearr, boolean showTitle) throws IOException{
		XSSFSheet mySheet = myWorkBook.getSheetAt(0);
		XSSFRow rowTitle = mySheet.getRow(rowTitles);
		
		System.out.print("\t");
		for(int j = 0; j < titles2.length; j++){
			String cellTitle = rowTitle.getCell(titles2[j]).getStringCellValue();
			cellTitle = showTitle ? cellTitle : new String(cellTitle.charAt(0) + new Integer(j).toString());
			System.out.print("|"+compactString(cellTitle) + "\t");
		}
		System.out.println();
		
		FileWriter writer = new FileWriter(file);
		writer.append("title1;title2;count\n");
		for(int i = 0; i < titles1.length; i++){
			String title1 = rowTitle.getCell(titles1[i]).getStringCellValue();
			title1 = showTitle ? title1 : new String(title1.charAt(0) + new Integer(i).toString());
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
