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

        def htmlRegExpCase1 = "(?i)<* (value|alt|title)\\s*=\\s*\"(?!\")\\s*(?!\\\$\\{(.*?)\\})(.*?)\""
        def resultantList = fileText.findAll(htmlRegExpCase1)

        def htmlRegExpCase2 = "(?i)<* (value|alt|title)\\s*=\\s*'(?!')\\s*(?!\\\$\\{(.*?)\\})(.*?)'"
        resultantList += fileText.findAll(htmlRegExpCase2)

        def htmlREgExpCase3 = "(?i)<*>(?!--}%)(?![^a-z|A-Z])(?!\\s*<)(?!<g:)(?!:<)(?!&nbsp;<)(?!\\s*\\*\\s*<)(?!\\\$\\{(.*?)\\})(.*?)<"
        resultantList += fileText.findAll(htmlREgExpCase3)
        return resultantList

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
