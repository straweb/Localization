package localizationtool

import org.apache.commons.lang.StringEscapeUtils
import localizationtool.parsers.PropertyParser

class DuplicateKeysController {

    def index() { }

    def generateReport(String path) {
        render("Welcome Here the report for duplicate keys def in properites file")
        def dirPath = params.get('dirPath', System.properties["projectDirPath"])
        dirPath += "\\grails-app\\i18n"
        def currentDir = new File(dirPath);
        def fileTypes = params.get('fileTypes', '.properties')

        def String enTextFile = ''
        def String deTextFile = ''
        def enKeys = []
        def deKeys = []
        def enKeysValues = []
        def deKeysValues = []

        //list all files recursively and pass each file to file parsers
        currentDir.eachFileRecurse(
                {file ->
                    if (Utility.isValidFile(file, fileTypes.toString().toLowerCase())) {
                        if (file.name.equalsIgnoreCase("messages.properties") || file.name.equalsIgnoreCase("messages-nimble.properties")) {
                            enTextFile += "\n\n" + file.text
                            enKeysValues += new PropertyParser().getAllKeysValues(file.text)
                            enKeys += new PropertyParser().getAllKeys(file.text)
                        } else if (file.name.equalsIgnoreCase("messages_de.properties") || file.name.equalsIgnoreCase("messages-nimble_de.properties")) {
                            deTextFile += "\n\n" + file.text
                            deKeysValues += new PropertyParser().getAllKeysValues(file.text)
                            deKeys += new PropertyParser().getAllKeys(file.text)
                        }
                    }
                }
        )

        def dupliKeys = PropertyParser.findDuplicates(enKeysValues)
        if (dupliKeys) {
            render("<br/><br/><b>Duplicate Keys in english found</b> ");
            dupliKeys.each { listValue ->
                render("<br/>" + StringEscapeUtils.escapeHtml(listValue.toString()))
            }
        }
        dupliKeys = PropertyParser.findDuplicates(deKeysValues)
        if (dupliKeys) {
            render("<br/><br/><b>Duplicate Keys in German found</b> ");
            dupliKeys.each { listValue ->
                render("<br/>" + StringEscapeUtils.escapeHtml(listValue.toString()))
            }
        }

        def missingKeys = PropertyParser.findMissingKey(enKeys, deKeys)
        if (missingKeys) {
            render("<br/><br/><b>Missing text for the Keys found in english</b> ");
            missingKeys.each { listValue ->
                render("<br/>" + StringEscapeUtils.escapeHtml(listValue.toString()))
            }
        }
        missingKeys = PropertyParser.findMissingKey(deKeys, enKeys)
        if (missingKeys) {
            render("<br/><br/><b>Missing text for the Keys found in German</b> ");
            missingKeys.each { listValue ->
                render("<br/>" + StringEscapeUtils.escapeHtml(listValue.toString()))
            }
        }

    }


}
