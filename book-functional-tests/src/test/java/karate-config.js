function fn() {
  karate.configure('connectTimeout', 5000);
  karate.configure('readTimeout', 5000);  
  var port = karate.properties['demo.server.port'] || '8080';
  var protocol = 'http';
  var config = { demoBaseUrl: protocol + '://127.0.0.1:' + port };
  
  return config;
}