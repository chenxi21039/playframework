/*
 * Copyright (C) 2009-2015 Typesafe Inc. <http://www.typesafe.com>
 */
package play.mvc;

/**
 * Defines a 'call', describing an HTTP request. For example used to create links or populate redirect data.
 * <p>
 * These values are usually generated by the reverse router.
 */
public abstract class Call {

    private static java.util.Random rand = new java.util.Random();

    /**
     * The request URL.
     *
     * @return the url
     */
    public abstract String url();

    /**
     * The request HTTP method.
     *
     * @return the http method (e.g. "GET")
     */
    public abstract String method();

    /**
     * The fragment of the URL.
     *
     * @return the fragment (without leading '#' character)
     */
    public abstract String fragment();

    /**
     * Append a unique identifier to the URL.
     *
     * @return a copy if this call with a unique identifier to this url
     */
    public Call unique() {
        return new play.api.mvc.Call(method(), this.uniquify(this.url()), fragment());
    }

    protected final String uniquify(String url) {
        return url + ((url.indexOf('?') == -1) ? "?" : "&") + rand.nextLong();
    }

    /**
     * Returns a new Call with the given fragment.
     *
     * @param fragment the URL fragment
     * @return a copy of this call that contains the fragment
     */
    public Call withFragment(String fragment) {
        return new play.api.mvc.Call(method(), url(), fragment);
    }

    /**
     * Returns the fragment (including the leading "#") if this call has one.
     *
     * @return the fragment, with leading "#"
     */
    protected String appendFragment() {
        if (this.fragment() != null && !this.fragment().trim().isEmpty()) {
            return "#" + this.fragment();
        } else {
            return "";
        }
    }

    /**
     * Transform this call to an absolute URL.
     *
     * @param request used to identify the host and protocol that should base this absolute URL
     * @return the absolute URL string
     */
    public String absoluteURL(Http.Request request) {
        return absoluteURL(request.secure(), request.host());
    }

    /**
     * Transform this call to an absolute URL.
     *
     * @param request used to identify the host that should base this absolute URL
     * @param secure true if the absolute URL should use HTTPS protocol
     * @return the absolute URL string
     */
    public String absoluteURL(Http.Request request, boolean secure) {
        return absoluteURL(secure, request.host());
    }

    /**
     * Transform this call to an absolute URL.
     *
     * @param secure true if the absolute URL should use HTTPS protocol instead of HTTP
     * @param host the absolute URL's domain
     * @return the absolute URL string
     */
    public String absoluteURL(boolean secure, String host) {
        return "http" + (secure ? "s" : "") + "://" + host + this.url() + this.appendFragment();
    }

    /**
     * Transform this call to an WebSocket URL.
     *
     * @param request used as the base for forming the WS url
     * @return the websocket url string
     */
    public String webSocketURL(Http.Request request) {
        return webSocketURL(request.secure(), request.host());
    }

    /**
     * Transform this call to an WebSocket URL.
     *
     * @param request used to identify the host for the absolute URL
     * @param secure true if it should be a wss rather than ws URL
     * @return the websocket URL string
     */
    public String webSocketURL(Http.Request request, boolean secure) {
      return webSocketURL(secure, request.host());
    }

    /**
     * Transform this call to an WebSocket URL.
     *
     * @param host the host for the absolute URL.
     * @param secure true if it should be a wss rather than ws URL
     * @return the url string
     */
    public String webSocketURL(boolean secure, String host) {
      return "ws" + (secure ? "s" : "") + "://" + host + this.url();
    }

    public String path() {
        return this.url() + this.appendFragment();
    }

    @Override
    public String toString() {
        return this.path();
    }

}
