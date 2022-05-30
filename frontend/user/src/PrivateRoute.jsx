import React from 'react';
import { Navigate } from 'react-router-dom';

const PrivateRoute = ({ children }) => {
    let auth = localStorage.getItem('isLogin') === 'true';
    if (!auth) {
        return <Navigate to="/login" />;
    }
    return children;
}


export default PrivateRoute