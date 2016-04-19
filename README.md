#Comments Content Type#
This POC is to demonstrate the capabilities of Jahia Digital Factory to execute other scripting languages.  In this demo, we will be using Jahia Digital Factory, Java 8 (Nashorn), ReactJS, Handlebars, Underscore, Thymeleaf, and Atmosphere.

##Requirements##
* JDK 1.8.0_60
* Jahia Digital Factory 7.2, Enterprise Distribution SDK, `https://www.jahia.com/documentation-and-downloads/developers-downloads`.
* Apache Felix SCR 2.0.2, `http://www.eu.apache.org/dist//felix/org.apache.felix.scr-2.0.2.jar`, deploy bundle `http://localhost:8080/tools/osgi/console/bundles`.
* Scripting Language Thymeleaf, `https://github.com/Jahia/scriptlanguages-thymeleaf`

##JSX##
* `mvn clean install` will process the `*.jsx` in the react `src/main/resources/javascript/react`.
* When in active development, use `node node_modules/gulp/bin/gulp.js --watch` to watch changes to `*.jsx` and process to `*.js`.  Run this command after a first time execution of `mvn clean install`.  Also, this command should be executed where the `gulpfile.js` is located.

##Site Export##
* Site export can be found in the `export` folder.  Go to `http://localhost:8080/cms/admin/default/en/settings.webProjectSettings.html`.

##Test##
* http://localhost:8080/sites/javaone/home.html
* Login root/root.  User will need to log in to properly test out the demo.  The REST API used to create the comment requires write permission to store in the JCR.
* Add comment.
