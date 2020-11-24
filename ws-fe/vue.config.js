module.exports = {
  devServer: {
    port: 8081,
    proxy: {
      "/people-app": {
        target: "http://localhost:8082",
        ws: true,
        changeOrigin: true
      },
      "/pets-app": {
        target: "http://localhost:8083",
        ws: true,
        changeOrigin: true
      }
    }
  }
};
