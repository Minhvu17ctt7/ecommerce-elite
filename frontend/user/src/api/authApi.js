import React from 'react'
import axiosClient from './axiosClients'

const authApi = {
    async login(data) {
        const url = '/login';
        const res = await axiosClient.post(url, data);
        const user = res.data;
        localStorage.setItem('user', JSON.stringify(user));
        localStorage.setItem('accessToken', res.data.accessToken);
        localStorage.setItem('refreshToken', res.data.refreshToken);
        localStorage.setItem('isLogin', 'true');
        return res;
    },
    register(data) {
        const url = '/register';
        return axiosClient.post(url, data);
    }
}

export default authApi