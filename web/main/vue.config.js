const ImportMapWebpackPlugin = require('webpack-import-map-plugin');
const importMap = new ImportMapWebpackPlugin({
    filter: x => {
        return ['app.js'].includes(x.name);
    },
    transformKeys: filename => {
        if (filename === 'app.js') {
            return '@opennms/main';
        }
    },
    fileName: 'import-map.json',
    baseUrl: 'assets/ui/main/'
});

module.exports = {
  configureWebpack: {
    plugins: [
        importMap
    ]
  }
}
