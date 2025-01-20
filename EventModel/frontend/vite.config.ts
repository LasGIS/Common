import { defineConfig } from 'vite';
import svgr from '@svgr/rollup';
import react from '@vitejs/plugin-react';
import path from 'path';
import version from 'vite-plugin-package-version';

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    version(),
    react(),
    svgr({
      include: '**/*.svg',
    }),
  ],
  server: {
    port: 8090,
  },
  preview: {
    port: 8092,
  },
  define: {
    APP_VERSION: JSON.stringify(process.env.npm_package_version),
  },
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src/'),
      '@components': `${path.resolve(__dirname, './src/components/')}`,
      '@public': `${path.resolve(__dirname, './public/')}`,
      '@pages': path.resolve(__dirname, './src/pages'),
      '@types': `${path.resolve(__dirname, './src/@types')}`,
    },
  },
});
