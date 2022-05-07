import React from 'react'
import axiosClient from './axiosClients'

const categoryApi = {
    getAllcategory: () => {
        const url = '/categories';
        return axiosClient.get(url);
    }
}

export default categoryApi;