import axios from 'axios';
import queryString from 'query-string';

const BASE_URL = 'http://localhost:8080/api/admin';

const axiosClient = axios.create({
	baseURL: BASE_URL,
	headers: {
		'content-type': 'application/json',
	},
	paramsSerializer: (params) => queryString.stringify(params),
});
axiosClient.interceptors.request.use(async (config) => {
	config.headers.Authorization = `Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJNaW5odnVAZ21haWwuY29tIiwiaWF0IjoxNjUyMTU1ODE5LCJleHAiOjE2NTI3NjA2MTl9.6DiDg1i2bhfCMybF6yrOl8Z5m5uRnuSzMuSRE7Ioc2baCiV504KJBspwt9IZQ6JUhY9HJRymu7qOtp5G2IiwiQ`;
	return config;
});
axiosClient.interceptors.response.use(
	(response) => {
		if (response && response.data) {
			return response.data;
		}
		return response;
	},
	(error) => {
		// Handle errors
		throw error?.response?.data;
	}
);
export default axiosClient;
