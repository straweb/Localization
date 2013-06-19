package localizationtool.parsers

import localizationtool.FileParser
import org.apache.commons.lang.StringEscapeUtils

/**
 * Created with IntelliJ IDEA.
 * User: lenovo
 * Date: 11/6/13
 * Time: 11:20 AM
 * To change this template use File | Settings | File Templates.
 */
class JScriptParser implements FileParser{

    def getReportEntries(String fileText, type = 'positive'){

        def jScripRegEx = "(?i)(alert|displayToasterMessage|_confirmDialog|showMsg|_okDialog|displayMessage|_showErrorMessage)\\s*\\(\\s*('|\")(?!keys\\[(.*?)\\])(.*?)(\"|')"
        def resultantList = fileText.findAll(jScripRegEx)

        def jScriptRegExCase2 = "(?i)(_okDialog|showMsg)\\s*\\(\\s*('|\")(?!keys\\[(.*?)\\])(.*?)(\"|')\\s*,\\s*('|\")(?!keys\\[(.*?)\\])(.*?)(\"|')"
        resultantList += fileText.findAll(jScriptRegExCase2)

        def jScriptRegExCase31 = "(?i)(label|title)\\s*:\\s*(')(?!keys\\[(.*?)\\])(.*?)(')"
        resultantList += fileText.findAll(jScriptRegExCase31)

        def jScriptRegExCase32 = "(?i)(label|title)\\s*:\\s*(\")(?!keys\\[(.*?)\\])(.*?)(\")"
        resultantList += fileText.findAll(jScriptRegExCase32)

        def jScriptRegExCase4 = "(?i)(return\\s*)(\")(?!keys\\[(.*?)\\])(.*?)(\")"
        resultantList += fileText.findAll(jScriptRegExCase4)

        def jScriptRegExCase41 = "(?i)(return\\s*)(')(?!keys\\[(.*?)\\])(.*?)(')"
        resultantList += fileText.findAll(jScriptRegExCase41)

        def jScriptRegExCase5 = "(?i)(innerHTML|value|msg|errMsg|message)\\s*=\\s*(')[^'](?!keys\\[(.*?)\\])(.*?)(')"
        resultantList += fileText.findAll(jScriptRegExCase5)

        def jScriptRegExCase6 = "(?i)(innerHTML|value|msg|errMsg|message)\\s*=\\s*(\")[^\"](?!keys\\[(.*?)\\])(.*?)(\")"
        resultantList += fileText.findAll(jScriptRegExCase6)
/*
        def jScriptRegExCase6 = ""
        resultantList += fileText.findAll(jScriptRegExCase6)

        def jScriptRegExCase7 = "(?i)(return\\s*)('|\")(?!keys\\[(.*?)\\])(.*?)(\"|')"
        resultantList += fileText.findAll(jScriptRegExCase7)
*/

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
