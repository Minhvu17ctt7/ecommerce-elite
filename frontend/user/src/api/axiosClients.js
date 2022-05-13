import axios from 'axios';
import queryString from 'query-string';

const BASE_URL = 'http://localhost:8080/api';

const axiosClient = axios.create({
	baseURL: BASE_URL,
	headers: {
		'content-type': 'application/json',
	},
	paramsSerializer: (params) => queryString.stringify(params),
});
axiosClient.interceptors.request.use(async (config) => {
	const token = localStorage.getItem('accessToken');
	if (token) {
		config.headers.Authorization = `Bearer ${token}`;
	}
	return config;
});
axiosClient.interceptors.response.use(
	(response) => {
		if (response && response.data) {
			return response.data;
		}
		return response.data;
	},
	(error) => {

		throw error?.response?.data;
	}
);
export default axiosClient;
