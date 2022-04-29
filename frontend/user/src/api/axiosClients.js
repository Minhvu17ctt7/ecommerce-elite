import axios from 'axios';
import queryString from 'query-string';

const BASE_URL = 'https://localhost:8080/api';

const axiosClient = axios.create({
	baseURL: BASE_URL,
	headers: {
		'content-type': 'application/json',
	},
	paramsSerializer: (params) => queryString.stringify(params),
});
axiosClient.interceptors.request.use(async (config) => {
	// Handle token here ...
	return config;
});
axiosClient.interceptors.response.use(
	(response) => {
		if (response && response.data) {
			return response;
		}
		return response;
	},
	(error) => {
		// Handle errors
		throw error?.response?.data;
	}
);
export default axiosClient;
