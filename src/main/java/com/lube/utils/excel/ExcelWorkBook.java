/**
  * 文件名：ExcelWorkBook.java
  * 描述：
  * 版本：v1.0
  * 作者：zhangqh
  * 创建日期：2009-3-30
  * 修改记录：（修改人）、（修改日期）、（修改内容）、（原因）
  *	
**/

package com.lube.utils.excel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**<p>功能说明：Excel WorkBook工具类</p>
 * <p>作者：zhangqh</p>
 * <p>时间：2009-3-30</p>
 */
public class ExcelWorkBook {
	
	public HSSFWorkbook workbook = null;
	public static HSSFWorkbook workbookTemp = null;
	//设置当前workbookName
	private String workbookName = null;
	private HSSFSheet sheet = null;
	private FileOutputStream fileOut;
	
	public ExcelWorkBook() {
		if(workbook != null) {
			workbook = null;
		}
		workbook = workbookTemp;
	}
	
	public ExcelWorkBook(String workbookName) {
		workbook = workbookTemp;
		setWorkbookName(workbookName);
	}
	public String getWorkbookName() {
		return workbookName;
	}

	public void setWorkbookName(String workbookName) {
		workbookName = workbookName;
	}

	public HSSFSheet getSheet() {
		sheet = workbook.createSheet(getWorkbookName());
		return sheet;
	}
	
	/**
	 * 用于stylUtils的所需要的workbook必须项所做的处理
	 * @return
	 */
	public static HSSFWorkbook getWorkbook() {
		return workbookTemp;
	}

	public static void setWorkbook(HSSFWorkbook workbook) {
		workbookTemp = workbook;
	}
	
	/**
	 * 输入当前WorkBook为下载临时文件记录
	 * @param excelName
	 */
	public void writerFileStream(String excelName) {
		try {
			fileOut = new FileOutputStream(excelName);
			workbook.write(fileOut);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fileOut.flush();
				fileOut.close();
				if(workbook != null) {
					workbook = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
