package commonUtils;

import exception.BizException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Excel 表格工具类
 *
 * @author duxiangchao
 */
public class ExcelUtils {
    private final static String excel2003L = ".xls";    //2003- 版本的excel
    private final static String excel2007U = ".xlsx";   //2007+ 版本的excel

    public static List<List<Object>> getExcelData(InputStream inputStream, String fileName) {
        List<List<Object>> list;
        //创建Excel工作薄
        Workbook work = getWorkbook(inputStream, fileName);
        if (null == work) {
            throw new BizException("创建Excel工作薄为空！");
        }
        Sheet sheet;
        Row row;
        Cell cell;
        list = new ArrayList<>();
        // 只遍历第一个sheet
        sheet = work.getSheetAt(0);
        if (null == sheet) {
            throw new RuntimeException("上传的Excel数据为空！");
        }
        //遍历当前sheet中的所有行
        for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {//sheet.getFirstRowNum()
            row = sheet.getRow(j);
            if (row == null) {
                continue;
            }
            //遍历所有的列
            List<Object> li = new ArrayList<>();
            for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                cell = row.getCell(y);
                if (cell == null || "".equals(cell.toString())) {
                    li.add("");
                    continue;
                }
                li.add(getStringCellValue(cell));
            }
            list.add(li);
        }
        try {
            work.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 描述：根据文件后缀，自适应上传文件的版本
     *
     * @param inStr    输入流
     * @param fileName 文件
     * @return excel
     */
    private static Workbook getWorkbook(InputStream inStr, String fileName) {
        Workbook wb;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (excel2003L.equals(fileType)) {
            try {
                wb = new HSSFWorkbook(inStr);  //2003-
            } catch (IOException e) {
                e.printStackTrace();
                throw new BizException("上传失败 ! ");
            }
        } else if (excel2007U.equals(fileType)) {
            try {
                wb = new XSSFWorkbook(inStr);  //2007+
            } catch (IOException e) {
                e.printStackTrace();
                throw new BizException("上传失败 ! ");
            }
        } else {
            throw new BizException("解析的文件格式有误！");
        }
        return wb;
    }

    public static void setColumnToTextFormat(XSSFWorkbook targetWorkbook,Cell cell) {
        XSSFCellStyle cellStyle = targetWorkbook.createCellStyle();
        XSSFDataFormat format = targetWorkbook.createDataFormat();
        cellStyle.setDataFormat(format.getFormat("@"));
        cell.setCellStyle(cellStyle);
    }

    public static void setColumnToTextFormat(XSSFWorkbook targetWorkbook, XSSFSheet targetSheet, int startRow, int startColumn, int endRow, int endColumn) {
        XSSFCellStyle cellStyle = targetWorkbook.createCellStyle();
        XSSFDataFormat format = targetWorkbook.createDataFormat();
        cellStyle.setDataFormat(format.getFormat("@"));
        if (startRow < 0 || endRow < 0 || startColumn < 0 || endColumn < 0 || (startRow > endRow) || (startColumn > endColumn)) {
            throw new BizException("生成Excel格式参数错误!");
        }
        for (int rowIndex = startRow; rowIndex <= endRow; rowIndex++) {
            XSSFRow row = targetSheet.getRow(rowIndex);
            if (row == null) {
                row = targetSheet.createRow(rowIndex);
            }
            for (int columnIndex = startColumn; columnIndex <= endColumn; columnIndex++) {
                XSSFCell cell = row.getCell(columnIndex);
                if (cell == null) {
                    cell = row.createCell(columnIndex);
                }
                String rawValue1 = cell.getRawValue();
                cell.setCellStyle(cellStyle);
                if (StringUtil.isNotBlank(rawValue1)) {
                    cell.setCellValue(rawValue1);
                }
            }
        }
    }
    /**
     * 获取单元格数据内容为字符串类型的数据
     *
     * @param cell Excel单元格
     * @return String 单元格数据内容
     */
    private static String getStringCellValue(Cell cell) {
        String strCell;
        switch (cell.getCellTypeEnum()) {
            case STRING:
                strCell = cell.getStringCellValue();
                break;
            case NUMERIC:
                strCell = String.valueOf(new BigDecimal(Double.toString(cell.getNumericCellValue())));
                break;
            case BOOLEAN:
                strCell = String.valueOf(cell.getBooleanCellValue());
                break;
            case BLANK:
                strCell = "";
                break;
            case FORMULA:
                throw new RuntimeException("上传的Excel数据存在公式运算，请去掉公式！");
            default:
                strCell = "";
                break;
        }
        if (strCell == null || strCell.equals("")) {
            return "";
        }
        return strCell;
    }

    /**
     * 移除所有空行
     *
     * @param excelData excel文档
     */
    public static void removeEmptyLines(List<List<Object>> excelData) {
        Iterator<List<Object>> iterator = excelData.iterator();
        while (iterator.hasNext()) {
            List<Object> list = iterator.next();
            if (list.size() == 0) {
                iterator.remove();
                continue;
            }
            int num = 0;
            for (Object o : list) {
                if (o == null || "".equals(o.toString().trim())) {
                    num++;
                }
            }
            if (num == list.size()) {
                iterator.remove();
            }
        }
    }

    /**
     * Excel导出
     *
     * @param wb 文档对象
     * @author huangyuheng 20170823
     */
    public static InputStream buildStream(Workbook wb) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            wb.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new BizException("生成表格失败");
        }
    }

    /**
     * 模板生成
     */
    public static class Template {

        private XSSFWorkbook workbook;
        private XSSFRow title;
        private XSSFRow body;
        private int i = 0;

        public Template(String sheetName) {
            if (workbook == null) {
                workbook = new XSSFWorkbook();
            }
            // 创建sheet
            XSSFSheet sheet = workbook.createSheet(sheetName);
            // 创建表头信息
            title = sheet.createRow(0);
            // 创建示例内容
            body = sheet.createRow(1);
        }

        public Template addCell(String tv) {
            title.createCell(i++).setCellValue(tv);
            return this;
        }

        public Template addCell(double tv) {
            title.createCell(i++).setCellValue(tv);
            return this;
        }

        public Template addCell(String tv, String bv) {
            title.createCell(i).setCellValue(tv);
            body.createCell(i++).setCellValue(bv);
            return this;
        }

        public Template addCell(String tv, double bv) {
            title.createCell(i).setCellValue(tv);
            body.createCell(i++).setCellValue(bv);
            return this;
        }

        public Workbook build() {
            return workbook;
        }
    }
}

