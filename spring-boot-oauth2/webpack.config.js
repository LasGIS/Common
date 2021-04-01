//const path = require('path');
const BabelPluginTransformImports = require('babel-plugin-transform-imports');
//const autoprefixer = require('autoprefixer');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const CopyWebpackPlugin = require('copy-webpack-plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');

module.exports = {
  entry: './src/main/js/Index.jsx',
  devtool: 'sourcemaps',
  cache: true,
  mode: 'development',
  output: {
    path: __dirname,
    filename: './src/main/resources/static/built/bundle.js',
    publicPath: '/',
  },

  module: {
    rules: [
      {
        test: /\.(ts|js)x?$/,
        exclude: /node_modules/,
        use: {
          loader: 'babel-loader',
          options: {
            cacheDirectory: true,
            plugins: [
              [
                BabelPluginTransformImports,
                {
                  '@fortawesome/free-solid-svg-icons': {
                    transform: '@fortawesome/free-solid-svg-icons/${member}',
                    skipDefaultConversion: true,
                  },
                },
              ],
            ],
          },
        },
      },
      {
        test: /\.(js|jsx)$/,
        exclude: /node_modules/,
        loader: 'eslint-loader',
        options: {
          emitError: true,
        },
      },
      {
        test: /\.(ts|tsx)$/,
        exclude: /node_modules/,
        loader: 'ts-loader',
      },
      {
        test: /\.(ts|tsx)$/,
        exclude: /node_modules/,
        enforce: 'pre',
        loader: 'tslint-loader',
        options: {
          emitErrors: true,
        },
      },
      {
        test: /\.(scss)$/,
        use: [
          {
            loader: MiniCssExtractPlugin.loader,
          },
          'css-hot-loader',
          {
            loader: 'css-loader',
            options: {
              sourceMap: true,
              module: true,
              localIdentName: "[name]__[local]___[hash:base64:5]",
              alias: {'../img': '../public/img'},
            },
          },
          /*
                    {
                      loader: 'postcss-loader',
                      options: {
                        plugins: [
                          autoprefixer({browsers: ['ie >= 10', 'last 4 version']}),
                        ],
                      },
                    },
          */
          {
            loader: 'sass-loader',
          },
        ],
      },
      {
        test: /\.css$/,
        use: [
          {
            loader: MiniCssExtractPlugin.loader,
          },
          {
            loader: 'css-loader',
            options: {
              sourceMap: true,
            },
          },
        ],
      },
      {
        test: /\.(png|jpg|jpeg|gif|ico)$/,
        use: [
          {
            loader: 'file-loader',
            options: {
              esModule: false,
              name: './img/[name].[hash].[ext]',
            },
          },
        ],
      },
      {
        test: /\.(woff(2)?|ttf|eot|svg)(\?v=\d+\.\d+\.\d+)?$/,
        loader: 'file-loader',
        options: {
          esModule: false,
          name: './fonts/[name].[hash].[ext]',
        },
      },
    ],
  },
  plugins: [
    new MiniCssExtractPlugin({
      filename: 'css/[name].[hash].css',
    }),
    new HtmlWebpackPlugin({
      inject: true,
      filename: 'assets/admin.html'
    }),
    new CopyWebpackPlugin(
      [
        // {from: './public/img', to: 'img'},
        // {from: './public/favicon.ico', to: 'favicon.ico'},
      ],
      {
        copyUnmodified: false,
      },
    ),
  ],
  resolve: {
    extensions: ['.ts', '.tsx', '.js', '.jsx'],
  },
};
