import axios from "axios";

const BASE_URL = "http://127.0.0.1:8080"; //implement a config file for the base url.

const axiosInstance = axios.create({ baseURL: BASE_URL });

export const get = async (path) => {
  try {
    const response = await axiosInstance.get(path);
    return response.data;
  } catch (error) {
    console.error("Error while fetching data: " + JSON.stringify(error));
  }
};

export const post = async (path, data = null) => {
  try {
    const response = await axiosInstance.post(path, data);
    return response.data;
  } catch (error) {
    if (error.response.data.status == 404) throw error;
    console.error("Error while posting data: " + JSON.stringify(error));
  }
};

export const put = async (path, data = null) => {
  try {
    const response = await axiosInstance.put(path, data);
    return response.data;
  } catch (error) {
    console.error("Error while putting data: " + JSON.stringify(error));
  }
};

export const remove = async (path) => {
  try {
    const response = await axiosInstance.delete(path);
    return response.data;
  } catch (error) {
    console.error("Error while removing data: " + JSON.stringify(error));
  }
};
