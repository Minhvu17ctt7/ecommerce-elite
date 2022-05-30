import React from 'react'
import axiosClient from './axiosClients'

const userApi = {
     getUserDetail: () => {
          const url = '/user';
          return axiosClient.get(url);
     }
}

export default userApi;