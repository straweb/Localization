package localizationtool

import localizationtool.parsers.GSPFileParser
import localizationtool.parsers.GroovyParser
import localizationtool.parsers.PropertyParser
import localizationtool.parsers.HTMLParser
import localizationtool.parsers.JScriptParser

/**
 * Created with IntelliJ IDEA.
 * User: lenovo
 * Date: 3/6/13
 * Time: 11:49 AM
 * To change this template use File | Settings | File Templates.
 */
class ParserFactory {
    private def ParserFactory() {

    }

    def static FileParser getParser(String parserType) {
        if (parserType.isEmpty()) {
            return
        }
        if (parserType.indexOf('gsp') > -1) {
            return new GSPFileParser()
        } else if (parserType.indexOf('groovy') > -1) {
            return new GroovyParser()
        } else if (parserType.indexOf('properties') > -1) {
            return new PropertyParser()
        } else if (parserType.indexOf('html') > -1) {
            return new HTMLParser()
        } else if (parserType.indexOf('js') > -1) {
            return new JScriptParser()
        }
    }
}
