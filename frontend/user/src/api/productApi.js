import React from 'react'
import axiosClient from './axiosClients'

const productApi = {
    getAllProduct: () => {
        const url = '/products';
        return axiosClient.get(url);
    },
    getProductById: (id) => {
        const url = `/products/${id}`;
        return axiosClient.get(url);
    }
}

export default productApi;