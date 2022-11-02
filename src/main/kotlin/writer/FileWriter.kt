package writer

import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream

object FileWriter {
    private var seedFilePath = ""
    fun writeToFile(rowsToWrite: List<List<String>>, headers: List<String>, filename: String) {
        val myWorkBook = XSSFWorkbook()
        val myWorkList = myWorkBook.createSheet(filename)


        myWorkList.createRow(0)
        headers.forEachIndexed { i, str ->
            myWorkList.getRow(0).createCell(i).setCellValue(str)
        }
        val startIndex = myWorkList.count()
        rowsToWrite.forEachIndexed { i, value ->
            myWorkList.createRow(i + startIndex)
            value.forEachIndexed { j , str ->
                myWorkList.getRow(i + startIndex).createCell(j).setCellValue(str)

            }
        }
        val file = File("$filename.xlsx")
        val output = FileOutputStream(file)
        seedFilePath = file.absolutePath
        myWorkBook.write(output)
    }
}