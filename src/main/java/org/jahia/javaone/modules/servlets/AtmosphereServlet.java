package org.jahia.javaone.modules.servlets;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.atmosphere.cpr.AsyncSupportListener;
import org.atmosphere.cpr.AtmosphereFramework;
import org.atmosphere.cpr.AtmosphereHandler;
import org.atmosphere.cpr.AtmosphereInterceptor;
import org.atmosphere.cpr.AtmosphereRequest;
import org.atmosphere.cpr.AtmosphereResponse;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.jahia.javaone.modules.services.AtmosphereService;
import org.osgi.framework.BundleContext;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by smomin on 10/10/15.
 */
@Component(
        label = "Atmosphere Servlet",
        immediate = true,
        metatype = true
)
@Service(value = AtmosphereService.class)
public class AtmosphereServlet extends HttpServlet implements AtmosphereService {
    private final static Logger LOGGER = LoggerFactory.getLogger(AtmosphereServlet.class);
    private final static String MAPPING = "/atmosphere";
    private final AtmosphereFramework framework;

    @Reference
    private HttpService httpService;

    /**
     *
     * @param context
     */
    @Activate
    protected void start(final BundleContext context) {
        //Register the AtmosphereFramework as a Servlet.
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(AtmosphereFramework.class.getClassLoader());
        try {
            final Hashtable<String, Object> properties = new Hashtable<String, Object>();
            //no cache TODO property for cache ?
//        properties.put(ApplicationConfig.BROADCASTER_CACHE, "org.atmosphere.cache.HeaderBroadcasterCache");
            properties.put("org.atmosphere.cpr.AtmosphereInterceptor", "org.atmosphere.client.TrackMessageSizeInterceptor");
            properties.put("org.atmosphere.cpr.CometSupport.maxInactiveActivity","30000");
            httpService.registerServlet(MAPPING, this, properties, null);
            LOGGER.info("Successfully registered custom servlet at " + MAPPING);
        } catch (ServletException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (NamespaceException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            Thread.currentThread().setContextClassLoader(loader);
        }
    }

    /**
     *
     * @param bundleContext
     */
    @Deactivate
    protected void stop(final BundleContext bundleContext) {
        httpService.unregister(MAPPING);
        LOGGER.info("Successfully unregistered custom servlet from " + MAPPING);
    }

    /**
     *
     */
    public AtmosphereServlet() {
        //Instantiate the AtmosphereFramework
        framework = new AtmosphereFramework(false, false);
    }

    /**
     *
     * @param config
     * @throws ServletException
     */
    @Override
    public void init(final ServletConfig config) throws ServletException {
        super.init(config);
        framework.init(config);
    }

    /**
     *
     */
    @Override
    public void destroy() {
        framework.destroy(); //Destroy the AtmosphereFramework
        super.destroy();
    }

    /**
     *
     * @return
     */
    @Override
    public BroadcasterFactory getBroadcasterFactory() {
        return framework.getBroadcasterFactory();
    }

    /**
     *
     * @param mapping The AtmosphereHandler mapping. (e.g /toto)
     * @param handler The AtmosphereHandler to be added.
     */
    @Override
    public void addAtmosphereHandler(final String mapping,
                                     final AtmosphereHandler handler) {
        framework.addAtmosphereHandler(constructMapping(mapping), handler);
    }

    /**
     *
     * @param mapping
     * @param handler
     * @param interceptors
     */
    @Override
    public void addAtmosphereHandler(final String mapping,
                                     final AtmosphereHandler handler,
                                     final List<AtmosphereInterceptor> interceptors) {
        framework.addAtmosphereHandler(constructMapping(mapping), handler, interceptors);
    }

    /**
     *
     * @param mapping
     * @param handler
     * @param broadcaster
     * @param interceptors
     */
    @Override
    public void addAtmosphereHandler(final String mapping,
                                     final AtmosphereHandler handler,
                                     final Broadcaster broadcaster,
                                     final List<AtmosphereInterceptor> interceptors) {
        framework.addAtmosphereHandler(constructMapping(mapping), handler, broadcaster, interceptors);
    }

    /**
     *
     * @param mapping The existing AtmosphereHandler mapping.
     */
    @Override
    public void removeAtmosphereHandler(final String mapping) {
        framework.removeAtmosphereHandler(constructMapping(mapping));
    }

    /**
     *
     * @param asyncSupportListener
     */
    @Override
    public void addAsyncSupportListenerAdapter(final AsyncSupportListener asyncSupportListener) {
        framework.asyncSupportListener(asyncSupportListener);
    }

    /**
     * Construct the AtmosphereHandler mapping.
     * @param mapping the given mapping.
     * @return the correct mapping.
     */
    private String constructMapping(final String mapping){
        return (MAPPING.equals("/") ? "" : MAPPING) + (mapping.startsWith("/") ? mapping : "/" + mapping);
    }

    /**
     *
     * @param req
     * @param res
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        doPost(req, res);
    }

    /**
     *
     * @param req
     * @param res
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        framework.doCometSupport(AtmosphereRequest.wrap(req), AtmosphereResponse.wrap(res));
    }

    /**
     *
     * @param req
     * @param res
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doHead(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        doPost(req, res);
    }

    /**
     *
     * @param req
     * @param res
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doOptions(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        doPost(req, res);
    }

    /**
     *
     * @param req
     * @param res
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doTrace(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        doPost(req, res);
    }

    /**
     *
     * @param req
     * @param res
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        doPost(req, res);
    }

    /**
     *
     * @param req
     * @param res
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        doPost(req, res);
    }
}
