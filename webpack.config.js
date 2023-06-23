const path = require('path');
const MiniCssExtractPlugin = require("mini-css-extract-plugin");
const CssMinimizerPlugin = require('css-minimizer-webpack-plugin');
const SimpleProgressWebpackPlugin = require('simple-progress-webpack-plugin');
const TerserPlugin = require("terser-webpack-plugin");

const isDebug = process.env.npm_lifecycle_event !== 'build_PROD';

const PATHS = {
    source: path.join(__dirname, 'src/main/webapp/www/src'),
    build: path.join(__dirname, 'src/main/webapp/www/build')
};

const withMT = require("@material-tailwind/react/utils/withMT");

module.exports = {
    watch: true,
    entry: {
        app: path.join(PATHS.source, '/App.tsx')
    },
    output: {
        path: path.join(PATHS.build, '/'),
        filename: '[name]/[name].bundle.js',
        assetModuleFilename: '../assets/[name].[ext]?[hash]'
    },
    resolve: {
        extensions: ['.ts', '.tsx', '.js', '.jsx']
    },
    module: {
        rules: [
            {
                test: /\.(js|jsx|ts|tsx)$/,
                exclude: /(node_modules)/,
                use: {
                    loader: 'babel-loader',
                    options: {
                        presets: ['@babel/preset-env', '@babel/preset-react', '@babel/preset-typescript'],
                        plugins: [
                            ["@babel/plugin-proposal-decorators", {legacy: true}],
                            ["@babel/plugin-proposal-class-properties"],
                            ["@babel/plugin-syntax-class-properties"],
                            "@babel/plugin-transform-runtime",
                        ]
                    }
                }
            },
            {
                test: /\.css$/,
                use: [MiniCssExtractPlugin.loader, "css-loader", "postcss-loader"]
            },
            {
                test: /\.less$/,
                use: [MiniCssExtractPlugin.loader, "css-loader", "postcss-loader", "less-loader"]
            },
            {
                test: /\.(png|jpg|gif|woff|woff2|eot|ttf|otf|svg)$/,
                type: 'asset/resource'
            }
        ]
    },
    plugins: [
        new MiniCssExtractPlugin({
            filename: "[name]/[name].css"
        }),
        new SimpleProgressWebpackPlugin({
            format: process.stderr.isTTY ? 'compact' : 'simple'
        })
    ],
    optimization: {
        minimize: !isDebug,
        minimizer: [
            new TerserPlugin({
                minify: TerserPlugin.terserMinify,
                parallel: true,
                extractComments: false,
            }),
            new CssMinimizerPlugin({
                minify: CssMinimizerPlugin.parcelCssMinify,
                parallel: true
            })
        ],
    },
    stats: {
        assets: false,
        builtAt: true,
        chunks: false,
        entrypoints: false,
        colors: true,
        errors: true,
        errorDetails: true,
        modules: false,
        moduleTrace: true,
        performance: true,
        timings: true,
        hash: false,
        version: false,
        warnings: true,
    },
    devtool: isDebug ? "eval-source-map" : false,
    cache: isDebug,
};