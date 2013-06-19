package localizationtool.parsers

import localizationtool.FileParser
import org.apache.commons.lang.StringEscapeUtils

/**
 * Created with IntelliJ IDEA.
 * User: lenovo
 * Date: 3/6/13
 * Time: 11:32 AM
 * To change this template use File | Settings | File Templates.
 */
class PropertyParser implements FileParser {
    def getReportEntries(String fileText, type = 'value') {

        def prop = fileText.findAll('(?i)(.*)\\s*=')
        def value = fileText.findAll('(?i)=\\s*(.*)')

        if(type.equals("keys")){
            def uniqueProp = prop.unique()
//        def invalidKeyList = new NonKeyReporter().getInvalidKeys(prop);
            if (uniqueProp.size() != prop.size()){
                uniqueProp.add('Following are the unique keys and there respective values')
                return uniqueProp
            }
            return
        } else{
            value.unique()
//        def invalidKeyList = new NonKeyReporter().getInvalidKeys(prop);
            if (prop.size() != value.size()){
                return value
            }
            return
        }
    }

    def getAllKeys(String fileText){
        return fileText.findAll('(.*)\\s*=')
    }

    def getAllKeysValues(String fileText){
        return fileText.findAll('=\\s*(.*)')
    }

    def isKeyAssigned(String fileText, String key){
        return fileText.findAll('(.*)\\s*=')
    }

    static def findMissingKey(List setArr, List finderArr) {
        Set set = new HashSet()
        set.addAll(setArr)
       /* if(enArr.size() < deArr.size()){
            arr = deArr
            set.addAll(enArr)
        } else{
            arr = enArr
            set.addAll(deArr)
        }*/
        def missing = []
        for (int i = 0; i < finderArr.size(); i++) {
            if (set.contains(finderArr[i])) {
            } else {
                missing.add(finderArr[i])
            }
        }
        return missing
    }
    static def findDuplicates(List arr) {
        Set set = new HashSet()
        def dupli = []
        for (int i = 0; i < arr.size(); i++) {
            if (set.contains(arr[i])) {
                dupli.add(arr[i])
            } else {
                set.add(arr[i])
            }
        }
        return dupli
    }

    static def uniqueList(List arr) {
        Set set = new HashSet()
        def dupli = []
        for (int i = 0; i < arr.size(); i++) {
            if (set.contains(arr[i])) {
                dupli.add(arr[i])
            } else {
                set.add(arr[i])
            }
        }
        return set
    }

}
