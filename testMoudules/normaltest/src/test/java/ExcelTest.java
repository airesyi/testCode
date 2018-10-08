
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.*;

/**
 * auth: shi yi
 * create date: 2018/7/25
 */
public class ExcelTest {
    public static void main(String[] args) {
        ExcelTest obj = new ExcelTest();
        // 此处为我创建Excel路径：E:/zhanhj/studysrc/jxl下
        String filePath = ("/Users/temp/123.xlsx");
        new ExcelTest().read(filePath);
    }

    public void read(String filePath) {
        File file = new File(filePath);
        InputStream inputStream = null;
        Workbook workbook = null;
        try {
            inputStream = new FileInputStream(file);
            workbook = WorkbookFactory.create(inputStream);
            inputStream.close();
            //工作表对象
            Sheet sheet = workbook.getSheetAt(0);
            //总行数
            int rowLength = sheet.getLastRowNum() + 1;
            //工作表的列
            Row row = sheet.getRow(0);
            //总列数
            int colLength = row.getLastCellNum();
            //得到指定的单元格
            Cell cell = row.getCell(0);
            //得到单元格样式
            CellStyle cellStyle = cell.getCellStyle();
            System.out.println("行数：" + rowLength + ",列数：" + colLength);
            for (int i = 0; i < rowLength; i++) {
                row = sheet.getRow(i);
                for (int j = 0; j < colLength; j++) {
                    cell = row.getCell(j);
                    //Excel数据Cell有不同的类型，当我们试图从一个数字类型的Cell读取出一个字符串时就有可能报异常：
                    //Cannot get a STRING value from a NUMERIC cell
                    //将所有的需要读的Cell表格设置为String格式
                    if (cell != null)
                        cell.setCellType(CellType.STRING);

                    //对Excel进行修改
                    if (i > 0 && j == 1)
                        cell.setCellValue("1000");
                    System.out.print(cell.getStringCellValue() + "\t");
                }
                System.out.println();
            }

            //将修改好的数据保存
            OutputStream out = new FileOutputStream(file);
            workbook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
