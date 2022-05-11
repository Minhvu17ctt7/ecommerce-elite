import React from 'react'
import axiosClient from './axiosClients'

const UserService = {
    getAllUser: (deleted) => {
        const url = `/admin/users/all?deleted=${deleted}`;
        return axiosClient.get(url);
    },
    createUser: (user) => {
        const url = "/admin/users";
        return axiosClient.post(url, user);
    },
    updateUserBlocked: (updateUser) => {
        console.log("update user: ", updateUser)
        const url = "/admin/users/update-block-user";
        return axiosClient.put(url, updateUser);
    },
    deleteUser: (userId) => {
        const url = `/admin/users/${userId}`;
        return axiosClient.delete(url);
    }
}

export default UserService;