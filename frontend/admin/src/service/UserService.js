import React from 'react'
import axiosClient from './axiosClients'

const UserService = {
    getAllUser: (deleted) => {
        const url = `/users/all?deleted=${deleted}`;
        return axiosClient.get(url);
    },
    createUser: (user) => {
        const url = "/users";
        return axiosClient.post(url, user);
    },
    updateUserBlocked: (updateUser) => {
        console.log("update user: ", updateUser)
        const url = "/users/update-block-user";
        return axiosClient.put(url, updateUser);
    },
    deleteUser: (userId) => {
        const url = `/users/${userId}`;
        return axiosClient.delete(url);
    }
}

export default UserService;