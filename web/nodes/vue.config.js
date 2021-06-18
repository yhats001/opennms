const ImportMapWebpackPlugin = require('webpack-import-map-plugin');
const importMap = new ImportMapWebpackPlugin({
    filter: x => {
        return ['app.js'].includes(x.name);
    },
    transformKeys: filename => {
        if (filename === 'app.js') {
            return '@opennms/nodes';
        }
    },
    fileName: 'import-map.json',
    baseUrl: 'assets/ui/nodes/'
});

module.exports = {
  configureWebpack: {
    plugins: [
        importMap
    ]
  }
}
