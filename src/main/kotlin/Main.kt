// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import compare.StyleGuideComparator
import data.Constants
import reader.FileReader
import writer.FileWriter

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }
    var olgSG: Map<String, ArrayList<String>>? = null
    var newSG: Map<String, ArrayList<String>>? = null

    MaterialTheme {
        Column {
            Button(onClick = {
                val src = FileReader.pickFile(".xlsx")
                println(src)
                olgSG = FileReader.getCategoryIdWithSG(src)
            }) {
                Text("open old SG")
            }
            Button(onClick = {
                val src = FileReader.pickFile(".xlsx")
                println(src)
                newSG = FileReader.getCategoryIdWithSG(src)
            }) {
                Text("open new SG")
            }
            Button(onClick = {
                if (olgSG !== null && newSG !== null) {
//                    println(StyleGuideComparator.findNewCategories(olgSG!!, newSG!!))
//                    StyleGuideComparator.compareCategoryName(olgSG!!, newSG!!)
//                    StyleGuideComparator.compareShortName(olgSG!!, newSG!!)
//
//                    StyleGuideComparator.findRemovedCategories(olgSG!!, newSG!!)
                    FileWriter.writeToFile(
                        StyleGuideComparator.compareFeatures(olgSG!!, newSG!!),
                        listOf(
                            Constants.CATEGORY_NAME,
                            Constants.TYPE,
                            Constants.OLD_RULE,
                            Constants.NEW_RULE
                        ),
                        "SG update"
                    )
                } else {
                    println("some error")
                }

            }) {
                Text("make test")
            }
        }

    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
