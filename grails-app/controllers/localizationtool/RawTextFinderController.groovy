package localizationtool

import org.apache.commons.lang.StringEscapeUtils
import groovy.util.logging.Log

class RawTextFinderController {
    def index() { }

    def generateReport(String path){
        //list all files recursively and pass each file to file parsers
        def dirPath = params.get('dirPath',System.properties["projectDirPath"])
        def currentDir = new File(dirPath);
        def fileTypes = params.get('fileTypes','.js,.html,.gsp,.groovy')

        currentDir.eachFileRecurse(
                {file ->
                    if(Utility.isValidFile(file, fileTypes.toString().toLowerCase())){
                        def resultantList
                        def parser;
                        parser =  ParserFactory.getParser(Utility.getExtensionFromFilename(file.name))
                        resultantList = parser.getReportEntries(file.text)
                        if(resultantList){
                            render("<br/><br/><b>Non Localized File Path:</b> "+file.path);
                            resultantList.each { listValue ->
                                render("<br/>"+StringEscapeUtils.escapeHtml(listValue.toString()))
                            }
                            //render("<br/><b>Non Localized File Path:</b> "+file.path+"<br/>"+StringEscapeUtils.escapeHtml(listOccurred.toListString()))
                        }
                    }
                }
        )

    }

}
