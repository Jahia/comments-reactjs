//<%--@elvariable id="currentNode" type="org.jahia.services.content.JCRNodeWrapper"--%>
//<%--@elvariable id="out" type="java.io.PrintWriter"--%>
//<%--@elvariable id="script" type="org.jahia.services.render.scripting.Script"--%>
//<%--@elvariable id="scriptInfo" type="java.lang.String"--%>
//<%--@elvariable id="workspace" type="java.lang.String"--%>
//<%--@elvariable id="renderContext" type="org.jahia.services.render.RenderContext"--%>
//<%--@elvariable id="currentResource" type="org.jahia.services.render.Resource"--%>
//<%--@elvariable id="url" type="org.jahia.services.render.URLGenerator"--%>
load('classpath:/javascript/lib/handlebars-v3.0.3.js');

var JcrTagUtils = Java.type("org.jahia.taglibs.jcr.node.JCRTagUtils");
var FileHelper = Java.type('org.jahia.javaone.modules.utils.FileHelper');
var InetAddress = Java.type("java.net.InetAddress");

var host = renderContext.request.serverName;
var port = renderContext.request.serverPort;
var source = FileHelper.loadFileContent("http://" + InetAddress.localHost.hostName + ":" + port + url.currentModule + "/javascript/handlebar/template.hbs");
var template = Handlebars.compile(source);

var title = currentNode.properties['jcr:title'].string;
var nodes = JcrTagUtils.getChildrenOfType(currentNode, 'javaone:comment');
var items = [];
// nodes is getting passed as an nodeiterator.  we are converting it to an array.
for (var i in nodes) {
  var node = nodes[i];
  items.push(node.properties.text.string);
}

print("<script type='text/javascript' src='" + url.currentModule + "/javascript/utils.js'></script>");
print(template({
  "title": title,
  "comments": items,
  "identifier": currentNode.identifier,
  "workspace": workspace
}));
