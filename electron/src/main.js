import { app, BrowserWindow } from "electron";
import path from "path";
import { spawn } from "child_process";
import treeKill from "tree-kill";
import { fileURLToPath } from "url";

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

let isDev = process.env.APP_DEV ? process.env.APP_DEV.trim() == "true" : false;
let backendProcess;
const jarPath = jarFinder();

const createWindow = () => {
  const mainWindow = new BrowserWindow({
    width: 800,
    height: 600,
    webPreferences: {
      preload: path.join(__dirname, "preload.js"),
    },
  });
  mainWindow.loadFile("./resources/index.html");
  mainWindow.openDevTools();
};

app.whenReady().then(() => {
  startBackend();
  createWindow();

  app.on("activate", () => {
    if (BrowserWindow.getAllWindows().length === 0) createWindow();
  });
});

app.on("window-all-closed", () => {
  stopBackend();
  setTimeout(() => {
    if (process.platform !== "darwin") app.quit();
  }, 1000);
});

function startBackend() {
  const backendPath = path.resolve(__dirname, jarPath);
  backendProcess = spawn("javaw", ["-jar", backendPath]);
}

function stopBackend() {
  if (backendProcess) {
    treeKill(backendProcess.pid, "SIGTERM", (err) => {
      if (err) {
        console.error("Error stopping backend process:", err);
      } else {
        console.error("Backend process stopped successfully.");
      }
    });
  } else {
    console.error("No backend process running.");
  }
}

function jarFinder() {
  let pathh;
  if (isDev) {
    pathh = path.join(__dirname, "../resources", "Plus30Server.jar");
    console.log("Running in development");
  } else {
    pathh = path.join(__dirname, "../../../resources", "app.asar.unpacked/resources", "Plus30Server.jar");
  }
  return pathh;
}
