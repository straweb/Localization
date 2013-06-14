package localizationtool.parsers

import org.apache.commons.lang.StringEscapeUtils
import localizationtool.FileParser

/**
 * Created with IntelliJ IDEA.
 * User: lenovo
 * Date: 3/6/13
 * Time: 5:46 PM
 * To change this template use File | Settings | File Templates.
 */
class HTMLParser implements FileParser{
    def getReportEntries(String fileText, type = 'positive'){

        fileText = fileText.replaceAll("(<!--)(.*?)(-->)"," ") // remove commented lines

        def listOccurred = fileText.findAll("(?i)<* (value|alt|title)\\s*=\\s*(\"|')[\"|']\\s*(?!\\\$\\{(.*?)\\})(.*?)(\"|')")
        // def listOccurred2 = fileText.findAll(gspRegExp2)
        //if(listOccurred2)
        //   render("<br/>  <u>Occurred2:</u> "+StringEscapeUtils.escapeHtml(listOccurred2.toListString()))
        def listOccurred3 = fileText.findAll("(?i)<*>(?!--}%)(?!\\s*<)(?!<g:)(?!:<)(?!&nbsp;<)(?!\\s*\\*\\s*<)(?!\\\$\\{(.*?)\\})(.*?)<")
//        if(listOccurred)
//            println("<br/> <u>Occurred1:</u> "+StringEscapeUtils.escapeHtml(listOccurred.toListString())+"<br/>")
//        if(listOccurred3)
//            return listOccurred3
        return listOccurred+listOccurred3

    }

    def getAllKeys(String fileText){
        return fileText.findAll('(?i)(.*)\\s*=')
    }

    def isKeyAssigned(String fileText, String key){
        def result =fileText.findAll(key)
        if(result && result.size() > 0)
            return true
        return false
    }
}
