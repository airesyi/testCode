import model.SetClass;
import org.apache.poi.ss.usermodel.*;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * auth: shi yi
 * create date: 2018/7/25
 */
public class ExcelTest {
    public static void main(String[] args) {
        ExcelTest obj = new ExcelTest();
        // 此处为我创建Excel路径：E:/zhanhj/studysrc/jxl下
        String filePath = ("/Users/temp/search.xls");
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
            Sheet sheet = workbook.getSheetAt(1);
            //总行数
            int rowLength = sheet.getLastRowNum() + 1;
            //工作表的列
            Row row = sheet.getRow(0);
            //第一行总列数
            int firstColLength = row.getLastCellNum();
            //得到指定的单元格
            Cell cell = row.getCell(0);
            //得到单元格样式
            CellStyle cellStyle = cell.getCellStyle();
            System.out.println("行数：" + rowLength + ",列数：" + firstColLength);
            for (int i = 0; i < rowLength; i++) {
                row = sheet.getRow(i);
                if (row != null) {
                    int colLength = row.getLastCellNum();
                    for (int j = 0; j < colLength; j++) {
                        if (row != null)
                            cell = row.getCell(j);
                        //Excel数据Cell有不同的类型，当我们试图从一个数字类型的Cell读取出一个字符串时就有可能报异常：
                        //Cannot get a STRING value from a NUMERIC cell
                        //将所有的需要读的Cell表格设置为String格式
                        if (cell != null) {
                            cell.setCellType(CellType.STRING);

                            //对Excel进行修改
//                    if (i > 0 && j == 1)
//                        cell.setCellValue("1000");
                            System.out.print(cell.getStringCellValue() + "\t");
                        }
                    }
                    System.out.println();
                } else {
                    System.out.println();
                }
            }

            //将修改好的数据保存
//            OutputStream out = new FileOutputStream(file);
//            workbook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void sbuStr() {
        String a = "1234567";
        StringBuffer sb1 = new StringBuffer("abc");
        StringBuffer sb2 = new StringBuffer(sb1);
        sb1.append("heheheh");
        System.out.println(sb2);

    }

    @Test
    public void setTest() {
        Set<SetClass> setClasses = new HashSet<>();
        List<SetClass> setClassList = new ArrayList<>();
        SetClass setClass1 = new SetClass(1, "aaa","nan");
        SetClass setClass2 = new SetClass(1, "aaa","nv");
        SetClass setClass3 = new SetClass(3, "bbb","nan");
        SetClass setClass4 = new SetClass(4, "aaa","nan");

        setClasses.add(setClass1);


        setClassList.add(setClass1);
        setClassList.add(setClass2);
        setClassList.add(setClass3);
        setClassList.add(setClass4);
        System.out.println(setClassList.size());
        System.out.println(setClassList.indexOf(setClass2));

        System.out.println(setClasses.size());
    }

}
