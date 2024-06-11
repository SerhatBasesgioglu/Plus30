import axios from "axios";

const BASE_URL = "http://127.0.0.1:8080"; //implement a config file for the base url.

const axiosInstance = axios.create({ baseURL: BASE_URL });

export const get = async (path) => {
  return execute("get", path);
};

export const post = async (path, data = null) => {
  return execute("post", path, data);
};

export const put = async (path, data = null) => {
  return execute("put", path, data);
};

export const remove = async (path) => {
  return execute("delete", path);
};

const execute = async (method, path, data = null) => {
  try {
    let response;
    switch (method) {
      case "get":
        response = await axiosInstance.get(path);
        break;
      case "post":
        response = await axiosInstance.post(path, data);
        break;
      case "delete":
        response = await axiosInstance.delete(path);
        break;
      case "put":
        response = await axiosInstance.put(path, data);
        break;
    }
    return response.data;
  } catch (error) {
    let statusCode = error.response.data.status;
    let method = error.config.method;
    if (method === "post" && (statusCode === 404 || statusCode === 530)) throw error; //lobby is not available anymore
    if (method === "post" && statusCode === 403) throw error; //lobby has password
    if (method === "post" && statusCode === 432) throw error; //lobby is full

    console.error("Request error", JSON.stringify(error.response));
  }
};
