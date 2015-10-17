#Comments Content Type#
This POC is to demonstrate the capabilities of Jahia Digital Factory to execute other scripting languages.  In this demo, we will be using Jahia Digital Factory, Java 8 (Nashorn), ReactJS, Handlebars, Underscore, and Atmosphere.

##Requirements##
* JDK 1.8_40, If you want to use JDK 1.8_60, `<jdk>\jre\lib\security\java.security` set `keystore.type.compat` to false.
* Jahia Digital Factory 7.1, Enterprise Distribution SDK, `https://www.jahia.com/documentation-and-downloads/developers-downloads`.
* Apache Felix SCR 2.0.2, `http://www.eu.apache.org/dist//felix/org.apache.felix.scr-2.0.2.jar`, deploy bundle `http://localhost:8080/tools/osgi/console/bundles`.

##Adjustments##
* Remove `js-1.7R2.jar` in `/EnterpriseDistribution-DigitalFactory7100-SDK/tomcat/webapps/ROOT/WEB-INF/lib`.  Jahia Digital Factory is bundled with Rhino.  In order to use Java 8 native Javascript compiler, this file needs to be removed.
* Replace `ahia-impl-7.1.0.0.jar` in `/EnterpriseDistribution-DigitalFactory7100-SDK/tomcat/webapps/ROOT/WEB-INF/lib`.  This patch allows for hot deploy of the Javascript view template.
* Deploy `org.jahia.bundles.extender.jahiamodules-7.1.0.0.jar` bundle in `http://localhost:8080/tools/osgi/console/bundles`.  This patch allows for hot deploy of the Javascript view template.

##JSX##
* `mvn clean install` will process the `*.jsx` in the react `src/main/resources/javascript/react`.
* When in active development, use `node node_modules/gulp/bin/gulp.js --watch` to watch changes to `*.jsx` and process to `*.js`.  Run this command after a first time execution of `mvn clean install`.  Also, this command should be executed where the `gulpfile.js` is located.

##Site Export##
* Site export can be found in the `export` folder.  Go to `http://localhost:8080/cms/admin/default/en/settings.webProjectSettings.html`.

##Test##
* http://localhost:8080/sites/javaone/home.html
* Login root/root.  Login is needed because the demo requires write permission to save comment on the server.
* Add comment.
