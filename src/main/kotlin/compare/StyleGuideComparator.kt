package compare

import data.Constants

object StyleGuideComparator {

    fun findNewCategories(
        oldSG: Map<String, ArrayList<String>>,
        newSG: Map<String, ArrayList<String>>
    ): Set<String> {
        val newCategories = newSG.keys.minus(oldSG.keys)
        if (newCategories.isNotEmpty()) {
            newCategories.forEach {
                println(newSG[it]?.get(0))
            }
        }
        return newSG.keys.minus(oldSG.keys)
    }

    fun findRemovedCategories(
        oldSG: Map<String, ArrayList<String>>,
        newSG: Map<String, ArrayList<String>>
    ): Set<String> {
        val removedCategories = oldSG.keys.minus(newSG.keys)
        if (removedCategories.isNotEmpty()) {
            removedCategories.forEach {
                println(oldSG[it]?.get(0))
            }
        }
        return oldSG.keys.minus(newSG.keys)
    }

    fun compareCategoryName(
        oldSG: Map<String, ArrayList<String>>,
        newSG: Map<String, ArrayList<String>>
    ): List<List<String>> {
        val result = mutableListOf<List<String>>()
        newSG.keys.forEach {
            if (!oldSG.containsKey(it)) {
                return@forEach
            }
            val oldSgCategoryName = oldSG[it]?.get(0).toString()
            val newSgCategoryName = newSG[it]?.get(0).toString()
            if (oldSgCategoryName != newSgCategoryName) {
                result.add(listOf(
                    it,
                    oldSgCategoryName,
                    newSgCategoryName

                ))
                println("Category name changed from \"$oldSgCategoryName\" to \"$newSgCategoryName\"")
            }
        }
        return result
    }

    fun compareShortName(
        oldSG: Map<String, ArrayList<String>>,
        newSG: Map<String, ArrayList<String>>
    ): String {
        newSG.keys.forEach {
            if (!oldSG.containsKey(it)) {
                return@forEach
            }
            val newShort = cutProblemCasesInShortName(newSG[it]?.get(1).toString())
            val oldShort = cutProblemCasesInShortName(oldSG[it]?.get(1).toString())
            if (newShort != oldShort) {
                println("New short name for category ${newSG[it]?.get(0)}\n\n${oldSG[it]?.get(1)}\"\n${newSG[it]?.get(1)}\n")
            }
        }
        return ""
    }

    fun compareFeatures(
        oldSG: Map<String, ArrayList<String>>,
        newSG: Map<String, ArrayList<String>>,
    ): MutableList<List<String>> {
        val result = mutableListOf<List<String>>()
        var changed = 0
        newSG.keys.forEach {
            if (!oldSG.containsKey(it)) {
                return@forEach
            }
            val oldShortName = cutProblemCasesInFeatures(oldSG[it]?.get(1).toString()).trim()
            val newShortName = cutProblemCasesInFeatures(newSG[it]?.get(1).toString()).trim()
            if (oldShortName != newShortName) {
                result.add(
                    listOf(
                        oldSG[it]?.get(0).toString(),
                        Constants.SKU_NAME,
                        oldSG[it]?.get(1).toString(),
                        newSG[it]?.get(1).toString()
                    )
                )
            }

            // val oldFeatures = cutProblemCasesInFeatures(oldSG[it]?.drop(2)!!.joinToString("|")).lowercase()
            for (i in 2..14) {
                val oldFeature = cutProblemCasesInFeatures(oldSG[it]?.get(i).toString()).trim()
                val newFeature = cutProblemCasesInFeatures(newSG[it]?.get(i).toString()).trim()
                if (oldFeature != newFeature) {
                    result.add(
                        listOf(
                            oldSG[it]?.get(0).toString(),
                            Constants.bullets[i - 2],
                            oldSG[it]?.get(i).toString(),
                            newSG[it]?.get(i).toString()
                        )
                    )
                }
            }
            // println("New features SG for ${newSG[it]?.get(0)}\n\n$newFeatures\n$oldFeatures\n")

        }
        result.forEach { line ->
            println("New features SG for ${line[0]}\n\n${line[1]}\n${line[2]}\n${line[3]}\n")
        }
        println("changed features ${result.size}")
        return result
    }

    // add here new cases that must be cut or replaced from check
    private fun cutProblemCasesInFeatures(str: String): String {
        return str.replace(" ", " ").replace("\\s+".toRegex(), " ")
            .replace("(\\| )|( \\|)".toRegex(), "|").replace("Standardss", "Standards").replace("&", "and")
            .replace("Certifications and Standards (ANSI/BIFMA)", "Certifications and Standards")
            .replace("decibals", "decibels")
            .replace("Safety Rating/Certifications", "Certifications and Standards")
            .replace("Deimanions", "Dimensions")
            .replace("Accesorry", "Accessory")
            .replace("<True Color (If Assorted = Include \"Colors\" after)>", "<True Color>")
            .replace("\"(\"<Manufacturer Model #>\")\"" , "(<Manufacturer Model #>)")

    }

    // add here new cases that must be cut from check
    private fun cutProblemCasesInShortName(str: String): String {
        return str.replace(" ", " ").replace("\\s+".toRegex(), " ")
            .replace("(\\| )|( \\|)".toRegex(), "|").replace("Standardss", "Standards")
    }
}