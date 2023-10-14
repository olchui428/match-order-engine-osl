import _ from "lodash";
import axios from "axios";
// import { toast } from "react-toastify";

// import { getStore } from './app/store';

const headers = {
  // Accept: "application/json",
  // "Content-type": "application/json",
  "Content-Type": "text/plain",
};

export const BACK_SERVER_URL = "http://localhost:8000"; // For Develop

export async function call(_method, _endpoint, _data, _config) {
  try {
    let method = _.toString(_method).toLowerCase();
    let endpoint = _.toString(_endpoint);
    let data = _.clone(_data) || {};
    let config = _.clone(_config) || {};

    let resp = {};

    if (method === "get") {
      resp = await axios[method](
        `${BACK_SERVER_URL}${endpoint}${addParams(data)}`,
        { ...config, headers }
      );
    } else {
      resp = await axios[method](`${BACK_SERVER_URL}${endpoint}`, data, {
        ...config,
        headers,
      });
    }

    let respData = resp.data;
    let { status, errorCode, errorMessage, result } = respData;

    if (status < 0) {
      toast.error(`${errorCode}: ${errorMessage}`);
      console.error(`Service.call() Error :: ${errorCode} :: ${errorMessage}`);
      let errorObj = {
        status,
        errorCode,
        errorMessage,
      };
      return errorObj;
    }

    if (result) {
      return result;
    } else {
      return respData;
    }
  } catch (err) {
    console.error(err, "from service");
  }
  return null;
}

function addParams(data) {
  let queryStr = "";
  _.each(data, (val, key, idx) => {
    if (idx < Object.keys(data).length - 1) {
      queryStr += `${key}=${val}&`;
    } else {
      queryStr += `${key}=${val}`;
    }
  });
  return `?${queryStr}`;
}
