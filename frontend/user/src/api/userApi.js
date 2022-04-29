import React from 'react'
import axiosClient from './axiosClients'

const userApi = {
    login(data) {
        const url = '/login'
        return axiosClient.post(url, data);
    }
}

export default userApi