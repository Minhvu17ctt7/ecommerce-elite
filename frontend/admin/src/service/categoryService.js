import React from 'react'
import axiosClient from './axiosClients'

const categoryApi = {
    getAllCategory: (deleted) => {
        const url = `/categories/all?deleted=${deleted}`;
        return axiosClient.get(url);
    },
    createCategory: (category) => {
        const url = "/categories";
        return axiosClient.post(url, category);
    },
    updateCategories: (category) => {
        const url = "/categories";
        return axiosClient.put(url, category);
    },
    deleteCategory: (categoryId) => {
        const url = `/categories/${categoryId}`;
        return axiosClient.delete(url);
    },
    checkAvailableDeleteCategory: (categoryId) => {
        const url = `/categories/${categoryId}/check-available-delete`;
        return axiosClient.get(url);
    }
}

export default categoryApi;