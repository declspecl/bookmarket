import path from "path";
import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

// https://vitejs.dev/config/
export default defineConfig({
  resolve: {
    alias: {
      "@": path.resolve(__dirname, "src"),
    },
  },
  plugins: [react()],
  server: {
    host: "0.0.0.0",
    proxy: {
      "/api": {
				target: "http://localhost:8080",
				changeOrigin: true,
				secure: false
			}
    }
  }
});
