package com.iber.portal.common;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ExcelUtil {

    /**
     * 创建excel文档，
     * @param list 数据
     * @param keys list中map的key数组集合
     * @param columnNames excel的列名
     * */
    public static Workbook createWorkBook(List<Map<String, Object>> list,String []keys,String columnNames[]) {
        // 创建excel工作簿
        Workbook wb = new HSSFWorkbook();
        // 创建第一个sheet（页），并命名
        final Object sheetName = list.get(0).get("sheetName");
        Sheet sheet = wb.createSheet(Objects.toString(sheetName,"1"));
        // 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
        for(int i=0;i<keys.length;i++){
            sheet.setColumnWidth((short) i, (short) (35.7 * 150));
        }
        // 创建第一行
        Row row = sheet.createRow((short) 0);
        // 创建两种单元格格式
        CellStyle cs = wb.createCellStyle();
        CellStyle cs2 = wb.createCellStyle();

        // 创建两种字体
        Font f = wb.createFont();
        Font f2 = wb.createFont();

        // 创建第一种字体样式（用于列名）
        f.setFontHeightInPoints((short) 10);
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 创建第二种字体样式（用于值）
        f2.setFontHeightInPoints((short) 10);
        f2.setColor(IndexedColors.BLACK.getIndex());

//        Font f3=wb.createFont();
//        f3.setFontHeightInPoints((short) 10);
//        f3.setColor(IndexedColors.RED.getIndex());

        // 设置第一种单元格的样式（用于列名）
        cs.setFont(f);
        cs.setBorderLeft(CellStyle.BORDER_THIN);
        cs.setBorderRight(CellStyle.BORDER_THIN);
        cs.setBorderTop(CellStyle.BORDER_THIN);
        cs.setBorderBottom(CellStyle.BORDER_THIN);
        cs.setAlignment(CellStyle.ALIGN_CENTER);

        // 设置第二种单元格的样式（用于值）
        cs2.setFont(f2);
        cs2.setBorderLeft(CellStyle.BORDER_THIN);
        cs2.setBorderRight(CellStyle.BORDER_THIN);
        cs2.setBorderTop(CellStyle.BORDER_THIN);
        cs2.setBorderBottom(CellStyle.BORDER_THIN);
        cs2.setAlignment(CellStyle.ALIGN_CENTER);
        //设置列名
        for(int i=0;i<columnNames.length;i++){
            Cell cell = row.createCell(i);
            cell.setCellValue(columnNames[i]);
            cell.setCellStyle(cs);
        }
        //设置每行每列的值
        for (short i = 1; i < list.size(); i++) {
            // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
            // 创建一行，在页sheet上
            Row row1 = sheet.createRow((short)i);
            // 在row行上创建一个方格
            for(short j=0;j<keys.length;j++){
                Cell cell = row1.createCell(j);
                cell.setCellValue(list.get(i).get(keys[j]) == null?" ": list.get(i).get(keys[j]).toString());
                cell.setCellStyle(cs2);
            }
        }
        return wb;
    }
    /**
     * 创建excel文档，
     * @param list 数据
     * @param keys list中map的key数组集合
     * @param columnNames excel的列名
     * @param totalSum 添加合计行
     * */
    public static Workbook createWorkBook2(List<Map<String, Object>> list,String []keys,String columnNames[],String [] totalSum) {
        // 创建excel工作簿
        Workbook wb = new HSSFWorkbook();
        // 创建第一个sheet（页），并命名
        final Object sheetName = list.get(0).get("sheetName");
        Sheet sheet = wb.createSheet(Objects.toString(sheetName,"1"));
        // 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
        for(int i=0;i<keys.length;i++){
            sheet.setColumnWidth((short) i, (short) (35.7 * 150));
        }
        // 创建第一行
        Row row = sheet.createRow((short) 0);
        // 创建两种单元格格式
        CellStyle cs = wb.createCellStyle();
        CellStyle cs2 = wb.createCellStyle();

        // 创建两种字体
        Font f = wb.createFont();
        Font f2 = wb.createFont();

        // 创建第一种字体样式（用于列名）
        f.setFontHeightInPoints((short) 10);
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 创建第二种字体样式（用于值）
        f2.setFontHeightInPoints((short) 10);
        f2.setColor(IndexedColors.BLACK.getIndex());

        // 设置第一种单元格的样式（用于列名）
        cs.setFont(f);
        cs.setBorderLeft(CellStyle.BORDER_THIN);
        cs.setBorderRight(CellStyle.BORDER_THIN);
        cs.setBorderTop(CellStyle.BORDER_THIN);
        cs.setBorderBottom(CellStyle.BORDER_THIN);
        cs.setAlignment(CellStyle.ALIGN_CENTER);

        // 设置第二种单元格的样式（用于值）
        cs2.setFont(f2);
        cs2.setBorderLeft(CellStyle.BORDER_THIN);
        cs2.setBorderRight(CellStyle.BORDER_THIN);
        cs2.setBorderTop(CellStyle.BORDER_THIN);
        cs2.setBorderBottom(CellStyle.BORDER_THIN);
        cs2.setAlignment(CellStyle.ALIGN_CENTER);
        //设置列名
        for(int i=0;i<columnNames.length;i++){
            Cell cell = row.createCell(i);
            cell.setCellValue(columnNames[i]);
            cell.setCellStyle(cs);
        }
        //设置每行每列的值
        for (short i = 1; i < list.size(); i++) {
            // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
            // 创建一行，在页sheet上
            Row row1 = sheet.createRow((short) i);
            // 在row行上创建一个方格
            for(short j=0;j<keys.length;j++){
                Cell cell = row1.createCell(j);
                cell.setCellValue(list.get(i).get(keys[j]) == null?" ": list.get(i).get(keys[j]).toString());
                cell.setCellStyle(cs2);
            }
        }
   	 	// 创建最后一行  总合计 (如果没有合计字段请设置为null)
	 		Row row3 = sheet.createRow(sheet.getLastRowNum()+1);
    	 	Cell c2 = row3.createCell(0);
    	 	c2.setCellStyle(cs2);
    	 	c2.setCellValue("总合计");
	 		for (int k = 1; k < totalSum.length; k++) {
	 			Cell c3 = row3.createCell(k);
	   	 		c3.setCellStyle(cs2);
				c3.setCellValue(totalSum[k] == "" ? "" : totalSum[k]);
			}
        return wb;
    }
    public static Workbook createInvoiceWorkBook(List<Map<String, Object>> list,String[] keys,String fileName,String columnNames[],String[] secondColumnNames){
    	  Workbook wb = new HSSFWorkbook();
    	  Sheet sheet = wb.createSheet(fileName);
    	// 创建两种单元格格式
          CellStyle cs = wb.createCellStyle();
          CellStyle cs2 = wb.createCellStyle();
          CellStyle cs3 = wb.createCellStyle();
    	  // 创建两种字体
          Font f = wb.createFont();
          Font f2 = wb.createFont();
          Font f3 = wb.createFont();

          // 创建第一种字体样式（用于列名）
          f.setFontHeightInPoints((short) 12);
          f.setColor(IndexedColors.BLACK.getIndex());
          f.setBoldweight(Font.BOLDWEIGHT_BOLD);

          // 创建第二种字体样式（用于值）
          f2.setFontHeightInPoints((short) 12);
          f2.setColor(IndexedColors.BLACK.getIndex());
          
          f3.setFontHeightInPoints((short) 24);
          f3.setColor(IndexedColors.BLACK.getIndex());

          // 设置第一种单元格的样式（用于列名）
          cs.setFont(f);
          cs.setBorderLeft(CellStyle.BORDER_THIN);
          cs.setBorderRight(CellStyle.BORDER_THIN);
          cs.setBorderTop(CellStyle.BORDER_THIN);
          cs.setBorderBottom(CellStyle.BORDER_THIN);
          cs.setAlignment(CellStyle.ALIGN_CENTER);
          cs.setWrapText(true);  
          // 设置第二种单元格的样式（用于值）
          cs2.setFont(f2);
          cs2.setBorderLeft(CellStyle.BORDER_THIN);
          cs2.setBorderRight(CellStyle.BORDER_THIN);
          cs2.setBorderTop(CellStyle.BORDER_THIN);
          cs2.setBorderBottom(CellStyle.BORDER_THIN);
          cs2.setAlignment(CellStyle.ALIGN_CENTER);
          cs2.setWrapText(true);  
          // 设置第二种单元格的样式（用于值）
          cs3.setFont(f3);
          cs3.setBorderLeft(CellStyle.BORDER_THIN);
          cs3.setBorderRight(CellStyle.BORDER_THIN);
          cs3.setBorderTop(CellStyle.BORDER_THIN);
          cs3.setBorderBottom(CellStyle.BORDER_THIN);
          cs3.setAlignment(CellStyle.ALIGN_CENTER);
          cs3.setWrapText(true);  
    	  // 创建第一行
    	  
          Row row = sheet.createRow((short) 0);
          Cell cell = row.createCell(0);
          cell.setCellValue(fileName);
          cell.setCellStyle(cs3); row.setHeight((short)700);
          sheet.addMergedRegion(new CellRangeAddress(0,0,0,columnNames.length-1));//添加合并
          
          Row row2 = sheet.createRow((short) 1);
          Row row3 = sheet.createRow((short) 2);
          int secondIndex = 0;
          for (int i = 0; i <columnNames.length; i++) {
        	  sheet.setColumnWidth(i, 5000);
        	  Cell cell1 = row2.createCell(i);
        	  Cell cell2 = row3.createCell(i);
              cell1.setCellStyle(cs);
              cell2.setCellStyle(cs);
        	  if(8>i || i>11){
        		  sheet.addMergedRegion(new CellRangeAddress(1,2,i,i));//添加合并
        		  cell1.setCellValue(columnNames[i]);
        	  }else{
        		  if(i == 8 || i == 10){
        			  sheet.addMergedRegion(new CellRangeAddress(1,1,i,i+1));//添加合并
        			  cell1.setCellValue(columnNames[i]);
        		  }
        		String val = secondColumnNames[secondIndex++];
        		cell2.setCellValue(val);
        	  }
          }
      	  // 创建最后一行  总合计 (如果没有合计字段请设置为null)
    	  for (short i = 0; i < list.size(); i++) {
        	  Row createRow = sheet.createRow((short) i+3);
        	  for(short j=0;j<keys.length;j++){
        		  Cell cell1 = createRow.createCell(j);
                  cell1.setCellValue(list.get(i).get(keys[j]) == null?" ": list.get(i).get(keys[j]).toString());
                  cell1.setCellStyle(cs2);
        	  }
          }
    	  return wb;
    }
    /**
     * 创建excel文档，
     * @param list 数据
     * @param keys list中map的key数组集合
     * @param columnNames excel的列名
     * */
    public static Workbook createTimeShareWorkBook(List<Map<String, Object>> list,String []keys,String columnNames[],String [] totalSum) {
        // 创建excel工作簿
        Workbook wb = new HSSFWorkbook();
        // 创建第一个sheet（页），并命名
        Sheet sheet = wb.createSheet(list.get(0).get("sheetName").toString());
        // 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
        for(int i=0;i<keys.length;i++){
            sheet.setColumnWidth((short) i, (short) (35.7 * 150));
        }

        // 创建第一行
        Row row = sheet.createRow((short) 0);

        // 创建两种单元格格式
        CellStyle cs = wb.createCellStyle();
        CellStyle cs2 = wb.createCellStyle();

        // 创建两种字体
        Font f = wb.createFont();
        Font f2 = wb.createFont();

        // 创建第一种字体样式（用于列名）
        f.setFontHeightInPoints((short) 10);
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 创建第二种字体样式（用于值）
        f2.setFontHeightInPoints((short) 10);
        f2.setColor(IndexedColors.BLACK.getIndex());

//        Font f3=wb.createFont();
//        f3.setFontHeightInPoints((short) 10);
//        f3.setColor(IndexedColors.RED.getIndex());

        // 设置第一种单元格的样式（用于列名）
        cs.setFont(f);
        cs.setBorderLeft(CellStyle.BORDER_THIN);
        cs.setBorderRight(CellStyle.BORDER_THIN);
        cs.setBorderTop(CellStyle.BORDER_THIN);
        cs.setBorderBottom(CellStyle.BORDER_THIN);
        cs.setAlignment(CellStyle.ALIGN_CENTER);

        // 设置第二种单元格的样式（用于值）
        cs2.setFont(f2);
        cs2.setBorderLeft(CellStyle.BORDER_THIN);
        cs2.setBorderRight(CellStyle.BORDER_THIN);
        cs2.setBorderTop(CellStyle.BORDER_THIN);
        cs2.setBorderBottom(CellStyle.BORDER_THIN);
        cs2.setAlignment(CellStyle.ALIGN_CENTER);
        //设置列名
        for(int i=0;i<columnNames.length;i++){
            Cell cell = row.createCell(i);
            cell.setCellValue(columnNames[i]);
            cell.setCellStyle(cs);
        }
        //设置每行每列的值
        for (short i = 1; i < list.size(); i++) {
            // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
            // 创建一行，在页sheet上
            Row row1 = sheet.createRow((short) i);
            // 在row行上创建一个方格
            for(short j=0;j<keys.length;j++){
                Cell cell = row1.createCell(j);
                String value = String.valueOf(list.get(i).get(keys[j]));
//                String[] doubleKey = keys[j].split("/") ;
//                String value = "" ;
//                for(int v = 0 ; v < doubleKey.length ; v++) {
//                	if(doubleKey[v].equals("mileageTemp")){
//                		value += 1+"/" ;
//                	}else{
//                		value += list.get(i).get(doubleKey[v])== null?" ": list.get(i).get(doubleKey[v]).toString()+"/" ;
//                	}
//                }
//                if(value.length() > 0) {
//                	value = value.substring(0,value.length() - 1) ;
//                }
                cell.setCellValue(value);
                cell.setCellStyle(cs2);
            }
        }
        if (totalSum != null && totalSum.length > 0) {
        	// 创建最后一行  总合计
	 		Row row3 = sheet.createRow(sheet.getLastRowNum()+1);
    	 	Cell c2 = row3.createCell(0);
    	 	c2.setCellStyle(cs2);
    	 	c2.setCellValue("总合计");
	 		for (int k = 1; k < totalSum.length; k++) {
	 			Cell c3 = row3.createCell(k);
	   	 		c3.setCellStyle(cs2);
				c3.setCellValue(totalSum[k] == "" ? "" : totalSum[k]);
			}
		}
        return wb;
    }


    /**
     * 创建excel文档，
     * @param list 数据
     * @param keys list中map的key数组集合
     * @param templatePath 模板的路径
     * @param startCount 设置数据开始的行
     * */
    public static Workbook createWorkBookByTemplate(List<Map<String, Object>> list,String []keys,String templatePath,Integer startCount) throws Exception {
        // 创建excel工作簿
       // File file = new File("/template/ab.xlsx");

        POIFSFileSystem fileSystem = new POIFSFileSystem(ExcelUtil.class.getClassLoader().getResourceAsStream(templatePath));

        Workbook wb = new HSSFWorkbook(fileSystem);
        // 创建第一个sheet（页），并命名
        Sheet sheet = wb.getSheet("sheet1");

        CellStyle cs2 = wb.createCellStyle();
        Font f2 = wb.createFont();
        f2.setFontHeightInPoints((short) 10);
        f2.setColor(IndexedColors.BLACK.getIndex());
        //设置每行每列的值
        for (short i = 0; i < list.size(); i++) {
            // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
            // 创建一行，在页sheet上
            Row row1 = sheet.createRow((short) i+startCount);
            // 在row行上创建一个方格
            for(short j=0;j<keys.length;j++){
                Cell cell = row1.createCell(j);
                String value = String.valueOf(list.get(i).get(keys[j]));
                if (value!=null&&!value.equalsIgnoreCase("null")){
                    cell.setCellValue(value);
                }
                cell.setCellStyle(cs2);
            }
        }
        return wb;
    }
}
