class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}
        "/report/"(controller: "rawTextFinder", parseRequest: false) {
            action = [GET: "generateReport"]
        }

        "/duplicateKeys/"(controller: "duplicateKeys", parseRequest: false) {
            action = [GET: "generateReport"]
        }

        "/missingText/"(controller: "missingText", parseRequest: false) {
            action = [GET: "generateReport"]
        }

        "/invalidKeys/"(controller: "invalidKeys", parseRequest: false) {
            action = [GET: "generateReport"]
        }

        "/"(view:"/index")
		"500"(view:'/error')
	}
}
