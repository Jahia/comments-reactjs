//<%--@elvariable id="currentNode" type="org.jahia.services.content.JCRNodeWrapper"--%>
//<%--@elvariable id="out" type="java.io.PrintWriter"--%>
//<%--@elvariable id="script" type="org.jahia.services.render.scripting.Script"--%>
//<%--@elvariable id="scriptInfo" type="java.lang.String"--%>
//<%--@elvariable id="workspace" type="java.lang.String"--%>
//<%--@elvariable id="renderContext" type="org.jahia.services.render.RenderContext"--%>
//<%--@elvariable id="currentResource" type="org.jahia.services.render.Resource"--%>
//<%--@elvariable id="url" type="org.jahia.services.render.URLGenerator"--%>

'use strict';
var ScriptingConstants = Java.type("org.jahia.services.render.scripting.thymeleaf.ScriptingConstants");
var ModuleService = function() {
  var Service = Java.type("org.jahia.services.render.scripting.thymeleaf.core.template.include.ModuleService");
  return {
    generate: function(path, nodeTypes) {
      var moduleService = new Service(renderContext,
        currentResource,
        null,
        null,
        null,
        path,
        null,
        null,
        nodeTypes,
        true,
        {}
      );
      return moduleService.doProcess();
    }
  }
}();

// var AreaService = Java.type("org.jahia.services.render.scripting.thymeleaf.core.template.include.AreaService");
// var areaService = new AreaService(renderContext,
//   currentResource,
//   "test",
//   ScriptingConstants.NT_JNT_CONTENT_LIST,
//   null,
//   null,
//   null,
//   ScriptingConstants.MODULE_TYPE_AREA,
//   null,
//   -1,
//   1,
//   false,
//   true,
//   true);
