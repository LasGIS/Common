// vue.config.js
module.exports = {
  devServer: {
    host: 'localhost',
    port: 3002,
    // proxy all webpack dev-server requests starting with /api
    // to our Spring Boot backend (localhost:8098) using http-proxy-middleware
    // see https://cli.vuejs.org/config/#devserver-proxy
    proxy: {
      '/api': {
        // this configuration needs to correspond to the Spring Boot backends' application.properties server.port
        target: 'http://localhost:8098',
        ws: true,
        changeOrigin: true,
      },
    },
  },
  // Change build paths to make them Maven compatible
  // see https://cli.vuejs.org/config/
  outputDir: 'build',
  assetsDir: 'static',
};
