import React from 'react'
import axiosClient from './axiosClients'

const reviewApi = {
    createReview: (data) => {
        const url = '/reviews';
        return axiosClient.post(url, data);
    },
    getReviews: (productId, pageReview) => {
        const url = `/reviews/${productId}/${pageReview}`;
        return axiosClient.get(url);
    }
}

export default reviewApi;