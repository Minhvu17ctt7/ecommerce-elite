import React from 'react'
import axiosClient from './axiosClients'

const RoleService = {
    getAllRole: () => {
        const url = "/admin/roles/all";
        return axiosClient.get(url);
    }
}

export default RoleService;