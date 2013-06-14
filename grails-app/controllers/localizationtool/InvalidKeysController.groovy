package localizationtool

import org.apache.commons.lang.StringEscapeUtils
import java.util.concurrent.CopyOnWriteArrayList

class InvalidKeysController {
    def index() {}

    def generateReport(String path) {
        //list all files recursively and pass each file to file parsers
        def dirPath = params.get('dirPath', System.properties["projectDirPath"])
        def currentDir = new File(dirPath+"\\grails-app\\i18n");
        def CopyOnWriteArrayList listOfKeys = []
        def CopyOnWriteArrayList invalidKeys = []
        currentDir.eachFileRecurse(
                {file ->
                    if (Utility.isValidFile(file, "properties")) {
                        def parser;
                        parser = ParserFactory.getParser(Utility.getExtensionFromFilename(file.name))
                        listOfKeys += parser.getAllKeys(file.text)
                    }
                }
        )
        listOfKeys = listOfKeys.unique()
        invalidKeys = []
        currentDir = new File(dirPath);

        currentDir.eachFileRecurse(
                {file ->
                    if (Utility.isValidFile(file, ".gsp,.groovy,.html,.js")) {
                        listOfKeys.eachWithIndex { listValue, index ->
                            def parser;
                            parser = ParserFactory.getParser(Utility.getExtensionFromFilename(file.name))
                            if (!parser.isKeyAssigned(file.text, ((listValue.findAll('(\\b([a-z|A-Z|0-9|(\\.)])[a-z]*\\b)+'))[0]) )) {
                                invalidKeys.add(listValue)
                            }
                        }
                        if (invalidKeys && invalidKeys.size()>0){
                            listOfKeys = invalidKeys
                            invalidKeys = null
                            invalidKeys = []
                        }
                    }
                }
        )


        if (listOfKeys) {
            listOfKeys = listOfKeys.unique()
            render("<b>List of keys that are missing in the code</b> count: ${listOfKeys.size()}<br/>")
            listOfKeys.each { listValue ->
                render("<br/>" + StringEscapeUtils.escapeHtml(listValue.toString()))
            }
        } else{
            render("There are no invalid keys")
        }

    }
/*

    def checkInvalidKeys() {

        listOfKeys.eachWithIndex { listValue, index ->
            if (isInvalidKey((listValue.findAll('(\\b([a-z|A-Z|0-9|(\\.)])[a-z]*\\b)+'))[0])) {
                invalidKeys.add(listValue)
            }

        }
    }

    def isInvalidKey(String key) {
        def dirPath = params.get('dirPath', "C:\\Users\\lenovo\\Documents\\affinnova\\affiserv")
        def currentDir = new File(dirPath);
        currentDir.eachFileRecurse(
                {file ->
                    if (Utility.isValidFile(file, ".gsp,.groovy,.html")) {
                        parser = ParserFactory.getParser(Utility.getExtensionFromFilename(file.name))
                        if (!parser.isKeyAssigned(file.text, key)) {
                            return true
                        }
                    }
                }
        )
        return false
    }
*/

}
