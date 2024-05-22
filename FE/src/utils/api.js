import axios from "axios";
const api = axios.create({
  baseURL: `http://localhost:8080/`,
  withCredentials: true, // credentials 옵션을 true로 설정하여 인증 정보를 포함
  headers: {
    "Content-Type": "application/json",
    "Authorization": "Bearer ",
  },
});


/**
 * console.log all requests and responses
 */
api.interceptors.request.use(
  (request) => {
    console.log("Starting Request", request);
    return request;
  },
  function (error) {
    console.log("REQUEST ERROR", error);
  }
);

api.interceptors.response.use(
  (response) => {
    console.log("Response:", response);
    return response;
  },
  function (error) {
    error = error.response.data;
    console.log("RESPONSE ERROR", error);
    return Promise.reject(error);
  }
);

export default api;