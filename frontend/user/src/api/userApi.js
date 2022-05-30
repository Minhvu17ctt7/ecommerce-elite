import React from 'react'
import axiosClient from './axiosClients'

const userApi = {
     getUserDetail: () => {
          const url = '/user';
          return axiosClient.get(url);
     },
     updateUserDetail: (data) => {
          const url = '/user/update';
          return axiosClient.put(url, data);
     },
     changePassword: (data) => {
          const url = '/user/change-password';
          return axiosClient.put(url, data);
     },
}

export default userApi;