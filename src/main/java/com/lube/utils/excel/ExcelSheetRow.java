/**
  * 文件名：ExcelSheetRow.java
  * 描述：
  * 版本：v1.0
  * 作者：zhangqh
  * 创建日期：2009-3-30
  * 修改记录：（修改人）、（修改日期）、（修改内容）、（原因）
  *	
**/

package com.lube.utils.excel;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;


/**<p>功能说明：Excel Row工具类</p>
 * <p>作者：zhangqh</p>
 * <p>时间：2009-3-30</p>
 */

public class ExcelSheetRow {
	
	
	public ExcelSheetRow() {

	}

	public static HSSFSheet sheet = null;
	/**
	 * 设置当前Sheet名字
	 */
	private static String sheetName = null;
	private static HSSFRow row = null;
	
	
	/**
	 * 创建当前标题行
	 * @return
	 */
	public static HSSFRow createCurrSheetTitle(ExcelWorkBook work) {
	   HSSFSheet sheet = work.getSheet();
	   row = sheet.createRow(0);
	   return row;
	}
	
	/**
	 * 创建当前excel记录内容
	 * @param i
	 * @return
	 */
	public static HSSFRow createCurrSheetRecord(ExcelWorkBook work,int i) {
		HSSFSheet sheet = work.getSheet();
		row = sheet.createRow(i+1);
		return row;
	} 

	public static String getSheetName() {
		return sheetName;
	}

	public static void setSheetName(String sheetName) {
		ExcelSheetRow.sheetName = sheetName;
	}

}
