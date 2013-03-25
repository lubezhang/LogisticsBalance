/**
  * 文件名：ExcelSheetCell.java
  * 描述：
  * 版本：v1.0
  * 作者：zhangqh
  * 创建日期：2009-3-30
  * 修改记录：（修改人）、（修改日期）、（修改内容）、（原因）
  *	
**/

package com.lube.utils.excel;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;

/**<p>功能说明：Excel Cell工具类</p>
 * <p>作者：zhangqh</p>
 * <p>时间：2009-3-30</p>
 */
public class ExcelSheetCell {
	
	private static HSSFRow row = null;
	private static HSSFCell cell = null;
	
	
	
	/**
	 * 用于产生当前excel标题
	 * @param firstRowValue [标题数组]
	 * @param style [当前单元格风格]
	 */
	public static void createCurrRowTitle(ExcelSheetRow sheetRow,ExcelWorkBook work ,String[] firstRowValue,HSSFCellStyle style) {
		row = sheetRow.createCurrSheetTitle(work);
		for (int i = 0; i < firstRowValue.length; i++) {
			cell = row.createCell((short) i);
			cell.setCellStyle(style);
//			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(firstRowValue[i]);
		}
	}
	
	/**
	 * 用于生成excel当前记录内容,标题除外
	 * @param beanList [当前数据列表,i=Object[]]
	 * @param style [当前单元格风格]
	 */
	public static void createCurrRowRecord(ExcelSheetRow sheetRow,ExcelWorkBook work,List beanList,HSSFCellStyle style) {
		Object[] obj = null;
		for (int i = 0; i < beanList.size(); i++) {
			row = sheetRow.createCurrSheetRecord(work,i);
			obj = (Object[]) beanList.get(i);
			if (obj != null) {
				createExcelCell(row, obj,style);
			}
		}
	}
	
	/**
	 * 需要以数组的方式提供当前每条记录
	 * 通过数组自动判断有多少列,生成当前行
	 */
	private static void createExcelCell(HSSFRow row, Object[] obj,HSSFCellStyle style) {
		try {
			for (int i = 0; i < obj.length; i++) {
				try {
					if (obj[i].toString() != null) {

						cell = row.createCell((short) i);
						cell.setCellStyle(style);
//						cell.setEncoding(HSSFCell.ENCODING_UTF_16);
						cell.setCellValue(obj[i].toString());
					}
				} catch (NullPointerException e) {
					continue;
				}

			}
		} catch (Exception ex) {
			System.out.print(ex);
		}
	}
}