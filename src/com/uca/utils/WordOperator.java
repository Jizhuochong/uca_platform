package com.uca.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class WordOperator {
	
	private XWPFDocument doc;
	
	private Map<String,String> textMap;

	private Map<String,String> textMap2;
	
	private File targetFile;
	
	public WordOperator(){
		
	}
	public WordOperator(String srcPath,String targetPath,Map<String,String> textMap,Map<String,String> textMap2) throws IOException{
		this.textMap = textMap;
		this.textMap2 = textMap2;
		this.targetFile = FileUtils.getFile(targetPath);
		OPCPackage pack = POIXMLDocument.openPackage(srcPath);
		this.doc = new XWPFDocument(pack);
	}
	
	public void processText(){
		this.processParagraphs();
		this.processTable();
	}
	
	public void outFile() throws IOException{
		FileOutputStream out = new FileOutputStream(this.targetFile);
		doc.write(out);
		out.close();
	}

	private void processParagraphs(){
		/*List<XWPFParagraph> paragraphs = doc.getParagraphs();
        for (XWPFParagraph tmp : paragraphs) {
            List<XWPFRun> runs = tmp.getRuns();
            for (XWPFRun r : runs) {
                for (Entry<String, String> e : textMap.entrySet()) {
                    if (r.getText(0).equals(e.getKey())) {
                        r.setText(textMap.get(r.getText(0)),0);
                    }
                }
            }
        }*/
		List<XWPFTable> tables = doc.getTables();
		int i = 0;
		for (Entry<String, String> e : textMap.entrySet()) {
            if (e.getKey()!= null && "sz".equals(e.getKey())) {
            	String key = e.getValue();
            	i = Integer.parseInt(key);
            }
        }
        for(XWPFTable table:tables){
        	List<XWPFTableRow> rows = table.getRows();
        	for(int k = 0; k <= rows.size(); k++){
        		if(k==14){
        			XWPFTableRow rowss = rows.get(k);
//        			Map<String,String> list = new HashMap<String, String>();
        			if(i>0){
        				List<String> list = new ArrayList<String>();
            			for(int j = 0; j < i; j++){
            				XWPFTableRow row1 = table.getRow(14+j);
            				List<XWPFTableCell> cells = row1.getTableCells();
            				for (int l = 0; l < cells.size(); l++) {
//            				w:for(XWPFTableCell cell:cells){
            					List<XWPFParagraph> cellParagraphs =cells.get(l).getParagraphs();
            					for (XWPFParagraph tmp : cellParagraphs) {
            						List<XWPFRun> runs = tmp.getRuns();
            						for (XWPFRun r : runs) {
            							String text = r.getText(0);
            							if(j<1)	list.add(text);
//            							for (Entry<String, String> e1 : list.entrySet()) {
            								String string = list.get(l);
            								for (Entry<String, String> e : textMap2.entrySet()) {
            									if (string!= null && (string+j).equals(e.getKey())) {
            										r.setText(textMap2.get(string+j),0);
            									}
            								}
    									}
            						}
            					}
            				table.addRow(row1, 14);
            			}
        			}
        			/*else if(i==2){
        				List<String> list = new ArrayList<String>();
            			for(int j = 0; j < i; j++){
            				XWPFTableRow row1 = table.getRow(14+j);
            				List<XWPFTableCell> cells = row1.getTableCells();
            				for (int l = 0; l < cells.size(); l++) {
//            				w:for(XWPFTableCell cell:cells){
            					List<XWPFParagraph> cellParagraphs =cells.get(l).getParagraphs();
            					for (XWPFParagraph tmp : cellParagraphs) {
            						List<XWPFRun> runs = tmp.getRuns();
            						for (XWPFRun r : runs) {
            							String text = r.getText(0);
            							if(j<1)	list.add(text);
//            							for (Entry<String, String> e1 : list.entrySet()) {
            								String string = list.get(l);
            								for (Entry<String, String> e : textMap2.entrySet()) {
            									if (string!= null && (string+j).equals(e.getKey())) {
            										r.setText(textMap2.get(string+j),0);
            									}
            								}
    									}
            						}
            					}
            				table.addRow(row1, 14);
            			}
        			}*/
        			else{
        				List<String> list = new ArrayList<String>();
            			for(int j = 0; j < 1; j++){
            				XWPFTableRow row1 = table.getRow(14+j);
            				List<XWPFTableCell> cells = row1.getTableCells();
            				for (int l = 0; l < cells.size(); l++) {
//            				w:for(XWPFTableCell cell:cells){
            					List<XWPFParagraph> cellParagraphs =cells.get(l).getParagraphs();
            					for (XWPFParagraph tmp : cellParagraphs) {
            						List<XWPFRun> runs = tmp.getRuns();
            						for (XWPFRun r : runs) {
            							String text = r.getText(0);
            							if(j<1)	list.add(text);
//            							for (Entry<String, String> e1 : list.entrySet()) {
            								String string = list.get(l);
            								for (Entry<String, String> e : textMap2.entrySet()) {
            									if (string!= null && (string+j).equals(e.getKey())) {
            										r.setText(textMap2.get(string+j),0);
            									}
            								}
    									}
            						}
            					}
            				table.addRow(row1, 14);
            			}
        			}
        		}
        		
        	}
        	table.removeRow(14);
        }
		
	}
	
	private void processTable(){
		List<XWPFTable> tables = doc.getTables();
        for(XWPFTable table:tables){
        	List<XWPFTableRow> rows = table.getRows();
        	for(XWPFTableRow row:rows){
        		List<XWPFTableCell> cells = row.getTableCells();
        		for(XWPFTableCell cell:cells){
        			List<XWPFParagraph> cellParagraphs =cell.getParagraphs();
        			for (XWPFParagraph tmp : cellParagraphs) {
        	            List<XWPFRun> runs = tmp.getRuns();
        	            for (XWPFRun r : runs) {
        	                for (Entry<String, String> e : textMap.entrySet()) {
        	                    if (r.getText(0)!= null && r.getText(0).equals(e.getKey())) {
        	                        r.setText(textMap.get(r.getText(0)),0);
        	                    }
        	                }
        	            }
        	        }
        		}
        	}
        }
	}
	
	public void processPicture(String tag,String picPath,int picType,double width,double height) throws Exception{
		File picture = new File(picPath);
		BufferedImage image = ImageIO.read(new FileInputStream(picture));
		List<XWPFParagraph> paragraphs = doc.getTables().get(2).getRows().get(0).getTableCells().get(1).getParagraphs();
        for (XWPFParagraph tmp : paragraphs) {
            List<XWPFRun> runs = tmp.getRuns();
            for (XWPFRun r : runs) {
                if(r.getText(0).equals(tag)){
                	tmp.setSpacingBefore(0);
                	tmp.setSpacingAfter(0);
            		r.addPicture(new FileInputStream(picture), picType, picPath, Units.toEMU(image.getWidth()*0.75), Units.toEMU(image.getHeight()*0.75));
            		r.setText("",0);
                }
            }
        }
	}

	public XWPFDocument getDoc() {
		return doc;
	}

	public void setDoc(XWPFDocument doc) {
		this.doc = doc;
	}

	public void processTelegraphPicture(String tag, String picPath, int picType, double width, double height) throws Exception {
		File picture = new File(picPath);
		BufferedImage image = ImageIO.read(new FileInputStream(picture));
		List<XWPFParagraph> paragraphs = doc.getTables().get(0).getRows().get(7).getTableCells().get(0).getParagraphs();
        for (XWPFParagraph tmp : paragraphs) {
            List<XWPFRun> runs = tmp.getRuns();
            for (XWPFRun r : runs) {
                if(r.getText(0).equals(tag)){
                	tmp.setSpacingBefore(0);
                	tmp.setSpacingAfter(0);
            		r.addPicture(new FileInputStream(picture), picType, picPath, Units.toEMU(image.getWidth()*0.75), Units.toEMU(image.getHeight()*0.75));
            		r.setText("",0);
                }
            }
        }
	}
	
	public void processPhonePicture(String tag, String picPath, int picType, double width, double height) throws Exception {
		File picture = new File(picPath);
		BufferedImage image = ImageIO.read(new FileInputStream(picture));
		List<XWPFParagraph> paragraphs = doc.getTables().get(1).getRows().get(0).getTableCells().get(0).getParagraphs();
        for (XWPFParagraph tmp : paragraphs) {
            List<XWPFRun> runs = tmp.getRuns();
            for (XWPFRun r : runs) {
                if(r.getText(0).equals(tag)){
                	tmp.setSpacingBefore(0);
                	tmp.setSpacingAfter(0);
            		r.addPicture(new FileInputStream(picture), picType, picPath, Units.toEMU(image.getWidth()*0.75), Units.toEMU(image.getHeight()*0.75));
            		r.setText("",0);
                }
            }
        }
	}
	public void processGenerateInstructionsPicture(String tag, String picPath, int picType, double width, double height) throws Exception {
		File picture = new File(picPath);
		BufferedImage image = ImageIO.read(new FileInputStream(picture));
		List<XWPFParagraph> paragraphs = doc.getTables().get(0).getRows().get(4).getTableCells().get(0).getParagraphs();
		for (XWPFParagraph tmp : paragraphs) {
			List<XWPFRun> runs = tmp.getRuns();
			for (XWPFRun r : runs) {
				if(r.getText(0).equals(tag)){
					tmp.setSpacingBefore(0);
					tmp.setSpacingAfter(0);
					r.addPicture(new FileInputStream(picture), picType, picPath, Units.toEMU(image.getWidth()*0.75), Units.toEMU(image.getHeight()*0.75));
					r.setText("",0);
				}
			}
		}
	}
	
	
}
