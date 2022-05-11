import React from 'react'
import axiosClient from './axiosClients'

const AuthService = {
    async login(data) {
        const url = '/login';
        const res = await axiosClient.post(url, data);
        return res;
    },
    register(data) {
        const url = '/register';
        return axiosClient.post(url, data);
    }
}

export default AuthService