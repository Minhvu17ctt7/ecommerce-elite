import React from 'react'
import toastr from 'toastr';
import axiosClient from './axiosClients'

const productApi = {
    getAllProduct: () => {
        const url = '/products';
        toastr.success('Have fun storming the castle!', 'Miracle Max Says')
        return axiosClient.get(url);
    },
    getProductById: (id) => {
        const url = `/products/${id}`;
        return axiosClient.get(url);
    }
}

export default productApi;