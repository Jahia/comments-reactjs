//<%--@elvariable id="currentNode" type="org.jahia.services.content.JCRNodeWrapper"--%>
//<%--@elvariable id="out" type="java.io.PrintWriter"--%>
//<%--@elvariable id="script" type="org.jahia.services.render.scripting.Script"--%>
//<%--@elvariable id="scriptInfo" type="java.lang.String"--%>
//<%--@elvariable id="workspace" type="java.lang.String"--%>
//<%--@elvariable id="renderContext" type="org.jahia.services.render.RenderContext"--%>
//<%--@elvariable id="currentResource" type="org.jahia.services.render.Resource"--%>
//<%--@elvariable id="url" type="org.jahia.services.render.URLGenerator"--%>

// Set variables server side
var InetAddress = Java.type("java.net.InetAddress");
var JcrTagUtils = Java.type("org.jahia.taglibs.jcr.node.JCRTagUtils");

var title = currentNode.properties['jcr:title'].string;
var nodes = JcrTagUtils.getChildrenOfType(currentNode, 'javaone:comment');
var host = renderContext.request.serverName;
var port = renderContext.request.serverPort;

// Load scripts into Nashorn Global space to execute application.js Server Side
load("classpath:/javascript/lib/react-0.14.0.min.js");
load("http://" + InetAddress.localHost.hostName + ":" + port + url.currentModule + "/javascript/react/application.js");

// Output Script markup so client side can load the proper libs to support the application.js
print("<script type='text/javascript' src='" + url.currentModule + "/javascript/lib/jquery.atmosphere.js'></script>");
print("<script type='text/javascript' src='" + url.currentModule + "/javascript/lib/react-0.14.0.min.js'></script>");
print("<script type='text/javascript' src='" + url.currentModule + "/javascript/lib/react-dom-0.14.0.min.js'></script>");
print("<script type='text/javascript' src='" + url.currentModule + "/javascript/socketService.js'></script>");
print("<script type='text/javascript' src='" + url.currentModule + "/javascript/react/application.js'></script>");

// Create two arrays, one to be used on the serverside and the other on the client side.
var items = [];
var clientItems = [];
// nodes is getting passed as an nodeiterator.  we are converting it to an array.
for (var i in nodes) {
  var node = nodes[i];
  items.push(node.properties.text.string);
  clientItems.push("'" + node.properties.text.string + "'");
}

// Execute server side script
print("<div id='comments-content'>" + renderServer(title, items) + "</div>");

// Output script client side to attach any event handlers
print("<script type='text/javascript'>$(function () {renderClient('" + title + "', [" + clientItems + "], '" + currentNode.identifier + "', '" + workspace + "');});</script>");
