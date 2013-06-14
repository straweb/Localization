package localizationtool

import org.apache.commons.lang.StringEscapeUtils

class MissingTextController {

    def index() { }


    def generateReport(String path) {
        render("Welcome Here the report for Missing Text for the keys def in non english properites files")
        //list all files recursively and pass each file to file parsers
        def dirPath = params.get('dirPath', System.properties["projectDirPath"])
        dirPath += "\\grails-app\\i18n"
        def currentDir = new File(dirPath);
        def fileTypes = params.get('fileTypes', '.properties')

        def String enTextFile = ''
        def String deTextFile = ''
        currentDir.eachFileRecurse(
                {file ->
                    if (Utility.isValidFile(file, fileTypes.toString().toLowerCase())) {
                        if (file.name.equalsIgnoreCase("messages.properties") || file.name.equalsIgnoreCase("messages-nimble.properties")) {
                            enTextFile += "\n\n" + file.text
                        } else {
                            deTextFile += "\n\n" + file.text
                        }
                    }
                }
        )

        def enLocaleKey = enTextFile.findAll("(\\b([a-z|A-Z|0-9|(\\.)])[a-z]*\\b)+=(.*)")
        Set set = new HashSet()
        set.addAll(enLocaleKey)
        def missingText = []
        deTextFile.eachLine(
                {  lineText, lineNo ->
                    if (set.contains(lineText)) {
                        missingText.add(lineText)
                    } else {
                        println("Missing Line " + lineText)
                    }
                }
        )

        if (missingText) {
            render("<br/><br/><b>Missing Text for the following english text </b>"+missingText.size()+"<br />");
            missingText.each { listValue ->
                render("<br/>" + StringEscapeUtils.escapeHtml(listValue.toString()))
            }
        }

    }


}
