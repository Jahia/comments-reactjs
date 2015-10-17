package org.jahia.javaone.modules.handlers;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Reference;
import org.atmosphere.cpr.AtmosphereInterceptor;
import org.atmosphere.cpr.AtmosphereResponse;
import org.atmosphere.handler.OnMessage;
import org.atmosphere.interceptor.AtmosphereResourceLifecycleInterceptor;
import org.atmosphere.interceptor.BroadcastOnPostAtmosphereInterceptor;
import org.jahia.javaone.modules.services.AtmosphereService;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component(
        label = "Comment Handler",
        immediate = true,
        metatype = true
)
public class CommentHandler extends OnMessage<String> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentHandler.class);
    private String mapping = "/comment";
    private List<AtmosphereInterceptor> interceptors;

    @Reference
    private HttpService httpService;

    @Reference
    private AtmosphereService atmosphereService;

    /**
     *
     * @param ctx
     */
    @Activate
    protected void start(final ComponentContext ctx) {
        try {
            //Register the server (itself)
            interceptors = new ArrayList<AtmosphereInterceptor>();
            interceptors.add(new BroadcastOnPostAtmosphereInterceptor());
            interceptors.add(new AtmosphereResourceLifecycleInterceptor());
            atmosphereService.addAtmosphereHandler(mapping, this, interceptors);

            httpService.registerResources(mapping, "/web", null);
            LOGGER.info("Successfully registered custom servlet at " + mapping);
        } catch (NamespaceException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     *
     * @param bundleContext
     */
    @Deactivate
    protected void stop(final BundleContext bundleContext) {
        httpService.unregister(mapping);
        atmosphereService.removeAtmosphereHandler(mapping);
        interceptors.clear();
    }

    /**
     *
     * @param atmosphereResponse
     * @param s
     * @throws IOException
     */
    @Override
    public void onMessage(final AtmosphereResponse atmosphereResponse,
                          final String s) throws IOException {
        try {
            final JSONObject json = new JSONObject(s);
            json.accumulate("time", new Date().getTime());
            atmosphereResponse.getWriter().write(json.toString());
        } catch (JSONException e) {
            throw new IOException(e);
        }
    }
}
