import React from 'react'
import axiosClient from './axiosClients'

const authApi = {
    async login(data) {
        const url = '/login';
        const res = await axiosClient.post(url, data);
        localStorage.setItem('user', JSON.stringify(res));
        localStorage.setItem('accessToken', res.accessToken);
        localStorage.setItem('refreshToken', res.refreshToken);
        localStorage.setItem('isLogin', 'true');
        return res;
    },
    register(data) {
        const url = '/register';
        return axiosClient.post(url, data);
    }
}

export default authApi