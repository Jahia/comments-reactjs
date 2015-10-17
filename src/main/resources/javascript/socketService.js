'use strict';
function SocketService() {
  var logged = false;
  var socket = $.atmosphere;
  var subSocket;
  var transport = 'sse';
  var request = {
    url: 'http://' + document.location.host.toString() + '/modules/atmosphere/comment',
    contentType: "application/json",
    logLevel: 'debug',
    shared: true,
    transport: transport,
    trackMessageLength: true,
    fallbackTransport: 'long-polling'
  };

  request.onOpen = function(response) {
    transport = response.transport;
  };

  request.onReconnect = function (request, response) {
    socket.info("Reconnecting");
  };

  request.onClose = function(response) {
    logged = false;
  };

  request.onError = function(response) {
  };

  this.getRequest = function() {
    return request;
  };

  this.buildSocket = function(onMessage) {
    request.onMessage = onMessage;
    subSocket = socket.subscribe(request);
  };

  this.getSocket = function() {
    return subSocket;
  };
}