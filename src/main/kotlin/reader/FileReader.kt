package reader

import com.monitorjbl.xlsx.StreamingReader
import java.io.File
import java.io.FileInputStream
import javax.swing.JFileChooser
import javax.swing.filechooser.FileSystemView

object FileReader {

    fun pickFile(extension: String): String {
        val jfc = JFileChooser(FileSystemView.getFileSystemView().homeDirectory)
        jfc.currentDirectory = File(System.getProperty("user.dir"))
        val returnValue = jfc.showOpenDialog(null)
        return if (returnValue == JFileChooser.APPROVE_OPTION) {
            val selectedFile = jfc.selectedFile
            if (selectedFile.absolutePath.endsWith(extension)) return selectedFile.absolutePath.toString() else ""
        } else ""
    }

    fun getCategoryIdWithSG(filePath: String): MutableMap<String, ArrayList<String>> {
        val listOfCategories = mutableMapOf<String, ArrayList<String>>()
        val staplesDumpFile = FileInputStream(File(filePath))
        val workBook = StreamingReader.builder()
            .rowCacheSize(100) // number of rows to keep in memory (defaults to 10)
            .bufferSize(4096) // buffer size to use when reading InputStream to file (defaults to 1024)
            .open(staplesDumpFile) // Input

        val fullDump = mutableListOf<MutableList<String>>()
        workBook.getSheetAt(0).forEach { row ->
            val tmp = mutableListOf<String>()
            row.forEach { cell ->
                tmp.add(cell.stringCellValue)
            }
            fullDump.add(tmp)
        }
        // println(fullDump)
        fullDump.forEach { row ->
            val tmp = arrayListOf<String>()
            tmp.add(row[0])
            for (i in 3..16) {
                tmp.add(row[i])
            }
            tmp.add(row[0])
            listOfCategories[row[1]] = tmp
        }
        listOfCategories.forEach {
            println(it)
        }
        return listOfCategories
    }
}

fun main() {
    val file = File("/../../../Staples Style Guides.xlsx")
    println(file.absoluteFile.toString())
}